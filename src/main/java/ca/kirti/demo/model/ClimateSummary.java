package ca.kirti.demo.model;

import java.io.Serializable;
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
public class ClimateSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	// Mapping Composite Key
	@Id
	private String stationName;
	@Id
	private String province;	
	
	@Id
	@Temporal(TemporalType.DATE)
	private Date climateDate;

	@Column(name ="meanTemp",nullable = true)
	private float meanTemp;

	/**
	 * highest_Monthly_Maxi_Temp	
	 */
	@Column(name="hMMTemp", nullable = true)
	private float hMMTemp	;

	/**
	 * lowest_Monthly_Min_Temp
	 */
	@Column(name="lMMTemp",nullable = true)
	private float lMMTemp;

	public ClimateSummary() {}

	public ClimateSummary(String stationName, String province, Date climateDate, float mean_Temp,
			float highest_Monthly_Maxi_Temp, float lowest_Monthly_Min_Temp) {
		super();
		this.stationName = stationName;
		this.province = province;
		this.climateDate = climateDate;
		this.meanTemp = mean_Temp;
		this.hMMTemp = highest_Monthly_Maxi_Temp;
		this.lMMTemp = lowest_Monthly_Min_Temp;
	}


	public float getMeanTemp() {
		return meanTemp;
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
		this.meanTemp = mean_Temp;
	}

	public float gethMMTemp() {
		return hMMTemp;
	}

	public void sethMMTemp(float hMMTemp) {
		this.hMMTemp = hMMTemp;
	}

	public float getlMMTemp() {
		return lMMTemp;
	}

	public void setlMMTemp(float lMMTemp) {
		this.lMMTemp = lMMTemp;
	}

	public void setMeanTemp(float meanTemp) {
		this.meanTemp = meanTemp;
	}

	@Override
	public String toString() {
		return "ClimateSummary [stationName=" + stationName + ", province=" + province + ", climateDate=" + climateDate
				+ ", mean_Temp=" + meanTemp + ", highest_Monthly_Maxi_Temp=" + hMMTemp
				+ ", lowest_Monthly_Min_Temp=" + lMMTemp + "]";
	}

}
