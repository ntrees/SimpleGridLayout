package com.ntrees.simplegridlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.ntrees.simplegridlayout.R;
import com.ntrees.widget.SimpleGridLayout;

public class MainActivity extends Activity {

	Rect[] rects = {
			new Rect(0, 0, 0, 1),
			new Rect(1, 0, 2, 1),
			new Rect(3, 0, 4, 0),
			new Rect(3, 1, 3, 1),

			new Rect(5, 0, 5, 0),
			new Rect(4, 1, 5, 1),
			new Rect(6, 0, 6, 0),
			new Rect(6, 1, 6, 1),

			new Rect(7, 0, 7, 0),
			new Rect(7, 1, 7, 1),
};
	
	int[] ids = {
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//		FrameLayout container = new FrameLayout(this);
//		View sbl = createBlockLayout(this, rects, ids);
//		container.addView(sbl);
        setContentView(R.layout.activity_main);
    }
    
    public SimpleGridLayout createBlockLayout(Context ctx, Rect[] rects, int[] ids) {
		SimpleGridLayout sbl = new SimpleGridLayout(ctx);
		SimpleGridLayout.LayoutParams p = new SimpleGridLayout.LayoutParams();
		p.cellWidth = 100;
		p.cellHeight = 100;
		p.spaceH = 10;
		p.spaceV = 10;
		
		for (int i = 0; i < rects.length; i ++) {
			ImageView iv = new ImageView(ctx);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(ids[i]);
			SimpleGridLayout.LayoutParams params = new SimpleGridLayout.LayoutParams(p);
			params.cellX = rects[i].left;
			params.cellY = rects[i].top;
			params.cellSpanX = rects[i].right - rects[i].left + 1;
			params.cellSpanY = rects[i].bottom - rects[i].top + 1;
			sbl.addView(iv, params);
		}
		
		sbl.setLayoutParams(p);
		return sbl;
	}

}
