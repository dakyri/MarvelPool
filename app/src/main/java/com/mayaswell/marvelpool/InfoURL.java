package com.mayaswell.marvelpool;

/**
 * Created by dak on 4/26/2016.
 */
public class InfoURL {
	public String type;
	public String url;

	public InfoURL(String type, String url) {
		this.type = type;
		this.url = url;
	}

	public InfoURL() {
		this("","");
	}
}
