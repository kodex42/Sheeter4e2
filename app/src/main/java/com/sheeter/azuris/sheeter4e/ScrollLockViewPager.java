package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

// Used instead of a ViewPager to prevent the user from going too far ahead initially
public class ScrollLockViewPager extends ViewPager {

    private boolean paging;

    public ScrollLockViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paging = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.paging && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.paging && super.onInterceptTouchEvent(ev);
    }

    public void setPagingEnabled(boolean enabled) {
        this.paging = enabled;
    }
}