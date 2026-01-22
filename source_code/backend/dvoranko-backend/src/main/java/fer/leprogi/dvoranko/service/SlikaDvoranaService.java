package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.SlikaDvorana;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.repository.SlikaDvoranaRepository;
import fer.leprogi.dvoranko.utils.FolderName;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SlikaDvoranaService {

    private final SlikaDvoranaRepository slikaDvoranaRepository;
    private final DvoranaRepository dvoranaRepository;
    private final CloudinaryService cloudinaryService;

    public List<SlikaDvorana> saveSlikeDvorana(List<MultipartFile> images, Long idDvorana) throws IOException {
//        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid dvorana id: " + idDvorana));

        ArrayList<SlikaDvorana> savedImages = new ArrayList<>();

        int i = 1;
        for (MultipartFile file : images) {
            SlikaDvorana image = new SlikaDvorana();
            String url = cloudinaryService.upload(file, idDvorana, i, FolderName.dvorane);

            image.setPoredakSlike("img_" + i);
            image.setUrlSlika(url);
            slikaDvoranaRepository.save(image);

            savedImages.add(image);
            i++;
        }

        return savedImages;
    }

    public SlikaDvorana getSlikaDvorana(Long id) {
        return slikaDvoranaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + id));
    }

    public SlikaDvorana updateSlika(Long idSlika, String newUrlSlika, String newPoredakSlike) {
        SlikaDvorana slika = slikaDvoranaRepository.findById(idSlika)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + idSlika));

        slika.setUrlSlika(newUrlSlika);
        slika.setPoredakSlike(newPoredakSlike);

        return slikaDvoranaRepository.save(slika);
    }

//    public Iterable<SlikaDvorana> getSlikeByDvorana(Long idDvorana) {
//        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + idDvorana));
//
//        return dvorana.getSlike();
//    }


}
