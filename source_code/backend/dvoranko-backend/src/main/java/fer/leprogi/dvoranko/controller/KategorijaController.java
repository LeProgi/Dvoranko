package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.KategorijaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateKategorijaRequest;
import fer.leprogi.dvoranko.service.KategorijaService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/kategorije")
@RequiredArgsConstructor
public class KategorijaController {

    private final KategorijaService kategorijaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KategorijaDTO>> createKategorija(@RequestBody CreateKategorijaRequest request) {
        KategorijaDTO kategorija = kategorijaService.createKategorija(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(kategorija, "Kategorija successfully created"));
    }

    @GetMapping("/{idKategorija}")
    public ResponseEntity<ApiResponse<KategorijaDTO>> getKategorijaById(@PathVariable Long idKategorija) {
        KategorijaDTO kategorija = kategorijaService.getKategorijaById(idKategorija);

        return ResponseEntity.ok(ApiResponse.success(kategorija, "Kategorija retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<KategorijaDTO>>> getAllKategorije() {
        Iterable<KategorijaDTO> kategorije = kategorijaService.getAllKategorije();

        return ResponseEntity.ok(ApiResponse.success(kategorije, "Kategorije retrieved successfully"));
    }

    @PutMapping("/{idKategorija}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KategorijaDTO>> updateKategorija(@PathVariable Long idKategorija, @RequestBody CreateKategorijaRequest request) {
        KategorijaDTO updated = kategorijaService.updateKategorija(idKategorija, request);

        return ResponseEntity.ok(ApiResponse.success(updated, "Kategorija updated successfully"));
    }

    @DeleteMapping("/{idKategorija}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteKategorija(@PathVariable Long idKategorija) {
        kategorijaService.deleteKategorija(idKategorija);

        return ResponseEntity.ok(ApiResponse.success(null, "Kategorija deleted successfully"));
    }

}
