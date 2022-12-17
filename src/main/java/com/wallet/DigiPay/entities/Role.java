package com.wallet.DigiPay.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class Role extends BaseModel{




    @Column(name = "rol_description")
    private String description;



    @Enumerated(EnumType.STRING)
    @Column(name = "rol_type")
    private RoleType roleType;


    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RoleDetail> roleDetails;







    public Role() {
    }

    public Role(String description, RoleType roleType) {
        this.description = description;
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType rollType) {
        this.roleType = rollType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<RoleDetail> getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(Set<RoleDetail> roleDetails) {
        this.roleDetails = roleDetails;
    }
}
