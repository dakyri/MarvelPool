package com.mayaswell.marvelpool;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 */
public class Character extends NamedResource {
	public int id;
	public String description;
	public ArrayList<Event> eventList;
	public ArrayList<Comic> comicList;
	public ArrayList<Story> storyList;
	public ArrayList<Series> seriesList;
	public ArrayList<InfoURL> urlList;

	public Character(int id, String name, String description, String thumbnailURI) {
		super(null, name, thumbnailURI);
		this.id = id;
		this.description = description;
		this.urlList = null;
		this.comicList = null;
		this.storyList = null;
		this.seriesList = null;
		this.eventList = null;
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
