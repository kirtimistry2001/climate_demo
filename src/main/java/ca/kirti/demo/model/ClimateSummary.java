package ca.kirti.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClimateSummary {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String station_Name;
	private String province;	
	private Date date_;	
	private float mean_Temp;
	private float highest_Monthly_Maxi_Temp	;
	private float lowest_Monthly_Min_Temp;
	
	public ClimateSummary() {}
	
	
	public ClimateSummary(String station_Name, String province, Date date_, float mean_Temp,
			float highest_Monthly_Maxi_Temp, float lowest_Monthly_Min_Temp) {
		super();
		this.station_Name = station_Name;
		this.province = province;
		this.date_ = date_;
		this.mean_Temp = mean_Temp;
		this.highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
		this.lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStation_Name() {
		return station_Name;
	}
	public void setStation_Name(String station_Name) {
		this.station_Name = station_Name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Date getDate_() {
		return date_;
	}
	public void setDate_(Date date_) {
		date_ = date_;
	}
	public float getMean_Temp() {
		return mean_Temp;
	}
	public void setMean_Temp(float mean_Temp) {
		mean_Temp = mean_Temp;
	}
	public float getHighest_Monthly_Maxi_Temp() {
		return highest_Monthly_Maxi_Temp;
	}
	public void setHighest_Monthly_Maxi_Temp(float highest_Monthly_Maxi_Temp) {
		highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
	}
	public float getLowest_Monthly_Min_Temp() {
		return lowest_Monthly_Min_Temp;
	}
	public void setLowest_Monthly_Min_Temp(float lowest_Monthly_Min_Temp) {
		lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
	}


}
