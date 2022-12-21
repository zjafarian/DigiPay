package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Getter
@Setter
public class RoleRequestDto {

    private Long id;

    @NotNull
    private RoleType roleType;

    private String roleDescription;


}
