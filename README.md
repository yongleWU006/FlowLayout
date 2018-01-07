# FlowLayout
FlowLayout自定义ViewGroup，实现流式标签。
参考：https://www.imooc.com/learn/237

## 使用步骤：（可参考本项目下的MainActivity）

1. copy本项目下的FlowLayout.java文件到你的项目中，内部是一个自定义ViewGroup的类（实现逻辑有注释）
2. 在布局文件中直接引用FlowLayout
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <com.example.administrator.flowlayout.FlowLayout
        android:id="@+id/flow_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
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
3. 在activity/fragment/adapter.viewHolder中findviewById，实例化FlowLayout调用API
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
1. setTag（List<String>） 根据参数集合决定标签的个数
2. setTagBackground(int resId) 根据参数决定标签样式或者点击变化样式
3. setTagPadding(int left,int top,int right,int bottom) 设置标签padding值
4. setTagMargin(int left,int top,int right,int bottom) 设置标签margin值
5. setTagLine(int line) 设置标签文本行数
6. setAvgModel(boolean) 设置标签均分铺满模式
7. initTags() 加载所有标签(必须在1~6之后调用)
8. setOnTagClickListener(FlowLayout.OnTagClickListener listener) 设置标签点击事件
9. removeAllTag() 清除所有标签，在onDestory调用
  ```
  @Override
    protected void onDestroy() {
        flowLayout.removeAllTag();
        super.onDestroy();
    }
  ```
  
## FlowLayout.OnTagClickListener
1. onTagClick(View view, String tagText, int position)
 ```
 view--标签View
 tagText--标签内容
 position--标签index
 ```

### 小结：
项目上传github前，已做过测试，目前暂无发现bug，内存泄漏

### 语录：
采用链式调用，目的是为了更方便使用者实现自己的效果。例如删除标签，增加标签，直接调用Viewgroup的removeView，addView。

### todo： 
为标签添加动画效果
