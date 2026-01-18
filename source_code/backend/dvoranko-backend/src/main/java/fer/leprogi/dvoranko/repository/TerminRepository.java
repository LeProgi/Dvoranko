package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {


    Iterable<Termin> findByKorisnikId(Long userId);
    List<Termin> findAllByJeJavniEvent(Integer jeJavniEvent);

}
