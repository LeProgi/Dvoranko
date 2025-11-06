package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.RequestAddDTO;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {

    @Autowired
    private ZahtjevOglasRepository zahtjevRepo;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> moderatorDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to moderator dashboard",
                "access", "MODERATOR, ADMIN"
        ));
    }

    @PostMapping("/requestAdd")
    public ResponseEntity<?> requestAdd(@RequestBody RequestAddDTO dto) {
        ZahtjevOglas z = new ZahtjevOglas();
        // vezanje na vlasnika (pretpostavka: User ima setId)
        User owner = new User();
        owner.setId(dto.getOwnerId());
        z.setUser(owner);

        z.setNaziv(dto.getNaziv());
        z.setOpis(dto.getOpis());
        z.setKapacitet(dto.getKapacitet());
        z.setLatitude(dto.getLatitude());
        z.setLongitude(dto.getLongitude());
        z.setKategorija(dto.getKategorija());
        z.setAdresa(dto.getAdresa());
        z.setMjesto(dto.getMjesto());

        // status će biti PENDING zbog @PrePersist
        zahtjevRepo.save(z);

        return ResponseEntity.ok("Zahtjev poslan");
    }
}