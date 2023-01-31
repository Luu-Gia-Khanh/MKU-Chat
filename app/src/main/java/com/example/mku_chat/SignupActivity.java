package com.example.mku_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

     EditText edtusername;
     EditText edtemail;
     EditText edtpassword;
     EditText edtaddress;
     EditText edtphone;
     TextView txtsignup;
    //
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        findView();
        auth = FirebaseAuth.getInstance();
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtsignup.setTextColor(getResources().getColor(R.color.color_btn));
                txtsignup.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_btn_profile));
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                String email = edtemail.getText().toString();
                String address = edtaddress.getText().toString();
                String phone = edtphone.getText().toString();
                if(!username.equals("") && !password.equals("") && !email.equals("") && !address.equals("") && !phone.equals("")){
                    if(phone.length() == 10){
                        register(edtusername.getText().toString(),edtemail.getText().toString(),edtpassword.getText().toString(),edtaddress.getText().toString(),edtphone.getText().toString());
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "phone number require 10 number", Toast.LENGTH_SHORT).show();
                        edtphone.requestFocus();
                    }

                }
                else{
                    Toast.makeText(SignupActivity.this, "require not null", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void findView(){
        edtusername = findViewById(R.id.edtusername);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        edtaddress = findViewById(R.id.edtaddress);
        edtphone = findViewById(R.id.edtphone);
        txtsignup = findViewById(R.id.txtsignup);
    }
    private boolean IsValid() {
        return true;
    }
    public void register(String username,String email,String password,String address, String phone){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageUrl","default");
                            hashMap.put("address",address);
                            hashMap.put("phone",phone);
                            hashMap.put("status","offline");
                            hashMap.put("search",username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(SignupActivity.this,"you need sign up with email and password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}