package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.SlikaDvorana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlikaDvoranaRepository extends JpaRepository<SlikaDvorana, Long> {
}
