package com.titan.estoque.estoquetitan.Classes_Especiais;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextViewLight extends TextView {

    public FontTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontTextViewLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoe.ttf");
            setTypeface(tf);
        }
    }

}
