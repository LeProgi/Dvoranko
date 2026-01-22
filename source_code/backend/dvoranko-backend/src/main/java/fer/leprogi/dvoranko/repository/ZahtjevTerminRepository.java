package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.dto.ZahtjevTerminDTO;
import fer.leprogi.dvoranko.model.Termin;
import fer.leprogi.dvoranko.model.ZahtjevTermin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZahtjevTerminRepository extends JpaRepository<ZahtjevTermin, Long> {
    public Optional<ZahtjevTermin> findById(Long id);

    List<ZahtjevTermin> findByDvorana_IdDvorana(Long idDvorana);

    List<ZahtjevTermin> findAllByDvorana_IdDvorana(Long idDvorana);

    List<ZahtjevTermin> findAllByIdKorisnik(Long userId);
}
