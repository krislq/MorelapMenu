package com.morelap.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 
 * @{#} MorelapMenu.java Create on 2013-8-7 下午10:04:26    
 *    
 * class desc:   
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: morelap</p>
 * @Version 1.0
 * @Author <a href="mailto:kris@morelap.com">Morelap</a>   
 *  
 *
 */
public class MorelapMenu extends FrameLayout {
    private List<MenuItem>  mMenus = null;
    private int mRadius = 300;
    private int mMaxItem = 4;
    private double intervalAngle = 0;
    private MenuItem mOperationMenu = null;

    public MorelapMenu(Context context) {
        super(context);
    }

    public MorelapMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMenus = new ArrayList<MenuItem>();
        setBackgroundColor(Color.GRAY);
        //add the logo
        
        MenuItem logo = new MenuItem(getContext());
        logo.setImageResource(R.drawable.logo);
        mMenus.add(logo);
        
        mOperationMenu = new MenuItem(getContext());
        mOperationMenu.setImageResource(R.drawable.ic_open);
        //need to set the clickable =true , otherwise , can not listen the touch event
        setClickable(true);
    }

    public MorelapMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public void addMenu(MenuItem item) {
        if(item!=null) {
            mMenus.add(item);
            //每次有项添加进来，都需要全部重新算过
            resetMenu();
        }
    }
    public void resetMenu(){
        //计算每个项的位置
        int itemSize = mMenus.size();
        if(itemSize<=0) {
            return;
        }
        if(itemSize > mMaxItem) {
            //如果用户设置的最多4个按钮，当只有3个的时候，那么就是三等分，
            //只有当用户已经设置了4个或者更多的时候我们才把半圆四等分
            itemSize = mMaxItem;
        }
        removeAllViews();
        intervalAngle = 90 / (double)itemSize;
        double centerAngle = 90 - (intervalAngle/2);
        MenuItem logo = mMenus.get(0);
        logo.setCircleAngle(90+(intervalAngle/2));
        addView(logo);
        FrameLayout.LayoutParams logoparams = (FrameLayout.LayoutParams)logo.getLayoutParams();
        logoparams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        logoparams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        logo.setLayoutParams(logoparams);

        for(int i = 1; i <mMenus.size();i++) {
            MenuItem item = mMenus.get(i);
            l("centerAngle:"+centerAngle);
            item.setCircleAngle(centerAngle);
            addView(item);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)item.getLayoutParams();
            params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            item.setLayoutParams(params);
            centerAngle = centerAngle -intervalAngle;
        }
        
        //add operation menu

        addView(mOperationMenu);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mOperationMenu.getLayoutParams();
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        mOperationMenu.setLayoutParams(params);
    }

    // 覆写View.onMeasure回调函数，用于计算所有child view的宽高，这里偷懒没有进行MeasureSpec模式判断
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mRadius, mRadius);
    }
    //refer:http://blog.csdn.net/androiddevelop/article/details/8108970
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        l( "changed = " + changed
                + ", left = " + left + ", top = " + top   
                + ", right = " + right + ", bottom = " + bottom);
        int childCount = getChildCount();
        for(MenuItem menuItem:mMenus) {
//            View childView = getChildAt(i);
//            if(childView instanceof MenuItem) {
//                MenuItem menuItem = (MenuItem) childView;
                double centerAngle = menuItem.getMoveAngle();
                //圆弧宽度
                int arcWidth = (int)(Math.cos(toRadian(centerAngle))*mRadius);
                int arcHeight = (int)(Math.sin(toRadian(centerAngle))*mRadius);

                l("centerAngle:"+centerAngle+"#arcWidth:"+arcWidth+"#"+"arcHeight:"+arcHeight);
                // 获取在onMeasure中计算的视图尺寸  
                   int measureHeight = menuItem.getMeasuredHeight();
                   int measuredWidth = menuItem.getMeasuredWidth();
                   int parentHeight = bottom - top;
                   int l = arcWidth - measuredWidth;
                   int r = arcWidth;
                   int t = parentHeight - arcHeight;
                   int b = parentHeight - arcHeight + measureHeight;
                   l("l:"+l+"#r:"+r+"#t:"+arcHeight+"#b:"+b);
                   menuItem.layout(l, t, r, b);
//            } 
        }
        //add menu
        int measureHeight = mOperationMenu.getMeasuredHeight();
        int measuredWidth = mOperationMenu.getMeasuredWidth();
        int layoutHeight = getHeight();
        int operationMenuPadding = 10;
        mOperationMenu.layout(0+operationMenuPadding, layoutHeight-measureHeight-operationMenuPadding, measuredWidth+operationMenuPadding, layoutHeight-operationMenuPadding);
