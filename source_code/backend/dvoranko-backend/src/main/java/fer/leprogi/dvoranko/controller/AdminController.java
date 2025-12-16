package fer.leprogi.dvoranko.controller;


import fer.leprogi.dvoranko.dto.ZahtjevIznajmljivacDTO;
import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.repository.ZahtjevIznajmljivacRepository;
import fer.leprogi.dvoranko.service.AdminService;

import fer.leprogi.dvoranko.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> adminDashboard() {
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to admin dashboard",
                "access", "ADMIN only"
        ));
    }

    @PostMapping("request/moderator/{id}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptRequest(@PathVariable Long id) {
        User user = adminService.acceptIznajmljivacRequest(id);
        return ResponseEntity.ok("user promoted");
    }

    @PostMapping("/request/moderator/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id) {
        User user = adminService.rejectIznajmljivacRequest(id);
        return ResponseEntity.ok("request rejected");
    }


    @PostMapping("/requests/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approve(@PathVariable Long id) {
        Dvorana dvorana = adminService.approveOglasRequest(id);   

		return ResponseEntity.ok("Zahtjev odobren");
	}

	@PostMapping("/requests/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> reject(@PathVariable Long id) {
        adminService.rejectOglasRequest(id);
		return ResponseEntity.ok("Zahtjev odbijen");
	}

    @GetMapping("/getall/zahtjeviznajmljivac")
    //@PreAuthorize("hasRole('ADMIN')") OVO KASNIJE TREBA OBAVITI!!!
    public ResponseEntity<ApiResponse<Iterable<ZahtjevIznajmljivacDTO>>> getAllZahtjeviznamljivac() {
        Iterable<ZahtjevIznajmljivacDTO> zahtjeviIznajmljivac = adminService.getAllIznajmljivacRequests();
        return ResponseEntity.ok(ApiResponse.success(zahtjeviIznajmljivac, "zahtjevi za iznajmljivac retrieved succesfully"));
    }

    @GetMapping("/getall/zahtjevidvorana")
    //@PreAuthorize("hasRole('ADMIN')") OVO TREBA KASNIJE OBAVITI!!!
    public ResponseEntity<ApiResponse<Iterable<ZahtjevOglasDTO>>> getAllZahtjevidvorana() {
        Iterable<ZahtjevOglasDTO> zahtjeviOglas = adminService.getAllDvoranaRequests();
        return ResponseEntity.ok(ApiResponse.success(zahtjeviOglas, "zahtjevi za dvorane retrieved succesfully"));
    }

}