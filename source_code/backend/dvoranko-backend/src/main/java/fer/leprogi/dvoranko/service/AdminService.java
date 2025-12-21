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
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import org.springframework.transaction.annotation.Transactional;

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
    public DvoranaDTO approveOglasRequest(Long requestId) {
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
//                new HashSet<>(),
                kategorije,
                zahtjev.getOwner().getId()
        ));

        zahtjevOglasRepository.delete(zahtjev);

        return novaDvorana;
    }

    public void rejectOglasRequest(Long requestId) {

//        ZahtjevOglas zahtjev = zahtjevOglasRepository.findById(requestId)
//                .orElseThrow(() -> new IllegalArgumentException("request not found"));
//
//        zahtjevOglasRepository.delete(zahtjev);

        if (!zahtjevOglasRepository.existsById(requestId)) {
            throw new IllegalArgumentException("request not found");
        }
        zahtjevOglasRepository.deleteById(requestId);
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

    public Iterable<DvoranaDTO> getAllDvorane(){
        return dvoranaRepository
                .findAll()
                .stream()
                .map(dtoMapper::toDvoranaDTO)
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

    public Dvorana deleteDvorana(Long id) {
        Dvorana dvorana = dvoranaRepository.findById(id).orElse(null);
        if(dvorana == null) throw new IllegalArgumentException("dvorana that you want to delete not found");
        dvoranaRepository.delete(dvorana);
        return dvorana;
    }
}


