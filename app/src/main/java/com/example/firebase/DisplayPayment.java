package com.example.firebase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.PaymentData.PaymentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayPayment extends AppCompatActivity {
    private TextView setDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_of_child_elements);
        String uId = getIntent().getStringExtra(PaymentAdaptor.user_key);
        setDetail = findViewById(R.id.setDetails);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(uId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               try {
                   PaymentData paymentData = dataSnapshot.getValue(PaymentData.class);
                   setDetail.setText(paymentData.getAmount() +"  " + paymentData.getName());
               }catch (Exception e){

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
