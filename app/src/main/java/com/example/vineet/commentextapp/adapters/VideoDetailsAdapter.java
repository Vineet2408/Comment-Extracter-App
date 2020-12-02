package com.example.vineet.commentextapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineet.commentextapp.R;
import com.example.vineet.commentextapp.VideoPlayerAct;
import com.example.vineet.commentextapp.models.Item;
import com.example.vineet.commentextapp.models.VideoDetails;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoDetailsAdapter extends RecyclerView.Adapter<VideoDetailsAdapter.VideoDetailsViewHolder>
{
    List<Item> videoDetailsList;
    Context context;
    String converted_date;


    public VideoDetailsAdapter(List<Item> videoDetailsList, Context context)
    {
        this.videoDetailsList = videoDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.row_item,viewGroup,false);

        return new VideoDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoDetailsViewHolder holder, final int position)
    {
       // String newDate=setUpDateFormat();

        holder.description.setText(videoDetailsList.get(position).getSnippet().getDescription());
       holder.date.setText(videoDetailsList.get(position).getSnippet().getPublishedAt());
       holder.title.setText(videoDetailsList.get(position).getSnippet().getTitle());
       holder.listVideoId.setText(videoDetailsList.get(position).getId().getVideoId());

       String url=videoDetailsList.get(position).getSnippet().getThumbnails().getMedium().getUrl();
        Picasso.with(context).load(url).into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, VideoPlayerAct.class);
                intent.putExtra("videoId",videoDetailsList.get(position).getId().getVideoId());
                context.startActivity(intent);
            }
        });
    }

/*    private String setUpDateFormat(String publishedAt)
    {
        try
        {
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/DD hh:mm a");
            Date date=dateFormat.parse(publishedAt);
            converted_date=simpleDateFormat.format(date);
        }
        catch (ParseException e)
        {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return converted_date;
    }
*/
    @Override
    public int getItemCount() {
        return videoDetailsList.size();
    }

    public class VideoDetailsViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,title,description,listVideoId;
        CircleImageView thumbnail;


        public VideoDetailsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            listVideoId=itemView.findViewById(R.id.listVideoId);

        }
    }
}