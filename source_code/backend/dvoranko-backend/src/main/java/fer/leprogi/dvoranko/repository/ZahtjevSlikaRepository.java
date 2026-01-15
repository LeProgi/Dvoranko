package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.ZahtjevSlika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZahtjevSlikaRepository extends JpaRepository<ZahtjevSlika, Long> {
}
