package com.example.charging_life.station;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class StationController {
    @Value ("${key.serviceKey}")
    private String key;

    private final StationService stationService;

    /*@GetMapping("/station")
    public String index(){
        return "index";
    }*/

    @GetMapping("/station")
    public String chargingStationApi() throws IOException {
        StringBuilder result = new StringBuilder();

            String apiUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?" +
                "serviceKey=" + key +
                "&numOfRows=10" +
                "&pageNo=9999" +
                "&dataType=JSON";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null){
                result.append(returnLine + "\n\r");
            }

             urlConnection.disconnect();

            stationService.init(result.toString());

        return result.toString();
    }
}
