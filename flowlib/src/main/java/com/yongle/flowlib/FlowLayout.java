package com.yongle.flowlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *一行代码实现流式标签
 */

public class FlowLayout extends ViewGroup implements View.OnClickListener{
    public FlowLayout(Context context) {
        super(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        leftMG = (int) array.getDimension(R.styleable.FlowLayout_tag_margin_left,0);
        rightMG = (int) array.getDimension(R.styleable.FlowLayout_tag_margin_right,0);
        topMG = (int) array.getDimension(R.styleable.FlowLayout_tag_margin_top,0);
        bottomMG = (int) array.getDimension(R.styleable.FlowLayout_tag_margin_bottom,0);
        leftPD = (int) array.getDimension(R.styleable.FlowLayout_tag_padding_left,0);
        rightPD = (int) array.getDimension(R.styleable.FlowLayout_tag_padding_left,0);
        topPD = (int) array.getDimension(R.styleable.FlowLayout_tag_padding_left,0);
        bottomPD = (int) array.getDimension(R.styleable.FlowLayout_tag_padding_left,0);
        tagGb = array.getResourceId(R.styleable.FlowLayout_tag_drawable,0);
        line = array.getInteger(R.styleable.FlowLayout_tag_lines,1);
        avgModel = array.getBoolean(R.styleable.FlowLayout_tag_avg_model,false);
        array.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *测量子view的宽高，设置自己的宽高
     * 根据子view的布局文件，为子view设置测量模式和测量值
     *
     * 测量模式：
     * EXACTLY（精确值） 100dp math_parent
     * AT_MOST (子view的大小不超过父容器)  wrap_content
     * UNSPCIFIED  子view想多大就多大
     *
     * 子view.getLayoutParams()获得的就是父容器的LayoutParams
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content(MeasureSpec.AT_MOST) 在宽高未确定时，通过记录子view的宽高来确定
        int width = 0;
        int height = 0;

        //记录一行子view的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子view的个数
        int count = getChildCount();
        for (int i = 0;i < count;i++){
            View childView = getChildAt(i);
            //测量子view的宽高
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            //得到layoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
            //得到子view的宽高
            int childWidth = childView.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = childView.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //换行
            if(childWidth + lineWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){
                //对比每行子view确定父容器的宽
                width = Math.max(width,lineWidth);
                //重置行宽；为下一行第一个的子view的宽
                lineWidth = childWidth;
                //记录父容器的高，换行加一行子view的高
                height += lineHeight;
                //记录下一行行高
                lineHeight = childHeight;

                //未换行
            }else {
                //叠加子view的宽
                lineWidth += childWidth;
                //得到当前行最大的高
                lineHeight = Math.max(lineHeight,childHeight);

            }
            //最后一个子view
            if (i == count-1){
                //对比最后一行子view确定父容器的宽
                width = Math.max(lineWidth,width);
                //最后不换行，做出加上最后一行子view的高
                height += lineHeight;
            }
        }
        //设置父容器最终的宽高
        setMeasuredDimension(
                //判断父容器当前测量模式来确定宽高
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());

    }

    //储存每一行子view
    private List<List<View>> allViews = new ArrayList<>();
    //储存每一行的高
    private List<Integer> mLineHeight = new ArrayList<>();
    //剩余的宽度
    private List<Integer> residues = new ArrayList<>();

    /**
     *设置子view的位置
     */
    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        allViews.clear();
        mLineHeight.clear();
        //得到当前父容器的宽
        int width = getWidth();
        //记录一行子view的宽高
        int lineWidth = 0;
        int lineHeight = 0;
        //存放一行view
        List<View> lineViews = new ArrayList<>();
        //得到所有子view
        int count = getChildCount();

        for (int i = 0; i < count; i++){
            //得到子view
            View childView = getChildAt(i);
            //得到layoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
            //得到onMeasure中测量的子view宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //当一个子view超过父容器的宽时，重新测量当前子view的宽
            if (childWidth+ layoutParams.rightMargin + layoutParams.leftMargin > width - getPaddingRight() - getPaddingLeft()){
                //子view之前的宽度减去设置的左右margin值，否则子view会顶出父容器一部分，显示不完整
                childWidth -= (layoutParams.rightMargin + layoutParams.leftMargin);
                childView.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
                childWidth = childView.getMeasuredWidth();
                childHeight = childView.getMeasuredHeight();
            }

            //换行
            if (childWidth + lineWidth + layoutParams.rightMargin + layoutParams.leftMargin > width - getPaddingRight() - getPaddingLeft()){
                //记录行高
                mLineHeight.add(lineHeight);
                //记录当前一行子view
                allViews.add(lineViews);
                //获取换行前剩余的宽度
                residues.add(width - getPaddingLeft() - getPaddingRight() - lineWidth);
                //重置行宽
                lineWidth = 0;
                //设置下一行高
                lineHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                //为储存下一行子view
                lineViews = new ArrayList<>();
            }
            //叠加子view宽，计算行宽
            lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            //设置当前行高
            lineHeight = Math.max(lineHeight,childHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            //储存当前行的子view
            lineViews.add(childView);
        }
        //处理最后一行，储存最后行高
        mLineHeight.add(lineHeight);
        //储存每一行子view
        allViews.add(lineViews);
        //获取最后一行剩余的宽度
        residues.add(width - getPaddingLeft() - getPaddingRight() - lineWidth);

        //确定每一行子view，左上角的起点坐标
        int left = getPaddingLeft();
        int top = getPaddingTop();

        //得到行数
        int lineNum = allViews.size();

        for (int i = 0; i < lineNum; i++){
            //得到当前行
            lineViews = allViews.get(i);
            //得到当前行高
            lineHeight = mLineHeight.get(i);
            //平均剩余的宽度
            int avg = residues.get(i)/lineViews.size();

            for (int j = 0; j < lineViews.size(); j++){
                //得到当前行的子view
                View childView = lineViews.get(j);
                //判断当前子view是否可见
                if (childView.getVisibility() == GONE){
                    continue;
                }
                //是否采用均分模式
                if (avgModel){
                    // 获取宽高
                    int measuredWidth = childView.getMeasuredWidth();
                    int measuredHeight = childView.getMeasuredHeight();
                    // 重新测量
                    childView.measure(MeasureSpec.makeMeasureSpec(measuredWidth + avg, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
                }
                //得到layoutParams
                MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
                //得到起点左边
                int lc = left + layoutParams.leftMargin;//x轴坐标
                int tc = top + layoutParams.topMargin;//y轴坐标
                //得到子view边长
                int rc = lc + childView.getMeasuredWidth();
                int bc = tc + childView.getMeasuredHeight();

                //设置子view的位置
                childView.layout(lc, tc, rc, bc);
                //叠加，确定下一个子view的x轴起点坐标
                left += childView.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;

            }
            //以上for遍历完上一行view，重置下一行的x轴起点坐标
            left = getPaddingLeft();
            //加上当前行高，为下一行的y轴起点坐标
            top += lineHeight;
        }
    }

    //标签排列模式
    private boolean avgModel = false;

    //根据tags集合添加标签
    private List<String> tags;

    //标签字体颜色，默认黑色
    private int tagTextColor = Color.parseColor("#000000");

    //标签字体大小，默认20px
    private float tagTextSize = 20;

    //标签字体颜色，默认黑色
    private int tagGb = 0;

    //标签上下左右padding值
    private int leftPD = 10;
    private int rightPD = 10;
    private int topPD = 5;
    private int bottomPD = 5;

    //标签上下左右margin值
    private int leftMG = 5;
    private int rightMG = 5;
    private int topMG = 5;
    private int bottomMG = 5;

    //标签文本行数
    private int line = 1;

    //标签点击回调接口
    private OnTagClickListener onTagClickListener;

    //存放所有标签
    private List<TextView> tagLists = new ArrayList<>();

    //设置标签个数
    public FlowLayout setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public FlowLayout setAvgModel(boolean avgModel) {
        this.avgModel = avgModel;
        return this;
    }

    public List<TextView> getTagLists() {
        return tagLists;
    }

    //设置标签颜色,字体大小
    public FlowLayout setTagTextColor(int color, float textSize){
        this.tagTextColor = color;
        this.tagTextSize = textSize;
        return this;
    }

    //设置标签视图样式，点击样式
    public FlowLayout setTagBackground(int bg){
        this.tagGb = bg;
        return this;
    }

    //设置标签左上右下padding值
    public FlowLayout setTagPadding(int left, int top, int right, int bottom){
        this.leftPD = left;
        this.topPD = top;
        this.rightPD = right;
        this.bottomPD = bottom;
        return this;
    }

    //设置标签左上右下margin值
    public FlowLayout setTagMargin(int left, int top, int right, int bottom){
        this.leftMG = left;
        this.topMG = top;
        this.rightMG = right;
        this.bottomMG = bottom;
        return this;
    }

    //设置标签文本行数
    public FlowLayout setTagLine(int line){
        this.line = line;
        return this;
    }

    //设置标签点击接口
    public FlowLayout setOnTagClickListener(OnTagClickListener onTagClickListener) {
        setTagOnClick();
        this.onTagClickListener = onTagClickListener;
        return this;
    }

    //初始化所有标签
    public FlowLayout initTags(){
        if (tags != null){
            for (int i = 0; i < tags.size(); i++){
                TextView tag = new TextView(this.getContext());
                tag.setText(tags.get(i));
                tag.setTextColor(tagTextColor);
                tag.setTextSize(tagTextSize);
                tag.setGravity(Gravity.CENTER);
                tag.setBackgroundResource(tagGb);
                tag.setPadding(leftPD,topPD,rightPD,bottomPD);
                tag.setMaxLines(line);
                tag.setEllipsize(TextUtils.TruncateAt.END);
                MarginLayoutParams params = (MarginLayoutParams) this.getLayoutParams();
                params.setMargins(leftMG,topMG,rightMG,bottomMG);
                //给标签设置tag标记
                tag.setTag(tags.get(i));
                //存放所有已添加标签
                tagLists.add(tag);
                //标签加入父容器
                this.addView(tagLists.get(i));
            }
        }
        return this;
    }

    //设置所有标签的点击事件
    public FlowLayout setTagOnClick(){
        for (int i = 0;i < tagLists.size(); i++){
            tagLists.get(i).setOnClickListener(this);
        }
        return this;
    }

    @Override
    public void onClick(View view) {
        //标签点击回调
        onTagClickListener.onTagClick(view, view.getTag().toString(),tags.indexOf(view.getTag()));
    }

    //标签点击回调接口
    public interface OnTagClickListener{
        void onTagClick(View view, String tagText, int position);
    }

    //清空所有数据，防止泄露
    public void removeAllTag(){
        this.onTagClickListener = null;
        this.tagLists.clear();
        this.allViews.clear();
        this.mLineHeight.clear();
        this.tags.clear();
        this.removeAllViews();
    }

}
