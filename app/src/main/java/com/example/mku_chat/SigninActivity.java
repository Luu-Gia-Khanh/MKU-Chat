package com.example.mku_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class SigninActivity extends AppCompatActivity {

    TextView txtsignup,txtsignin,txt_forgotpass;
    EditText edtemail,edtpassword;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        txtsignup = findViewById(R.id.txtsignup);
        txtsignin = findViewById(R.id.txtsignin);
        txt_forgotpass = findViewById(R.id.txt_forgotpass);
        auth = FirebaseAuth.getInstance();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1566e0"));
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString();
                String password = edtpassword.getText().toString();
                txtsignin.setTextColor(getResources().getColor(R.color.color_btn));
                txtsignin.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_btn_profile));
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SigninActivity.this, "require not null", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SigninActivity.this, "account not exsist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtsignup.setTextColor(getResources().getColor(R.color.color_btn));
                txtsignup.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_btn_profile));

                Intent intent = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this,ResetPassWordActivity.class);
                startActivity(intent);
            }
        });
    }
    FirebaseUser firebaseUser;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(SigninActivity.this, MainActivity.class));
            finish();
        }
    }

}