package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.service.AdresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/adrese")
@RequiredArgsConstructor
public class AdresaController {

    private final AdresaService adresaService;

    @PostMapping("/{sifMjesto}")
    public Adresa createAdresa(@RequestBody Adresa adresa, @PathVariable Long sifMjesto) {
        return adresaService.createAdresa(adresa, sifMjesto);
    }


    @GetMapping("/{koordinate}")
    public Adresa getAdresaById(@PathVariable String koordinate) {
        return adresaService.getAdresaById(koordinate);
    }

    @GetMapping
    public Iterable<Adresa> getAllAdrese() {
        return adresaService.getAllAdrese();
    }

}
