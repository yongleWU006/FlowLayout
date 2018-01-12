# FlowLayout
FlowLayout自定义ViewGroup，实现流式标签。
参考：https://www.imooc.com/learn/237

## 使用步骤：（可参考本项目下的MainActivity）

### 1. 添加依赖
```
compile 'com.yonglelib:flowlayout:1.1.0'
```
注意:如果添加依赖报错，在gradle中的allprojects/repositories下添加 maven { url 'https://dl.bintray.com/wuyongle006/maven' }
```
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/wuyongle006/maven' }
    }
}
```
### 2. 在布局文件中直接引用FlowLayout(自定义属性效果)
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <com.example.administrator.flowlayout.FlowLayout
        android:id="@+id/flow_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tag_margin_left="10dp"//设置标签的margin_left
        app:tag_margin_right="10dp"//设置标签的margin_right
        app:tag_margin_top="10dp"//设置标签的margin_top
        app:tag_margin_bottom="10dp"//设置标签的margin_bottom
        app:tag_padding_left="2dp"//设置标签的padding_left
        app:tag_padding_right="2dp"//设置标签的padding_right
        app:tag_padding_top="2dp"//设置标签的padding_top
        app:tag_padding_bottom="2dp"//设置标签的padding_bottom
        app:tag_drawable="@drawable/selector_blue"//设置标签的背景
        app:tag_lines="1"//设置标签文本行数
        app:tag_avg_model="true">//设置标签铺满均分模式
  
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="12345"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="hello world"/>
    </com.example.administrator.flowlayout.FlowLayout>
</LinearLayout>
```
### 3. 布局引用自定义属性
#### 设置标签的margin_left
```
tag_margin_left="10dp"  
```
#### 设置标签的margin_right
```
tag_margin_right="10dp"
```
#### 设置标签的margin_top
```
tag_margin_top="10dp"
```
#### 设置标签的margin_bottom
```
tag_margin_bottom="10dp"
```
#### 设置标签的padding_left
```
tag_padding_left="2dp"
```
#### 设置标签的padding_right
```
tag_padding_right="2dp"
```
#### 设置标签的padding_top
```
tag_padding_top="2dp"
```
#### 设置标签的padding_bottom
```
tag_padding_bottom="2dp"
```
#### 设置标签的背景
```
tag_drawable="@drawable/selector_blue"
```
#### 设置标签文本行数
```
tag_lines="1"
```
#### 设置标签铺满均分模式
```
tag_avg_model="true"
```
### 4. 在activity/fragment/adapter.viewHolder中findviewById，实例化FlowLayout调用API
```
flowLayout.setTags(tags)//设置标签个数，文本
                    .setTagBackground(R.drawable.shape_orange)//设置标签样式，点击样式
                    .setTagPadding(20,10,20,10)//设置标签padding值
                    .setTagMargin(10,10,10,10)//设置标签margin值
                    .setTagLine(2)//设置标签文本行数
                    .setAvgModel(true)//设置标签均分铺满模式
                    .initTags()//加载所有标签
                    .setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                        @Override
                        public void onTagClick(View view, String tagText, int position) {//设置标签点击事件
                            flowLayout.removeView(view);//移除控件
                            Toast.makeText(MainActivity.this,tagText+"--"+position,Toast.LENGTH_SHORT).show();
                        }
                    });
```

## FlowLayout API（链式调用）
#### 1. setTag（List<String>）
```
根据参数集合决定标签的个数
```
    
#### 2. setTagBackground(int resId)
```
根据参数决定标签样式或者点击变化样式
```

#### 3. setTagPadding(int left,int top,int right,int bottom) 
```
设置标签padding值
```

#### 4. setTagMargin(int left,int top,int right,int bottom)
```
设置标签margin值
```

#### 5. setTagLine(int line)
```
设置标签文本行数
```

#### 6. setAvgModel(boolean)
```
设置标签均分铺满模式
```

#### 7. initTags() 
```
加载所有标签(必须在1~6之后调用)
```

#### 8. setOnTagClickListener(FlowLayout.OnTagClickListener listener)
```
设置标签点击事件

FlowLayout.OnTagClickListener 标签点击接口
onTagClick(View view, String tagText, int position)
view--标签View
tagText--标签内容
position--标签index
```

#### 9. removeAllTag()
```
清除所有标签，在onDestory调用
@Override
protected void onDestroy() {
   flowLayout.removeAllTag();
   super.onDestroy();
}
```

### 小结：
项目上传github前，已做过测试，目前暂无发现bug，内存泄漏

### 语录：
采用链式调用，目的是为了更方便使用者实现自己的效果。例如删除标签，增加标签，直接调用Viewgroup的removeView，addView。

### todo： 
为标签添加动画效果
