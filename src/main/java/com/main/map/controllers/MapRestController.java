package com.main.map.controllers;

import com.main.map.models.AreaInformation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
public class MapRestController {
    @PostMapping(value = "/get_query")
    public void PostAreaInformation(@RequestBody String json) {
        System.out.println("Json:"+json);
        /*JSONObject outputJsonObj = new JSONObject();
        outputJsonObj.put("output", output);

        return outputJsonObj.toString();*/
    }
}
