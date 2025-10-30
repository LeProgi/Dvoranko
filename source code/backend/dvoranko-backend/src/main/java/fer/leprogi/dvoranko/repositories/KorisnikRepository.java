package fer.leprogi.dvoranko.repositories;

import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<KorisnikEntity, Long> {
    Optional<KorisnikEntity> findByEmail(String email);

    Optional<KorisnikEntity> findByIdKorisnik(Long idKorisnik);
}
