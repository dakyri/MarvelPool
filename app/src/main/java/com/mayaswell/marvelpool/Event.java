package com.mayaswell.marvelpool;

/**
 * Created by dak on 4/26/2016.
 */
public class Event extends NamedResource {
	public Event(String resourceURI, String name) {
		super(resourceURI, name);
	}

	public Event() {
		this("","");
	}
}
