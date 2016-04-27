package com.mayaswell.marvelpool;

/**
 * Created by dak on 4/26/2016.
 */
public class Series extends NamedResource {

	public Series(String resourceURI, String name) {
		super(resourceURI, name);
	}

	public Series() {
		this("","");
	}
}
