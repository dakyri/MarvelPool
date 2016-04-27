package com.mayaswell.marvelpool;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by dak on 4/26/2016.
 */
public class Character {
	public int id;
	public String name;
	public String description;
	public String thumbnailURL;
	public ArrayList<Event> eventList;
	public ArrayList<Comic> comicList;
	public ArrayList<Story> storyList;
	public ArrayList<Series> seriesList;
	public ArrayList<InfoURL> urlList;
	public Bitmap thumbnailBitmap;

	public Character(int id, String name, String description, String thumbnailURL) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumbnailURL = thumbnailURL;
		this.urlList = null;
		this.comicList = null;
		this.storyList = null;
		this.seriesList = null;
		this.eventList = null;
		this.thumbnailBitmap = null;
	}

	public Character() {
		this(0, "", "", "");
	}

	public void addURLList(ArrayList<InfoURL> urlList) {
		this.urlList = urlList;
	}

	public void addComicList(ArrayList<Comic> comicList) {
		this.comicList = comicList;
	}

	public void addStoryList(ArrayList<Story> storyList) {
		this.storyList = storyList;
	}

	public void addSeriesList(ArrayList<Series> seriesList) {
		this.seriesList = seriesList;
	}

	public void addEventList(ArrayList<Event> eventList) {
		this.eventList = eventList;
	}
}
