package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, String>> userDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to user dashboard",
                "access", "USER, MODERATOR, ADMIN"
        ));
    }


}