package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.PaymentData.PaymentData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class PaymentAdaptor extends RecyclerView.Adapter<PaymentAdaptor.MyViewHolder> {

    public static final String user_key = "user_id";
    Context context;
    List<PaymentData> mDatalist ;

    public PaymentAdaptor(Context c , List<PaymentData> p){
        this.context = c;
        this.mDatalist = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View myView = LayoutInflater.from(context).inflate(R.layout.payment_detail_list,parent,false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final PaymentData mPay = mDatalist.get(position);
        holder.mName.setText(mPay.getName());
        holder.mName.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String uid = mPay.getuId();
                Intent intent = new Intent(context,DisplayPayment.class);
                intent.putExtra(user_key , uid);
                context.startActivity(intent);
            }
        });
        holder.mMonth.setText(mPay.getMonth());
        holder.mDate.setText(mPay.getDate());
        holder.mAmount.setText(mPay.getAmount());
        holder.myCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String uId = mPay.getuId();
                Task<Void> task = Utls.removeData(uId);
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"data deleted ...",Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName , mAmount , mDate ,mMonth;
        CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.putName);
            mAmount = itemView.findViewById(R.id.putAmount);
            mDate= itemView.findViewById(R.id.putDate);
            mMonth= itemView.findViewById(R.id.putMonth);
            myCardView = itemView.findViewById(R.id.myCard);
        }
    }
}
