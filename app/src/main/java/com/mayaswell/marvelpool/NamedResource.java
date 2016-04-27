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
		this.name = name;
		this.resourceURI = resourceURI;
		this.thumbnailURI = null;
		this.thumbnailBitmap = null;
	}

	public void setThumbnailBitmap(final ImageView comicImageView) {
		comicImageView.setImageResource(android.R.color.transparent);
		new ImageWorker() {
			@Override
			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					thumbnailBitmap = result;
					comicImageView.setImageBitmap(thumbnailBitmap);
				} else {
					comicImageView.setImageResource(android.R.color.transparent); // clears the image.
				}
			}
		}.execute(thumbnailURI);
	}
}
