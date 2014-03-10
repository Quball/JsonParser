package com.example.jsonparser;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends ListActivity {
	
	EditText inputSearch;
	CharSequence searchTxt = "";
	
	SimpleAdapter adapter;
	
	//TODO: Implementer activity som viser det norske flagget ved hjelp av layout 

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	// trakt api key: 3c9e72585fd596e63bf851a98023b8c7
	private static String url = "http://api.trakt.tv/movies/trending.json/3c9e72585fd596e63bf851a98023b8c7";
	
	// JSON Node names
	private static final String TAG_TITLE = "title";
	private static final String TAG_YEAR = "year";
	private static final String TAG_TRAILER = "trailer";
	private static final String TAG_RUNTIME = "runtime";
	private static final String TAG_TAGLINE = "tagline";
	private static final String TAG_OVERVIEW = "overview";
	private static final String TAG_GENRES = "genres"; //Array
	
	
	// movies JSONarray
	JSONArray movies = null;
	
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> movieList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		movieList = new ArrayList<HashMap<String, String>>();
		
		ListView lv = getListView();
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		final Button searchBtn = (Button) findViewById(R.id.searchBtn);
		
		inputSearch.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
            	System.out.println(cs);
            	searchTxt = cs; 
            	if(searchTxt.length() == 0) {
            		MainActivity.this.adapter.getFilter().filter(null);
            	}
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                 
            }
            @Override
            public void afterTextChanged(Editable arg0) {                        
            }
        });
        
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(searchTxt.length() > 0) {
                	MainActivity.this.adapter.getFilter().filter(searchTxt);
                	Toast.makeText(MainActivity.this, "Searching for: " + searchTxt, Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(MainActivity.this, "Enter search text", Toast.LENGTH_SHORT).show();
                }
            }
        });
	
		// Listen to click on a single list item
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String year = ((TextView) view.findViewById(R.id.year)).getText().toString();
				String trailer = ((TextView) view.findViewById(R.id.trailer)).getText().toString();
				
				Intent i = new Intent(getApplicationContext(), SingleListItem.class);
				
				Bundle extras = new Bundle();
				extras.putString("TAG_TITLE", name);
				extras.putString("TAG_YEAR", year);
				extras.putString("TAG_TRAILER", trailer);
				i.putExtras(extras);
				
				startActivity(i);
			}
		});
		
		new GetMovies().execute();
	}
	
	//Actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_activity_actions, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    //Actionbar listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//Handle clicked item
    	switch (item.getItemId()) {
    		case R.id.flagAct:
    			openFlag();
    			return true;
			default:
    			return super.onOptionsItemSelected(item);
    	}
    }
    
    //Method for actionbar flag icon
    public void openFlag() {
    	Intent intent = new Intent(this, DisplayFlagActivity.class);
    	startActivity(intent);
    }
	
	/*
	 * Async task class to get json by making HTTP call
	 */
	private class GetMovies extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			// Making request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStr);
			
			if(jsonStr != null) {
				try {
					JSONArray jsonArr = new JSONArray(jsonStr);
					
					// Getting JSON Object node
					
					// Looping through all contacts
					for(int i = 0; i < jsonArr.length(); i++){
						
						JSONObject m = jsonArr.getJSONObject(i);
						
						String name = m.getString(TAG_TITLE);
						//int year = m.getInt(TAG_YEAR);
						String year = m.getString(TAG_YEAR);
						String trailer = m.getString(TAG_TRAILER);
						//int runtime = m.getInt(TAG_RUNTIME);
						String runtime = m.getString(TAG_RUNTIME);
						String tagline = m.getString(TAG_TAGLINE);
						String overview = m.getString(TAG_OVERVIEW);
						// Genres node is JSON Array
						JSONArray genres = m.getJSONArray(TAG_GENRES);
						String genres_list = "";
						
						if(genres != null){
							for(int y = 0; y < genres.length(); y++) {
								String tmp = genres.get(y).toString();
								if (y == genres.length() -1){
									genres_list += tmp;
								}else{
									genres_list += tmp + ", ";
								}
							}
						}
						
						// tmp hashmap for single contact
						HashMap<String, String> movie = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						movie.put(TAG_TITLE, name);
						movie.put(TAG_YEAR, year);
						movie.put(TAG_TRAILER, trailer);
						movie.put(TAG_RUNTIME, runtime);
						movie.put(TAG_TAGLINE, tagline);
						movie.put(TAG_OVERVIEW, overview);
						movie.put(TAG_GENRES, genres_list);
						
						// adding contact to contact list
						movieList.add(movie);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			// Dismiss the progress dialog
			if(pDialog.isShowing()) {
				pDialog.dismiss();
			}
			/*
			 * Updating parsed JSON into ListView
			 */
			adapter = new SimpleAdapter(
					MainActivity.this, movieList,
					R.layout.list_item, new String[] { TAG_TITLE, TAG_YEAR, TAG_TRAILER, TAG_RUNTIME, TAG_TAGLINE, TAG_OVERVIEW, TAG_GENRES },
					new int[] { R.id.name, R.id.year, R.id.trailer, R.id.runtime, R.id.tagline, R.id.overview, R.id.genres });
			setListAdapter(adapter);
		}
	}
}
