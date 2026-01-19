package fer.leprogi.dvoranko.service;


import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.dto.TerminZaFrontDTO;
import fer.leprogi.dvoranko.dto.ZahtjevTerminDTO;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevTermin;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.repository.TerminRepository;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevTerminRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminService {

    private final TerminRepository terminRepository;
    private final UserRepository userRepository;
    private final DvoranaRepository dvoranaRepository;
    private final ZahtjevTerminRepository zahtjevTerminRepository;
    private final DtoMapper dtoMapper;

    public Termin create(TerminDTO terminDTO) {

        Termin termin = new Termin();
        termin.setDatumVrijemeStart(terminDTO.getDatumVrijemeStart());
        termin.setDatumVrijemeEnd(terminDTO.getDatumVrijemeEnd());
        termin.setJeJavniEvent(terminDTO.getJeJavniEvent());
        termin.setImeDogadanja(terminDTO.getImeDogadanja());
        termin.setOpisDogadanja(terminDTO.getOpisDogadanja());

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
        existing.setImeDogadanja(updatedTermin.getImeDogadanja());
        existing.setOpisDogadanja(updatedTermin.getOpisDogadanja());

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

    public List<ZahtjevTerminDTO> getZahtjeviTerminiByDvoranaId(Long dvoranaId) {

        List<ZahtjevTermin> zahtjevi = zahtjevTerminRepository.findByIdDvorana(dvoranaId);
        List<ZahtjevTerminDTO> zahtjeviDTO = zahtjevi.stream().map(zahtjev -> {
            ZahtjevTerminDTO dto = dtoMapper.toZahtjevTerminDTO(zahtjev);
            return dto;
        }).toList();


        return  zahtjeviDTO;
    }
    
    @Transactional
    public List<TerminZaFrontDTO> getAllPublicTermin (){
        List<Termin> termini = terminRepository.findAllByJeJavniEvent(1);
//        List<Termin> termini = terminRepository.findAllPublicTerminWithDvorana();
        System.out.println(termini.size());

//        System.out.println(termini.get(0).getDvorana().getSlike().getFirst().getUrlSlika());

        List<TerminZaFrontDTO> result = new LinkedList<>();

        for (Termin termin : termini) {
            Dvorana dvorana = termin.getDvorana(); // inicijaliziraj lazy
            if(dvorana != null && dvorana.getVlasnik() != null) {
                dvorana.getVlasnik().getName(); // inicijaliziraj vlasnika
            }

            TerminZaFrontDTO dto = new TerminZaFrontDTO();
            dto.setId(termin.getId());
            dto.setIdDvorana(dvorana.getIdDvorana());
            dto.setIdKorisnik(termin.getKorisnik().getId());
            dto.setDatumVrijemeEnd(termin.getDatumVrijemeEnd());
            dto.setDatumVrijemeStart(termin.getDatumVrijemeStart());
            dto.setOpisDogadanja(termin.getOpisDogadanja());
            dto.setImeDogadanja(termin.getImeDogadanja());
            dto.setImeVlasnika(dvorana.getVlasnik().getName());
            dto.setJeJavniEvent(termin.getJeJavniEvent());

            // mapiranje DTO-a
            dto.setDvorana(dtoMapper.toDvoranaDTO(dvorana));

            result.add(dto);
        }

        System.out.println("Proslo");

        return result;
    }
}
