package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mRef;
    private Button mWriteData, readData , mUpdate ,mdelete;
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



        mWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final  String data = mtext.getText().toString();
                final String age = mAge.getText().toString();
//                mRef.child("user!").setValue(data);
                Map<String,Object> map = new HashMap<>();
                map.put("name",data);
                map.put("age",age);

                String key = mRef.push().getKey().toString();
                mRef.child(key).setValue(map);
              /*  mRef.child(key).child("name").setValue(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "write successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: name : " + data);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: permissiion denied");
                            }
                        });*/
//   mRef.child("age").setValue(age)
/*
               final String age = mAge.getText().toString();
                mRef.child(key).child("age").setValue(age)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: to add age" );
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: age :" + age);
                            }
                        });
               // mRef.setValue(data);
                Toast.makeText(MainActivity.this, "data inserted " , Toast.LENGTH_LONG).show();*/
            }
        });


        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*
//                THIS EVENT LISTENER ID USE TO REFERENCE TO A NODE AND SHOW THE VALUE OF THE NODE
                mValuelistener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                             try {
                                 Map<String,Object> map = (Map<String, Object>) snapshot.getValue();
                                Log.d(TAG, "onDataChange: key " + snapshot.getKey() );
                                Log.d(TAG, "onDataChange: name " + map.get("name"));
                                Log.d(TAG, "onDataChange: age" + map.get("age"));
                            }catch (Exception e){
                                Log.d(TAG, "onDataChange: error found");
                            }


                        }
*/

                       /*
                        Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                         String data = map.get("name").toString();
                        setData.setText(data);
                        Log.d(TAG, "onDataChange: " + data);
                        */

                   /* }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: failed in reading");
                    }
                };*/
            //    mRef.addListenerForSingleValueEvent(mValuelistener);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        try {
                            Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Log.d(TAG, "onChildAdded: key " + dataSnapshot.getKey() );
                            Log.d(TAG, "onChildAdded: name " + map.get("name"));
                            Log.d(TAG, "onChildAdded: age" + map.get("age"));
                        }catch (Exception e){
                            Log.d(TAG, "onChildAdded: error found");
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                      /*  try {
                            Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Log.d(TAG, "onDataChange: key " + dataSnapshot.getKey() );
                            Log.d(TAG, "onDataChange: name " + map.get("name"));
                            Log.d(TAG, "onDataChange: age" + map.get("age"));
                        }catch (Exception e){
                            Log.d(TAG, "onChildChange: error found");
                        }*/
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
                Map<String,Object> mapUpdate = new HashMap<>() ;
//                mapUpdate.put("/-M0mCIPlEMgBxvn8f0s1/name","vishan kumar gupta");
//                mapUpdate.put("/-M0mCIPlEMgBxvn8f0s1/age",47);
                mapUpdate.put("/user1/name","ram");
                mapUpdate.put("/user1/age","19");
                mRef.updateChildren(mapUpdate);
            }
        });

        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> task =  mRef.child("user1").child("name").removeValue();
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
        mtext = findViewById(R.id.setData);
        mWriteData = findViewById(R.id.sendData);
        readData = findViewById(R.id.getDataFromDataBase);
        setData = findViewById(R.id.setReadData);
        mAge = findViewById(R.id.setAge);
        mUpdate = findViewById(R.id.update);
        mdelete = findViewById(R.id.delete);
    }
}
