package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DvoranaService {

    private DvoranaRepository dvoranaRepository;

    public Dvorana createDvorana(Dvorana dvorana) {
        return dvoranaRepository.save(dvorana);
    }

    public Iterable<Dvorana> getAllDvorane() {
        return dvoranaRepository.findAll();
    }
}
