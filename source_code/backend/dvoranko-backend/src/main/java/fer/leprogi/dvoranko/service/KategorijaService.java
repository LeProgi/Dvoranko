package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.KategorijaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateKategorijaRequest;
import fer.leprogi.dvoranko.model.Kategorija;
import fer.leprogi.dvoranko.repository.KategorijaRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KategorijaService {

    private final KategorijaRepository kategorijaRepository;
    private final DtoMapper dtoMapper;

    public KategorijaDTO createKategorija(CreateKategorijaRequest request){
        if (kategorijaRepository.existsByNazivKategorija(request.getNazivKategorije()))
            throw new IllegalArgumentException("Kategorija with name " + request.getNazivKategorije() + " already exists");

        Kategorija kategorija = new Kategorija();
        kategorija.setNazivKategorija(request.getNazivKategorije());

        Kategorija saved = kategorijaRepository.save(kategorija);

        return dtoMapper.toKategorijaDTO(saved);
    }

    public KategorijaDTO getKategorijaById(Long idKategorija){
        Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                .orElseThrow(() -> new ResourceNotFoundException("Kategorija with idKategorija " + idKategorija + " does not exist"));

        return dtoMapper.toKategorijaDTO(kategorija);
    }

    public Iterable<KategorijaDTO> getAllKategorije(){
        return kategorijaRepository
                .findAll()
                .stream()
                .map(dtoMapper::toKategorijaDTO)
                .collect(Collectors.toList());
    }

    public KategorijaDTO updateKategorija(Long idKategorija, CreateKategorijaRequest request){
        Kategorija kategorija = kategorijaRepository.findById(idKategorija)
                .orElseThrow(() -> new ResourceNotFoundException("Kategorija with idKategorija " + idKategorija + " does not exist"));

        if (kategorijaRepository.existsByNazivKategorija(request.getNazivKategorije()))
            throw new IllegalArgumentException("Kategorija with name " + request.getNazivKategorije() + " already exists");

        kategorija.setNazivKategorija(request.getNazivKategorije());

        Kategorija saved = kategorijaRepository.save(kategorija);

        return dtoMapper.toKategorijaDTO(saved);
    }

    public void deleteKategorija(Long idKategorija){
        if (!kategorijaRepository.existsById(idKategorija)) {
            throw new ResourceNotFoundException("Kategorija with idKategorija " + idKategorija + " does not exist");
        }
        kategorijaRepository.deleteById(idKategorija);
    }
}
