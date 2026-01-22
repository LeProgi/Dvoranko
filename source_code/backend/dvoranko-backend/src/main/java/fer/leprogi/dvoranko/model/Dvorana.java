package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "dvorana", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Termin> termini;

    private String opis;

    private String daysOpen;

    private Float cijenaPoSatu;

    @NotNull
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAdresa", nullable = false)
    private Adresa adresa;

    @ManyToMany
    @JoinTable(name = "dvorana_kategorija",
            joinColumns = @JoinColumn(name = "idDvorana"),
            inverseJoinColumns = @JoinColumn(name = "idKategorija"))
    private Set<Kategorija> kategorije = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "idVlasnik")
    private User vlasnik;

    @OneToMany(
            mappedBy = "dvorana",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SlikaDvorana> slike = new ArrayList<>();

    @OneToMany(
            mappedBy = "dvorana",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ZahtjevTermin>  zahtjevTermins = new ArrayList<>();


}
