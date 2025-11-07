package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.SlikaDvorana;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.repository.SlikaDvoranaRepository;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class SlikaDvoranaService {

    private final SlikaDvoranaRepository slikaDvoranaRepository;
    private final DvoranaRepository dvoranaRepository;

    public SlikaDvorana saveSlikaDvorana(MultipartFile imageData, Long idDvorana) throws IOException {
        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + idDvorana));

        SlikaDvorana image = new SlikaDvorana();
        image.setImageData(imageData.getBytes());
//        image.setDvorana(dvorana);

        return slikaDvoranaRepository.save(image);
    }

    public SlikaDvorana getSlikaDvorana(Long id) {
        return slikaDvoranaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + id));
    }

//    public Iterable<SlikaDvorana> getSlikeByDvorana(Long idDvorana) {
//        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid id: " + idDvorana));
//
//        return dvorana.getSlike();
//    }


}
