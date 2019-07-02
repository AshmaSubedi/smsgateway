package com.himalayantechies.smsapp.ui.login;

import com.himalayantechies.smsapp.models.Auth;

public interface LoginActivityContract {
    interface View{

        String getUserEmail();

        String getPassword();

        void validationError(String errorMessage);

        void loginError();

        void loginSuccess();

        String getToken();

    }

    interface Presenter{

        void authenticate(String username, String password);

        void validation();

        void saveUserEmail(boolean isRemember);

    }

}
