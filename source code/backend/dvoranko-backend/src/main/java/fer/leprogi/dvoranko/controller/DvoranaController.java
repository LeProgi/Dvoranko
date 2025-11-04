package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.service.DvoranaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dvorana")
public class DvoranaController {

    @Autowired
    DvoranaService dvoranaService;

    @GetMapping
    public Iterable<Dvorana> getAllDvorane() {
        return dvoranaService.getAllDvorane();
    }
}
