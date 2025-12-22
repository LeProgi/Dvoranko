package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.Kategorija;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.repository.AdresaRepository;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.repository.KategorijaRepository;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DvoranaService {

    private final DvoranaRepository dvoranaRepository;
    private final AdresaRepository adresaRepository;
    private final KategorijaRepository kategorijaRepository;
    private final DtoMapper dtoMapper;
    private final UserRepository userRepository;


    @Transactional
    public DvoranaDTO createDvorana(CreateDvoranaRequest request) {
        Adresa adresa = adresaRepository.findById(request.getIdAdresa())
                .orElseThrow(() -> new ResourceNotFoundException("Adresa with idAdresa " + request.getIdAdresa() + " does not exist"));

        Dvorana dvorana = new Dvorana();
        dvorana.setNazivDvorana(request.getNazivDvorana());
        dvorana.setKapacitet(request.getKapacitet());
        dvorana.setOpis(request.getOpis());
        dvorana.setAdresa(adresa);

        if (request.getIdKategorija() != null && !request.getIdKategorija().isEmpty()) {
            Set<Kategorija> kategorije = new HashSet<>();
            for (Long idKategorija : request.getIdKategorija()) {
                Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                        .orElseThrow(() -> new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist"));
                kategorije.add(kategorija);
            }
            dvorana.setKategorije(kategorije);
        }

        User vlasnik = userRepository.findById(request.getIdVlasnik())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + request.getIdVlasnik() + " does not exist"));

        dvorana.setVlasnik(vlasnik);

        Dvorana saved = dvoranaRepository.save(dvorana);

        return dtoMapper.toDvoranaDTO(saved);
    }

    public DvoranaDTO getDvoranaById(Long idDvorana) {
        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with idDvorana " + idDvorana + " does not exist"));

        return dtoMapper.toDvoranaDTO(dvorana);
    }

    public Iterable<DvoranaDTO> getAllDvorane(){
        return dvoranaRepository
                .findAll()
                .stream()
                .map(dtoMapper::toDvoranaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DvoranaDTO updateDvorana(Long idDvorana, CreateDvoranaRequest request) {
        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
                .orElseThrow(() -> new ResourceNotFoundException("Dvorana with idDvorana " + idDvorana + " does not exist"));

        Adresa adresa = adresaRepository.findById(request.getIdAdresa())
                .orElseThrow(() -> new ResourceNotFoundException("Adresa with idAdresa " + request.getIdAdresa() + " does not exist"));

        dvorana.setNazivDvorana(request.getNazivDvorana());
        dvorana.setKapacitet(request.getKapacitet());
        dvorana.setOpis(request.getOpis());
        dvorana.setAdresa(adresa);

        if (request.getIdKategorija() != null) {
            Set<Kategorija> kategorije = new HashSet<>();
            for (Long idKategorija : request.getIdKategorija()) {
                Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                        .orElseThrow(() -> new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist"));
                kategorije.add(kategorija);
            }
            dvorana.setKategorije(kategorije);
        } else {
            dvorana.getKategorije().clear();
        }

        Dvorana updated = dvoranaRepository.save(dvorana);

        return dtoMapper.toDvoranaDTO(updated);
    }

    public Iterable<DvoranaDTO> getDvoraneByKategorija(Long idKategorija) {
        if (!kategorijaRepository.existsById(idKategorija)) {
            throw new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist");
        }

        return dvoranaRepository.findByKategorijeIdKategorija(idKategorija)
                .stream()
                .map(dtoMapper::toDvoranaDTO)
                .collect(Collectors.toList());
    }


    public Iterable<DvoranaDTO> getDvoraneByOwner(Long idOwner) {

        List<Dvorana> dvorane = dvoranaRepository.findAllByVlasnik_Id(idOwner);

        return dvorane.stream()
                .map(dtoMapper::toDvoranaDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteDvorana(Long idDvorana) {
//        if (!dvoranaRepository.existsById(idDvorana)) {
//            throw new ResourceNotFoundException("Dvorana with idDvorana " + idDvorana + " does not exist");
//        }
//        dvoranaRepository.deleteById(idDvorana);

        Dvorana dvorana = dvoranaRepository.findById(idDvorana)
                .orElseThrow(() -> new ResourceNotFoundException("dvorana that you want to delete not found"));
        dvoranaRepository.delete(dvorana);
    }

}
