package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.ZahtjevIznajmljivac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZahtjevIznajmljivacRepository extends JpaRepository<ZahtjevIznajmljivac, Long> {
    Optional<ZahtjevIznajmljivac> findByUserId(Long userId);
}
