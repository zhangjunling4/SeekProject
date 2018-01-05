package com.seek.seekproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.fanwe.lib.select.config.FSelectImageViewConfig;
import com.fanwe.lib.select.view.FSelectView;
import com.seek.seekproject.R;


/**
 * 首页底部菜单tab
 */
public class LiveTabMainMenuView extends FSelectView
{
    public ImageView iv_tab_image;

    public LiveTabMainMenuView(Context context)
    {
        super(context);
        init();
    }

    public LiveTabMainMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabMainMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_tab_main_menu, this, true);
        iv_tab_image = (ImageView) findViewById(R.id.iv_tab_image);
    }

    public FSelectImageViewConfig configImage()
    {
        return configImage(iv_tab_image);
    }
}
