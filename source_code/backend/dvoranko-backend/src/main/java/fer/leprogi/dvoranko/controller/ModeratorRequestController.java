package fer.leprogi.dvoranko.controller;


import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/public/moderator/request")
public class ModeratorRequestController {

    @Autowired
    private  ModeratorService moderatorService;

    @PostMapping("/requestAdd")
    public ResponseEntity<ApiResponse<ZahtjevOglasDTO>> requestAdd(@RequestBody CreateZahtjevOglas request) {

        ZahtjevOglasDTO created = moderatorService.createAddRequest(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Zahtjev successfully created"));
    }
}
