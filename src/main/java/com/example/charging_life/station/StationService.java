package com.example.charging_life.station;

import com.example.charging_life.station.entity.ChargingStation;
import com.example.charging_life.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public boolean checkParkingFree(String parkingFree){
        if (parkingFree == "Y") {
            return true;
        }
        return true;
    }

    public boolean checkLimitYn(String limitYn){
        if (limitYn == "Y") {
            return true;
        }
        return true;
    }

    @Transactional
    public void init(String jsonData){
        System.out.println(jsonData);
        try{
            JSONObject jsonObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseResponse = (JSONObject) jsonObj.get("items");
            System.out.println(parseResponse.toJSONString());
            //JSONObject parseBody = (JSONObject) parseResponse.get("item");

            JSONArray array = (JSONArray) parseResponse.get("item");
            System.out.println(array.toJSONString());


            for (Object o : array) {
                jsonObject = (JSONObject) o;
                System.out.println(jsonObject);
                String statNm = (String) jsonObject.get("statNm");
                System.out.println(statNm);
                String statId =  (String) jsonObject.get("statId");
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
                stationRepository.save(chargingStation);
                System.out.println(statNm);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
