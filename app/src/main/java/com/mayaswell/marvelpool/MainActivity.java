package com.mayaswell.marvelpool;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private MarvelSlurper marvelAPI;
	private OSListView characterListView;
	private CharacterAdapter characterAdapter;
	private Character selectedCharacter;
	private int nextOffset;
	private int currentlyRetrievingOffset;
	private DetailFragment detailFragment;
	private RelativeLayout mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nextOffset = 0;
		currentlyRetrievingOffset = 0;
		selectedCharacter = null;
		characterAdapter = new CharacterAdapter(this, R.layout.character_list_item);

		characterListView = (OSListView) findViewById(R.id.characterListView);
		characterListView.setAdapter(characterAdapter);
		characterListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedCharacter = characterAdapter.getItem(position);
				showDetailView(selectedCharacter);
			}
		});

		final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View loadingFooter = inflater.inflate(R.layout.character_list_footer, null, false);
		characterListView.addFooterView(loadingFooter);

		characterListView.setListener(new OSListView.Listener() {

			@Override
			public void overscrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
				if (characterListView.getCount() > 0 && characterListView.getChildAt(0).getTop() < 0) {
					synchronized (marvelAPI) {
						if (currentlyRetrievingOffset < nextOffset) {
							marvelAPI.getCharacters(nextOffset);
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

		detailFragment = new DetailFragment();
		detailFragment.setMarvelAPI(marvelAPI);
	}

	/**
	 * @param c
	 */
	private void showDetailView(Character c) {
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		View detailView = detailFragment.getView();
		FragmentManager fm = getFragmentManager();
		if (detailView == null) {
			fm.beginTransaction().add(detailFragment, "details").commit();
			fm.executePendingTransactions();
			detailView = detailFragment.getView();
		}
		if (detailView != null && detailView.getParent() == null) {
			mainView.addView(detailView);
		}
		detailFragment.setCharacter(c);
		detailView.setVisibility(View.VISIBLE);
		characterListView.setVisibility(View.GONE);
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	private void showMainView() {
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
		characterListView.setVisibility(View.VISIBLE);
		View detailView = detailFragment.getView();
		if (detailView != null) {
			detailView.setVisibility(View.GONE);
		}

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
	protected void onStart()
	{
		super.onStart();
		marvelAPI.getCharacters();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d("Main", "got intent " + intent.toString());
		super.onNewIntent(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));

		Log.d("Main", "got menu ");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Log.d("Main", "got item ");

		switch (item.getItemId()) {
			case android.R.id.home:
				//Write your logic here
				showMainView();
				return true;
			default:
				;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}