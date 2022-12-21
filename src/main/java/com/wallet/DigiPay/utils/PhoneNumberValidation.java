package com.wallet.DigiPay.utils;

public class PhoneNumberValidation {
    public static boolean validationPhoneNumber(String phone){
        //phone number will check with pattern that it will match or not
        return phone.matches("(\\+98|0)?9\\d{9}");
    }
}
