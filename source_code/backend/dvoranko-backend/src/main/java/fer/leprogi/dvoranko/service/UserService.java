package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.model.User;

import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
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




}
