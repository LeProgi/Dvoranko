package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.ZahtjevTermin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZahtjevTerminRepository extends JpaRepository<ZahtjevTermin, Long> {
    public Optional<ZahtjevTermin> findById(Long id);
}
