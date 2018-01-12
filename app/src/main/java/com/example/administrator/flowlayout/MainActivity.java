package com.example.administrator.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yongle.flowlib.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private List<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    .setTagBackground(R.drawable.shape_orange)//设置标签样式，点击样式
                    .setTagPadding(10,10,10,10)//设置标签padding值
                    .setTagMargin(10,10,10,10)//设置标签margin值
                    .setTagLine(2)
                    .initTags()//加载所有标签
                    .setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                        @Override
                        public void onTagClick(View view, String tagText, int position) {//设置标签点击事件
                            flowLayout.removeView(view);//移除控件
                            Toast.makeText(MainActivity.this,tagText+"--"+position,Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    @Override
    protected void onDestroy() {
        flowLayout.removeAllTag();
        super.onDestroy();
    }
}
