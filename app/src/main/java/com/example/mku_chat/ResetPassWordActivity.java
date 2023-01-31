package com.example.mku_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPassWordActivity extends AppCompatActivity {

    EditText edtemail;
    TextView txt_sendmail;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_word);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edtemail = findViewById(R.id.edtemail);
        txt_sendmail = findViewById(R.id.txt_sendmail);

        firebaseAuth = FirebaseAuth.getInstance();

        txt_sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString();
                if (email.equals("")){
                    Toast.makeText(ResetPassWordActivity.this,"Require not null",Toast.LENGTH_SHORT).show();

                }
                else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPassWordActivity.this,"Please  check your email",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassWordActivity.this,SigninActivity.class));
                            }
                            else{
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPassWordActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}