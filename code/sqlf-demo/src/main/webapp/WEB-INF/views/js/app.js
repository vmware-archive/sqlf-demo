var DEBUG = true;

$(document).ready(function(){

	jQuery.fn.log = function (msg) {
		if (DEBUG && window.console)
			console.log("%s: %o", msg, this);
	 	return this;
	};
	
	$.ajaxSetup({
  		crossDomain: true,
		contentType: "application/json",
		error: function(request, status, error) {
			if (request){
				showMessage(request.responseText, true);
				saveButton.log("Error: " + request.responseText);
			}
	    }
	});

	var defaultMessage = "Demo illustrating performance benefits of SQLFire.",
	    loger = $("<log>"),
		infoIcon = $("#header-info-icon"),
    	infoText = $("#header-info-text"),
    	infoBox = $("#header-info"),
    	storeTypeRDBSM = "rdbms",
    	tabs = $("#tabs").tabs(),
	    storeTypeSQLF = "sqlf",
    	numberOfRequest = $("#numberOfRequest"),
    	numberOfRequestSlider = $("#numberOfRequestSlider").slider({
			value:500,
			min: 50,
			max: 500,
			step: 50,
			slide: function(event, ui) {
				numberOfRequest.val(ui.value);
			}
		}),
    	numberOfOrders = $("#numberOfOrders"),
    	numberOfOrdersSlider = $("#numberOfOrdersSlider").slider({
			value:10,
			min: 5,
			max: 30,
			step: 5,
			slide: function(event, ui) {
				numberOfOrders.val(ui.value);
			}
		}),
    	numberOfOrderItems = $("#numberOfOrderItems"),
    	numberOfOrderItemsSlider = $("#numberOfOrderItemsSlider").slider({
			value: 5,
			min: 5,
			max: 30,
			step: 5,
			slide: function(event, ui) {
				numberOfOrderItems.val(ui.value);
			}
		}),
		goRdbmsButton = $("#goRdbmsButton").button({ 
			icons: { primary: "ui-icon-play" }
		}),
		goSqlFireButton = $("#goSqlFireButton").button({ 
			icons: { primary: "ui-icon-play" }
		}),
		requestIdBox = $("#requestId"),
		progressbar = $("#progressbar").progressbar({
			value: 0
		}),
		refreshIdButton = $("#refreshIdButton").button({ 
			icons: { primary: "ui-icon-gear" },
            text: false
		}),
		findIdButton = $("#findIdButton").button({ 
			icons: { primary: "ui-icon-search" },
            text: false
		}),
		totalNumberOfRequests = 0,
		resultChartOptions = {
	        series: { shadowSize: 0 },
	        yaxis: { min: 0, max: 300 },
	        xaxis: { min: 0, max: 500, show: false }
		},
		resultChartData = [],
		resultChartCtrl = $("#resultChart"),
		resultChart = $.plot(resultChartCtrl, [ resultChartData ], resultChartOptions);
	
	
	function showMessage(text, alert){
		if(alert){
			infoBox.removeClass("ui-state-highlight")
				    .addClass("ui-state-error");
			infoIcon.removeClass("ui-icon-info")
				    .addClass("ui-icon-alert");
		}else{
			infoBox.removeClass("ui-state-error")
				    .addClass("ui-state-highlight");
			infoIcon.removeClass("ui-icon-alert")
					.addClass("ui-icon-info");
		}
		infoText.html(text);
		return false;
	};
	
	function toggleButtons(on){
		if (on){
			goRdbmsButton.button("enable");
			goSqlFireButton.button("enable");
		}else{
			goRdbmsButton.button("disable");
			goSqlFireButton.button("disable");
		}
	}
	
	function refreshRequestId(){
		requestIdBox.val(Math.random().toString(36).substring(7));
	}
	
	//Default sliders
	numberOfRequest.val(numberOfRequestSlider.slider( "value"));
	numberOfOrders.val(numberOfOrdersSlider.slider( "value"));
	numberOfOrderItems.val(numberOfOrderItemsSlider.slider( "value"));
	

	// show default message
	showMessage(defaultMessage);
	
	// set the request Id 
	refreshRequestId();
	
	// rdbms click
	goRdbmsButton.click(function(){
		startTest(storeTypeRDBSM);
	});
	
	// sqlfire click
	goSqlFireButton.click(function(){
		startTest(storeTypeSQLF);
	});
	
	// refresh id
	refreshIdButton.click(function(){
		refreshRequestId();
		toggleButtons(true);
	});
	
	// find id
	findIdButton.click(function(){
		getRequestDetailsBoth();
	});
	
	
	function startTest(storeType){
		loger.log("startTest:" + storeType);
		resultChartData = [];
		resultChart.getOptions().xaxis = parseInt(numberOfRequest.val());
		resultChart.draw();
		
		toggleButtons(false);
		progressbar.progressbar("value", 0);
		totalNumberOfRequests = 0;
		var requestId = requestIdBox.val();
		initRequest(requestId, storeType, "POST", function(reqResult){
			
			if(reqResult){
				if(reqResult.request){		
					
					loger.log("startTest: " + JSON.stringify(reqResult));
					showMessage("Request created. Store type: " + storeType);
					reqResult.request.orders = parseInt(numberOfOrders.val());
					reqResult.request.items = parseInt(numberOfOrderItems.val());
					totalNumberOfRequests = parseInt(numberOfRequest.val());
					
					for(var reqC=0; reqC < totalNumberOfRequests; reqC++){
						reqResult.request.index = reqC;	
						fireOrders(reqResult.request, storeType, procerOrderResults);
					}//for
				} //if request
				if(reqResult.error){
					showMessage(reqResult.error, true);
				} //if error
			} //if resutl
		});//initRequest
	};
	
	// Chart
	function updateChart(request){
		loger.log("updateChart:");
		if (request){
			resultChartData.push([ request.index, request.duration ]);
			//loger.log("Pushed: " + request.index + "," + request.duration);
			resultChart.setData([ resultChartData ]);
			resultChart.draw();
		}
	}
	
	function procerOrderResults(orderResult){
		loger.log("procerOrderResults:");
		if(orderResult && orderResult.request){
			//loger.log("procerOrderResults: " + JSON.stringify(orderResult));
			
			var totalNumberOfRequest = parseInt(numberOfRequest.val());
			var percentCompleted = Math.round(((orderResult.request.index + 1) / totalNumberOfRequest) * 100);
			--totalNumberOfRequests;
			
			if (totalNumberOfRequests > 0 && 
			    percentCompleted > parseInt(progressbar.progressbar("value"))){
				progressbar.progressbar("value", percentCompleted);
				showMessage("Posted " + orderResult.request.index + " requests");
				
				//Update chart
				updateChart(orderResult.request);
			}
			
			if (totalNumberOfRequests == 0){
				//this is the last one
				progressbar.progressbar("value", 100);
				showMessage("Done. Processed " + totalNumberOfRequests + " requests.");
				toggleButtons(true);
				getRequestDetails(orderResult.storeType, function(request){
					if (request){
						displayResults(orderResult.storeType, request);
					}
					refreshRequestId();
				});
			}
						
		}//if results
	 };
	
	
	function initRequest(id, storeType, ajaxType, callback){
		loger.log("initRequest:" + storeType);
		$.ajax({
		    contentType : "application/json",
		    dataType : 'json',
		    type : ajaxType,
		    url : 'store/' + storeType + '/request/' + id,
		    data : {},
		    success : function(data) {
		    	callback(data);
		    },
		    error : function(request, status, error) {
		    	if (request){
		    		showMessage(request.responseText, true);
		    	}
		    	callback(null);
		    }
		});
	};
	
	function fireOrders(req, storeType, callback){
		loger.log("fireOrders:" + storeType);
		$.ajax({
		    contentType : "application/json",
		    dataType : 'json',
		    type : 'POST',
		    url : 'store/' + storeType + '/order/',
		    data : JSON.stringify(req),
		    success : function(data) {
		    	callback(data);
		    },
		    error : function(request, status, error) {
		    	if (request){
		    		showMessage(request.responseText, true);
		    	}
		    	callback(null);
		    }
		});
	};
	
	
	function getRequestDetailsBoth(){
		loger.log("getRequestDetailsBoth:");
		$("#resultTable > tbody").empty();
		getRequestDetails(storeTypeRDBSM, function(data){
			displayResults(storeTypeRDBSM, data);
		});
		getRequestDetails(storeTypeSQLF, function(data){
			displayResults(storeTypeSQLF, data);
		});
	}
	
	
	function getRequestDetails(storeType, callback){
		loger.log("getRequestDetails: " + storeType);
		var requestId = requestIdBox.val();
		initRequest(requestId, storeType, "GET", function(reqResult){
			if(reqResult){
				if(reqResult.request){		
					//loger.log("getRequestDetails: " + JSON.stringify(reqResult));
					callback(reqResult.request);
				} //if request
				if(reqResult.error){
					showMessage(reqResult.error, true);
				} //if error
			} //if result
		});//initRequest
	};
	
	
	function displayResults(storeType, request){
		loger.log("displayResults: " + storeType);
		if (request){
			var on = new Date(request.on);
			var html = "<tr title='" + request.id + "'>";
				html += "<td>" + on.format("longTime") + "</td>";
				html += "<td>" + storeType + "</td>";
				html += "<td>" + numberOfRequest.val() + " / " + request.orders + " / " + request.items + "</td>";
				html += "<td>" + request.duration + "ms.</td>";
				html += "</tr>";
				
			var row = $(html).data("rq", request)
							 .data("st", storeType);
			
			$("#resultTable > tbody").append(row);
		}
		
	}
	
	$(".highlighter").mouseenter(function() {
		var ctrlId = $(this).attr("data-ctrl-id");
		var ctrl = $("#" + ctrlId);
		
		ctrl.siblings().css({'opacity': '0.1'});	
		ctrl.parent().siblings().css({'opacity': '0.1'});
		ctrl.css({'opacity': '1.0'});
	}).mouseleave(function() {
		var ctrlId = $(this).attr("data-ctrl-id");
		var ctrl = $("#" + ctrlId);
		ctrl.siblings().fadeTo('100', '1.0');
		ctrl.parent().siblings().fadeTo('100', '1.0');
	});
	

});



