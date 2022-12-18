package com.wallet.DigiPay.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


public interface BaseMapper<DTO,OBJ> {


    DTO mapToDTO(OBJ obj);

    OBJ mapToObject(DTO dto);

}
