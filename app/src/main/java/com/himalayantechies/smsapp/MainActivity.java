package com.himalayantechies.smsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.himalayantechies.smsapp.models.Sms;
import com.himalayantechies.smsapp.ui.sms.SMSActivity;
import com.himalayantechies.smsapp.ui.sms.SmsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonShowMessage, buttonSendMessage;
    RecyclerView recyclerView;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonShowMessage = findViewById(R.id.search_button);
        buttonSendMessage = findViewById(R.id.send_sms);
        recyclerView = findViewById(R.id.recycler_view);
        buttonShowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShowMessage.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSmsPermission()) {
                        showInboxMessages();
                    }else{
                        requestSMSReadPermission();
                    }
                }

            }
        });
        buttonShowMessage.setVisibility(View.VISIBLE);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSMS(v);
            }
        });
    }

    private void showInboxMessages() {
        List<Sms> smsList = new ArrayList<>();
        Sms sms;
        Uri uriSms = Uri.parse("content://sms/inbox");
        final Cursor cursor = getContentResolver().query(uriSms,
                new String[]{"_id", "address", "date", "body"}, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String address = cursor.getString(1);
            String msg = cursor.getString(3);
            sms = new Sms();
            sms.setId(id);
            sms.setNumber(address);
            sms.setMsg(msg);
            smsList.add(sms);
        }
        SmsListAdapter smsListAdapter = new SmsListAdapter(smsList, getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(smsListAdapter);
    }


    private boolean checkSmsPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            return false;
        }else {
            return true;
        }
    }

    private void requestSMSReadPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, MY_PERMISSIONS_REQUEST_READ_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_SMS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                            PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    public void launchSMS(View view){
        Intent intent = new Intent(MainActivity.this, SMSActivity.class);
        startActivity(intent);
    }
}
