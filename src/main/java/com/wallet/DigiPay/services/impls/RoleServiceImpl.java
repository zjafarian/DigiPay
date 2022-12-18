package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.RoleService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {


    private RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    protected BaseRepository<Role, Long> getBaseRepository() {
        return roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return super.findAll();
    }

    @Override
    public Role save(Role entity) {
        return super.save(entity);
    }

    @Override
    public List<Role> saveAll(List<Role> entities) {
        return super.saveAll(entities);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public Role update(Role entity) {
        return super.update(entity);
    }

    @Override
    public List<Role> findAllById(Iterable<Long> ids) {
        return roleRepository.findAllById(ids);
    }
}
