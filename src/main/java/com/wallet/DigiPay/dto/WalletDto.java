package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long id;

    @NotBlank
    private String title;

    private Double balance;

    @NotNull
    private Long userId;

    private String walletNumber;

    private Boolean isActive;


}
