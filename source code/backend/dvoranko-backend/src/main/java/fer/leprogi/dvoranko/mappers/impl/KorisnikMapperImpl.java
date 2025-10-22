package fer.leprogi.dvoranko.mappers.impl;

import fer.leprogi.dvoranko.domain.dto.KorisnikDto;
import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;
import fer.leprogi.dvoranko.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KorisnikMapperImpl implements Mapper<KorisnikEnitity, KorisnikDto> {

    private ModelMapper modelMapper;

    public KorisnikMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public KorisnikDto mapTo(KorisnikEnitity korisnikEnitity) {
        return modelMapper.map(korisnikEnitity, KorisnikDto.class);
    }

    @Override
    public KorisnikEnitity mapFrom(KorisnikDto korisnikDto) {
        return modelMapper.map(korisnikDto, KorisnikEnitity.class);
    }
}
