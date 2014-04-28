package fr.infinitestudio.maps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends Activity {

	private GoogleMap map;
	private static final LatLng TOULOUSE_CAPITOLE = new LatLng(43.604674,1.442941);
	private static final LatLng NEW_YORK = new LatLng(40.705891, -74.006843);
	private static final LatLng ANGOLA = new LatLng(-12.428626, 17.613331);
	private static final LatLng CHINE = new LatLng(39.894119, 116.415312);
	private static final LatLng MOSCOU = new LatLng(55.755588, 37.618762);
	
	private boolean showZoomCompassLocation = false;
	private boolean markerCapitoleExist = false;
	private boolean showTraffic = false;
	private boolean linesAdded = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.action_active_zoom_compass_location:
	        	if(showZoomCompassLocation){
	        		map.getUiSettings().setZoomControlsEnabled(true);
	        		map.getUiSettings().setCompassEnabled(true);
	        		map.getUiSettings().setMyLocationButtonEnabled(true);
	        		showZoomCompassLocation = false;
	        	}else{
	        		map.getUiSettings().setZoomControlsEnabled(false);
	        		map.getUiSettings().setCompassEnabled(false);
	        		map.getUiSettings().setMyLocationButtonEnabled(false);
	        		showZoomCompassLocation = true;
	        	}
	            break;
	        case R.id.action_zoom_in:
	        	// Zoom par incrément de 1.0
	        	map.animateCamera(CameraUpdateFactory.zoomIn());
    	 		break;
	        case R.id.action_zoom_out:
	        	// Zoom par incrément de -1.0
	        	map.animateCamera(CameraUpdateFactory.zoomOut());
    	 		break;
	        case R.id.action_zoom_to:
	        	// Zoom à un niveau précis
	        	map.animateCamera(CameraUpdateFactory.zoomTo(15));
    	 		break;
	        case R.id.action_go_to_location:
	        	CameraPosition cameraPosition = new CameraPosition.Builder()
	        	 	.target(TOULOUSE_CAPITOLE) // Position que l'on veut atteindre
	        	 	.zoom(15)             // Niveau de zoom
	        	 	.bearing(180) 		  // Orientation de la caméra, ici au sud
	                .tilt(60)    		  // Inclinaison de la caméra
	                .build();
	            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    	 		break;
    	 	case R.id.action_type_map_none:
    	 		map.setMapType(GoogleMap.MAP_TYPE_NONE);
    	 		break;
    	 	case R.id.action_type_map_normal:
    	 		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	 		break;
    	 	case R.id.action_type_map_terrain:
    	 		map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    	 		break;
    	 	case R.id.action_type_map_satellite:
    	 		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    	 		break;
    	 	case R.id.action_type_map_hybrid:
    	 		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    	 		break;
    	 	case R.id.action_show_traffic:
    	 			if(!showTraffic){
    	 				map.setTrafficEnabled(true);
    	 				showTraffic = true;
    	 			}else{
	 					map.setTrafficEnabled(false);
	 					showTraffic = false;
	 				}
    	 		break;
    	 	case R.id.action_add_marker:
    	 		if(!markerCapitoleExist){
	    	 		MarkerOptions markerOptions = new MarkerOptions();
	    	 		BitmapDescriptor iconMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
	    	 		markerOptions.position(TOULOUSE_CAPITOLE).title("Toulouse Capitole")
	    	 			.snippet("Place du capitole située à Toulouse").icon(iconMarker).draggable(true);
	    	 		map.addMarker(markerOptions);
	    	 		
	    	 		// On zoome sur le marqueur après l'avoir ajouté
	    	 		CameraPosition cameraPos = new CameraPosition.Builder()
		        	 	.target(TOULOUSE_CAPITOLE) // Position que l'on veut atteindre
		        	 	.zoom(15)             // Niveau de zoom
		        	 	.bearing(180) 		  // Orientation de la caméra, ici au sud
		                .tilt(60)    		  // Inclinaison de la caméra
		                .build();
		            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
	    	 		markerCapitoleExist = true;
    	 		}
    	 		break;
    	 	case R.id.action_add_lines:
    	 		if(!linesAdded){
    	 			map.addMarker(new MarkerOptions().position(TOULOUSE_CAPITOLE));
    	 			map.addMarker(new MarkerOptions().position(NEW_YORK));
    	 			map.addMarker(new MarkerOptions().position(ANGOLA));
    	 			map.addMarker(new MarkerOptions().position(CHINE));
    	 			map.addMarker(new MarkerOptions().position(MOSCOU));
    	 			map.addPolyline(new PolylineOptions()
	    	 		     .add(TOULOUSE_CAPITOLE, NEW_YORK, ANGOLA, CHINE, MOSCOU)
	    	 		     .width(5)
	    	 		     .color(Color.BLUE));
    	 			
    	 			map.addPolyline(new PolylineOptions()
	    	 			 .add(TOULOUSE_CAPITOLE, NEW_YORK, ANGOLA, CHINE, MOSCOU)
		   	 		     .width(5)
		   	 		     .color(Color.RED)
		   	 		     .geodesic(true));
    	 		}
    	 		break;
        }
        return true;
    }
}
