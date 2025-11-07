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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/request")
public class UserRequestController {

    @Autowired
    private UserService userService;

    @GetMapping("/getModerator")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createModeratorRequest(@AuthenticationPrincipal CustomOAuth2User principal) {

         ZahtjevIznajmljivac zahtjev = userService.createModeratorRequest(principal);

        return ResponseEntity.ok("request created");
    }
}
