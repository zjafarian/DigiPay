package com.wallet.DigiPay.base.baseServide;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T,ID extends Serializable> {

    T save (T entity);
    List<T> saveAll(List<T> entities);

    Optional<T> findById(ID id);
    List<T> findAll();

    void delete(ID id);
    T update(T entity);

    List<T> findAllById(Iterable<ID> ids);




}
