package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserRequestController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final ZahtjevIznajmljivacRepository zahtjevRepository;

    public UserRequestController(UserRepository userRepository, ZahtjevIznajmljivacRepository zahtjevRepository) {
        this.userRepository = userRepository;
        this.zahtjevRepository = zahtjevRepository;
    }

    @GetMapping("/getModerator")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createModeratorRequest(@AuthenticationPrincipal CustomOAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        UserDTO userDTO = userService.convertToDTO(principal.getUser());


        if (userDTO == null) {
            return ResponseEntity.status(401).body("unauthenticated");
        }
        Optional<User> u = userRepository.findById(userDTO.getId());
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("user not found");
        }
        User user = u.get();
        Optional<ZahtjevIznajmljivac> existing = zahtjevRepository.findByUserId(user.getId());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("request already exists");
        }
        ZahtjevIznajmljivac zahtjev = new ZahtjevIznajmljivac(user);
        zahtjevRepository.save(zahtjev);
        return ResponseEntity.ok("request created");
    }
}
