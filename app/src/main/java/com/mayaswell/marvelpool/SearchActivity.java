package com.mayaswell.marvelpool;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

	private MarvelSlurper marvelAPI;
	private ShortCharacterAdapter characterAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		handleIntent(getIntent());

		characterAdapter = new ShortCharacterAdapter(this, R.layout.character_list_item);

		marvelAPI = new MarvelSlurper(
				getResources().getString(R.string.marvel_url_base),
				getResources().getString(R.string.api_key),
				getResources().getString(R.string.private_key)
		);
		marvelAPI.setListener(new MarvelSlurper.Listener() {

			@Override
			public void error(String msg) {
				Log.d("Main", "error " + msg);
//				synchronized (marvelAPI) {
//					currentlyRetrievingOffset = 0;
//				}
			}

			@Override
			public void characterListResult(int offset, ArrayList<Character> cl) {
				if (cl != null) {
					displayCharacterList(offset, cl);
//					synchronized (marvelAPI) {
//						nextOffset = offset + cl.size();
//						currentlyRetrievingOffset = 0;
//					}
				}
			}
		});
	}

	private void displayCharacterList(int offset, ArrayList<Character> cl) {
		for (Character c: cl) {
			if (!isDisplayed(c)) {
				characterAdapter.add(c);
			}
		}
	}
	
	private boolean isDisplayed(Character c) {
		for (int i=0; i<characterAdapter.getCount(); i++) {
			Character c2 = characterAdapter.getItem(i);
			if (c2.id == c.id) {
				return true;
			}
		}
		return false;
	}


	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			Log.d("Search", "Got to search for " + query);
			marvelAPI.getCharacters(0, -1, query);
		}
	}

}
