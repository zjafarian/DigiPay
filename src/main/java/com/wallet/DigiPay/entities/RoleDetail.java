package com.wallet.DigiPay.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "t_roluse")
public class RoleDetail {

    @EmbeddedId
    private RoleDetailId roleDetailId;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roluse_role_id")
    @JsonIgnore
    private Role role;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "roluse_user_id")
    @JsonIgnore
    private User user;

    public RoleDetail() {
    }

    public RoleDetail(Role role, User user) {
        this.role = role;
        this.user = user;
        roleDetailId = new RoleDetailId(role.getId(), user.getId());
    }

    public RoleDetailId getRoleDetailId() {
        return roleDetailId;
    }

    public void setRoleDetailId(RoleDetailId rollDetailId) {
        this.roleDetailId = rollDetailId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roll) {
        this.role = roll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
