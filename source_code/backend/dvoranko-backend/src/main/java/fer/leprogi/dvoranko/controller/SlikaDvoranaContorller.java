package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.SlikaDvorana;
import fer.leprogi.dvoranko.service.SlikaDvoranaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/public/images")
@AllArgsConstructor
public class SlikaDvoranaContorller {

    private final SlikaDvoranaService slikaDvoranaService;

    @PostMapping
    public ResponseEntity<List<SlikaDvorana>> uploadImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("dvorana") Long idDvorana) throws IOException {
        return ResponseEntity.ok(slikaDvoranaService.saveSlikeDvorana(files, idDvorana));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable Long id){
//        SlikaDvorana img = slikaDvoranaService.getSlikaDvorana(id);
//        String base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(img.getImageData());

        return ResponseEntity.ok("");
    }


}
