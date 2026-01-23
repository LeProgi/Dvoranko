package fer.leprogi.dvoranko.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.service.DvoranaService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/dvorane")
@RequiredArgsConstructor
public class DvoranaController {

    private final DvoranaService dvoranaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<DvoranaDTO>> createDvorana(@Valid @RequestPart("request") String requestJson, @RequestPart("files") List<MultipartFile> files) throws JsonProcessingException {

        CreateDvoranaRequest request = new ObjectMapper().readValue(requestJson, CreateDvoranaRequest.class);

        DvoranaDTO dvorana = dvoranaService.createDvorana(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(dvorana, "Dvorana successfully created"));
    }

    @GetMapping("/{idDvorana}")
    public ResponseEntity<ApiResponse<DvoranaDTO>> getDvoranaById(@PathVariable Long idDvorana){
        DvoranaDTO dvorana = dvoranaService.getDvoranaById(idDvorana);

        return ResponseEntity.ok(ApiResponse.success(dvorana, "Dvorana retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<DvoranaDTO>>> getAllDvorane(){
        Iterable<DvoranaDTO> dvorane = dvoranaService.getAllDvorane();

        return ResponseEntity.ok(ApiResponse.success(dvorane, "Dvorane retrieved successfully"));
    }

    @PutMapping("/{idDvorana}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<DvoranaDTO>> updateDvorana(@PathVariable Long idDvorana, @Valid @RequestBody CreateDvoranaRequest request){
        DvoranaDTO updated = dvoranaService.updateDvorana(idDvorana, request);

        return ResponseEntity.ok(ApiResponse.success(updated, "Dvorana updated successfully"));
    }

    @DeleteMapping("/{idDvorana}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Void>> deleteDvorana(@PathVariable Long idDvorana){
        dvoranaService.deleteDvorana(idDvorana);

        return ResponseEntity.ok(ApiResponse.success(null, "Dvorana deleted successfully"));
    }

}
