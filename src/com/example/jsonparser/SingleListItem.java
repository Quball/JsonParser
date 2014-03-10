package com.example.jsonparser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class SingleListItem extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_list_item);
		
		TextView movie_name = (TextView) findViewById(R.id.movie_title);
		TextView movie_year = (TextView) findViewById(R.id.movie_year);
		TextView movie_trailer = (TextView) findViewById(R.id.movie_trailer);
		
		
		Bundle b = getIntent().getExtras();
		
		// getting attached intent data from bundle
		String name = b.getString("TAG_TITLE");
		String year = b.getString("TAG_YEAR");
		String trailer = b.getString("TAG_TRAILER");
		
		movie_name.setText(name);
		movie_year.setText(year);
		movie_trailer.setText(trailer);
		//TODO: Embed youtube api (https://developers.google.com/youtube/android/player/)
		
		Toast.makeText(SingleListItem.this, "You clicked " + name, Toast.LENGTH_SHORT).show();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_list_item, menu);
		return true;
	}
	
}
