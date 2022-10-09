package com.example.charging_life.station;

import com.example.charging_life.member.entity.Member;
import com.example.charging_life.member.entity.MemberChargingStation;
import com.example.charging_life.station.dto.ChargingStationDto;
import com.example.charging_life.station.dto.StationResDto;
import com.example.charging_life.station.entity.*;
import com.example.charging_life.station.repository.*;
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
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = false)
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
    //Station Analysis Repository
    private final JpaStationAnalysisRepository jpaStationAnalysisRepository;
    //Business Repository
    private final JpaBusinessRepository jpaBusinessRepository;
    //Zcode Repository
    private final JpaZcodeRepository jpaZcodeRepository;


    //change the ParkingFree type to boolean
    public Integer checkParkingFree(String parkingFree){
        if (parkingFree.equals("Y")) {
            return 1;
        }
        return 0;
    }

    //change the LimitYn type to boolean
    public Integer checkLimitYn(String limitYn){
        if (limitYn.equals("Y")) {
            return 1;
        }
        return 0;
    }

    //save the data to Business
    @Transactional
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
        //jpaBusinessRepository.save(businessEntity);

        Business findBusiness = jpaBusinessRepository.findByBusinessId(businessId);
        //System.out.println(findBusiness);

        if (findBusiness == null) jpaBusinessRepository.saveAndFlush(businessEntity);
    }

    //save the count of station
    public void saveStationAnalysis(JSONObject jsonObj){
        Object totalResponse = jsonObj.get("totalCount");
        Integer countStatation = Integer.parseInt(String.valueOf(totalResponse));
        String creationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
//        Integer countStatation = Integer.getInteger(String.valueOf(jsonObj.get("totalCount")));
//        System.out.println(countStatation);
        StationAnalysis stationAnalysis = StationAnalysis.builder()
                .countStatation(countStatation)
                .creationDateTime(creationDateTime)
                .build();
        jpaStationAnalysisRepository.save(stationAnalysis);
    }

    //Process for verifying that output, statUpdDt, lastTsdt, lastTedt, nowTsdt data exists.
    public Long verify(JSONObject jsonObject, String check) {
        Boolean nullCheck = jsonObject.get(check).equals("");
        if (nullCheck) {
            return null;
        }
        else {
            Long result = Long.valueOf((String) jsonObject.get(check));
            return result;
        }
    }

    //save the data to charger
    @Transactional
    public void saveCharger(JSONObject jsonObject, ChargingStation chargingStation){


        Integer chargerId = Integer.valueOf((String) jsonObject.get("chgerId"));
        String chargerType = (String) jsonObject.get("chgerType");
        Integer output = Math.toIntExact(verify(jsonObject, "output"));
        Integer stat = Integer.valueOf((String) jsonObject.get("stat"));
        Long statUpdDt = verify(jsonObject, "statUpdDt");
        Long lastTsdt = verify(jsonObject, "lastTsdt");
        Long lastTedt = verify(jsonObject, "lastTedt");
        Long nowTsdt = verify(jsonObject, "nowTsdt");

//        System.out.println(output +"/"+ stat +"/"+ statUpdDt);
        Charger charger = Charger.builder()
                .chargingStation(chargingStation)
                .chargerId(chargerId)
                .chargerType(chargerType)
                .output(output)
                .stat(stat)
                .statUpdDt(statUpdDt)
                .lastTsdt(lastTsdt)
                .lastTedt(lastTedt)
                .nowTsdt(nowTsdt)
                .build();
        jpaChargerRepository.save(charger);
        //System.out.println(charger.getChargingStation().getId().toString() + " " + charger.getId().toString());
    }

    //save the data to ChargingStation
    @Transactional
    public void saveChargingStation(JSONObject jsonObject) {
        String statNm = (String) jsonObject.get("statNm");
        String statId = (String) jsonObject.get("statId");
        //System.out.println(statId);
        String address = (String) jsonObject.get("addr");
        String location = (String) jsonObject.get("location");
        Double lat = Double.parseDouble((String) jsonObject.get("lat"));
        Double lng = Double.parseDouble((String) jsonObject.get("lng"));
        String useTime = (String) jsonObject.get("useTime");
        Integer parkingFree = checkParkingFree((String) jsonObject.get("parkingFree"));
        String note = (String) jsonObject.get("note");
        Integer limitYn = checkLimitYn((String) jsonObject.get("limitYn"));
        String limitDetail = (String) jsonObject.get("limitDetail");
        String businessId = (String) jsonObject.get("busiId");
        Long zcodeId = Long.parseLong((String) jsonObject.get("zcode"));
//        System.out.println(businessId + "/ " + zcodeId);

        Business byBusinessId = jpaBusinessRepository.findByBusinessId(businessId);
        Zcode referenceById = jpaZcodeRepository.findByZcode(zcodeId);
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
                .business(byBusinessId)
                .zcode(referenceById)
                .build();

        ChargingStation findChargingStation = jpaStationRepository.findByStatId(statId);
        //System.out.println(findchargingStation);

        //if the data isn't exist in database then stored in both ChargingStation and Charger
        if (findChargingStation == null) {
            ChargingStation saveChargingStation = jpaStationRepository.save(chargingStation);
            //save the data to charger
            saveCharger(jsonObject, saveChargingStation);
        }
        //if the data is exist in database then stored in only Charger
        else {
            //save the data to charger
            saveCharger(jsonObject, findChargingStation);
        }
    }

    // If we have to update the data, then can start at last point which is the end of the previous data
    public Integer page(Boolean isnew) {
        if (isnew) {
            Integer start = findStationAnalysis();
            return ((start/1000)+1);
        } else {
            return 1;
        }
    }


    // Main function for storing data in Business, Charger, and ChargingStaion
    @Transactional
    public void saveChargingStationData(Boolean isBusiness, Boolean isnew) throws IOException, ParseException {
            // count the page
            int i = page(isnew);
            // To break this function on the last page
            boolean flag = true;

            while (flag){
                long startTime = System.currentTimeMillis(); // start time
                //used to create a mutable
                StringBuilder result = new StringBuilder();

                //openApi address
                String apiUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?" +
                        "serviceKey=" + key +
                        "&numOfRows=1000" +
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

                urlConnection.disconnect();

                //extract the data which we need
                JSONObject jsonObject;
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());

                //System.out.println(jsonObj.toJSONString());
                JSONObject parseResponse = (JSONObject) jsonObj.get("items");
                Object totalResponse = jsonObj.get("totalCount");
                Integer countStatation = Integer.parseInt(String.valueOf(totalResponse));
                if (i == countStatation) {
                    saveStationAnalysis(jsonObj);
                }
//                System.out.println(totalResponse);
                //JSONObject parseBody = (JSONObject) parseResponse.get("item");

                JSONArray array = (JSONArray) parseResponse.get("item");
                //System.out.println(array.toJSONString());
                //pick the data from array
                for (Object chargingStationJson : array) {

                    jsonObject = (JSONObject) chargingStationJson;
//                    System.out.println(jsonObject);

                    if (isBusiness) saveBusiness(jsonObject); //save the data to business
                    else saveChargingStation(jsonObject); //save the data to charging station & charger

                }
                System.out.println("success: " + i);
                long endTime = System.currentTimeMillis(); // end time
                long secDiffTime = (endTime - startTime)/1000; // total time
                System.out.println("The time of " + i +" page : "+secDiffTime + " (μs)");
                if (i < ((countStatation / 1000)+1)) {
                    i++; // if there is still a page left, increase it
                } else {
                    flag = false; // if the page number is the same as last page number, then stop the loop
                }
            }
    }

    // Update function for updating data in  Charger
    @Transactional
    public void updateChargerData() throws IOException, ParseException {
        int i = 1;
        boolean flag = true;

        while (flag) {
            //used to create a mutable
            StringBuilder result = new StringBuilder();

            //openApi address
            String StatusApiUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerStatus?" +
                    "serviceKey=" + key +
                    "&numOfRows=1000" +
                    "&pageNo=" + i +
                    "&period=5" +
                    "&dataType=JSON";
            URL url = new URL(StatusApiUrl);

            //connection with api
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine + "\n\r");
            }

            urlConnection.disconnect();

            //extract the data which we need
            JSONObject jsonObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());

            JSONObject parseResponse = (JSONObject) jsonObj.get("items");
            Object totalResponse = jsonObj.get("totalCount");
            Integer countCharger = Integer.parseInt(String.valueOf(totalResponse));

            JSONArray array = (JSONArray) parseResponse.get("item");

            //pick the data from array
            for (Object chargerJson : array) {
                jsonObject = (JSONObject) chargerJson;
//                System.out.println(jsonObject);
                String statId = (String) jsonObject.get("statId");
//                System.out.println(statId);
                Long findId = jpaStationRepository.findByStatId(statId).getId();
//                System.out.println("findId"+findId);
                if (findId == null) {
                    System.out.println(statId);
                }
                else {
                    Integer chargerId = Integer.valueOf((String) jsonObject.get("chgerId"));
                    Long findcharger = jpaChargerRepository.findTop1ByChargingStation_IdAndChargerId(findId, chargerId).getId();
                    if (findcharger != null) {
//                Long findcharger = jpaChargerRepository.findByChargingStationId(findStation(statId).getId()).getId();
                        Integer stat = Integer.valueOf((String) jsonObject.get("stat"));

                        Long statUpdDt = verify(jsonObject, "statUpdDt");
                        Long lastTsdt = verify(jsonObject, "lastTsdt");
                        Long lastTedt = verify(jsonObject, "lastTedt");
                        Long nowTsdt = verify(jsonObject, "nowTsdt");

                        jpaChargerRepository.updateStat(stat, findcharger);
                        jpaChargerRepository.updateStatUpdDt(statUpdDt, findcharger);
                        jpaChargerRepository.updateLastTsdt(lastTsdt, findcharger);
                        jpaChargerRepository.updateLastTedt(lastTedt, findcharger);
                        jpaChargerRepository.updateNowTsdt(nowTsdt, findcharger);
                    }
                    else{
                        continue;
                    }
                }
            }
            System.out.println("success: " + 1);

            if (i < ((countCharger / 1000) + 1)) {
                i++; // if there is still a page left, increase it
            } else {
                flag = false; // if the page number is the same as last page number, then stop the loop
            }
        }
    }

    // It is a function to know how much data there was before
    public Integer findStationAnalysis() {
        Integer count = jpaStationAnalysisRepository.findByOrderByIdDesc().getCountStatation();
        return count;
    }


    // It is the station information function that comes out when we look for the station ID
    public ChargingStation findStation(String statId) {
        ChargingStation chargingStation = jpaStationRepository.findByStatId(statId);
        return chargingStation;
    }

    //It is a function that shows station information by customizing it differently for each member
    public List<ChargingStationDto> findStationByManager(Member member) {
        List<ChargingStationDto> stationResDtos = new ArrayList<>();
        for (MemberChargingStation memberStation : member.getMemberChargingStations()) {
            ChargingStation chargingStation = memberStation.getChargingStation();
            stationResDtos.add(new ChargingStationDto(chargingStation));
        }
        return stationResDtos;
    }

    // It is the station information function that comes out when we look for the station name
    public List<StationResDto> findStationByStatNm(String statNm) {
        List<ChargingStation> stationsByStatNm = jpaStationRepository.findByStatNmContaining(statNm);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stationsByStatNm) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;
    }


    public List<StationResDto> findStationByCity(String city) {
        Long cityId = jpaZcodeRepository.findByCity(city).getId();
        List<ChargingStation> stationsByCity = jpaStationRepository.findByZcode_Id(cityId);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stationsByCity) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;
    }

    public List<StationResDto> findStationByBusiness(String business) {
        Long businessId = jpaBusinessRepository.findByBusiness(business).getId();
        List<ChargingStation> stationsByBusiness = jpaStationRepository.findByBusiness_Id(businessId);
        List<StationResDto> stationResDtos = new ArrayList<>();
        for (ChargingStation chargingStation : stationsByBusiness) {
            stationResDtos.add(new StationResDto(chargingStation));
        }
        return stationResDtos;

    }

}
