package main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class MainController extends WebMvcConfigurerAdapter {

    @GetMapping("/")
    public String redirectMap() {
        return "redirect:/map";
    }
}