    package com.example.mku_chat.Adapter;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.FragmentActivity;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.example.mku_chat.Fragment.DetailPost_Fragment;
    import com.example.mku_chat.Model.Post;
    import com.example.mku_chat.R;

    import java.util.List;

    public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.ViewHolder> {

        Context context;
        List<Post> mPost;

        public MyPhotoAdapter(Context context, List<Post> mPost) {
            this.context = context;
            this.mPost = mPost;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.photo_item, viewGroup, false);
            return new MyPhotoAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            Post post = mPost.get(position);
            Glide.with(context.getApplicationContext()).load(post.getPostimage()).into(viewHolder.myphoto);


            viewHolder.myphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                    editor.putString("postid", post.getPostid());
                    editor.apply();
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DetailPost_Fragment()).commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPost.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView myphoto;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                myphoto = itemView.findViewById(R.id.myphoto);
            }
        }
    }
