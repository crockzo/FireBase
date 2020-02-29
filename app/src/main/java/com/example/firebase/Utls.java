package com.example.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Utls {
    public static Task<Void> removeData(String uId){
        Task<Void> task = FirebaseDatabase.getInstance().getReference().child("user").child(uId).setValue(null);

        return task;
    }

}
