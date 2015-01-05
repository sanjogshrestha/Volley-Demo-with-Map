package info.androidhive.volleyjson.util;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Content  implements Serializable
	{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private String name;

	    private LatLng latlng;

	    private Double lat,lng;
	    private String population,icon,relation;

	    public String getName ()
	    {
	        return name;
	    }

	    public void setName (String name)
	    {
	        this.name = name;
	    }
	    public String getRelation ()
	    {
	        return relation;
	    }

	    public void setRelation (String relation)
	    {
	        this.relation = relation;
	    }

	    public LatLng getLatlng ()
	    {
	        return latlng;
	    }

	    public void setLatlng (LatLng lt)
	    {
	        this.latlng = lt;
	    }
	    
	    public Double getlat()
	    {
	    	return lat;
	    }
	    
	    public void setlat(Double lat) {
	    	this.lat= lat;
			// TODO Auto-generated method stub

		}
	    public Double getlng()
	    {
	    	return lng;
	    }
	    
	    public void setlng(Double lng) {
	    	this.lng= lng;
			// TODO Auto-generated method stub

		}

	    public String getPopulation ()
	    {
	        return population;
	    }

	    public void setPopulation (String population)
	    {
	        this.population = population;
	    }

	    public String getIcon()
	    {
	    	return icon;
	    }

		public void setIcon(String icon) {
			// TODO Auto-generated method stub
			this.icon = icon;
		}
	}
				
		

