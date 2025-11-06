package fer.leprogi.dvoranko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.ZahtjevOglasRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminZahtjevController {

	@Autowired
	private ZahtjevOglasRepository zahtjevRepo;

	@PostMapping("/requests/{id}/approve")
	public ResponseEntity<?> approve(@PathVariable Long id) {
		ZahtjevOglas z = zahtjevRepo.findById(id).orElse(null);
		if (z == null) {
			return ResponseEntity.notFound().build();
		}

		z.approve();
		zahtjevRepo.save(z);

		// Ovdje fali spajanje i kreiranje Dvorana entiteta jer nije jos napravljeno da se moze izradit dvorana jer nemamo podatke o gradovima 
    

		return ResponseEntity.ok("Zahtjev odobren");
	}

	@PostMapping("/requests/{id}/reject")
	public ResponseEntity<?> reject(@PathVariable Long id) {
		ZahtjevOglas z = zahtjevRepo.findById(id).orElse(null);
		if (z == null) {
			return ResponseEntity.notFound().build();
		}
		z.reject();
		zahtjevRepo.save(z);
		return ResponseEntity.ok("Zahtjev odbijen");
	}
}
