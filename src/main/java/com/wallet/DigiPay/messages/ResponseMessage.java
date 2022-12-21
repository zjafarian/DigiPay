package com.wallet.DigiPay.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseMessage<T> {

    private T responseClassType;

    private String message;

    private String messageType;

    public static <T> ResponseMessage<T> withResponseData(T classType, String message,String messageType) {
        return new ResponseMessage<T>(classType, message, messageType);
    }


    public static ResponseMessage<Void> empty() {
        return new ResponseMessage<>();
    }


}
