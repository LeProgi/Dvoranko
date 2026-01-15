package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zahtjev_oglas")
public class ZahtjevOglas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idZahtjevOglas;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    private LocalDateTime createdAt;

    @Column(name = "nazDvorana")
    private String naziv;

    @Column(name = "opis", columnDefinition = "TEXT")
    private String opis;


    private String daysOpen;

    private Float cijenaPoSatu;

    private Integer kapacitet;

    @ManyToMany
    @JoinTable(
            name = "zahtjev_oglas_kategorija",
            joinColumns = @JoinColumn(name = "idZahtjevOglas"),
            inverseJoinColumns = @JoinColumn(name = "idKategorija")
    )
    private Set<Kategorija> kategorije = new HashSet<>();

    private Long postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private Double latitude;
    private Double longitude;


    @OneToMany(
            mappedBy = "zahtjevOglas",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ZahtjevSlika> slike = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


}