//        mOperationMenu.layout(0, layoutHeight-measureHeight, measuredWidth, layoutHeight);
    }
    
    private int startx;
    private int starty;
    private double startAngle;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getx, gety是针对你控件的
//        int x = (int) event.getX();
//        int y = (int) event.getY();
        int rawx = (int) event.getRawX();
        int rawy = (int) event.getRawY();
        l("rawx:"+rawx+"#rawy:"+rawy);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            l("MotionEvent.ACTION_DOWN");
            startx = rawx;
            starty = rawy;
            startAngle = calcAngle(startx, starty);
            l("startAngle:"+startAngle);
            break;
        case MotionEvent.ACTION_MOVE:
            l("MotionEvent.ACTION_MOVE");
            double currentAngle = calcAngle(rawx, rawy);
            l("startAngle:"+startAngle+"#currentAngle:"+currentAngle);
            double rotateAngle = currentAngle-startAngle;
            l("rotateAngle:"+rotateAngle);
            //重置每个菜单的位置
            for(MenuItem item:mMenus) {
//                item.setCircleAngle(item.getCircleAngle() - rotateAngle);
                item.setMoveAngle(item.getCircleAngle() + rotateAngle);
            }
            requestLayout();
            l("#####################");
            break;

        case MotionEvent.ACTION_UP:
            l("MotionEvent.ACTION_UP");
            double endAngle = calcAngle(rawx, rawy);
            double offsetAngle = endAngle-startAngle;
            for(MenuItem item:mMenus) {
//              item.setCircleAngle(item.getCircleAngle() - rotateAngle);
              item.setCircleAngle(item.getCircleAngle() + offsetAngle);
            }
            requestLayout();
            //TODO check the menu position
            adjustMenuPosition();
            break;
        default:
            break;
        }
        return super.onTouchEvent(event);
    }
    
    private void adjustMenuPosition(){
        MenuItem firstItem = mMenus.get(1);
        MenuItem lastItem = mMenus.get(mMenus.size()-1);
        double moveAngle = 0;
        double firstAngle = 90 - (intervalAngle/2);

        if(firstItem.getCircleAngle() < firstAngle) {
            //当第一个菜单按钮的位置等于他在初始化的位置时，就需要把位置还原
            l("the first angle is too low");
            moveAngle = firstAngle - firstItem.getCircleAngle();
        } else if(lastItem.getCircleAngle() > (intervalAngle/2)){
            //当最后一个菜单的位置大于地平线时
            //需要把最后一个菜单还原到可见的最后一个位置
            moveAngle = (intervalAngle/2) - lastItem.getCircleAngle();
        }
        for(MenuItem item:mMenus) {
          item.setCircleAngle(item.getCircleAngle() + moveAngle);
        }
        requestLayout();
    }
    
    /**
     * 计算给定坐标与menu left| bottom的角度
     * method desc：
     * @param x
     * @param y
     * @return
     */
    private double calcAngle(int x, int y) {
        //get the location of this menu
        int[] location =new int[2];
        getLocationOnScreen(location);
//        int menuLeft = location[0];
        int menuBottom = location[1]+getHeight();
        //这里记得转化成double,要不然，精确度都没有了
        double radian = Math.atan((menuBottom-x)/(double)y);
        return toAngle(radian);
    }

    /**
     * 角度 转化成弧度
     * method desc：
     * @param angle
     * @return
     */
    private double toRadian(double angle) {
        //360度 = 2 * PI
        return (Math.PI/180)*angle;
    }
    private double toAngle(double radian) {
        return 180 * radian / Math.PI;
    }

    private void l(String msg) {
        Log.e(this.getClass().getName(), msg);
    }
}
