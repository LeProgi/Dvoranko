package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.AdresaDTO;
import fer.leprogi.dvoranko.dto.createRequest.CreateAdresaRequest;
import fer.leprogi.dvoranko.model.Adresa;
import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.repository.AdresaRepository;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdresaService {
    private final AdresaRepository adresaRepository;
    private final MjestoRepository mjestoRepository;
    private final DtoMapper dtoMapper;

    public AdresaDTO createAdresa(CreateAdresaRequest request) {
        Mjesto mjesto = mjestoRepository.findById(request.getIdMjesto())
                .orElseThrow(() -> new IllegalArgumentException("Mjesto with idMjesta " + request.getIdMjesto() + " does not exist"));

        Adresa adresa = new Adresa();
        adresa.setLatitude(request.getLatitude());
        adresa.setLongitude(request.getLongitude());
        adresa.setUlica(request.getUlica());
        adresa.setKucniBroj(request.getKucniBroj());
        adresa.setMjesto(mjesto);

        Adresa saved = adresaRepository.save(adresa);

        return dtoMapper.toAdresaDTO(saved);
    }

    public AdresaDTO getAdresaById(Long idAdresa){
        Adresa adresa = adresaRepository.findById(idAdresa)
                .orElseThrow(() -> new ResourceNotFoundException("Adresa with koordinate " + idAdresa + " does not exist"));

        return dtoMapper.toAdresaDTO(adresa);
    }

    public Iterable<AdresaDTO> getAllAdrese(){
        return adresaRepository.findAll()
                .stream()
                .map(adresa -> dtoMapper.toAdresaDTO(adresa))
                .collect(Collectors.toList());
    }

    public AdresaDTO updateAdresa(Long idAdresa, CreateAdresaRequest request) {
        Adresa adresa = adresaRepository.findById(idAdresa)
                .orElseThrow(() -> new ResourceNotFoundException("Adresa with koordinate " + idAdresa + " does not exist"));

        Mjesto mjesto = mjestoRepository.findById(request.getIdMjesto())
                        .orElseThrow(() -> new ResourceNotFoundException("Mjesto with idMjesta " + request.getIdMjesto() + " does not exist"));

        adresa.setLatitude(request.getLatitude());
        adresa.setLongitude(request.getLongitude());
        adresa.setUlica(request.getUlica());
        adresa.setKucniBroj(request.getKucniBroj());
        adresa.setMjesto(mjesto);

        Adresa updated = adresaRepository.save(adresa);

        return dtoMapper.toAdresaDTO(updated);
    }

    public void deleteAdresa(Long idAdresa) {
        if (!adresaRepository.existsById(idAdresa)) {
            throw new ResourceNotFoundException("Adresa with idAdresa " + idAdresa + " does not exist");
        }
        adresaRepository.deleteById(idAdresa);
    }

}
