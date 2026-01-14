package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.dto.ZahtjevTerminDTO;
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
    private ZahtjevTerminRepository zahtjevTerminRepository;
    @Autowired
    private DvoranaRepository dvoranaRepository;

    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private KategorijaRepository kategorijaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DvoranaService dvoranaService;
    @Autowired
    private TerminService terminService;

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
        zahtjev.setDaysOpen(request.getDaysOpen());

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

    public Iterable<ZahtjevTerminDTO> getAllTerminRequestsForModerator(CustomOAuth2User principal) {

        Long moderatorId = userService.getIdForPrincipal(principal);

        Set<ZahtjevTermin> sviZahtjevi = new HashSet<>();

        Iterable<Dvorana> dvoraneModerator = dvoranaRepository.findAllByVlasnik_Id(moderatorId);
        for (Dvorana dvorana : dvoraneModerator) {
            Iterable<ZahtjevTermin> zahtjeviDvorana = zahtjevTerminRepository.findByIdDvorana(dvorana.getIdDvorana());
            for (ZahtjevTermin zahtjev : zahtjeviDvorana) {
                sviZahtjevi.add(zahtjev);
            }
        }

        Set<ZahtjevTerminDTO> sviZahtjeviDTO = new HashSet<>();
        for (ZahtjevTermin zahtjev : sviZahtjevi) {
            sviZahtjeviDTO.add(dtoMapper.toZahtjevTerminDTO(zahtjev));
        }

        return sviZahtjeviDTO;
    }

    public void approveTerminRequest(Long idZahtjev, CustomOAuth2User principal) {

        ZahtjevTermin zahtjev = zahtjevTerminRepository.findById(idZahtjev)
                .orElseThrow(() -> new ResourceNotFoundException("ZahtjevTermin with id " + idZahtjev + " not found for this moderator"));

        TerminDTO terminDTO = new TerminDTO();
        terminDTO.setDatumVrijemeStart(zahtjev.getDatumVrijemeStart());
        terminDTO.setDatumVrijemeEnd(zahtjev.getDatumVrijemeEnd());
        terminDTO.setJeJavniEvent(zahtjev.getJeJavniEvent());
        terminDTO.setIdKorisnik(zahtjev.getIdKorisnik());
        terminDTO.setIdDvorana(zahtjev.getIdDvorana());

        terminService.create(terminDTO);
        zahtjevTerminRepository.delete(zahtjev);
    }

    public void rejectTerminRequest(Long idZahtjev, CustomOAuth2User principal) {

        ZahtjevTermin zahtjev = zahtjevTerminRepository.findById(idZahtjev)
                .orElseThrow(() -> new ResourceNotFoundException("ZahtjevTermin with id " + idZahtjev + " not found for this moderator"));

        zahtjevTerminRepository.delete(zahtjev);
    }


}
