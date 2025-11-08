package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.service.MjestoService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/public/mjesta")
@RequiredArgsConstructor
public class MjestoController {

    private final MjestoService mjestoService;

    @PostMapping()
    public ResponseEntity<ApiResponse<MjestoDTO>> createMjesto(@Valid @RequestBody CreateMjestoRequest request) {
        MjestoDTO created = mjestoService.createMjesto(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Mjesto successfully created"));
    }

    @GetMapping("/{idMjesto}")
    public ResponseEntity<ApiResponse<MjestoDTO>> getMjestoById(@PathVariable Long idMjesto){
        MjestoDTO mjesto = mjestoService.getMjestoById(idMjesto);

        return ResponseEntity.ok(ApiResponse.success(mjesto, "Mjesto retrieved successfully"));
    }

    @GetMapping("/postanski-broj/{postanskiBroj}")
    public ResponseEntity<ApiResponse<MjestoDTO>> getMjestoByPostanskiBroj(@PathVariable Long postanskiBroj) {
        MjestoDTO mjesto = mjestoService.getMjestoByPostanskiBroj(postanskiBroj);

        return ResponseEntity.ok(ApiResponse.success(mjesto, "Mjesto retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<MjestoDTO>>> getAllMjesta(){
        Iterable<MjestoDTO> mjesta = mjestoService.getAllMjesta();

        return ResponseEntity.ok(ApiResponse.success(mjesta, "Mjesta retrieved successfully"));
    }

    @PutMapping("/{idMjesto}")
    public ResponseEntity<ApiResponse<MjestoDTO>> updateMjesto(@PathVariable Long idMjesto, @Valid @RequestBody CreateMjestoRequest request) {
        MjestoDTO updated = mjestoService.updateMjesto(idMjesto, request);

        return ResponseEntity.ok(ApiResponse.success(updated, "Mjesto updated successfully"));
    }

    @DeleteMapping("/{idMjesto}")
    public ResponseEntity<ApiResponse<Void>> deleteMjesto(@PathVariable Long idMjesto) {
        mjestoService.deleteMjesto(idMjesto);

        return ResponseEntity.ok(ApiResponse.success(null, "Mjesto deleted successfully"));
    }
}
