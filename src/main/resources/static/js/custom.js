/**
 * custom js for climate demo
 */
document.addEventListener("DOMContentLoaded", function(event) {
	setDateRange() ;
});	
		
/** 
 Set the date range to the selected dates on page reload after search/filter 
*/
function setDateRange() {
	if (!isEmpty(startDate) && !isEmpty(endDate)) {
		var date1 = new Date($('input[name="dateFrom"]').val());
		var date2 = new Date($('input[name="dateTo"]').val());
		$('#dateFilter').data('daterangepicker').setStartDate(date1);
		$('#dateFilter').data('daterangepicker').setEndDate(date2);
		$dateFilter.val($('#dateFilter').data('daterangepicker').startDate.format('MM/DD/YYYY') + ' - '
				+ $('#dateFilter').data('daterangepicker').endDate.format('MM/DD/YYYY'));
	} else {
		$(this).val("");
		//console.log("start date or end date is empty");
	}
}

/**
 Filter by Date range
*/
function filterByDateRange() {
		var date1 = new Date($('input[name="dateFrom"]').val());
		var date2 = new Date($('input[name="dateFrom"]').val());
		console.log("filterbydaterange: date1" +date1 +" date2: "+date2);
		document.getElementById ('searchForm').submit();
}		
/** 
 Navigate to pageNo
 */
function navigateToPageNo(pageNo) {
	$('input[name="pageNo"]').val(pageNo);
	document.getElementById ('searchForm').submit();
}


/** 
 Sort the column/field
 */
function setSortBy(sortField,sortDir) {
	console.log("setSortBy: sortField" +sortField +" sortDir: "+sortDir);
	$('input[name="sortDir"]').val(sortDir);
	$('input[name="sortField"]').val(sortField);
	document.getElementById ('searchForm').submit();
}



/** 
 check if given string empty, null or undefined
 */
function isEmpty(val) {
	return (val === undefined || val == null || val.length <= 0) ? true
			: false;
}



/** 
 Show details using ajax call	
 */	
function showClimateDetail(stationName,province,climateDate) {
	console.log("calling showClimateDetail with ajax call"+stationName +" " + province +", " + climateDate);
	var compositeKey = {
           "province": province,
           "stationName": stationName,
           "climateDate": climateDate
       };
	$.ajax({
		url : '/showDetails',
		type : 'GET',
		contentType: "application/json; charset=utf-8",
		data: compositeKey,
        cache: false,
        timeout: 600000,
		success : function(data) {
		 console.log(data);
		  window.location.href = '/viewControllerHandler?climateDetail='+ encodeURIComponent(JSON.stringify(data)); 
			},
		error : function() {
			 alert("Error while getting detail information");
		}
	});
}



