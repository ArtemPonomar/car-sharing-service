package com.example.carsharingservice.dto.mapper;

public interface DtoMapper<M, Q, R> {
    M toModel(Q requestDto);

    R toDto(M model);
}
