package com.example.vineet.commentextapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vineet.commentextapp.adapters.CommentAdapter;
import com.example.vineet.commentextapp.models.comments.Comments;
import com.example.vineet.commentextapp.retrofitPack.GetDataService;
import com.example.vineet.commentextapp.retrofitPack.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAct extends AppCompatActivity implements View.OnClickListener {

    RecyclerView commentActRecyclerView;
    CommentAdapter commentAdapter;
    String videoId;
    Bundle bundle;
    Intent intent;
    Button button;
    public static List<Comments.Item> forAnalysisList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list2);

        bundle=new Bundle();
        bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            videoId=bundle.getString("videoId");
        }

        commentActRecyclerView=findViewById(R.id.commentListActView);
        setUpCommentsRecyclerView(forAnalysisList);
        
        getCommentsData();
    }

    private void getCommentsData()
    {
        GetDataService dataService= RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<Comments.Model> commentDataRequest=dataService
                .getCommentsData("snippet,replies",videoId,50,Constants.GOOGLE_YOUTUBE_API_KEY);

        commentDataRequest.enqueue(new Callback<Comments.Model>() {
            @Override
            public void onResponse(Call<Comments.Model> call, Response<Comments.Model> response)
            {
                Toast.makeText(CommentListAct.this, "comment response success", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful())
                {
                    Toast.makeText(CommentListAct.this, "comment success", Toast.LENGTH_SHORT).show();
                    if (response.body()!=null)
                    {
                        Toast.makeText(CommentListAct.this, "comment not null 1", Toast.LENGTH_SHORT).show();
                        setUpCommentsRecyclerView(response.body().getItems());
                    }
                    else
                    {
                        Toast.makeText(CommentListAct.this, "comment is null else 2", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CommentListAct.this, ""+response.code()+" "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(CommentListAct.this, "Comment not success", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Comments.Model> call, Throwable t)
            {
                Toast.makeText(CommentListAct.this, "comments failed", Toast.LENGTH_SHORT).show();
            }
        });


        }

    private void setUpCommentsRecyclerView(List<Comments.Item> items)
    {
        Toast.makeText(this, "setup", Toast.LENGTH_SHORT).show();
        forAnalysisList=items;
        commentAdapter=new CommentAdapter(CommentListAct.this,items);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CommentListAct.this);
        commentActRecyclerView.setLayoutManager(layoutManager);
        commentActRecyclerView.setAdapter(commentAdapter);

    }

    @Override
    public void onClick(View v)
    {
        intent=new Intent(CommentListAct.this,AnalysisAct.class);
        intent.putExtra("list", (Parcelable) forAnalysisList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        commentAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        commentAdapter.notifyDataSetChanged();
    }
}
