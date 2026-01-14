package fer.leprogi.dvoranko.service;


import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.repository.TerminRepository;
import fer.leprogi.dvoranko.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminService {

    private final TerminRepository terminRepository;
    private final UserRepository userRepository;
    private final DvoranaRepository dvoranaRepository;

    public Termin create(TerminDTO terminDTO) {

        Termin termin = new Termin();
        termin.setDatumVrijemeStart(terminDTO.getDatumVrijemeStart());
        termin.setDatumVrijemeEnd(terminDTO.getDatumVrijemeEnd());
        termin.setJeJavniEvent(terminDTO.getJeJavniEvent());

        // postavljanje korisnika
        User korisnik = userRepository.findById(terminDTO.getIdKorisnik())
                .orElseThrow(() ->
                        new RuntimeException("Korisnik ne postoji"));
        termin.setKorisnik(korisnik);

        // postavljanje dvorane
        Dvorana dvorana = dvoranaRepository.findById(terminDTO.getIdDvorana())
                .orElseThrow(() ->
                        new RuntimeException("Dvorana ne postoji"));
        termin.setDvorana(dvorana);

        return terminRepository.save(termin);
    }

    public Termin findById(Long id) {
        return terminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Termin s ID-om " + id + " ne postoji"));
    }

    public List<Termin> getAll() {
        return terminRepository.findAll();
    }


    public Termin update(Long id, Termin updatedTermin) {

        Termin existing = findById(id);

        existing.setDatumVrijemeStart(updatedTermin.getDatumVrijemeStart());
        existing.setDatumVrijemeEnd(updatedTermin.getDatumVrijemeEnd());
        existing.setJeJavniEvent(updatedTermin.getJeJavniEvent());

        // promjena korisnika (ako treba)
        if (updatedTermin.getKorisnik() != null) {
            User korisnik = userRepository.findById(
                    updatedTermin.getKorisnik().getId()
            ).orElseThrow(() ->
                    new RuntimeException("Korisnik ne postoji"));
            existing.setKorisnik(korisnik);
        }

        // promjena dvorane (ako treba)
        if (updatedTermin.getDvorana() != null) {
            Dvorana dvorana = dvoranaRepository.findById(
                    updatedTermin.getDvorana().getIdDvorana()
            ).orElseThrow(() ->
                    new RuntimeException("Dvorana ne postoji"));
            existing.setDvorana(dvorana);
        }


        return terminRepository.save(existing);
    }


    public void delete(Long id) {
        if (!terminRepository.existsById(id)) {
            throw new RuntimeException("Termin s ID-om " + id + " ne postoji");
        }
        terminRepository.deleteById(id);
    }
}
