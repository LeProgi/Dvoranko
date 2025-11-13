package fer.leprogi.dvoranko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping({
        "/",                        // root
        "/{path:^(?!api$|assets$)[^\\.]*}$",          // /maps, /profile
        "/{path:^(?!api$|assets$)[^\\.]*}/**"        // /maps/settings
    })
    public String forward() {
        return "forward:/index.html";
    }
}