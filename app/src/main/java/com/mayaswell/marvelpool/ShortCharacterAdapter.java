package com.mayaswell.marvelpool;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dak on 4/27/2016.
 */
public class ShortCharacterAdapter extends ArrayAdapter<Character> {
	public ShortCharacterAdapter(Context context, int resource) {
		super(context, resource);
	}

	public ShortCharacterAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ShortCharacterAdapter(Context context, int resource, Character[] objects) {
		super(context, resource, objects);
	}

	public ShortCharacterAdapter(Context context, int textViewResourceId, List<Character> objects) {
		super(context, textViewResourceId, objects);
	}

	public ShortCharacterAdapter(Context context, int resource, int textViewResourceId, Character[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ShortCharacterAdapter(Context context, int resource, int textViewResourceId, List<Character> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.short_character_list_item, parent, false);
		}
		final Character p = getItem(position);
		final TextView nameView = (TextView) convertView.findViewById(R.id.itemNameView);
		final ImageView imageView = (ImageView) convertView.findViewById(R.id.itemImageView);
		Log.d("CharacterAdapter", "got " + p.name + " .. " + p.thumbnailURI);

//		imageView.setImageURI(thumbnailURI);
// that doesn't work!!!! worker thread is better anyway for responsiveness
		p.setThumbnailBitmap(imageView);
		nameView.setText(p.name);
		return convertView;
	}
}
