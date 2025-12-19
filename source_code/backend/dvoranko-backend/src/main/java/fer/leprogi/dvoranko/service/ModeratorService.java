package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.model.Kategorija;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.*;

import java.util.HashSet;
import java.util.Set;


@Service
public class ModeratorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevRepository;

    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private KategorijaRepository kategorijaRepository;


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

        Set<Kategorija> kategorije = new HashSet<>(kategorijaRepository.findAllById(request.getIdKategorije()));
        zahtjev.setKategorije(kategorije);

        ZahtjevOglas saved = zahtjevRepository.save(zahtjev);

        return dtoMapper.toZahtjevOglasDTO(saved);
    }
}
