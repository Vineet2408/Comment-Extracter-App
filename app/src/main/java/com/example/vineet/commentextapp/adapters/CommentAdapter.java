package com.example.vineet.commentextapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vineet.commentextapp.R;
import com.example.vineet.commentextapp.models.comments.Comments;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>
{

    Context context;
    private List<Comments.Item> itemList;

    public CommentAdapter(Context context, List<Comments.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.comments_row_items_layout,viewGroup,false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder c_holder, int i)
    {
        c_holder.comment.setText(itemList.get(i).getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        c_holder.username.setText(itemList.get(i).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());

        String url=itemList.get(i).getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl();
        Picasso.with(context).load(url).into(c_holder.user_thumbnail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder
    {
        ImageView user_thumbnail;
        TextView username,comment;

        public CommentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            user_thumbnail=itemView.findViewById(R.id.user_thumbnail);
            username=itemView.findViewById(R.id.username);
            comment=itemView.findViewById(R.id.comment);
        }
    }
}
