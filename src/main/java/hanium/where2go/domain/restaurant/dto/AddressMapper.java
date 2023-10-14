package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.restaurant.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address addressDtoToAddress(MapDto.AddressDto addressDto);

    MapDto.AddressDto addressToAddressDto(Address address);
}
