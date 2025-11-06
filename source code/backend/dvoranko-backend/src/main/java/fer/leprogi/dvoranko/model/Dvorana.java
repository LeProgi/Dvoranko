package fer.leprogi.dvoranko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "kategorije")
public class Dvorana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDvorana;

    @NotBlank(message = "Naziv dvorane ne smije biti prazan.")
    private String nazivDvorana;

    private Integer kapacitet;

    private String opis;


    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAdresa", nullable = false)
    private Adresa adresa;

    @ManyToMany
    @JoinTable(name = "dvorana_kategorija",
            joinColumns = @JoinColumn(name = "idDvorana"),
            inverseJoinColumns = @JoinColumn(name = "idKategorija"))
    private Set<Kategorija> kategorije = new HashSet<>();

}
