package fer.leprogi.dvoranko.repositories;

import fer.leprogi.dvoranko.domain.entity.IznajmljivacEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IznajmljivacRepository extends JpaRepository<IznajmljivacEntity, Long> {
}
