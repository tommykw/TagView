package com.github.tommykw.tagview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.tommykw.tagview.TagView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List list = new ArrayList();
        list.add("緊急帝王切開");
        list.add("男の子");
        list.add("子宮");
        list.add("流産");
        list.add("妊娠");
        list.add("妊娠予定日");
        list.add("注射");

        TagView tags = findViewById(R.id.tagview);
        tags.setTags(list);
        tags.setClickListener(new TagView.TagClickListener() {
            @Override
            public void onTagClick(@NotNull String name) {
            }
        });
    }
}
