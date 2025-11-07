package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.SlikaDvorana;
import fer.leprogi.dvoranko.service.SlikaDvoranaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/public/images")
@AllArgsConstructor
public class SlikaDvoranaContorller {

    private final SlikaDvoranaService slikaDvoranaService;

    @PostMapping
    public ResponseEntity<SlikaDvorana> uploadImage(@RequestPart("file") MultipartFile file, @RequestPart("dvorana") Long idDvorana) throws IOException {
        return ResponseEntity.ok(slikaDvoranaService.saveSlikaDvorana(file, idDvorana));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable Long id){
        SlikaDvorana img = slikaDvoranaService.getSlikaDvorana(id);
        String base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(img.getImageData());

        return ResponseEntity.ok(base64);
    }


}
