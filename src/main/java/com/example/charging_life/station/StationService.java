package com.example.charging_life.station;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.Business;
import com.example.charging_life.station.entity.Charger;
import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.JpaBusinessRepository;
import com.example.charging_life.station.repository.JpaChargerRepository;
import com.example.charging_life.station.repository.JpaStationRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StationService{

    //call the API serviceKey
    @Value("${key.serviceKey}")
    private String key;

    //Charging Station Repository
    private final JpaStationRepository jpaStationRepository;
    //Charger Repository
    private final JpaChargerRepository jpaChargerRepository;
    //Business Repository
    private final JpaBusinessRepository jpaBusinessRepository;

    //change the ParkingFree type to boolean
    public boolean checkParkingFree(String parkingFree){
        if (parkingFree == "Y") {
            return true;
        }
        return true;
    }

    //change the LimitYn type to boolean
    public boolean checkLimitYn(String limitYn){
        if (limitYn == "Y") {
            return true;
        }
        return true;
    }

    public void saveBusiness(JSONObject jsonObject) {
        String businessId = (String) jsonObject.get("busiId");
        String business = (String) jsonObject.get("bnm");
        String operator = (String) jsonObject.get("busiNm");
        String businessCall = (String) jsonObject.get("busiCall");

        Business businessEntity = Business.builder()
                .businessId(businessId)
                .business(business)
                .operator(operator)
                .businessCall(businessCall)
                .build();
        jpaBusinessRepository.save(businessEntity);

        List<Business> findBusiness = jpaBusinessRepository.findByOperator(operator);
        //System.out.println(findBusiness);

        if (findBusiness.isEmpty()) jpaBusinessRepository.save(businessEntity);
    }

    //save the data to charger
    public void saveCharger(JSONObject jsonObject, ChargingStation chargingStation){
        Integer chargerId = Integer.valueOf((String) jsonObject.get("chgerId"));
        String chargerType = (String) jsonObject.get("chgerType");
        Integer outPut = Integer.getInteger((String) jsonObject.get("outPut"));

        Charger charger = Charger.builder()
                .chargingStation(chargingStation)
                .chargerId(chargerId)
                .chargerType(chargerType)
                .outPut(outPut)
                .build();
        jpaChargerRepository.save(charger);
        //System.out.println(charger.getChargingStation().getId().toString() + " " + charger.getId().toString());
    };

    public void saveChargingStation(JSONObject jsonObject) {
        String statNm = (String) jsonObject.get("statNm");
        String statId = (String) jsonObject.get("statId");
        //System.out.println(statId);
        String address = (String) jsonObject.get("addr");
        String location = (String) jsonObject.get("location");
        Double lat = Double.parseDouble((String) jsonObject.get("lat"));
        Double lng = Double.parseDouble((String) jsonObject.get("lng"));
        String useTime = (String) jsonObject.get("useTime");
        Boolean parkingFree = checkParkingFree((String) jsonObject.get("parkingFree"));
        String note = (String) jsonObject.get("note");
        Boolean limitYn = checkLimitYn((String) jsonObject.get("limitYn"));
        String limitDetail = (String) jsonObject.get("limitDetail");

        ChargingStation chargingStation = ChargingStation.builder()
                .statNm(statNm)
                .statId(statId)
                .address(address)
                .location(location)
                .lat(lat)
                .lng(lng)
                .useTime(useTime)
                .parkingFree(parkingFree)
                .note(note)
                .limitYn(limitYn)
                .limitDetail(limitDetail)
                .build();

        ChargingStation findChargingStation = jpaStationRepository.findByStatId(statId);
        //System.out.println(findchargingStation);

        if (findChargingStation == null) {
            ChargingStation saveChargingStation = jpaStationRepository.save(chargingStation);
            //save the data to charger
            saveCharger(jsonObject, saveChargingStation);
        } else {
            //save the data to charger
            saveCharger(jsonObject, findChargingStation);
        }
    }



    @Transactional
    public void saveChargingStationData(Boolean isBusiness) {
        for (int i = 1; i < 14; i++) {
            try {
                //used to create a mutable
                StringBuilder result = new StringBuilder();

                //openApi address
                String apiUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?" +
                        "serviceKey=" + key +
                        "&numOfRows=9999" +
                        "&pageNo=" + i +
                        "&dataType=JSON";
                URL url = new URL(apiUrl);

                //connection with api
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader br;

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine + "\n\r");
                }

                //urlConnection.disconnect();

                //extract the data which we need
                JSONObject jsonObject;
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());
                JSONObject parseResponse = (JSONObject) jsonObj.get("items");
                // System.out.println(parseResponse.toJSONString());
                //JSONObject parseBody = (JSONObject) parseResponse.get("item");

                JSONArray array = (JSONArray) parseResponse.get("item");
                //System.out.println(array.toJSONString());

                //pick the data from array
                for (Object chargingStationJson : array) {

                    jsonObject = (JSONObject) chargingStationJson;
                    //System.out.println(jsonObject);

                    if (isBusiness) saveBusiness(jsonObject); //save the data to business
                    else saveChargingStation(jsonObject); //save the data to charging station & charger

                }

            } catch (ParseException | MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("page : " + i);
        }
    }

    public ChargingStation findStation(String statId) {
        ChargingStation chargingStation = jpaStationRepository.findByStatId(statId);
        return chargingStation;
    }

    public List<StationResDto> findStationByStatNm(String statNm) {
        List<ChargingStation> stations = jpaStationRepository.findByStatNmContaining(statNm);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stations) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;
    }

    public List<ChargingStationDto> findStationByManager(Member member) {
        List<ChargingStationDto> stationResDtos = new ArrayList<>();
        for (MemberChargingStation memberStation : member.getMemberChargingStations()) {
            ChargingStation chargingStation = memberStation.getChargingStation();
            stationResDtos.add(new ChargingStationDto(chargingStation));
        }
        return stationResDtos;
    }
}
