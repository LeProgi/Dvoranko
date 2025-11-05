package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.service.DvoranaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/dvorane")
@RequiredArgsConstructor
public class DvoranaController {

    private final DvoranaService dvoranaService;

    @PostMapping("/{koordinate}")
    public Dvorana createDvorana(@RequestBody Dvorana dvorana, @PathVariable String koordinate){
        return dvoranaService.createDvorana(dvorana, koordinate);
    }
}
