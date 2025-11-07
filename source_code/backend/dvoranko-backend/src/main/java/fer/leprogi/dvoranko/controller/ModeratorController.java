
package fer.leprogi.dvoranko.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> moderatorDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to moderator dashboard",
                "access", "MODERATOR, ADMIN"
        ));
    }
}