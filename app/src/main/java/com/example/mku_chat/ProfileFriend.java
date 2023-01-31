package com.example.mku_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mku_chat.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFriend extends AppCompatActivity {

    Intent intent;
    EditText  phone, address;
    TextView btnsendmessage, name;
    CircleImageView avt;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_friend);
        //
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1566e0"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //
        intent = getIntent();
        String userid = intent.getStringExtra("profileid");
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        btnsendmessage = findViewById(R.id.btnsendmessage);
        avt = findViewById(R.id.avt);
        //
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.getValue(Account.class);
                name.setText(account.getUsername());
                name.setEnabled(false);
                phone.setText(account.getPhone());
                phone.setEnabled(false);
                address.setText(account.getAddress());
                address.setEnabled(false);
                name.setTextColor(getResources().getColor(R.color.black));
                phone.setTextColor(getResources().getColor(R.color.black));
                address.setTextColor(getResources().getColor(R.color.black));

                if (account.getImageURL().equals("default")){
                    avt.setImageResource(R.mipmap.ic_launcher);
                }
                else{
//                    Glide.with(getActivity()).load(account.getImageURL()).into(avt);
                    Picasso.get().load(account.getImageURL()).into(avt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnsendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFriend.this, MessageActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}