package fer.leprogi.dvoranko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping({
        "/",
        "/{path:[^\\.]*}",
        "/{path:[^\\.]*}/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}