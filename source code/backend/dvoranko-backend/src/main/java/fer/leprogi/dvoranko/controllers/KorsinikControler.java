package fer.leprogi.dvoranko.controllers;

import fer.leprogi.dvoranko.domain.dto.KorisnikDto;
import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;
import fer.leprogi.dvoranko.mappers.Mapper;
import fer.leprogi.dvoranko.services.KorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class KorsinikControler {
    private Mapper <KorisnikEntity, KorisnikDto> korisnikMapper;
    private KorisnikService korisnikService;

    public KorsinikControler(KorisnikService korisnikService, Mapper<KorisnikEntity, KorisnikDto> korisnikMapper) {
        this.korisnikService = korisnikService;
        this.korisnikMapper = korisnikMapper;
    }

    @PostMapping()
    public ResponseEntity<KorisnikDto> createKorisnik(@RequestBody KorisnikDto korisnik){
        KorisnikEntity korisnikEntity = korisnikMapper.mapFrom(korisnik);
        Optional<KorisnikEntity> temp = korisnikService.getKorisnikByEmail(korisnikEntity.getEmail());
        if (temp.isPresent()){
            return  ResponseEntity.badRequest().build();
        }
        KorisnikEntity savedKorisnikEntity = korisnikService.createKorisnik(korisnikEntity);
        return ResponseEntity.ok(korisnikMapper.mapTo(savedKorisnikEntity));
    }

    @GetMapping
    public ResponseEntity<List<KorisnikEntity>> getAllKorisnik(){
        return ResponseEntity.ok(korisnikService.getAllKorisnik());
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<KorisnikEntity> getKorisnikByEmail(@PathVariable String email){
        Optional<KorisnikEntity> temp = korisnikService.getKorisnikByEmail(email);
        if (temp.isPresent()){
            return ResponseEntity.ok(temp.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
