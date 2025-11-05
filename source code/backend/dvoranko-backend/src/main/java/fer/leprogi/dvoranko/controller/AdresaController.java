package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.AdresaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateAdresaRequest;
import fer.leprogi.dvoranko.service.AdresaService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/adrese")
@RequiredArgsConstructor
public class AdresaController {

    private final AdresaService adresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdresaDTO>> createAdresa(@Valid @RequestBody CreateAdresaRequest request) {
        AdresaDTO adresa = adresaService.createAdresa(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(adresa, "Adresa successfully created"));
    }


    @GetMapping("/{idAdresa}")
    public ResponseEntity<ApiResponse<AdresaDTO>> getAdresaById(@PathVariable Long idAdresa) {
        AdresaDTO adresa = adresaService.getAdresaById(idAdresa);

        return ResponseEntity.ok(ApiResponse.success(adresa, "Adresa retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<AdresaDTO>>> getAllAdrese() {
        Iterable<AdresaDTO> adrese = adresaService.getAllAdrese();

        return ResponseEntity.ok(ApiResponse.success(adrese, "Adrese retrieved successfully"));
    }

    @PutMapping("{idAdresa}")
    public ResponseEntity<ApiResponse<AdresaDTO>> updateAdresa(@PathVariable Long idAdresa, @Valid @RequestBody CreateAdresaRequest request) {
        AdresaDTO updated = adresaService.updateAdresa(idAdresa, request);

        return ResponseEntity.ok(ApiResponse.success(updated, "Adresa updated successfully"));
    }

    @DeleteMapping("{idAdresa}")
    public ResponseEntity<ApiResponse<Void>> deleteAdresa(@PathVariable Long idAdresa) {
        adresaService.deleteAdresa(idAdresa);

        return ResponseEntity.ok(ApiResponse.success(null, "Adresa deleted successfully"));
    }

}
