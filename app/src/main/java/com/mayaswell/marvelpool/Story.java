package com.mayaswell.marvelpool;

/**
 * Created by dak on 4/26/2016.
 */
public class Story extends NamedResource {
	public String type;

	public Story(String resourceURI, String name, String type) {
		super(resourceURI, name);
		this.type = type;
	}

	public Story() {
		this("","", "");
	}
}
