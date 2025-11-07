package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.Kategorija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
    boolean existsByNazivKategorija(String nazivKategorija);
}
