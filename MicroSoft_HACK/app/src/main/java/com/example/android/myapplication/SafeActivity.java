package com.example.android.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.myapplication.LoginWork.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SafeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        mAuth= FirebaseAuth.getInstance();
        //Check whether user is already login in or not
        if(mAuth.getCurrentUser()==null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        Button Yes=(Button)findViewById(R.id.btn_yes);
        Button No=(Button)findViewById(R.id.btn_no);
        progressDialog=new ProgressDialog(this);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maps("Yes");
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maps("No");
            }
        });

    }
    public void maps(String Status)
    {
        FirebaseUser user=mAuth.getCurrentUser();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Status").setValue(Status);
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.safe_loading));
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Status").setValue(Status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else
                {
                    progressDialog.hide();
                    Toast.makeText(SafeActivity.this,getApplicationContext().getResources().getString(R.string.safe_unsucessful), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

