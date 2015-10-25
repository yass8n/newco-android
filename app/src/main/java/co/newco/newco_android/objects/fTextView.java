package co.newco.newco_android.Objects;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import co.newco.newco_android.R;


/**
 * Created by noshaf on 3/3/15.
 */
public class fTextView extends TextView {

    public fTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        parseAttributes(context, attrs);
    }

    public fTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        parseAttributes(context, attrs);
    }

    public fTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Reg.otf");
            setTypeface(tf);
        }
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.fTextView);

        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.fTextView_typeface, 0);

        switch(typeface) {
            case 0: default:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Reg.otf"));
                break;
            case 1:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Bold.otf"));
                break;
            case 2:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Light.otf"));
                break;
            case 3:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Black.otf"));
                break;
            case 4:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Sbold.otf"));
                break;
            case 5:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Thin.otf"));
                break;
            case 6:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Xbold.otf"));
                break;
        }

        values.recycle();
    }
}
