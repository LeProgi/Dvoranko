package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.service.MjestoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/public/mjesta")
@RequiredArgsConstructor
public class MjestoController {

    private final MjestoService mjestoService;

    @PostMapping
    public Mjesto createMjesto(@Valid @RequestBody Mjesto mjesto){
        return mjestoService.createMjesto(mjesto);
    }

    @GetMapping("/{id}")
    public Mjesto getMjestoById(@PathVariable Long id){
        return mjestoService.getMjestoById(id);
    }

    @GetMapping
    public Iterable<Mjesto> getAllMjesta(){
        return mjestoService.getAllMjesta();
    }
}
