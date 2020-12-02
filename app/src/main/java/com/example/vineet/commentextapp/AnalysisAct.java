package com.example.vineet.commentextapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vineet.commentextapp.models.comments.Comments;

import java.util.List;

public class AnalysisAct extends AppCompatActivity {

    TextView textView;
    Bundle bundle;
    List<Comments.Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        textView=findViewById(R.id.textview);
        itemList=CommentListAct.forAnalysisList;

        Analysis ob=new Analysis();
        int count=ob.getString(itemList);
        String value=String.valueOf(count);
        textView.setText(value);

    }
}
