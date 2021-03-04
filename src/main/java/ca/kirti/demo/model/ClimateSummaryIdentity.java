package ca.kirti.demo.model;

import java.io.Serializable;
import java.util.Date;



/**
 * This class used to get the composite primary key
 * @author Kirti
 *
 */
public class ClimateSummaryIdentity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stationName;
	private String province;	
	private Date climateDate;
	
	public ClimateSummaryIdentity() {
	}
	
	public ClimateSummaryIdentity(String station_Name, String province, Date climateDate) {
		super();
		this.stationName = station_Name;
		this.province = province;
		this.climateDate = climateDate;
	}
	
	public String getStationName() {
		return stationName;
	}
	public void setStation_Name(String station_Name) {
		this.stationName = station_Name;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((climateDate == null) ? 0 : climateDate.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
		return result;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClimateSummaryIdentity other = (ClimateSummaryIdentity) obj;
		if (climateDate == null) {
			if (other.climateDate != null)
				return false;
		} else if (!climateDate.equals(other.climateDate))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (stationName == null) {
			if (other.stationName != null)
				return false;
		} else if (!stationName.equals(other.stationName))
			return false;
		return true;
	}


}
