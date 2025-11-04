package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MjestoService {

    private final MjestoRepository mjestoRepository;

    public List<Mjesto> getAllMjesta() {
        return mjestoRepository.findAll();
    }

    public Mjesto getMjestoById(Long idMjesto) {
        return mjestoRepository.findById(idMjesto)
                .orElseThrow(() -> new IllegalArgumentException("Mjesto with idMjesto " + idMjesto + " does not exist"));
    }

    public Mjesto createMjesto(Mjesto mjesto) {
        return mjestoRepository.save(mjesto);
    }
}

