package fer.leprogi.dvoranko.controller;

import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ZahtjevIznajmljivacRepository zahtjevRepository;
    private final UserRepository userRepository;

    public AdminController(ZahtjevIznajmljivacRepository zahtjevRepository, UserRepository userRepository) {
        this.zahtjevRepository = zahtjevRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> adminDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to admin dashboard",
                "access", "ADMIN only"
        ));
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptRequest(@PathVariable Long id) {
        Optional<ZahtjevIznajmljivac> z = zahtjevRepository.findById(id);
        if (z.isEmpty()) {
            return ResponseEntity.status(404).body("request not found");
        }
        ZahtjevIznajmljivac zahtjev = z.get();
        User user = userRepository.findById(zahtjev.getUser().getId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("user not found");
        }
        user.setRole(Role.MODERATOR);
        userRepository.save(user);
        zahtjevRepository.delete(zahtjev);
        return ResponseEntity.ok("user promoted");
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id) {
        Optional<ZahtjevIznajmljivac> z = zahtjevRepository.findById(id);
        if (z.isEmpty()) {
            return ResponseEntity.status(404).body("request not found");
        }
        ZahtjevIznajmljivac zahtjev = z.get();
        zahtjevRepository.delete(zahtjev);
        return ResponseEntity.ok("request rejected");
    }
}