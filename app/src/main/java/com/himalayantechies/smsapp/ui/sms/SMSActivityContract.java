package com.himalayantechies.smsapp.ui.sms;

public interface SMSActivityContract {

    interface view{
        void failedMsg();
        void successSend();
        String getNumber();
        String getMessage();
        Integer getDeviceId();
        boolean validation(String phone);
    }

    interface presenter{
        void sendSMS(String number, String message, String token );

    }
}
