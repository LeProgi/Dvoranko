package fer.leprogi.dvoranko.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import fer.leprogi.dvoranko.service.ModeratorService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/moderator/request")
public class ModeratorRequestController {

    @Autowired
    private  ModeratorService moderatorService;

    @PostMapping(value = "/requestAdd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ZahtjevOglasDTO>> requestAdd(@RequestPart("request") String requestJson, @RequestPart("files") List<MultipartFile> files){

        try {
            CreateZahtjevOglas request = new ObjectMapper().readValue(requestJson, CreateZahtjevOglas.class);

            ZahtjevOglasDTO created = moderatorService.createAddRequest(request, files);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(created, "Zahtjev successfully created"));

        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Kume nes ne dela", e.getMessage()));
        }
    }


}
