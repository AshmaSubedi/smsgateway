package com.himalayantechies.smsapp.ui.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.himalayantechies.smsapp.R;
import com.himalayantechies.smsapp.SaveSharedPreferences;
import com.himalayantechies.smsapp.network.SetUpRetrofit;

import java.util.List;

public class SMSActivity extends AppCompatActivity implements SMSActivityContract.view{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private EditText smsText, phoneNumber;
    SaveSharedPreferences saveSharedPrefernces;
    private Button buttonSendSMS;
    private BroadcastReceiver sentStatusReceiver, deliveredStatusReceiver;
    private SMSActivityContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        saveSharedPrefernces =  new SaveSharedPreferences(this);

        smsText = findViewById(R.id.message);
        phoneNumber = findViewById(R.id.phone_number);
        buttonSendSMS = findViewById(R.id.send_button);

        presenter = new SMSActivityPresenter(this, SetUpRetrofit.getRetrofitClient(), this);

        buttonSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSendSMS.setEnabled(false);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSmsPermission()){
                        sendSms();
                        callRetrofit();
                    }else{
                        requestPermission();
                    }
                }
                buttonSendSMS.setEnabled(true);
            }
        });

    }

    private void callRetrofit() {
        presenter.sendSMS(getNumber(), getMessage(), saveSharedPrefernces.readToken());
    }

    private boolean checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            return false;
        }else {
            return true;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SEND_SMS);
    }

    private void sendSms(){
        String message = smsText.getText().toString().trim();
        String phoneNum = phoneNumber.getText().toString().trim();
        if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNum)) {
            SmsManager smsManager = SmsManager.getDefault();
            List<String> messageList = smsManager.divideMessage(message);
            for (String msg : messageList) {
                PendingIntent sentIntent =
                        PendingIntent.getBroadcast(this, 1, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredIntent =
                        PendingIntent.getBroadcast(this, 1, new Intent("SMS_DELIVERED"), 0);
                smsManager.sendTextMessage(phoneNum, null, msg, sentIntent, deliveredIntent);
            }
        }else{
            Toast.makeText(SMSActivity.this, "Phone number and Message cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void sendSmsForMultipleSim(){
    }

    public void onResume(){
        super.onResume();
        sentStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = "Unknown Error";
                switch (getResultCode()){
                    case RESULT_OK:
                        s = "Message Sent Successfully!!";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error: No Service Available";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s= "Error: Nll PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error: Radio is off";
                        break;
                        default:
                            break;
                }
                Toast.makeText(SMSActivity.this, s, Toast.LENGTH_LONG).show();
            }
        };
        deliveredStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = "Message Not Delivered";
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        s = "Message Delivered Successfully";
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                Toast.makeText(SMSActivity.this, s, Toast.LENGTH_LONG).show();
                phoneNumber.setText("");
                smsText.setText("");
            }
        };
        registerReceiver(sentStatusReceiver, new IntentFilter("SMS_SENT"));
        registerReceiver(deliveredStatusReceiver, new IntentFilter("SMS_DELIVERED"));
    }

    public void onPause(){
        super.onPause();
        unregisterReceiver(sentStatusReceiver);
        unregisterReceiver(deliveredStatusReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission accepted", Toast.LENGTH_LONG ).show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                    Button sendSms = findViewById(R.id.send_button);
                    sendSms.setEnabled(false);
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void failedMsg() {
        Toast.makeText(this, "Cannot send message", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successSend() {
        Toast.makeText(this, "Message Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNumber() {
        return phoneNumber.getText().toString();
    }

    @Override
    public String getMessage() {
        return smsText.getText().toString();
    }

    @Override
    public Integer getDeviceId() {
        return saveSharedPrefernces.readDeviceId();
    }

    @Override
    public boolean validation(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }


}
