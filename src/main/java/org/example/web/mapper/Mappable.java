package org.example.web.mapper;

public interface Mappable<E, D> {
    //E - Entity
    //D - DTO
    E toEntity(D d);

    // turn DTO object into DTO
    D toDTO(E entity);
    //turn Entity to DTO
}
