package com.example.administrator.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private List<String> tags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        tags = new ArrayList<>();
        tags.add("hhaa");
        tags.add("hhaadd");
        tags.add("hhaad");
        tags.add("hhaafgh");
        tags.add("hha");
        tags.add("haa");
        tags.add("hhaazzxccvhhaazzxccvhhaazzxccvxccvxccvxccv");
        tags.add("welcome");
        tags.add("world");
        tags.add("big");
        tags.add("youyoyu");
        tags.add("hpplly");
        tags.add("nice");
        flowLayout.setTags(tags)//设置标签个数，文本
                .initTags()//加载所有标签
                .setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                    @Override
                    public void onTagClick(View view, String tagText, int position) {//设置标签点击事件
                        Toast.makeText(Main2Activity.this,tagText+"--"+position,Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        flowLayout.removeAllTag();
        super.onDestroy();
    }
}
