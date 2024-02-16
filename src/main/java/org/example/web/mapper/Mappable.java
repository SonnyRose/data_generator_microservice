package org.example.web.mapper;

public interface Mappable<E, D> {
    E toEntity(D d);
    D toDTO(E entity);
}
