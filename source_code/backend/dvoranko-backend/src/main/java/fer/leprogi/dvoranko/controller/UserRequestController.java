package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateTerminRequest;
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
@RequestMapping("/api/public/user/request")
public class UserRequestController {

    @Autowired
    private UserService userService;

    @PostMapping("/getModerator")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createModeratorRequest(@AuthenticationPrincipal CustomOAuth2User principal) {

         ZahtjevIznajmljivac zahtjev = userService.createModeratorRequest(principal);

        return ResponseEntity.ok("request created");
    }

    @PostMapping("/createZahtjevTermin")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTerminRequest(@RequestBody CreateTerminRequest request) {
        userService.createTerminRequest(request);
        return ResponseEntity.ok("termin request created");
    }

}
