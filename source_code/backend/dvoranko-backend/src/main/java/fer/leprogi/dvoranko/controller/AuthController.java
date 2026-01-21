package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        UserDTO userDTO = userService.convertToDTO(userRepository.findByGoogleId(principal.getUser().getGoogleId()).get());
        return ResponseEntity.ok(userDTO);
    }
}
