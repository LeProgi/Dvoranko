package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateTerminRequest;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import fer.leprogi.dvoranko.service.UserService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, String>> userDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to user dashboard",
                "access", "USER, MODERATOR, ADMIN"
        ));
    }


    @GetMapping("/getMyReservations")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<Iterable<TerminDTO>>> getAllReservationsForUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        Iterable<TerminDTO> termini = userService.getAllReservationsForUser(principal);

        return ResponseEntity.ok(ApiResponse.success(termini, "My reservations fetched successfully"));
    }

    @PostMapping("/request/getModerator")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createModeratorRequest(@AuthenticationPrincipal CustomOAuth2User principal) {

        ZahtjevIznajmljivac zahtjev = userService.createModeratorRequest(principal);

        return ResponseEntity.ok("request created");
    }

    @PostMapping("/request/createZahtjevTermin")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTerminRequest(@RequestBody CreateTerminRequest request) {
        try {
            userService.createTerminRequest(request);
            return ResponseEntity.ok("termin request created");
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User principal) {
        UserDTO userdto = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userdto, "successfully returned user"));
    }
}