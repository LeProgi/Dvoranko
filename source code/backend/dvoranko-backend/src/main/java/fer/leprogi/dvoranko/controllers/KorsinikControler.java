package fer.leprogi.dvoranko.controllers;

import fer.leprogi.dvoranko.domain.dto.KorisnikDto;
import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;
import fer.leprogi.dvoranko.mappers.Mapper;
import fer.leprogi.dvoranko.services.KorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
public class KorsinikControler {
    private Mapper <KorisnikEnitity, KorisnikDto> korisnikMapper;
    private KorisnikService korisnikService;

    public KorsinikControler(KorisnikService korisnikService, Mapper<KorisnikEnitity, KorisnikDto> korisnikMapper) {
        this.korisnikService = korisnikService;
        this.korisnikMapper = korisnikMapper;
    }

    @PostMapping(path = "/users")
    public KorisnikDto createKorisnik(@RequestBody KorisnikDto korisnik){
        KorisnikEnitity korisnikEnitity = korisnikMapper.mapFrom(korisnik);
        KorisnikEnitity savedKorisnikEnitity = korisnikService.createKorisnik(korisnikEnitity);
        return korisnikMapper.mapTo(savedKorisnikEnitity);
    }
    /*
    @GetMapping(path = "/users/{id}")
    public ResponseEntity<KorisnikEnitity> getKorisnikbyId(@PathVariable Long id){
        return new ResponseEntity<>(korisnikService.getKorisnikById(id), HttpStatus.OK);
    }
    */
}
