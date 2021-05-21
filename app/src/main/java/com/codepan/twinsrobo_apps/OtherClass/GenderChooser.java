package com.codepan.twinsrobo_apps.OtherClass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;

public class GenderChooser extends AppCompatRadioButton {

    private OnCheckedChangeListener onCheckedChangeListener;

    public GenderChooser(Context context) {
        super(context);
    }

    public GenderChooser(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenderChooser(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setOwnOnCheckedChangeListener();
        setButtonDrawable(null);
    }

    public void setOwnonCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private void setOwnOnCheckedChangeListener() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });
    }
}
