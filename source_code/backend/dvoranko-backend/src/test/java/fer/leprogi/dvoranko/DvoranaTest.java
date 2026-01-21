package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.dto.DvoranaDTO;
import fer.leprogi.dvoranko.model.Dvorana;
import fer.leprogi.dvoranko.repository.DvoranaRepository;
import fer.leprogi.dvoranko.service.DvoranaService;
import fer.leprogi.dvoranko.utils.DtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DvoranaTest {

    @Mock
    private DvoranaRepository dvoranaRepository;
    @InjectMocks
    private DvoranaService dvoranaService;
    @Mock
    private DtoMapper dtoMapper;

    @Test
    @AutoConfigureTestDatabase
    void gettingExistingDvoranaById() { //provjerava je li dobro radi funkcija getDvoranaById
        Dvorana dvorana1 = new Dvorana();
        dvorana1.setIdDvorana(1L);
        dvorana1.setNazivDvorana("Dvorana Lebrona Jamesa");

        DvoranaDTO dto = new DvoranaDTO();
        dto.setIdDvorana(1L);
        dto.setNazivDvorana("Dvorana Lebrona Jamesa");

        when(dvoranaRepository.findById(1L)).thenReturn(java.util.Optional.of(dvorana1));
        when(dtoMapper.toDvoranaDTO(dvorana1)).thenReturn(dto);

        DvoranaDTO dvoranaIzBaze = dvoranaService.getDvoranaById(1L);

        assertEquals(dvoranaIzBaze.getIdDvorana(), dvorana1.getIdDvorana());
        assertEquals(dvoranaIzBaze.getNazivDvorana(), dvorana1.getNazivDvorana());
    }

}
