package com.mayaswell.marvelpool;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		handleIntent(getIntent());

	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			Log.d("Search", "Got to search for " + query);
			//use the query to search your data somehow
		}
	}

}
