package ca.kirti.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * class used to pass the object to frontend
 * @author Kirti
 *
 */
//TODO use lombok and clear getter/setter
public class PageableFilterData {

	@DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateTo;


	@DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateFrom;
	
	//search keyword
	private String keyword="";
	
	// rows per page
	private int pageSize =10;
	
	//page number
	private int pageNo =1;
	
	//field to be sorted
	private String sortField ="stationName";
	
	//direction of soring
	private String sortDir="ASC";
	

	public PageableFilterData() {	
	}
	
	
	public PageableFilterData(Date dateFrom, Date dateTo, String keyword,int pageSize, int pageNo, String sortField, String sortDir) {
		super();
		this.dateTo = dateTo;
		this.dateFrom = dateFrom;
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.sortField = sortField;
		this.sortDir = sortDir;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public String getSortField() {
		return sortField;
	}


	public void setSortField(String sortField) {
		this.sortField = sortField;
	}


	public String getSortDir() {
		return sortDir;
	}


	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}


	@Override
	public String toString() {
		return "FilterData [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", keyword=" + keyword + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + ", sortField=" + sortField + ", sortDir=" + sortDir + "]";
	}


	
}
