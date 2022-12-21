package com.wallet.DigiPay.services.impls;



import com.wallet.DigiPay.dto.RoleRequestDto;
import com.wallet.DigiPay.entities.Role;

import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.mapper.RoleMapper;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.RoleService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {


    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    private ErrorMessages errorMessages;


    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,RoleMapper roleMapper, ErrorMessages errorMessages) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.errorMessages = errorMessages;
    }

    public Role generateRole(RoleRequestDto roleRequestDto){
       return roleMapper.mapToObject(roleRequestDto);
    }

    public RoleRequestDto generateRoleDto(Role role){
      return  roleMapper.mapToDTO(role);

    }


    @Override
    protected BaseRepository<Role, Long> getBaseRepository() {
        return roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role entity) {
        if (entity.getRoleType() == null)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());

        return roleRepository.save(entity);
    }

    @Override
    public List<Role> saveAll(List<Role> entities) {
        return roleRepository.saveAll(entities);
    }

    @Override
    public Optional<Role> findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);

        if (!role.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_ROLE());

        return roleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role update(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public List<Role> findAllById(Iterable<Long> ids) {
        return roleRepository.findAllById(ids);
    }
}
