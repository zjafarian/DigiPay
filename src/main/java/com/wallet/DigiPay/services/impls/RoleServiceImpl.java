package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.RoleService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;


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
}
