package fer.leprogi.dvoranko.controllers;

import fer.leprogi.dvoranko.domain.dto.IznajmljivacDto;
import fer.leprogi.dvoranko.domain.entity.IznajmljivacEntity;
import fer.leprogi.dvoranko.mappers.Mapper;
import fer.leprogi.dvoranko.services.IznajmljivacService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class IznajmljivacController {

    private Mapper<IznajmljivacEntity, IznajmljivacDto> mapper;

    private final IznajmljivacService iznajmljivacService;

    @PostMapping
    public ResponseEntity<IznajmljivacEntity> create(@RequestParam Long idKorisnik, @RequestParam String oib) {
        return ResponseEntity.ok(iznajmljivacService.createIznajmljivac(oib, idKorisnik));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IznajmljivacEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(iznajmljivacService.getIznajmljivacById(id));
    }
}
