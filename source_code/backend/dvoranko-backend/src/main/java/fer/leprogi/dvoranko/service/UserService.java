package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateTerminRequest;
import fer.leprogi.dvoranko.model.User;

import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.model.ZahtjevTermin;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.repository.ZahtjevTerminRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ZahtjevIznajmljivacRepository zahtjevRepository;

    @Autowired
    private ZahtjevTerminRepository zahtjevTerminRepository;


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

    public void createTerminRequest(CreateTerminRequest request) {
        if(request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        ZahtjevTermin  zahtjev = new ZahtjevTermin();
        zahtjev.setDatumVrijemeStart(request.getDatumVrijemeStart());
        zahtjev.setDatumVrijemeEnd(request.getDatumVrijemeEnd());
        zahtjev.setJeJavniEvent(request.getJeJavniEvent());
        zahtjev.setIdKorisnik(request.getIdKorisnik());
        zahtjev.setIdDvorana(request.getIdDvorana());
        zahtjevTerminRepository.save(zahtjev);
    }


    public Long getIdForPrincipal(CustomOAuth2User principal) {
        if (principal == null) throw new IllegalArgumentException("Principal cannot be null");
        if (principal.getUser() == null) throw new IllegalArgumentException("User cannot be null");

        return principal.getUser().getId();
    }


}
