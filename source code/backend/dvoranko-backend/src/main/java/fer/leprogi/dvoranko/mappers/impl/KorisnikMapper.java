package fer.leprogi.dvoranko.mappers.impl;

import fer.leprogi.dvoranko.domain.dto.KorisnikDto;
import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;
import fer.leprogi.dvoranko.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KorisnikMapper implements Mapper<KorisnikEntity, KorisnikDto> {

    private ModelMapper modelMapper;

    public KorisnikMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public KorisnikDto mapTo(KorisnikEntity korisnikEntity) {
        return modelMapper.map(korisnikEntity, KorisnikDto.class);
    }

    @Override
    public KorisnikEntity mapFrom(KorisnikDto korisnikDto) {
        return modelMapper.map(korisnikDto, KorisnikEntity.class);
    }
}
