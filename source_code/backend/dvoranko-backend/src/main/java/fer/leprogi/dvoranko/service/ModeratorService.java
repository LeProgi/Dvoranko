package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.AdresaDTO;
import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.model.*;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class ModeratorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevRepository;
    @Autowired
    private DvoranaRepository dvoranaRepository;
    @Autowired
    private AdresaRepository adresaRepository;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private KategorijaRepository kategorijaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DvoranaService dvoranaService;
    @Autowired
    private MjestoService mjestoService;

    @Transactional
    public ZahtjevOglasDTO createAddRequest(CreateZahtjevOglas request) {
        ZahtjevOglas zahtjev = new ZahtjevOglas();

        User owner = userRepository.findById(request.getIdOwner())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + request.getIdOwner() + " not found"));

        zahtjev.setOwner(owner);
        zahtjev.setNaziv(request.getNaziv());
        zahtjev.setOpis(request.getOpis());
        zahtjev.setKapacitet(request.getKapacitet());
        zahtjev.setPostalCode(request.getPostalCode());
        zahtjev.setCity(request.getCity());
        zahtjev.setStreet(request.getStreet());
        zahtjev.setStreetNumber(request.getStreetNumber());
        zahtjev.setLatitude(request.getLat());
        zahtjev.setLongitude(request.getLng());

        if (request.getIdKategorije() != null && !request.getIdKategorije().isEmpty()) {
            Set<Kategorija> kategorije = new HashSet<>();
            for (Long idKategorija : request.getIdKategorije()) {
                Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                        .orElseThrow(() -> new ResourceNotFoundException("Kategorija with id " + idKategorija + " does not exist"));
                kategorije.add(kategorija);
            }
            zahtjev.setKategorije(kategorije);
        }

        ZahtjevOglas saved = zahtjevRepository.saveAndFlush(zahtjev);

        return dtoMapper.toZahtjevOglasDTO(saved);
    }


    public Iterable<DvoranaDTO> getAllDvoranaForModerator(CustomOAuth2User principal) {

        Long ownerId = userService.getIdForPrincipal(principal);

        return dvoranaService.getDvoraneByOwner(ownerId);
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
}
