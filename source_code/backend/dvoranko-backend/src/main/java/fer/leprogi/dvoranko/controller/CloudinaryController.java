package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.service.CloudinaryService;
import fer.leprogi.dvoranko.utils.FolderName;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return cloudinaryService.upload(file, 2L, 1, FolderName.dvorane);
    }
}
