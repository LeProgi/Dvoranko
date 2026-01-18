package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.dto.ZahtjevTerminDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.service.DvoranaService;
import fer.leprogi.dvoranko.service.ModeratorService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moderator")
public class ModeratorController {


    private final ModeratorService moderatorService;

    @GetMapping("/getMyDvorane")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Iterable<DvoranaDTO>>> getAllDvoraneForModerator(@AuthenticationPrincipal CustomOAuth2User principal) {

        Iterable<DvoranaDTO> dvorane = moderatorService.getAllDvoranaForModerator(principal);

        return ResponseEntity.ok(ApiResponse.success(dvorane, "My dvorane fetched successfully"));
    }

    @PostMapping("/approveTeminRequest/{id}")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Void>> approveTerminRequest(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User principal) {
        moderatorService.approveTerminRequest(id, principal);
        return ResponseEntity.ok(ApiResponse.success(null, "Termin request approved successfully"));
    }

    @PostMapping("/rejectTeminRequest/{id}")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Void>> rejectTerminRequest(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User principal) {
        moderatorService.rejectTerminRequest(id, principal);
        return ResponseEntity.ok(ApiResponse.success(null, "Termin request rejected successfully"));
    }
      
      
    @PutMapping(value = "/dvorana/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<DvoranaDTO>> updateDvorana(@PathVariable("id") Long id, @Valid @RequestPart("request") String requestJson, @RequestPart(value = "files", required = false) List<MultipartFile> files){
        try {
            CreateDvoranaRequest request = new ObjectMapper().readValue(requestJson, CreateDvoranaRequest.class);

            DvoranaDTO updated = moderatorService.updateDvorana(id, request, files);

            return ResponseEntity.ok(ApiResponse.success(updated, "Dvorana updated successfully"));

        }catch (Exception e){
            return ResponseEntity.ok(ApiResponse.error("Kume nes ne dela", e.getMessage()));
        }
    }


    @GetMapping("/getMyZahtjevTermin")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Iterable<ZahtjevTerminDTO>>> getAllTerminRequestsForModerator(@AuthenticationPrincipal CustomOAuth2User principal) {

        Iterable<ZahtjevTerminDTO> termini = moderatorService.getAllTerminRequestsForModerator(principal);

        return ResponseEntity.ok(ApiResponse.success(termini, "My termin requests fetched successfully"));
    }

    @GetMapping("/getZahtjeviForDvorana/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Iterable<ZahtjevTerminDTO>>> getAllTerminRequestsForDvorana(@AuthenticationPrincipal CustomOAuth2User principal, @PathVariable("id") Long id) {

        Iterable<ZahtjevTerminDTO> termini = moderatorService.getAllTerminRequestsForThisDvorana(principal, id);
        return ResponseEntity.ok(ApiResponse.success(termini, "Termin requests for this dvorana fetched successfully"));
    }

    @GetMapping("/getPotvrdeniTerminiForDvorana/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Iterable<TerminDTO>>> getAllPotvrdeniTerminiForDvorana(@AuthenticationPrincipal CustomOAuth2User principal, @PathVariable("id") Long id) throws Exception {
        Iterable<TerminDTO> termini = moderatorService.getAllPotvrdeniTerminiForThisDvorana(principal, id);
        return ResponseEntity.ok(ApiResponse.success(termini, "Potvrdeni termini fetched successfully"));
    }

}