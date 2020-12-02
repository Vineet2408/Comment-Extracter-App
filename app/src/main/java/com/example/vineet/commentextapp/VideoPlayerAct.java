package com.example.vineet.commentextapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vineet.commentextapp.adapters.CommentAdapter;
import com.example.vineet.commentextapp.models.comments.Comments;
import com.example.vineet.commentextapp.models.videoStats.VideoStats;
import com.example.vineet.commentextapp.retrofitPack.GetDataService;
import com.example.vineet.commentextapp.retrofitPack.RetrofitInstance;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayerAct extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    Bundle bundle;
    String videoId;
    YouTubePlayerView playerView;
    TextView likesNumber,dislikesNumber,viewsNumber;
    RecyclerView commentsRecyclerView;
    CommentAdapter commentAdapter;
    Button viewbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        bundle=new Bundle();
        bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            videoId=bundle.getString("videoId");
        }
        //p8zaUKBzgtE

        playerView=findViewById(R.id.youtube_player_view);
        likesNumber=findViewById(R.id.likesNumber);
        dislikesNumber=findViewById(R.id.dislikesNumber);
        viewsNumber=findViewById(R.id.viewsNumber);
        commentsRecyclerView=findViewById(R.id.commentsRecyclerView);
        viewbtn=findViewById(R.id.viewbtn);

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoPlayerAct.this,CommentListAct.class);
                intent.putExtra("videoId",videoId);
                startActivity(intent);
            }
        });


        playerView.initialize(Constants.GOOGLE_YOUTUBE_API_KEY,this);

        getStats();
        getComments();
    }

    private void getComments()
    {
        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<Comments.Model> commentDataRequest=dataService
                .getCommentsData("snippet,replies",videoId,50,Constants.GOOGLE_YOUTUBE_API_KEY);

        commentDataRequest.enqueue(new Callback<Comments.Model>() {
            @Override
            public void onResponse(Call<Comments.Model> call, Response<Comments.Model> response)
            {
                Toast.makeText(VideoPlayerAct.this, "comment response success", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful())
                {
                    Toast.makeText(VideoPlayerAct.this, "comment success", Toast.LENGTH_SHORT).show();
                    if (response.body()!=null)
                    {
                        Toast.makeText(VideoPlayerAct.this, "comment not null", Toast.LENGTH_SHORT).show();
                        setUpCommentsRecyclerView(response.body().getItems());
                    }
                    else
                    {
                        Toast.makeText(VideoPlayerAct.this, "comment is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(VideoPlayerAct.this, ""+response.code()+" "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(VideoPlayerAct.this, "Comment not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comments.Model> call, Throwable t)
            {
                Toast.makeText(VideoPlayerAct.this, "comments failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCommentsRecyclerView(List<Comments.Item> items)
    {
        Toast.makeText(this, "setup", Toast.LENGTH_SHORT).show();
        commentAdapter=new CommentAdapter(VideoPlayerAct.this,items);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(VideoPlayerAct.this);
        commentsRecyclerView.setLayoutManager(layoutManager);
        commentsRecyclerView.setAdapter(commentAdapter);

    }

    private void getStats()
    {
        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoStats> videoStatsRequest=dataService.getVideoStats("statistics",Constants.GOOGLE_YOUTUBE_API_KEY,videoId);

        videoStatsRequest.enqueue(new Callback<VideoStats>() {
            @Override
            public void onResponse(Call<VideoStats> call, Response<VideoStats> response)
            {
                Toast.makeText(VideoPlayerAct.this, "success", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        likesNumber.setText(response.body().getItems().get(0).getStatistics().getLikeCount());
                        dislikesNumber.setText(response.body().getItems().get(0).getStatistics().getDislikeCount());
                        viewsNumber.setText(response.body().getItems().get(0).getStatistics().getViewCount());
                    }
                }

            }

            @Override
            public void onFailure(Call<VideoStats> call, Throwable t)
            {
                Toast.makeText(VideoPlayerAct.this, "no stats retrieved=failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {
        youTubePlayer.cueVideo(videoId);
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        Toast.makeText(this, "oops", Toast.LENGTH_SHORT).show();
    }
}
