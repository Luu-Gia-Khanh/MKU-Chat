package com.example.mku_chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mku_chat.Fragment.ProfileFragment;
import com.example.mku_chat.MessageActivity;
import com.example.mku_chat.Model.Account;
import com.example.mku_chat.Model.Chat;
import com.example.mku_chat.ProfileFriend;
import com.example.mku_chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {
    private Context mContext;
    private List<Account> mUser;
    private boolean ischat;
    public FirebaseUser firebaseUser;



    public ListUserAdapter(Context mContext, List<Account> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    public ListUserAdapter(ArrayList<Account> accounts, int user_item) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_user,parent,false);
        return new ListUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final Account user = mUser.get(position);
        viewHolder.btnfollow.setVisibility(View.VISIBLE);
        viewHolder.txtusername.setText(user.getUsername());
        if(user.getImageURL().equals("default") && user.getImageURL()!=null){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContext).load(user.getImageURL()).into(viewHolder.profile_image);
        }
        isFollowing(user.getId(), viewHolder.btnfollow);
        if(user.getId().equals(firebaseUser.getUid())){
            viewHolder.btnfollow.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileFriend.class);
                intent.putExtra("profileid",user.getId());
                mContext.startActivity(intent);
//                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
//                editor.putString("profileid", user.getId());
//                editor.apply();
//                mContext.startActivity(editor);
                //((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ProfileFragment()).commit();
            }
        });
        viewHolder.btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.btnfollow.getText().toString().equals("Follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following").child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following").child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
        viewHolder.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtusername, btnfollow;
        public ImageView profile_image, sendMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtusername = itemView.findViewById(R.id.txtusername);
            sendMessage = itemView.findViewById(R.id.sendMessage);
            profile_image = itemView.findViewById(R.id.profile_image);

            btnfollow = itemView.findViewById(R.id.btnfollow);

        }
    }
    private void isFollowing(String userid, TextView button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userid).exists()){
                    button.setText("Following");
                    button.setTextColor(Color.parseColor("#1566e0"));
                    button.setBackground(ContextCompat.getDrawable( mContext,R.drawable.btn_following));
                }
                else{
                    button.setText("Follow");
                    button.setTextColor(Color.parseColor("#ffffff"));
                    button.setBackground(ContextCompat.getDrawable( mContext,R.drawable.btn_follow));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
