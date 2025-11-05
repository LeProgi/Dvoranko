package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.repository.AdresaRepository;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DvoranaService {

    private final DvoranaRepository dvoranaRepository;
    private final AdresaRepository adresaRepository;


    public Dvorana createDvorana(Dvorana dvorana, String koordinate) {
        Adresa adresa = adresaRepository.findById(koordinate)
                .orElseThrow(() -> new IllegalArgumentException("Adresa with koordinate " + koordinate + " does not exist"));

        dvorana.setAdresa(adresa);
        return dvoranaRepository.save(dvorana);
    }
}
