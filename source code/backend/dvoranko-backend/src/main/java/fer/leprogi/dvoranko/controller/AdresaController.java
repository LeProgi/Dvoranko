package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.service.AdresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/adresa")
@RequiredArgsConstructor
public class AdresaController {

    private final AdresaService adresaService;

    @GetMapping
    public Iterable<Adresa> getAllAdrese() {
        return adresaService.getAllAdrese();
    }

    @GetMapping("/{koordinate}")
    public Adresa getAdresaById(@PathVariable String koordinate) {
        return adresaService.getAdresaById(koordinate);
    }

    @PostMapping("/mjesto/{sifMjesto}")
    public Adresa createAdresa(@RequestBody Adresa adresa, @PathVariable Long sifMjesto) {
        return adresaService.createAdresa(adresa, sifMjesto);
    }

}
