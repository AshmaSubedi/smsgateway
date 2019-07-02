package com.himalayantechies.smsapp.base;

public interface BaseActivityContract {

    interface View{

        void showToolbar();

        void hideToolbar();

        void successSnackBar(String message);

        void errorSnackBar(String message);

        void showProgressBar();

        void hideProgressBar();

        void removeBackButton();

    }

    interface Presenter {

        void showToolbar(boolean show);

        void showSnackBar(String message, boolean isSucess);

        void showProgressBar(boolean show);

    }
}
