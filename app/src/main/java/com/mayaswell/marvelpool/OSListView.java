package com.mayaswell.marvelpool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dak on 4/27/2016.
 */
public class OSListView extends ListView {
	private Listener listener = null;

	public interface Listener {
		void overscrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
	}
	public OSListView(Context context) {
		super(context);
	}

	public OSListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OSListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onOverScrolled (int scrollX, int scrollY, boolean clampedX, boolean clampedY)
	{
		if (listener != null) {
			listener.overscrolled(scrollX, scrollY, clampedX, clampedY);
		}
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	/**
	 * sets up our listener
	 * @param l
	 */
	public void setListener(Listener l )
	{
		listener  = l;
	}
}
