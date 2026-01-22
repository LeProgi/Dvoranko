package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateTerminRequest;
import fer.leprogi.dvoranko.model.*;

import fer.leprogi.dvoranko.repository.*;
import fer.leprogi.dvoranko.security.CustomOAuth2User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fer.leprogi.dvoranko.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private ZahtjevIznajmljivacRepository zahtjevRepository;

    @Autowired
    private ZahtjevTerminRepository zahtjevTerminRepository;

    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private DvoranaRepository dvoranaRepository;
    @Autowired
    private MailService mailService;


    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPictureUrl(),
                user.getRole()
        );
    }

    public ZahtjevIznajmljivac createModeratorRequest(CustomOAuth2User principal) {

        if (principal == null) {
            throw new IllegalArgumentException("Principal cannot be null");
        }
        UserDTO userDTO = convertToDTO(principal.getUser());

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        Optional<User> u = userRepository.findById(userDTO.getId());
        if (u.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = u.get();
        Optional<ZahtjevIznajmljivac> existing = zahtjevRepository.findByUserId(user.getId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Request already exists");
        }
        ZahtjevIznajmljivac zahtjev = new ZahtjevIznajmljivac(user);
        zahtjevRepository.save(zahtjev);
        return zahtjev;
    }

    public void createTerminRequest(CreateTerminRequest request) throws Exception {
        if(request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Dvorana dvorana = dvoranaRepository.findById(request.getIdDvorana())
                .orElseThrow(() -> new IllegalArgumentException("Dvorana with id " + request.getIdDvorana() + " does not exist"));

        User owner = dvorana.getVlasnik();

        ZahtjevTermin  zahtjev = new ZahtjevTermin();
        zahtjev.setDatumVrijemeStart(request.getDatumVrijemeStart());
        zahtjev.setDatumVrijemeEnd(request.getDatumVrijemeEnd());
        zahtjev.setJeJavniEvent(request.getJeJavniEvent());
        zahtjev.setIdKorisnik(request.getIdKorisnik());
        zahtjev.setDvorana(dvorana);
        zahtjev.setOpisDogadanja(request.getOpisDogadanja());
        zahtjev.setImeDogadanja(request.getImeDogadanja());
        zahtjevTerminRepository.save(zahtjev);

        mailService.sendMail(owner.getEmail(), "Zahtjev termina za dvoranu '" + dvorana.getNazivDvorana() + "'","Poštovani, \n\nimate novi zahtjev za termin u dvorani '" + dvorana.getNazivDvorana() + "' na platformi Dvoranko.\n\nLijep pozdrav,\nDvoranko tim");
    }


    public Long getIdForPrincipal(CustomOAuth2User principal) {
        if (principal == null) throw new IllegalArgumentException("Principal cannot be null");
        if (principal.getUser() == null) throw new IllegalArgumentException("User cannot be null");

        return principal.getUser().getId();
    }

    public UserDTO getUSerForPrincipal(CustomOAuth2User principal) {
        if (principal == null) throw new IllegalArgumentException("Principal cannot be null");
        if (principal.getUser() == null) throw new IllegalArgumentException("User cannot be null");

        return dtoMapper.toUserDTO(principal.getUser());
    }


    public Iterable<TerminDTO> getAllReservationsForUser(CustomOAuth2User principal) {
        Long userId = getIdForPrincipal(principal);
        Iterable<Termin> termini = terminRepository.findByKorisnikId(userId);

        List<TerminDTO> terminiDTO = new ArrayList<>();

        for (Termin termin : termini) {
            TerminDTO dto = dtoMapper.toTerminDTO(termin);
            terminiDTO.add(dto);
        }
        return terminiDTO;
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        UserDTO userDTO = dtoMapper.toUserDTO(user.get());
        return userDTO;
    }
}
