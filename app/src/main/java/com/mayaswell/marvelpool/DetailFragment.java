package com.mayaswell.marvelpool;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dak on 4/27/2016.
 */
public class DetailFragment extends Fragment {

	private ImageView imageView;
	private TextView descriptionText;
	private TextView titleText;
	private LinearLayout comicList;
	private LinearLayout seriesList;
	private LinearLayout storyList;
	private LinearLayout eventList;
	private LinearLayout urlList;
	private Character displayedCharacter = null;
	private MarvelSlurper marvelAPI = null;
	private TextView comicListTitle;
	private TextView storyListTitle;
	private TextView seriesListTitle;
	private TextView eventListTitle;
	private TextView urlListTitle;

	public void setMarvelAPI(MarvelSlurper marvelAPI) {
		this.marvelAPI = marvelAPI;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.character_detail, container, true);

		imageView = (ImageView) v.findViewById(R.id.detailImageView);
		titleText = (TextView) v.findViewById(R.id.detailTitle);
		descriptionText = (TextView) v.findViewById(R.id.detailContent);
		comicList = (LinearLayout) v.findViewById(R.id.comicList);
		seriesList = (LinearLayout) v.findViewById(R.id.seriesList);
		storyList = (LinearLayout) v.findViewById(R.id.storyList);
		eventList = (LinearLayout) v.findViewById(R.id.eventList);
		urlList = (LinearLayout) v.findViewById(R.id.urlList);

		comicListTitle = (TextView) v.findViewById(R.id.comicTitle);
		seriesListTitle = (TextView) v.findViewById(R.id.seriesTitle);
		storyListTitle = (TextView) v.findViewById(R.id.storyTitle);
		eventListTitle = (TextView) v.findViewById(R.id.eventTitle);
		urlListTitle = (TextView) v.findViewById(R.id.urlTitle);

		if (displayedCharacter != null) {
			setCharacter(displayedCharacter);
		}
		return v;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	public void setCharacter(Character c) {
		displayedCharacter = null;
		if (c == null) {
			return;
		}
		if (titleText != null) {
			titleText.setText(c.name);
		}
		if (imageView != null) {
			c.setThumbnailBitmap(imageView);
		}
		if (descriptionText != null) {
			descriptionText.setText(c.description);
		}
		checkVisible(c.comicList, comicList, comicListTitle);
		if (comicList != null) {
			comicList.removeAllViews();
			for (Comic comic: c.comicList) {
				comicList.addView(getComicThumbView(comic));
			}
		}

		checkVisible(c.seriesList, seriesList, seriesListTitle);
		if (seriesList != null) {
			seriesList.removeAllViews();
			for (Series comic: c.seriesList) {
				seriesList.addView(getComicThumbView(comic));
			}
		}
		checkVisible(c.storyList, storyList, storyListTitle);
		if (storyList != null) {
			storyList.removeAllViews();
			for (Story comic: c.storyList) {
				storyList.addView(getComicThumbView(comic));
			}
		}
		checkVisible(c.eventList, eventList, eventListTitle);
		if (eventList != null) {
			eventList.removeAllViews();
			for (Event comic: c.eventList) {
				eventList.addView(getComicThumbView(comic));
			}
		}
		checkVisible(c.urlList, urlList, urlListTitle);
		if (urlList != null) {
			urlList.removeAllViews();
			for (InfoURL info: c.urlList) {
				urlList.addView(getURLView(info));
			}
		}
	}

	private void checkVisible(ArrayList<?> l, LinearLayout lll, TextView lt) {
		if (l.size() == 0) {
			if (lt != null) lt.setVisibility(View.GONE);
			if (lll != null) lll.setVisibility(View.GONE);
		} else {
			if (lt != null) lt.setVisibility(View.VISIBLE);
			if (lll != null) lll.setVisibility(View.VISIBLE);
		}
	}

	private View getURLView(InfoURL info) {
		final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View urlView = inflater.inflate(R.layout.url_info_item_view, null, false);
		TextView tv = (TextView) urlView.findViewById(R.id.urlTextView);
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setText(Html.fromHtml("<a href=\"" + info.url + "\">" + info.type.toUpperCase() + "</a>"));
		return urlView;
	}

	private View getComicThumbView(final NamedResource c) {
		final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View comicView = inflater.inflate(R.layout.comic_thumb_view, null, false);
		final ImageView comicImageView = (ImageView) comicView.findViewById(R.id.itemImageView);
		final TextView comicNameView = (TextView) comicView.findViewById(R.id.itemNameView);
		if (comicNameView != null) {
			comicNameView.setText(c.name);
		}
		if (c.thumbnailBitmap == null) {
			if (c.thumbnailURI == null) {
				if (marvelAPI != null) {
					marvelAPI.getThumbnailResourceAtURI(c, comicImageView);
				}
			} else {
				c.setThumbnailBitmap(comicImageView);
			}

		} else {
			comicImageView.setImageBitmap(c.thumbnailBitmap);
		}
		return comicView;
	}

}
