package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.createRequest.CreateMjestoRequest;
import fer.leprogi.dvoranko.dto.MjestoDTO;
import fer.leprogi.dvoranko.model.Mjesto;
import fer.leprogi.dvoranko.repository.MjestoRepository;
import fer.leprogi.dvoranko.utils.DtoMapper;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MjestoService {

    private final MjestoRepository mjestoRepository;
    private final DtoMapper dtoMapper;

    public MjestoDTO createMjesto(CreateMjestoRequest request) {
        if (mjestoRepository.existsByPostanskiBroj(request.getPostanskiBroj()))
            throw new IllegalArgumentException("Mjesto with postanski broj " + request.getPostanskiBroj() + " already exists");

        Mjesto mjesto = new Mjesto();
        mjesto.setPostanskiBroj(request.getPostanskiBroj());
        mjesto.setNazivMjesto(request.getNazivMjesto());

        Mjesto saved = mjestoRepository.save(mjesto);

        return dtoMapper.toMjestoDTO(saved);
    }

    public MjestoDTO getMjestoById(Long idMjesto) {
        Mjesto mjesto = mjestoRepository.findById(idMjesto)
                .orElseThrow(() -> new IllegalArgumentException("Mjesto with idMjesto " + idMjesto + " does not exist"));

        return dtoMapper.toMjestoDTO(mjesto);
    }

    public MjestoDTO getMjestoByPostanskiBroj(Long postanskiBroj) {
        Mjesto mjesto = mjestoRepository.findByPostanskiBroj(postanskiBroj)
                .orElseThrow(() -> new ResourceNotFoundException("Mjesto with poštanski broj " + postanskiBroj + " does not exist"));

        return dtoMapper.toMjestoDTO(mjesto);
    }



    public Iterable<MjestoDTO> getAllMjesta() {
        return mjestoRepository.findAll()
                .stream()
                .map(mjesto -> dtoMapper.toMjestoDTO(mjesto))
                .collect(Collectors.toList());
    }

    public MjestoDTO updateMjesto(Long idMjesto, CreateMjestoRequest request) {
        Mjesto mjesto = mjestoRepository.findById(idMjesto)
                .orElseThrow(() -> new ResourceNotFoundException("Mjesto with id " + idMjesto + " does not exist"));

        // ako novi pbr vec postoji
        if (!mjesto.getPostanskiBroj().equals(request.getPostanskiBroj()) && mjestoRepository.existsByPostanskiBroj(request.getPostanskiBroj()))
            throw new IllegalArgumentException("Mjesto s poštanskim brojem " + request.getPostanskiBroj() + " već postoji");

        mjesto.setPostanskiBroj(request.getPostanskiBroj());
        mjesto.setNazivMjesto(request.getNazivMjesto());

        Mjesto updated = mjestoRepository.save(mjesto);

        return dtoMapper.toMjestoDTO(updated);
    }


    public void deleteMjesto(Long idMjesto) {
        if (!mjestoRepository.existsById(idMjesto))
            throw new ResourceNotFoundException("Mjesto with id " + idMjesto + " does not exist");

        mjestoRepository.deleteById(idMjesto);
    }
}

