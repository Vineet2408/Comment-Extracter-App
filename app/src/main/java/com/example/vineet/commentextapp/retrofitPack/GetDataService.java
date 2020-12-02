package com.example.vineet.commentextapp.retrofitPack;

import com.example.vineet.commentextapp.models.Item;
import com.example.vineet.commentextapp.models.VideoDetails;
import com.example.vineet.commentextapp.models.comments.Comments;
import com.example.vineet.commentextapp.models.videoStats.VideoStats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService
{
    @GET("search")
    Call<VideoDetails> getVideoData(@Query("part")String part,
                                    @Query("channelId")String channelId,
                                    @Query("key")String key,
                                    @Query("order")String order);

    @GET("videos")
    Call<VideoStats> getVideoStats(@Query("part")String part,
                                  @Query("key")String key,
                                   @Query("id")String id);

    @GET("commentThreads")
    Call<Comments.Model> getCommentsData(@Query("part")String part,
                                         @Query("videoId")String videoId,
                                         @Query("maxResults")int maxResults,
                                         @Query("key")String key

    );

}
