package com.mayaswell.marvelpool;

/**
 * Created by dak on 4/26/2016.
 */
public class Comic extends NamedResource {

	public Comic(String resourceURI, String name) {
		super(resourceURI, name);
	}

	public Comic() {
		this("","");
	}
}
