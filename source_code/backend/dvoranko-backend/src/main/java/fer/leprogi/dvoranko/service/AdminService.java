package fer.leprogi.dvoranko.service;

import java.util.*;
import java.util.stream.Collectors;

import fer.leprogi.dvoranko.dto.*;
import fer.leprogi.dvoranko.dto.createRequest.CreateAdresaRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateDvoranaRequest;
import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.model.*;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevIznajmljivacRepository zahtjevIznajmljivacRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevOglasRepository;
    @Autowired
    private DvoranaRepository dvoranaRepository;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private MjestoRepository mjestoRepository;
    @Autowired
    private MjestoService mjestoService;
    @Autowired
    private AdresaService adresaService;
    @Autowired
    private DvoranaService dvoranaService;
    @Autowired
    private SessionAdminService sessionAdminService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CloudinaryService cloudinaryService;


    public User acceptIznajmljivacRequest(Long requestId) {
        Optional<ZahtjevIznajmljivac> z = zahtjevIznajmljivacRepository.findById(requestId);
        if (z.isEmpty()) {
            throw new IllegalArgumentException("request not found");
        }
        ZahtjevIznajmljivac zahtjev = z.get();
        User user = userRepository.findById(zahtjev.getUser().getId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }
        user.setRole(Role.MODERATOR);
        sessionAdminService.logoutUserEverywhere(user.getGoogleId());

        userRepository.save(user);
        zahtjevIznajmljivacRepository.delete(zahtjev);
        return user;
    }

    public User rejectIznajmljivacRequest(Long requestId) {
        Optional<ZahtjevIznajmljivac> z = zahtjevIznajmljivacRepository.findById(requestId);
        if (z.isEmpty()) {
            throw new IllegalArgumentException("request not found");
        }
        ZahtjevIznajmljivac zahtjev = z.get();
        User user = userRepository.findById(zahtjev.getUser().getId()).orElse(null);
        zahtjevIznajmljivacRepository.delete(zahtjev);
        return user;
    }


    @Transactional
    public DvoranaDTO approveOglasRequest(Long requestId) throws Exception {
        ZahtjevOglas zahtjev = zahtjevOglasRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("request not found"));

        MjestoDTO mjestoSaved;
        try {
            mjestoSaved = mjestoService.createMjesto(new CreateMjestoRequest(zahtjev.getPostalCode(), zahtjev.getCity()));
        }catch (Exception e){
            mjestoSaved = mjestoService.getMjestoByPostanskiBroj(zahtjev.getPostalCode());
        }

        AdresaDTO adresaSaved;

        adresaSaved = adresaService.createAdresa(new CreateAdresaRequest(
                zahtjev.getLatitude(),
                zahtjev.getLongitude(),
                zahtjev.getStreet(),
                zahtjev.getStreetNumber(),
                mjestoSaved.getIdMjesto()
        ));

        zahtjev.getKategorije().size();
        Set<Long> kategorije = zahtjev.getKategorije().stream().map(Kategorija::getIdKategorija).collect(Collectors.toSet());

        DvoranaDTO novaDvorana = dvoranaService.createDvorana(new CreateDvoranaRequest(
                zahtjev.getNaziv(),
                zahtjev.getKapacitet(),
                zahtjev.getOpis(),
                adresaSaved.getIdAdresa(),
                kategorije,
                zahtjev.getOwner().getId(),
                zahtjev.getDaysOpen(),
                zahtjev.getCijenaPoSatu()
        ), zahtjev.getSlike());

        zahtjevOglasRepository.delete(zahtjev);

        try {
            mailService.sendMail(zahtjev.getOwner().getEmail(), "Vaš oglas je odobren", "Poštovani,\n\nVaš zahtjev za oglas dvorane pod nazivom '" + zahtjev.getNaziv() + "' je odobren i dvorana je sada dostupna na platformi Dvoranko.\n\nHvala vam što koristite našu uslugu!\n\nLijep pozdrav,\nDvoranko tim");
        }catch (Exception e){
            log.warn("Error sending approval email to user");
        }

        return novaDvorana;
    }

    public void rejectOglasRequest(Long requestId) throws Exception {
        System.out.println("PROBNI PRINT");

        ZahtjevOglas zahtjev = zahtjevOglasRepository.findById(requestId)
                .map(z -> {
                    z.getSlike().size(); // prisilno inicijalizira lazy listu
                    return z;
                })
                .orElseThrow(() -> new IllegalArgumentException("request not found"));

//        System.out.println("DRUGI PRINT");
//        System.out.println(zahtjev.toString());
//        System.out.println(zahtjev.getSlike().toString());
//        System.out.println("TRECI PRINT");
        for (ZahtjevSlika slika : zahtjev.getSlike()) {
            try{
                cloudinaryService.deleteImage(slika.getUrlSlika());
            }catch (Exception e){
                log.warn("Error deleting image on cloudinary");
            }
        }

        zahtjevOglasRepository.deleteById(requestId);

        try {
            mailService.sendMail(zahtjev.getOwner().getEmail(), "Vaš oglas je odbijen", "Poštovani,\n\nVaš zahtjev za oglas dvorane pod nazivom '" + zahtjev.getNaziv() + "' je nažalost odbijen.\n\nZa dodatne informacije ili pitanja, slobodno nas kontaktirajte.\n\nLijep pozdrav,\nDvoranko tim");
        } catch (Exception e) {
            log.warn("Error sending rejection email to user");
        }
    }

    public List<ZahtjevIznajmljivacDTO> getAllIznajmljivacRequests() {
        return zahtjevIznajmljivacRepository.findAll()
                .stream()
                .map(dtoMapper::toZahtjevIznajmljivacDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ZahtjevOglasDTO> getAllDvoranaRequests() {
        List<ZahtjevOglas> zahtjevi = zahtjevOglasRepository.findAll();
        zahtjevi.forEach(zahtjev -> zahtjev.getKategorije().size());

        return zahtjevi
                .stream()
                .map(dtoMapper::toZahtjevOglasDTO)
                .collect(Collectors.toList());
    }


    public Iterable<UserDTO> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(dtoMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new IllegalArgumentException("user that you want to delete not found");
        userRepository.delete(user);
        return user;
    }
}


