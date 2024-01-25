package org.example.web.mapper;

import org.example.model.test.DataTestOptions;
import org.example.web.DTO.DataTestOptionsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataTestOptionsMapper extends Mappable<DataTestOptions, DataTestOptionsDTO> {
}
