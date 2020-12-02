package com.example.vineet.commentextapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.vineet.commentextapp.adapters.VideoDetailsAdapter;
import com.example.vineet.commentextapp.models.Item;
import com.example.vineet.commentextapp.models.VideoDetails;
import com.example.vineet.commentextapp.retrofitPack.GetDataService;
import com.example.vineet.commentextapp.retrofitPack.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListAct extends AppCompatActivity
{
    RecyclerView recyclerView;
    VideoDetailsAdapter videoDetailsAdapter;

    Toolbar toolbar;
    

    List<Item> itemList=new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        recyclerView=findViewById(R.id.recyclerView);

         setUpRecyclerView(itemList);
         //UCtvAhslPuVLNs1LVI5wuMOw
        //UCP1iRaFlS5EYjJBryFV9JPw

        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoDetails> videoDetailsRequest=dataService
       .getVideoData("snippet",Constants.CHANNEL_ID,Constants.GOOGLE_YOUTUBE_API_KEY,"date");

        Toast.makeText(this, "hello Act", Toast.LENGTH_SHORT).show();
        videoDetailsRequest.enqueue(new Callback<VideoDetails>()
        {
            @Override
            public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response)
            {
                 if(response.isSuccessful())
                 {
                     if(response.body()!=null)
                     {
                         Toast.makeText(VideoListAct.this, "successful", Toast.LENGTH_SHORT).show();

                         setUpRecyclerView(response.body().getItem());
                     }
                     else
                     {
                         Toast.makeText(VideoListAct.this, "failure null", Toast.LENGTH_SHORT).show();
                     }
                 }
                 else
                 {
                     Toast.makeText(VideoListAct.this, "failed response", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<VideoDetails> call, Throwable t)
            {
                Toast.makeText(VideoListAct.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(List<Item> item)
    {
        Toast.makeText(this, "setup", Toast.LENGTH_SHORT).show();
        videoDetailsAdapter=new VideoDetailsAdapter(item,VideoListAct.this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(VideoListAct.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoDetailsAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        videoDetailsAdapter.notifyDataSetChanged();

    }
}
