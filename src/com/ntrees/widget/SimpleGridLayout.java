package com.ntrees.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ntrees.simplegridlayout.R;

public class SimpleGridLayout extends FrameLayout {

	private static final String TAG = "SimpleGridLayout";
	static final boolean debug = false;

	int cellHeight, cellWidth, spaceH, spaceV;

	public SimpleGridLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SimpleGridLayout);
		cellHeight = (int) a.getDimension(
				R.styleable.SimpleGridLayout_cell_height, 0);
		cellWidth = (int) a.getDimension(
				R.styleable.SimpleGridLayout_cell_width, 0);
		spaceH = (int) a.getDimension(
				R.styleable.SimpleGridLayout_space_horizental, 0);
		spaceV = (int) a.getDimension(
				R.styleable.SimpleGridLayout_space_vertical, 0);

		Log.d(TAG, "cellHeight: " + cellHeight);
	}

	public SimpleGridLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SimpleGridLayout(Context context) {
		this(context, null);
	}

	public String toString() {
		return "BlockLayout: " + getLayoutParams();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		int maxHeight = 0;
		int maxWidth = 0;
		int childState = 0;

		LayoutParams tempLp = new LayoutParams(getLayoutParams());

		for (int i = 0; i < count; i++) {

			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {

				// Log.d(TAG, "child: " + child.toString());
				final LayoutParams params = (LayoutParams) child
						.getLayoutParams();
				tempLp.merge(params);
				int height = params.height();
				int width = params.width();

				int childWidthMesureSpec = MeasureSpec.makeMeasureSpec(width,
						MeasureSpec.EXACTLY);
				int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
						height, MeasureSpec.EXACTLY);

				child.measure(childWidthMesureSpec, childHeightMeasureSpec);
				maxWidth = Math.max(maxWidth, params.right());
				maxHeight = Math.max(maxHeight, params.bottom());
				childState = combineMeasuredStates(childState,
						child.getMeasuredState());
			}
		}

		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
		setMeasuredDimension(
				resolveSizeAndState(maxWidth + params.leftMargin
						+ params.rightMargin + getPaddingLeft()
						+ getPaddingRight(), widthMeasureSpec, childState),
				resolveSizeAndState(maxHeight, heightMeasureSpec,
						childState << MEASURED_HEIGHT_STATE_SHIFT));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		// Log.d("grid", left + ", " + top + ", " + right + ", " + bottom);
		int count = getChildCount();
		if (debug)
			Log.d(TAG, "onLayout");

		FrameLayout.LayoutParams selfParams = (android.widget.FrameLayout.LayoutParams) getLayoutParams();
		Rect blockPadding;
		if (selfParams instanceof LayoutParams)
			blockPadding = ((LayoutParams) selfParams).blockPadding;
		else
			blockPadding = new Rect();

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams params = (LayoutParams) child.getLayoutParams();
				Log.d(TAG, "layout params: " + params);
				child.layout(params.left() + blockPadding.left
						+ getPaddingLeft(), params.top() + blockPadding.top,
						params.right() + blockPadding.left + getPaddingLeft(),
						params.bottom() + blockPadding.top);
			}
		}
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams p = new LayoutParams(getContext(), attrs);
		p.cellHeight = cellHeight;
		p.cellWidth = cellWidth;
		p.spaceH = spaceH;
		p.spaceV = spaceV;
		return p;
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		// Log.d("grid", "checkLayoutParams:" + p);
		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		// Log.d("grid", "generateLayoutParams: " + p);
		return new LayoutParams(p);
	}

	public static class LayoutParams extends FrameLayout.LayoutParams {
		public Rect blockPadding = new Rect();

		public int spaceH = 0;
		public int spaceV = 0;
		public int cellWidth = 0;
		public int cellHeight = 0;

		public int cellX = 0;
		public int cellY = 0;
		public int cellSpanX = 0;
		public int cellSpanY = 0;

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);

			TypedArray a = c.obtainStyledAttributes(attrs,
					R.styleable.SimpleGridLayout);
			cellX = a.getInt(R.styleable.SimpleGridLayout_cell_x, 0);
			cellY = a.getInt(R.styleable.SimpleGridLayout_cell_y, 0);
			cellSpanX = a.getInt(R.styleable.SimpleGridLayout_cell_spanX, 1);
			cellSpanY = a.getInt(R.styleable.SimpleGridLayout_cell_spanY, 1);
			Log.d(TAG, "cellWidth: " + this.toString());
			a.recycle();
		}

		public LayoutParams(ViewGroup.LayoutParams source) {
			super(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			if (!(source instanceof LayoutParams))
				return;

			final LayoutParams blockParams = (LayoutParams) source;
			spaceH = blockParams.spaceH;
			spaceV = blockParams.spaceV;
			cellWidth = blockParams.cellWidth;
			cellHeight = blockParams.cellHeight;
			cellX = blockParams.cellX;
			cellY = blockParams.cellY;
			cellSpanX = blockParams.cellSpanX;
			cellSpanY = blockParams.cellSpanY;
			blockPadding = new Rect(blockParams.blockPadding);
		}

		/**
		 * Is this item currently being dragged
		 */
		public boolean isDragging;

		public String toString() {
			return String
					.format(getClass().getSimpleName()
							+ "(%d,%d,%d,%d)-(%d,%d,%d,%d)-(%d,%d,%d,%d)-pading(%s)-xy(%d,%d)",
							cellX, cellY, cellSpanX, cellSpanY, left(), top(),
							right(), bottom(), cellWidth, cellHeight, spaceH,
							spaceV, blockPadding.toString(), 0, 0);
		}

		public LayoutParams() {
			super(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

		public LayoutParams(int w, int h) {
			super(w, h);
		}

		public void setOutPadding(Rect padding) {
			blockPadding = padding;
		}

		public int width() {
			return cellSpanX * cellWidth + (cellSpanX - 1) * spaceH
					+ blockPadding.left + blockPadding.right;
		}

		public int height() {
			return cellSpanY * cellHeight + (cellSpanY - 1) * spaceV
					+ blockPadding.top + blockPadding.bottom;
		}

		public int childWidth() {
			return cellSpanX * cellWidth + (cellSpanX - 1) * spaceH;
		}

		public int childHeight() {
			return cellSpanY * cellHeight + (cellSpanY - 1) * spaceV;
		}

		public int right() {
			return left() + width();
		}

		public int top() {
			return cellY * (cellHeight + spaceV) - blockPadding.top;
		}

		public int left() {
			return cellX * (cellWidth + spaceH) - blockPadding.left;
		}

		public int bottom() {
			return top() + height();
		}

		public int cellRight() {
			return cellX + cellSpanX;
		}

		public int cellBottom() {
			return cellY + cellSpanY;
		}

		public Rect getDrawingRect() {
			return new Rect(left(), top(), right(), bottom());
		}

		public void merge(LayoutParams paras) {
			cellX = Math.min(cellX, paras.cellX);
			cellY = Math.min(cellY, paras.cellY);
			int cellR = Math.max(cellRight(), paras.cellRight());
			cellSpanX = cellR - cellX;
			int cellH = Math.max(cellBottom(), paras.cellBottom());
			cellSpanY = cellH - cellY;
		}
	}

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
		bringFocusToFrontIfNeed();
	}

	private void bringFocusToFrontIfNeed() {
		if (hasFocus())
			findFocus().bringToFront();
	}
}
