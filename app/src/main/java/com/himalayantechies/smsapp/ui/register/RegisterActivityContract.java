package com.himalayantechies.smsapp.ui.register;

public interface RegisterActivityContract {


    interface View{

        String getUserEmail();

        String getUserPassword();

        void registerSuccess();

        void validationError(String errorMsg);

    }

    interface Presenter{
        void register();
        void validate();
    }
}
