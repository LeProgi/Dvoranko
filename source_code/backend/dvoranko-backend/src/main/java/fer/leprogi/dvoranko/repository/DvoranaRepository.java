package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.Dvorana;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DvoranaRepository extends JpaRepository<Dvorana, Long> {
    List<Dvorana> findByKategorijeIdKategorija(Long idKategorija);

    List<Dvorana> findAllByVlasnik_Id(Long vlasnikId);
}
