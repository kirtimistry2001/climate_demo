package ca.kirti.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model class for ClientSummary data.
 * Mapping Composite Key using @IdClass Annotation
 * @author Kirti
 * 
 *
 */
@Entity
@Table(name="climate_summary")
@IdClass(ClimateSummaryIdentity.class)
public class ClimateSummary {

	// Mapping Composite Key
	@Id
	private String stationName;
	@Id
	private String province;	
	@Id
	@Temporal(TemporalType.DATE)
	private Date climateDate;

	@Column(name ="meanTemp",nullable = true)
	private float mean_Temp;


	@Column(name="hMMTemp", nullable = true)
	private float highest_Monthly_Maxi_Temp	;

	@Column(name="lMMTemp",nullable = true)
	private float lowest_Monthly_Min_Temp;

	public ClimateSummary() {}

	public ClimateSummary(String stationName, String province, Date climateDate, float mean_Temp,
			float highest_Monthly_Maxi_Temp, float lowest_Monthly_Min_Temp) {
		super();
		this.stationName = stationName;
		this.province = province;
		this.climateDate = climateDate;
		this.mean_Temp = mean_Temp;
		this.highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
		this.lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
	}


	public float getMean_Temp() {
		return mean_Temp;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getClimateDate() {
		return climateDate;
	}

	public void setClimateDate(Date climateDate) {
		this.climateDate = climateDate;
	}

	public void setMean_Temp(float mean_Temp) {
		this.mean_Temp = mean_Temp;
	}

	public float getHighest_Monthly_Maxi_Temp() {
		return highest_Monthly_Maxi_Temp;
	}

	public void setHighest_Monthly_Maxi_Temp(float highest_Monthly_Maxi_Temp) {
		this.highest_Monthly_Maxi_Temp = highest_Monthly_Maxi_Temp;
	}

	public float getLowest_Monthly_Min_Temp() {
		return lowest_Monthly_Min_Temp;
	}

	public void setLowest_Monthly_Min_Temp(float lowest_Monthly_Min_Temp) {
		this.lowest_Monthly_Min_Temp = lowest_Monthly_Min_Temp;
	}

	@Override
	public String toString() {
		return "ClimateSummary [stationName=" + stationName + ", province=" + province + ", climateDate=" + climateDate
				+ ", mean_Temp=" + mean_Temp + ", highest_Monthly_Maxi_Temp=" + highest_Monthly_Maxi_Temp
				+ ", lowest_Monthly_Min_Temp=" + lowest_Monthly_Min_Temp + "]";
	}

}
