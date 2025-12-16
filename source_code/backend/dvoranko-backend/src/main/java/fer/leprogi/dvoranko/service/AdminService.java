package fer.leprogi.dvoranko.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fer.leprogi.dvoranko.dto.ZahtjevIznajmljivacDTO;
import fer.leprogi.dvoranko.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevIznajmljivacRepository zahtjevIznajmljivacRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevOglasRepository;
    @Autowired
    private DtoMapper dtoMapper;


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


    public Dvorana approveOglasRequest(Long requestId) {
        Optional<ZahtjevOglas> z = zahtjevOglasRepository.findById(requestId);
        if (z.isEmpty()) {
            throw new IllegalArgumentException("request not found");
        }
        ZahtjevOglas zahtjev = z.get();

        Dvorana dvorana = new Dvorana();

        //potrebno izradit dvoranu


        zahtjevOglasRepository.delete(zahtjev);
        return dvorana;
    }

    public void rejectOglasRequest(Long requestId) {
        Optional<ZahtjevOglas> z = zahtjevOglasRepository.findById(requestId);
        if (z.isEmpty()) {
            throw new IllegalArgumentException("request not found");
        }
        ZahtjevOglas zahtjev = z.get();
        zahtjevOglasRepository.delete(zahtjev);
    }

    public List<ZahtjevIznajmljivacDTO> getAllIznajmljivacRequests() {
        return zahtjevIznajmljivacRepository.findAll()
                .stream()
                .map(dtoMapper::toZahtjevIznajmljivacDTO)
                .collect(Collectors.toList());
    }

    public List<ZahtjevOglasDTO> getAllDvoranaRequests() {
        return zahtjevOglasRepository.findAll()
                .stream()
                .map(dtoMapper::toZahtjevOglasDTO)
                .collect(Collectors.toList());
    }
}


