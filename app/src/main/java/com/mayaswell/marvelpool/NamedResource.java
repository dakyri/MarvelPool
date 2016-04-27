package com.mayaswell.marvelpool;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Base class for comics, events, stories and series
 */
public class NamedResource {
	public String resourceURI;
	public String name;
	public String thumbnailURI;
	public Bitmap thumbnailBitmap;

	public NamedResource(String resourceURI, String name) {
		this(resourceURI, name, null);
	}

	public NamedResource(String resourceURI, String name, String thumbnailURI) {
		this.name = name;
		this.resourceURI = resourceURI;
		this.thumbnailURI = thumbnailURI;
		this.thumbnailBitmap = null;
	}

	public void setThumbnailBitmap(final ImageView iv) {
		if (thumbnailURI == null || thumbnailURI.equals(""))
			return;
		if (iv != null) {
			if (thumbnailBitmap != null) {
				iv.setImageBitmap(thumbnailBitmap);
				return;
			}
			iv.setImageResource(android.R.color.transparent);
		}
		new ImageWorker() {
			@Override
			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					thumbnailBitmap = result;
					if (iv != null)
						iv.setImageBitmap(thumbnailBitmap);
				} else {
					if (iv != null)
						iv.setImageResource(android.R.color.transparent); // clears the image.
				}
			}
		}.execute(thumbnailURI);
	}
}
