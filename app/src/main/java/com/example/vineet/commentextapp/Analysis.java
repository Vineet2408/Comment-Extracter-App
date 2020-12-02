package com.example.vineet.commentextapp;

import com.example.vineet.commentextapp.models.comments.Comments;

import java.util.List;

public class Analysis
{
    public int getString(List<Comments.Item> itemList)
    {
        String comment;
        int count=0;
        int size=itemList.size();
        for (int i=0;i<size;i++)
        {
             comment = itemList.get(i).getSnippet().getTopLevelComment().getSnippet().getTextOriginal();
             count=count+filter(comment.toLowerCase());
        }
        return count;
    }

    private int filter(String comment)
    {
        int count =0;

        String positive[]={"good","fine","awesome","marvelous","fantastic","satisfied","great","amazing"};
        String negative[]={"bad","ugly","poor","horrible","let down"};
        String word;
        StringBuffer sb=new StringBuffer(comment);
        for (int i=0;i<negative.length;i++)
        {
            word=negative[i];
            if(comment.indexOf(word)!=-1)
            {
                count = count - 1;
            }
        }
        for (int i=0;i<positive.length;i++) {
            word = positive[i];
            if (comment.indexOf("not " + word) != -1) {
                count = count - 1;
            }
        }
            for (int i=0;i<positive.length;i++)
            {
                word = positive[i];
                if (comment.indexOf("not " + word) != -1) {
                    count = count + 1;
                }
            }

        return count;
    }
}
