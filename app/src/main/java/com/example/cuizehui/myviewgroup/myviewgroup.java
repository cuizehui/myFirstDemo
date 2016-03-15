package com.example.cuizehui.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by cuizehui on 2016/3/9.
 */
public class myviewgroup extends ViewGroup {
    int lastx, lasty;
    int mx=0,my=0;
    Scroller mscroller;
    public myviewgroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mscroller =new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mscroller.computeScrollOffset()){
            //这里在起滑动的作用，主要是这个ScrollTO指导滑动
            scrollTo(mscroller.getCurrX(),mscroller.getCurrY());
            postInvalidate();
        }
    }
    public void smothscroll(int dx,int dy){
        mscroller.startScroll(getScrollX(),getScrollY(),-dx,dy);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpeccode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecsize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureSpeccode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSpecsize = MeasureSpec.getSize(heightMeasureSpec);
        if (count == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMeasureSpeccode == MeasureSpec.AT_MOST && heightMeasureSpeccode == MeasureSpec.AT_MOST) {
            final   View childview0 = getChildAt(0);
            //只能获取到测量值
            int childheigt = childview0.getMeasuredHeight();
            int childwidth = childview0.getMeasuredWidth();
                setMeasuredDimension(childwidth*count,childheigt);
        }else if(widthMeasureSpeccode == MeasureSpec.AT_MOST){
            View childview =getChildAt(0);
            int measurewidth=childview.getMeasuredWidth()*count;
            setMeasuredDimension(measurewidth,heightMeasureSpecsize);
        }else if(heightMeasureSpeccode == MeasureSpec.AT_MOST){
            View childview =getChildAt(0);
            int measuredHeight=childview.getMeasuredHeight();
            setMeasuredDimension(widthMeasureSpecsize,measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int clidrencount = getChildCount();
        int childrenleft = 0;
        View childview1=getChildAt(0);
        int chileviewheight1=childview1.getMeasuredHeight();
        int chileviewwidth1=childview1.getMeasuredWidth();
        for (int c = 0; c < clidrencount; c++) {
            View childrenView = getChildAt(c);
            childrenView.layout(childrenleft, 0, childrenleft + chileviewwidth1,chileviewheight1);
            childrenleft = childrenleft + chileviewwidth1;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastx = x;
                lasty = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetx = x - lastx;
                int offsety = y - lasty;
                Log.d("xy", x + "ww" + y);
                Log.d("last", lastx + "-" + lasty);
               // smothscroll(offsetx,0);
                  scrollBy(-offsetx,0);
                Log.d("ddd", offsetx + "");
                //由于这个函数是不断调用的所以应该重置上一次的坐标。
                lastx=x;
                lasty=y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
              return true;
    }
//viewGroup默认不拦截，这里采用外部拦截法处理滑动冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Boolean intercept=false;
        //获取坐标点：
          int x= (int) ev.getX();
          int y= (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept=false;

                break;
            case MotionEvent.ACTION_MOVE:
                int deletx=x-mx;
                int delety=y-my;
                if(Math.abs(deletx)>Math.abs(delety))
                {
                    intercept=true;
                    Log.d("拦截了","111");
                }
                else {
                    intercept=false;
                    Log.d("没拦截","222");
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
            default:
                break;
        }
        //这里尤其重要，解决了拦截MOVE事件却没有拦截DOWN事件没有坐标的问题
        lastx=x;
        lasty=y;
        mx=x;
        my=y;
        return intercept;
    }
}