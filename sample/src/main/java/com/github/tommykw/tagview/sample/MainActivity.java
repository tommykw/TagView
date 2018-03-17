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

        List<Item> list = new ArrayList<>();

        list.add(new Item(1, "cesarean section"));
        list.add(new Item(2, "boy"));
        list.add(new Item(3, "natural birth"));
        list.add(new Item(4, "pregnancy"));

        TagView<Item> tags = findViewById(R.id.tagview);
        tags.setTags(list, new DataTransform<Item>() {
            @NotNull
            @Override
            public String transfer(Item item) {
                return item.getName();
            }
        });
        tags.setClickListener(new TagView.TagClickListener<Item>() {
            @Override
            public void onTagClick(Item item) {
                item.getId();
            }
        });
    }
}
