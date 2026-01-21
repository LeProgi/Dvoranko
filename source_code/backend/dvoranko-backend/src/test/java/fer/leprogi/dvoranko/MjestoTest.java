package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import fer.leprogi.dvoranko.service.MjestoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import fer.leprogi.dvoranko.utils.DtoMapper;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MjestoTest {

    @InjectMocks
    private MjestoService mjestoService;

    @Mock
    private MjestoRepository mjestoRepository;

    @Mock
    private DtoMapper dtoMapper;

    @Test
    void createMjestoKreiranjeIstogMjestaDvaPuta() {
        CreateMjestoRequest request = new CreateMjestoRequest();
        request.setPostanskiBroj(10000L);
        request.setNazivMjesto("Zagreb");

        when(mjestoRepository.existsByPostanskiBroj(10000L))
            .thenReturn(false)
                .thenReturn(true);

        Mjesto savedMjesto = new Mjesto();
        savedMjesto.setPostanskiBroj(10000L);
        savedMjesto.setNazivMjesto("Zagreb");

        when(mjestoRepository.save(any(Mjesto.class))).thenReturn(savedMjesto);

        MjestoDTO dto = new MjestoDTO();
        dto.setPostanskiBroj(10000L);
        dto.setNazivMjesto("Zagreb");
        when(dtoMapper.toMjestoDTO(savedMjesto)).thenReturn(dto);

        assertDoesNotThrow(() -> mjestoService.createMjesto(request)); //prvo treba proć
        assertThrows(IllegalArgumentException.class, () -> mjestoService.createMjesto(request)); //pa na drugom pokusaju bacit exception
    }
}
