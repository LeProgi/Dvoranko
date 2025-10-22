package fer.leprogi.dvoranko.repositories;

import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepository extends CrudRepository<KorisnikEnitity, Long> {
}
