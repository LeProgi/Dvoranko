package fer.leprogi.dvoranko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fer.leprogi.dvoranko.model.ZahtjevOglas;

@Repository
public interface ZahtjevOglasRepository extends JpaRepository<ZahtjevOglas, Long> {
}