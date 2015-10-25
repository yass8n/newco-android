package co.newco.newco_android.Objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by noshaf on 3/3/15.
 */
public class scrollablefTextView extends fTextView {
    private float x1,x2;
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            this.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            this.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onTouchEvent(ev);
    }

    public scrollablefTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public scrollablefTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public scrollablefTextView(Context context) {
        super(context);
    }
}
