package com.krisczar.neptun.SupportServices;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Connections {



    public static void deleteUser(long id){
        final String URL = "https://neptun-FCM.herokuapp.com/admin/api/users/{id}";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL, id);
    }

    public static String getUsersIDs(){
        final String URL = "https://neptun-FCM.herokuapp.com/admin/api/users/id/all";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL, String.class);

        Gson g = new Gson();
        Map<String, Integer> son = new Gson().fromJson(response, HashMap.class);

        response = response.replaceAll("\\{", "");
        response = response.replaceAll("}", "");
        response = response.replaceAll(",", "\n");

        System.out.println(response);
//        System.out.println(resultsStr);

        return response;
    }
}
