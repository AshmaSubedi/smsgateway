package com.himalayantechies.smsapp.ui.bulkmessage;

import java.util.List;

public class BulkMessageContract {

    interface view{
        void failedMsg();
        void successSend();
        List<Long> getNumber();
        String getMessage();
        Integer getDeviceId();
        boolean validation(String phone);
    }

    interface presenter{
        void sendBulkSMS();

    }
}
