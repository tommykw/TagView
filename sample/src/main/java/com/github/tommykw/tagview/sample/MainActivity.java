package com.github.tommykw.tagview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.tommykw.tagview.DataTransform;
import com.github.tommykw.tagview.TagView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List list = new ArrayList<Item>();

        list.add(new Item(1, "緊急帝王切開"));
        list.add(new Item(2, "男の子"));
        list.add(new Item(3, "子宮"));
        list.add(new Item(4, "流産"));
        list.add(new Item(5, "妊娠"));

        TagView tags = findViewById(R.id.tagview);
        tags.setTags(list, new DataTransform() {
            @NotNull
            @Override
            public String transfer(Object item) {
                if (item instanceof Item) {
                    return ((Item) item).getName();
                }
                return item.toString();
            }
        });
        tags.setClickListener(new TagView.TagClickListener() {
            @Override
            public void onTagClick(Object item) {
                if (item instanceof Item) {
                    int id = ((Item) item).getId();
                }

            }
        });
    }
}
