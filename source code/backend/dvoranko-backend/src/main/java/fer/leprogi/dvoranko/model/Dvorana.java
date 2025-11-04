package fer.leprogi.dvoranko.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dvorana {
    @Id
    private Long idDvorana;
    private String nazivDvorana;
    private Integer kapacitet;
    private String opis;
    private String koordinate;

}
