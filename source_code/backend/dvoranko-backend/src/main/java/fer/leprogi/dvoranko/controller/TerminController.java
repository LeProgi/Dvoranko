package fer.leprogi.dvoranko.controller;


import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.service.TerminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/public/termini")
@RequiredArgsConstructor
public class TerminController {


    private final TerminService terminService;



    @GetMapping
    public ResponseEntity<List<Termin>> getAllTermini() {
        return ResponseEntity.ok(terminService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Termin> getTerminById(@PathVariable Long id) {
        return ResponseEntity.ok(terminService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Termin> createTermin(@RequestBody TerminDTO termin) {
        Termin saved = terminService.create(termin);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermin(@PathVariable Long id) {
        terminService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
