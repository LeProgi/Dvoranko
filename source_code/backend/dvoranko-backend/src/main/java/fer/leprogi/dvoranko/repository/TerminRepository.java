package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {


    Iterable<Termin> findByKorisnikId(Long userId);
    Iterable<Termin> findAllByJeJavniEvent(Integer jeJavniEvent);
}
