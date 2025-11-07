package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "zahtjev_iznajmljivac")
public class ZahtjevIznajmljivac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    private Instant createdAt = Instant.now();

    public ZahtjevIznajmljivac() {}

    public ZahtjevIznajmljivac(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
