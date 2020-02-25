package com.example.firebase;
import com.example.firebase.PaymentData.PaymentData;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PaymentAdaptor mAdaptor ;
    private RecyclerView mRecyclerView;
    private FirebaseDatabase mfirebaseDatabase;
    private List<PaymentData> mList;
    private DatabaseReference mRef;
    private Button mWriteData, readData , mUpdate ,mdelete;
    private TextView setData;
    private EditText mName, mAmount , mDate , mMonth;
    private String TAG = "mytag";

//    THIS LISTENER IS USED TO REFER A LISTNER FOR JUST ONE TIME
    private ValueEventListener mValuelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseDatabase = FirebaseDatabase.getInstance();

        mRef = mfirebaseDatabase.getReference();

       init();
        mList = new ArrayList<>();
       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       mAdaptor = new PaymentAdaptor(this,mList);
       mRecyclerView.setAdapter(mAdaptor);


        mWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String name = mName.getText().toString();
                final String amount = mAmount.getText().toString();
                final String date = mDate.getText().toString();
                final String month = mMonth.getText().toString();
                PaymentData paymentData = new PaymentData();
                paymentData.setName(name);
                paymentData.setAmount(amount);
                paymentData.setDate(date);
                paymentData.setMonth(month);

                String key = mRef.push().getKey().toString();
                mRef.child("user").child(key).setValue(paymentData);

       }
        });


        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRef.child("user").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        try {
                           PaymentData paymentData = dataSnapshot.getValue(PaymentData.class);
                           paymentData.setuId(dataSnapshot.getKey().toString());

                           mList.add(paymentData);
                           mAdaptor.notifyDataSetChanged();

                        }catch (Exception e){
                            Log.d(TAG, "onChildAdded: error found");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mRef.updateChildren(mapUpdate);
            }
        });

        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> task =  mRef.child("user1").removeValue();
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: data deleted");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: dalete cancel");
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.child("mohit gupta").removeEventListener(mValuelistener);
    }

    public void init(){

        mWriteData = findViewById(R.id.sendData);
        readData = findViewById(R.id.getDataFromDataBase);
        mUpdate = findViewById(R.id.update);
        mdelete = findViewById(R.id.delete);
        setData = findViewById(R.id.setReadData);
        mAmount = findViewById(R.id.setAmount);
        mName = findViewById(R.id.setName);
        mDate = findViewById(R.id.setDate);
        mMonth = findViewById(R.id.setMonth);
        mRecyclerView=findViewById(R.id.PaymentRecyclerView);
    }
}
