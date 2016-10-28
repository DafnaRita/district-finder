package com.main.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class MapController extends WebMvcConfigurerAdapter {

    @GetMapping("/map")
    public String showMap() {
        return "/map";
    }
}