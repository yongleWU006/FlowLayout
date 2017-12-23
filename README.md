# FlowLayout
FlowLayout自定义ViewGroup，实现流式标签。
参考：https://www.imooc.com/learn/237

使用步骤：（可参考本项目下的MainActivity）
1.copy本项目下的FlowLayout.java文件到你的项目中，内部是一个自定义ViewGroup的类（实现逻辑有注释）。
2.在布局文件中直接引用FlowLayout。
3.在activity/fragment/adapter.viewHolder中findviewById，实例化FlowLayout。
4.FlowLayout API（链式调用）
  4.1 setTag（List<String>） 根据参数集合决定标签的个数
  4.2 setTagBackground(int resId) 根据参数决定标签样式或者点击变化样式
  4.3 setTagPadding(int left,int top,int right,int bottom) 设置标签padding值
  4.4 setTagMargin(int left,int top,int right,int bottom) 设置标签margin值
  4.5 initTags() 加载所有标签(必须在4.1~4.4之后调用)
  4.6 setOnTagClickListener(FlowLayout.OnTagClickListener listener) 设置标签点击事件
  4.7 removeAllTag() 清除所有标签，在onDestory调用
5.FlowLayout.OnTagClickListener
  5.1 onTagClick(View view, String tagText, int position)
    5.1.1 view--标签View
    5.1.2 tagText--标签内容
    5.1.3 position--标签index

小结：项目上传github前，已做过测试，目前暂无发现bug，内存泄漏

语录：采用链式调用，目的是为了更方便使用者实现自己的效果。例如删除标签，增加标签，直接调用Viewgroup的removeView，addView。

todo： 为标签添加动画效果
