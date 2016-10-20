package auth.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class MainController extends WebMvcConfigurerAdapter {


    @RequestMapping("/")
    public String index() {
        return "redirect:/form";
    }
}