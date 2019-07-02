package com.himalayantechies.smsapp.ui.sms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himalayantechies.smsapp.R;
import com.himalayantechies.smsapp.models.Sms;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SmsListAdapter extends RecyclerView.Adapter<SmsListAdapter.myHolder> {

    private List<Sms> smsList;
    private Context context;

    public SmsListAdapter(List<Sms> list, Context context) {
        this.smsList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_sms, null);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        Sms sms = smsList.get(position);
        holder.phoneNumber.setText(sms.getNumber());
        holder.msg.setText(sms.getMsg());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        TextView msg;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.ph_no);
            msg = itemView.findViewById(R.id.msg);
        }
    }
}
