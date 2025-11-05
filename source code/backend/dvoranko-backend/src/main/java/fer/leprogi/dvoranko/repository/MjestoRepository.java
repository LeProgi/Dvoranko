package fer.leprogi.dvoranko.repository;

import fer.leprogi.dvoranko.model.Mjesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MjestoRepository extends JpaRepository<Mjesto, Long> {

    boolean existsByPostanskiBroj(Long postanskiBroj);

    Optional<Mjesto> findByPostanskiBroj(Long postanskiBroj);
}
