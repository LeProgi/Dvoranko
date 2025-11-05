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

    @PostMapping("/{idAdresa}")
    public Dvorana createDvorana(@RequestBody Dvorana dvorana, @PathVariable Long idAdresa){
        return dvoranaService.createDvorana(dvorana, idAdresa);
    }

    @GetMapping("/{idDvorana}")
    public Dvorana getDvoranaById(@PathVariable Long idDvorana){
        return dvoranaService.getDvoranaById(idDvorana);
    }

    @GetMapping
    public Iterable<Dvorana> getAllDvorane(){
        return dvoranaService.getAllDvorane();
    }
}
