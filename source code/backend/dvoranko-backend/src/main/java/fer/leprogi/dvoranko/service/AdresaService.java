package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.repository.AdresaRepository;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdresaService {
    private final AdresaRepository adresaRepository;
    private final MjestoRepository mjestoRepository;

    public Adresa createAdresa(Adresa adresa, Long idMjesta) {
        Mjesto mjesto = mjestoRepository.findById(idMjesta)
                .orElseThrow(() -> new IllegalArgumentException("Mjesto with idMjesta " + idMjesta + " does not exist"));
        adresa.setMjesto(mjesto);
        return adresaRepository.save(adresa);
    }

    public Adresa getAdresaById(String koordinate){
        return adresaRepository.findById(koordinate)
                .orElseThrow(() -> new IllegalArgumentException("Adresa with koordinate " + koordinate + " does not exist"));
    }

    public List<Adresa> getAllAdrese(){
        return adresaRepository.findAll();
    }

}
