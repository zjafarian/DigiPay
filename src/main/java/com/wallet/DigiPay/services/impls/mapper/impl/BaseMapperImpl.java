package com.wallet.DigiPay.services.impls.mapper.impl;



import com.wallet.DigiPay.services.impls.mapper.BaseMapper;


public abstract class BaseMapperImpl<DTO,OBJ> implements BaseMapper<DTO,OBJ> {
    protected BaseMapper<DTO,OBJ> getBaseMapper;
    @Override
    public DTO mapToDTO(OBJ obj) {
        return getBaseMapper.mapToDTO(obj);
    }

    @Override
    public OBJ mapToObject(DTO dto) {
        return getBaseMapper.mapToObject(dto);
    }
}
