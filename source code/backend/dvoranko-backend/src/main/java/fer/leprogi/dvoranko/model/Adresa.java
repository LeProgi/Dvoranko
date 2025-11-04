package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresa {
    @Id
    private String koordinate;

    private String ulica;

    private String broj;

    @ManyToOne
    @JoinColumn(name = "idMjesto", nullable = false)
    private Mjesto mjesto;
}
