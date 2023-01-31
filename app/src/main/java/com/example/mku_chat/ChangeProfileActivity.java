package com.example.mku_chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mku_chat.Model.Account;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeProfileActivity extends AppCompatActivity {

    EditText name, phone, address;
    CircleImageView avt;
    TextView btnChange;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    FirebaseUser fuser;

    StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1566e0"));
        actionBar.setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_change_profile);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        avt = findViewById(R.id.avt);
        btnChange = findViewById(R.id.btnChange);

        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());



        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChange.setTextColor(getResources().getColor(R.color.color_btn));
                btnChange.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_btn_profile));
                if(!name.getText().toString().equals("") && !phone.getText().toString().equals("") && !address.getText().toString().equals("")){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("username",name.getText().toString());
                    hashMap.put("address", address.getText().toString());
                    hashMap.put("phone", phone.getText().toString());
                    reference.updateChildren(hashMap);
                    Toast.makeText(ChangeProfileActivity.this, "Change Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangeProfileActivity.this,MainActivity.class));
                }
                else{
                    Toast.makeText(ChangeProfileActivity.this, "require not null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //lay du leu dia vao userid
            LoadProfileFirebase();
        }
    }

    public void LoadProfileFirebase(){
        reference = FirebaseDatabase.getInstance()
                .getReference().
                        child("Users").
                        child(mAuth.getCurrentUser().
                                getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account.getImageURL().equals("default")){
                    avt.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    //Glide.with(getApplicationContext()).load(account.getImageURL()).into(avt);
                    Picasso.get().load(account.getImageURL()).into(avt);
                }
                setDataToViews(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    public void setDataToViews(Account account){
        name.setText(account.getUsername().toString());
        phone.setText(account.getPhone().toString());
        address.setText(account.getAddress().toString());
    }
    //
    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMape = MimeTypeMap.getSingleton();
        return mimeTypeMape.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(ChangeProfileActivity.this);
        pd.setMessage("uploading");
        pd.show();

        if(imageUri != null){
            final StorageReference fiReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));
            uploadTask = fiReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fiReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageUrl",mUri);
                        reference.updateChildren(map);

                        //pd.dismiss();
                        pd.dismiss();
                        //LoadProfileFirebase();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"failed !",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"No Image !",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if(uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(),"Upload in preogress !",Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
    }

    //event button back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}