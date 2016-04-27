package com.mayaswell.marvelpool;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * This and MainActivity could happily be refactored
 */
public class SearchActivity extends AppCompatActivity {

	private int nextOffset;
	private int currentlyRetrievingOffset;
	private MarvelSlurper marvelAPI;
	private ShortCharacterAdapter characterAdapter;
	private OSListView characterListView;
	private RelativeLayout mainView;
	private String currentQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		nextOffset = 0;
		currentlyRetrievingOffset = 0;

		characterAdapter = new ShortCharacterAdapter(this, R.layout.character_list_item);

		characterListView = (OSListView) findViewById(R.id.characterListView);
		characterListView.setAdapter(characterAdapter);
		characterListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});

		final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View loadingFooter = inflater.inflate(R.layout.short_character_list_footer, null, false);
		characterListView.addFooterView(loadingFooter);

		characterListView.setListener(new OSListView.Listener() {

			@Override
			public void overscrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
				if (characterListView.getCount() > 0 && characterListView.getChildAt(0).getTop() < 0) {
					synchronized (marvelAPI) {
						if (currentlyRetrievingOffset < nextOffset) {
							marvelAPI.getCharacters(nextOffset, -1, currentQuery);
							currentlyRetrievingOffset = nextOffset;
						}
					}
				}
			}
		});
		mainView = (RelativeLayout) findViewById(R.id.mainView);
		marvelAPI = new MarvelSlurper(
				getResources().getString(R.string.marvel_url_base),
				getResources().getString(R.string.api_key),
				getResources().getString(R.string.private_key)
		);
		marvelAPI.setListener(new MarvelSlurper.Listener() {

			@Override
			public void error(String msg) {
				Log.d("Main", "error " + msg);
				synchronized (marvelAPI) {
					currentlyRetrievingOffset = 0;
				}
			}

			@Override
			public void characterListResult(int offset, ArrayList<Character> cl) {
				if (cl != null) {
					displayCharacterList(offset, cl);
					synchronized (marvelAPI) {
						nextOffset = offset + cl.size();
						currentlyRetrievingOffset = 0;
					}
				}
			}
		});

		handleIntent(getIntent());

	}

	@Override
	protected void onStart()
	{
		super.onStart();
		doCurrentSearch();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));

		Log.d("Search", "got menu ");
		return true;
	}


	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
		doCurrentSearch();
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			Log.d("Search", "Got to search for " + query);
			currentQuery = query;
			refreshSearch();
		}
	}

	private void doCurrentSearch() {
		if (currentQuery == null) {
			Log.d("Search", "current query is null");
		}
		marvelAPI.getCharacters(currentlyRetrievingOffset, -1, currentQuery);
		synchronized (marvelAPI) {
			if (currentlyRetrievingOffset < nextOffset) {
				currentlyRetrievingOffset = nextOffset;
				marvelAPI.getCharacters(nextOffset, -1, currentQuery);
			}
		}
	}

	private void refreshSearch() {
		currentlyRetrievingOffset = 0;
		nextOffset = 0;
	}

}
