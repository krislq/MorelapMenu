package com.morelap.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @{#} MenuItem.java Create on 2013-8-7 下午10:04:40    
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
public class MenuItem extends ImageView {

    private int circleX ;//圆弧上x位置
    private int circleY;//圆弧上y位置
    //角度
    private double circleAngle;
    private double moveAngle;


    public MenuItem(Context context) {
        super(context);
    }
    public MenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    
    public int getCircleX() {
        return circleX;
    }
    public void setCircleX(int circleX) {
        this.circleX = circleX;
    }
    public int getCircleY() {
        return circleY;
    }
    public void setCircleY(int circleY) {
        this.circleY = circleY;
    }
    public double getCircleAngle() {
        return circleAngle;
    }
    public void setCircleAngle(double circleAngle) {
        this.circleAngle = circleAngle;
        setMoveAngle(circleAngle);
    }
    
    public double getMoveAngle() {
        return moveAngle;
    }
    public void setMoveAngle(double moveAngle) {
        this.moveAngle = moveAngle;
    }
    private void l(String msg) {
        Log.e(this.getClass().getName(), msg);
    }
}
