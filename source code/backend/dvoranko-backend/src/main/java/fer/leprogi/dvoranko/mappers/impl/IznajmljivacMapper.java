package fer.leprogi.dvoranko.mappers.impl;

import fer.leprogi.dvoranko.domain.dto.IznajmljivacDto;
import fer.leprogi.dvoranko.domain.entity.IznajmljivacEntity;
import fer.leprogi.dvoranko.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class IznajmljivacMapper implements Mapper<IznajmljivacEntity, IznajmljivacDto> {

    ModelMapper mapper;

    public IznajmljivacMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public IznajmljivacDto mapTo(IznajmljivacEntity iznajmljivacEntity) {
        return mapper.map(iznajmljivacEntity, IznajmljivacDto.class);
    }

    @Override
    public IznajmljivacEntity mapFrom(IznajmljivacDto iznajmljivacDto) {
        return mapper.map(iznajmljivacDto, IznajmljivacEntity.class);
    }
}
