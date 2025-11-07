package fer.leprogi.dvoranko.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import fer.leprogi.dvoranko.service.ModeratorService;

@RestController
@RequestMapping("/api/moderator/request")
public class ModeratorRequestController {

    @Autowired
    private  ModeratorService moderatorService;

    @GetMapping("/requestAdd")
    public ResponseEntity<?> requestAdd(@RequestBody ZahtjevOglasDTO dto) {


        ZahtjevOglas zahtjev = moderatorService.createAddRequest(dto);

        return ResponseEntity.ok("Zahtjev poslan");
    }
}
