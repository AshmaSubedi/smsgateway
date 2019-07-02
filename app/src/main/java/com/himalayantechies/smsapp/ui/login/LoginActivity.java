package com.himalayantechies.smsapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.himalayantechies.smsapp.MainActivity;
import com.himalayantechies.smsapp.R;
import com.himalayantechies.smsapp.SaveSharedPreferences;
import com.himalayantechies.smsapp.base.BaseActivity;
import com.himalayantechies.smsapp.models.Auth;
import com.himalayantechies.smsapp.network.SetUpRetrofit;
import com.himalayantechies.smsapp.ui.sms.SMSActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements LoginActivityContract.View {

    @BindView(R.id.login_username)
    TextInputEditText edt_username;

    @BindView(R.id.login_password)
    TextInputEditText edt_password;

    @BindView(R.id.login_ok)
    MaterialButton btn_ok;

    Auth auth = new Auth();

    SaveSharedPreferences saveSharedPrefernces;
    LoginActivityContract.Presenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        saveSharedPrefernces = new SaveSharedPreferences(getApplicationContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        presenter = new LoginActivityPresenter(this, SetUpRetrofit.getRetrofitClient(), this);

        if (saveSharedPrefernces.readLoginStatus()){
            startActivity(new Intent(LoginActivity.this, SMSActivity.class));
            finish();
        }

    }

    @OnClick(R.id.login_ok)
    public void onClick(){
//        validate();
        presenter.validation();
        presenter.authenticate(getUserEmail(), getPassword());

    }

//    public void validate() {
//        final String username = edt_username.getText().toString();
//        final String password = edt_password.getText().toString();
//
//        if (TextUtils.isEmpty(username)) {
//            edt_username.setError("Please enter email");
//            edt_username.requestFocus();
//            return;
//        }
//       if (!validatePassword(password)) {
//            edt_password.setError("Password must contain mix of upper and lower case letters as well as digits and one special charecter(4-20)");
//        }
//
//
//    }

//    public boolean validatePassword(final String password){
//        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(password);
//        return matcher.matches();
//    }


    @Override
    public String getUserEmail() {
        return edt_username.getEditableText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edt_password.getEditableText().toString().trim();
    }

    @Override
    public void validationError(String errorMessage) {
        Toast.makeText(this, "Validation Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loginError() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
//         if (saveSharedPrefernces.readLoginStatus()){
        startActivity(new Intent(LoginActivity.this, SMSActivity.class));
        finish();
//        }
    }

    @Override
    public String getToken() {
        return auth.getToken();
    }

}
