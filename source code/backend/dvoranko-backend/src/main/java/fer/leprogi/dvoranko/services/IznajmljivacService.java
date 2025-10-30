package fer.leprogi.dvoranko.services;

import fer.leprogi.dvoranko.domain.entity.IznajmljivacEntity;

import java.util.List;

public interface IznajmljivacService{
    IznajmljivacEntity createIznajmljivac(String oib, Long korisnikId);

    IznajmljivacEntity getIznajmljivacById(Long id);
}
