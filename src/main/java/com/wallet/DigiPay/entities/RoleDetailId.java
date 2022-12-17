package com.wallet.DigiPay.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoleDetailId implements Serializable {


    private Long roleId;

    private Long userId;

    public RoleDetailId(Long roleId, Long userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public RoleDetailId() {

    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleId(), getUserId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RoleDetailId)) return false;
        RoleDetailId roleDetailId = (RoleDetailId) obj;
        return Objects.equals(getRoleId(), roleDetailId.roleId) && Objects.equals(getUserId(), roleDetailId.userId);
    }
}
