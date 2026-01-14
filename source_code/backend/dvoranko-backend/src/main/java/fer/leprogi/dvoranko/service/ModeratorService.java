package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateZahtjevOglas;
import fer.leprogi.dvoranko.model.*;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.FolderName;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
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
    @Autowired
    private UserService userService;
    @Autowired
    private DvoranaService dvoranaService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ZahtjevSlikaRepository zahtjevSlikaRepository;

    @Transactional
    public ZahtjevOglasDTO createAddRequest(CreateZahtjevOglas request, List<MultipartFile> images) {
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

        try {
            int i = 1;
            for (MultipartFile image : images) {
                String url = cloudinaryService.upload(image, saved.getIdZahtjevOglas(), i, FolderName.zahtjevi);

                ZahtjevSlika slika = new ZahtjevSlika();
                slika.setUrlSlika(url);
                slika.setPoredakSlike("img_" + i);
                slika.setZahtjevOglas(saved);

                saved.getSlike().add(slika);

                zahtjevSlikaRepository.save(slika);
                i++;
            }
        }catch (Exception e){}

        ZahtjevOglas finalSaved = zahtjevRepository.save(saved);

        return dtoMapper.toZahtjevOglasDTO(finalSaved);
    }


    public Iterable<DvoranaDTO> getAllDvoranaForModerator(CustomOAuth2User principal) {

        Long ownerId = userService.getIdForPrincipal(principal);

        return dvoranaService.getDvoraneByOwner(ownerId);
    }
}
