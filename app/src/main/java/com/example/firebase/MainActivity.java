package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mRef;
    private Button mButton , readData;
    private TextView setData;
    private EditText mtext , mAge;
    private String TAG = "mytag";

//    THIS LISTENER IS USED TO REFER A LISTNER FOR JUST ONE TIME
    private ValueEventListener mValuelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        THIS IS TO REFER THE FIRE BASE DATABASE
        mfirebaseDatabase = FirebaseDatabase.getInstance();

//        THIS IS TO REFER TO THE NODE OF THE FIRE BASE DATABASE
        mRef = mfirebaseDatabase.getReference("user");

//        THIS IS THE WAY TO CREATE THE CHILD IN THE DATA BASE
//        mRef = mfirebaseDatabase.getReference("user");

       init();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = mtext.getText().toString();

//                TO CREATE THE FURTHER CHILD NODE
//                mRef.child("user!").setValue(data);


                String key = mRef.push().getKey().toString();
//                THIS IS TO SET THE VALUE OF TO THE RELATED VALUE  : HERE THE mRef REFERES TO THE NODE
                mRef.child(key).child("name").setValue(data)

//                        THIS IS TO KNOW THAT THE PROCESS IS SUCCESSFUL OR NOT
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "write successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: "+"dataloaded");
                            }
                        })

//                        THIS OS TO KNOW THAT THE PROCESS IS FAILED
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: permissiion denied");
                            }
                        });

                String age = mAge.getText().toString();
                mRef.child(key).child("age").setValue(age)
             //   mRef.child("age").setValue(age)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: to add age" );
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: age add successfully");
                            }
                        });
               // mRef.setValue(data);
                Toast.makeText(MainActivity.this, "data inserted " , Toast.LENGTH_LONG).show();
            }
        });


        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                THIS EVENT LISTENER ID USE TO REFERENCE TO A NODE AND SHOW THE VALUE OF THE NODE
                mValuelistener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        DATA SNAPSHOT GIVE THE SNAPSHOT OF THE CURRENT NODE TO ACCESS THE VALUE ATTACHED TO THE NODE
                        Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();

                       // String data = dataSnapshot.getValue(String.class);
                        String data = map.get("name").toString();
                        setData.setText(data);
                        Log.d(TAG, "onDataChange: " + data);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: failed in reading");

                    }
                };
//                THIS IS TO SET THE EVENT LISTENER WHICH GET INVOKED WHEN THE DATA OF THE NODE IS GET UPDATED OR CHANGES
                mRef.child("user").addValueEventListener(mValuelistener);
                /*(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DATA SNAPSHOT GIVE THE SNAPSHOT OF THE CURRENT NODE TO ACCESS THE VALUE ATTACHED TO THE NODE
                            String data = dataSnapshot.getValue(String.class);
                            setData.setText(data);
                        Log.d(TAG, "onDataChange: " + data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: failed in reading");
                    }
                });*/
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.child("mohit gupta").removeEventListener(mValuelistener);
    }

    public void init(){
        mtext = findViewById(R.id.setData);
        mButton= findViewById(R.id.sendData);
        readData = findViewById(R.id.getDataFromDataBase);
        setData = findViewById(R.id.setReadData);
        mAge = findViewById(R.id.setAge);
    }
}
