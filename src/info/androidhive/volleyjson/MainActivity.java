package info.androidhive.volleyjson;

import info.androidhive.volleyjson.R;
import info.androidhive.volleyjson.app.AppController;
import info.androidhive.volleyjson.util.Content;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity 
	{
	    private GoogleMap googleMap;

		// json object response url
		private String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";
		
		// json array response url
		private String urlJsonArry = "https://gist.githubusercontent.com/sanjogshrestha/58e6e9c72556af80195f/raw/37ec8f08aee5522e5527d1de5fe293ea92defa9c/json";
		//https://gist.githubusercontent.com/sanjogshrestha/968f713b4c707f4cfbd4/raw/b2c9fd087ad3d274ac8ae043049a8e98ce975814/Json

		//http://api.androidhive.info/volley/person_array.json

		private static String TAG = MainActivity.class.getSimpleName();

		// Progress dialog
		private ProgressDialog pDialog;
		
		Content content = new Content();
		public static String valueEntered;


		// temporary string to show the parsed response
		private String jsonResponse;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_map);	
			redirect();
			//initializeMap();
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    	googleMap.getUiSettings();
	    	googleMap.getUiSettings().setZoomControlsEnabled(true);

			

		}

			/**
		 * Method to make json object request where json response starts wtih {
		 * */
		private void redirect() {

			
			//showpDialog();

			JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							Log.d(TAG, response.toString());

							
							try {
							Content content = new Content();
								// Parsing json array response
								// loop through each json object
								jsonResponse = "";
								for (int i = 0; i < response.length(); i++) {

									 JSONObject person = (JSONObject) response
			                                    .get(i);
			 
			                            String name = person.getString("name");
			                            String email = person.getString("population");
			                            String relation = person.getString("relation");
			                            JSONArray latlng = person
			                                    .getJSONArray("latlng");
			                            Double lat=person.getJSONArray("latlng").getDouble(0);
			                            Double lng=person.getJSONArray("latlng").getDouble(1);
			                            
			                           
			                            content.setName(name);
			                            
			                            content.setPopulation(email);
			                            content.setlat(lat);
			                            content.setlng(lng);
			                            content.setRelation(relation);
			                            System.out.println("name="+content.getName()+content.getlat());
			                            LatLng lt = new LatLng(content.getlat(), content.getlng());
			                            content.setLatlng(lt);
			                            //valueEntered=lt.toString();
			                            System.out.println("address="+content.getLatlng());
			                            /*String home = latlng.getLong("home");
			                            String mobile = phone.getString("mobile");*/
			                            System.out.println("relation="+content.getRelation());

			                            jsonResponse += "Name: " + name + "\n\n";
			                            jsonResponse += "Population: " + email + "\n\n";
			                            jsonResponse += "Latitude: " + lat + "\n\n";
			                            jsonResponse += "Longitude: " + lng + "\n\n\n";
			                            jsonResponse += "Relation" + relation +"\n\n\n";
			                           
			                            if(content.getRelation().equals("son"))
			                            {
			                            	googleMap.addMarker(new MarkerOptions()
				                            .position(lt)
				                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_son))
				                            .snippet(email)
				                            .title(content.getName())).showInfoWindow();
											
			                            }
			                            else
			                            {
			                            	googleMap.addMarker(new MarkerOptions()
				                            .position(lt)
				                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_daughter))
				                            
				                            .title(content.getName())).showInfoWindow();
											
			                            }
			                           
			                            
										
										 
				                            
			                            CircleOptions circleOptions = new CircleOptions()
			            	            .center(lt)
			            	            .radius(500) //in meter
			            	            
			            	            .strokeWidth(2)
			            	            
			            	            .strokeColor(Color.parseColor("#E7C8D8"))
			            	            .fillColor(Color.parseColor("#DAE6E2"));
			            	       	     googleMap.addCircle(circleOptions);
			            	       	    
			            	           
			            	       	
			            	         
			            	            CameraPosition cameraPosition = new CameraPosition.Builder().target(lt).zoom(15.0f).build();
			            	            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
			            	            googleMap.moveCamera(cameraUpdate);  
			            	            
			            	        
			            	        
			 							}

								//txtResponse.setText(jsonResponse);

							} catch (JSONException e) {
								e.printStackTrace();
								Toast.makeText(getApplicationContext(),
										"Error: " + e.getMessage(),
										Toast.LENGTH_LONG).show();
							}

							//hidepDialog();
							

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
							Toast.makeText(getApplicationContext(),
									error.getMessage(), Toast.LENGTH_SHORT).show();
							//hidepDialog();
						}
					});

			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(req);
		}

		private void showpDialog() {
			if (!pDialog.isShowing())
				pDialog.show();
		}

		private void hidepDialog() {
			if (pDialog.isShowing())
				pDialog.dismiss();
		}
		private int manageMarkerIcon(String markerIcon)
		{
		    if (markerIcon.equals("icon1"))
		        return R.drawable.marker_daughter;
		    else if(markerIcon.equals("icon2"))
		        return R.drawable.marker_son;
			return R.drawable.marker_daughter;
		   
		}
	    
		@SuppressWarnings("null")
		private void initializeMap() {
			// TODO Auto-generated method stub
			
	        System.out.println("map");
	    	//LatLng Test2 = new LatLng(17.413191200000000000, 78.526665099999950000);
	    	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    	googleMap.getUiSettings();
	    	googleMap.getUiSettings().setZoomControlsEnabled(false);

	        //googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter()); 
	       
	        //googleMap.setOnInfoWindowClickListener(this);    
	    	

	        if (googleMap !=null)
	        {
	        	
	        
	        	Double lat = content.getlat();
	        	Double lng = content.getlng();
	            LatLng position = new LatLng(lat,lng);
	            System.out.println("latlong="+position);
	            Marker marker =googleMap.addMarker(new MarkerOptions()
	            .position(position)
	            .title(content.getName())
	            .alpha(0.7f)
	            .snippet("Dummy Message-zyoba")
	            
	            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_son)));
	   	
	            CircleOptions circleOptions = new CircleOptions()
	            .center(position)
	            .radius(500) //in meter
	            
	            .strokeWidth(2)
	            
	            .strokeColor(Color.parseColor("#E7C8D8"))
	            .fillColor(Color.parseColor("#DAE6E2"));
	       	     googleMap.addCircle(circleOptions);
	       	    
	            marker.showInfoWindow();
	           
	         
	            CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(15.0f).build();
	            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
	            googleMap.moveCamera(cameraUpdate);  
	            
	        
	            
	        } 
	        
	        else 
	        { 
	            Log.d("zonebay", "map is null");
	        } 
	       
	        
		}

		
		
	}
