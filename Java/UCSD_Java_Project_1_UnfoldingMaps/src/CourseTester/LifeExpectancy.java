package CourseTester;

import java.util.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet{
	UnfoldingMap map;
	// String countryID -> 
	Map<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	String file;
	public void setup(){
		size(800, 600, OPENGL);
		background(0, 0, 255);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		// to allow interaction with map
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// setup life expectancy
		file = "LifeExpectancyWorldBankModule3.csv";
		lifeExpByCountry = loadLifeExpectancyFromCSV(file);
		
		// setup country features
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	public void draw(){
		map.draw();
		drawButtons();
	}
	private void drawButtons(){
		fill(255,255,255);
		rect(100,100,25,25);
		
		fill(100,100,100);
		rect(100, 150, 25, 25);
	
	}
	public void mouseReleased(){
		if(mouseX>100 && mouseX < 125 && mouseY > 100 & mouseY <125){
			background(255,255,255);
		}
		else if(mouseX>100 && mouseX < 125 && mouseY > 150 & mouseY <175){
			background(100,100,100);
		}
	}
	// helper function
	private Map<String, Float> loadLifeExpectancyFromCSV(String fileName){
		Map<String, Float> lmap = new HashMap<String, Float>();
		
		String[] rows = loadStrings(fileName);
		for(String row : rows){
			String[] cols = row.split(",");
			if(cols.length == 6 && !cols[5].equals("..")){
				float value = Float.parseFloat(cols[5]);
				lmap.put(cols[4], value);
			}
		}
		
		return lmap;
	}
	private void shadeCountries(){
		for(Marker marker : countryMarkers){
			String countryId = marker.getId();
			if(lifeExpByCountry.containsKey(countryId)){
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int)map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}else{
				marker.setColor(color(150, 150, 150));
			}
		}
		
	}
	
	
}
