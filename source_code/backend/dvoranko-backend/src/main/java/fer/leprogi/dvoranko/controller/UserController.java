package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.dto.TerminDTO;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.service.UserService;
import fer.leprogi.dvoranko.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Iterable<TerminDTO>>> getAllReservationsForUser(Principal principal) {

        Iterable<TerminDTO> termini = userService.getAllReservationsForUser(principal);

        return ResponseEntity.ok(ApiResponse.success(termini, "My reservations fetched successfully"));
    }




}