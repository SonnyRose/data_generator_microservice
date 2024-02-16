package org.example.web.mapper;

import org.example.model.Data;
import org.example.web.DTO.DataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataMapper extends Mappable<Data, DataDTO> {
}
