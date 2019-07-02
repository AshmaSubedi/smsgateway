package com.himalayantechies.smsapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.himalayantechies.smsapp.R;
import com.himalayantechies.smsapp.base.BaseActivity;
import com.himalayantechies.smsapp.network.ApiInterface;
import com.himalayantechies.smsapp.network.SetUpRetrofit;
import com.himalayantechies.smsapp.ui.login.LoginActivity;

public class RegisterActivity extends BaseActivity implements RegisterActivityContract.View{


    @BindView(R.id.register_email)
    TextInputEditText email;

    @BindView(R.id.register_password)
    TextInputEditText password;

    @BindView(R.id.register)
    MaterialButton registerBtn;

    RegisterActivityContract.Presenter presenter;
    private ApiInterface apiInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterActivityPresenter(this, SetUpRetrofit.getRetrofitClient(), this);

    }

    @OnClick(R.id.register)
    public void register(){

        presenter.validate();
        presenter.register();

    }

    @Override
    public String getUserEmail() {
        return email.getEditableText().toString().trim();
    }

    @Override
    public String getUserPassword() {
        return password.getEditableText().toString().trim();
    }

    @Override
    public void registerSuccess() {

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @Override
    public void validationError(String errorMsg) {
        Toast.makeText(this, "Validation Error", Toast.LENGTH_SHORT).show();
    }
}
