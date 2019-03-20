<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <link rel="shortcut icon" href="favicon.ico" />
  <title>SQLFire Demo</title>
  <link type="text/css" href="css/theme/ui.css" rel="stylesheet" />  
  <link type="text/css" href="css/app.css" rel="stylesheet" />
  <script type="text/javascript" src="js/dep.js"></script>
  <script type="text/javascript" src="js/app.js"></script>
</head>
<body>
<div class="container ui-corner-all">
    <div class="header">
        <div id="header-title">SQLFire Demo</div>
        <div class="ui-widget">
          <div id="header-info" class="ui-state-highlight ui-corner-all"> 
            <p>
              <span id="header-info-icon" class="ui-icon ui-icon-info"></span>
              <span id="header-info-text">Demo illustrating performance benefits of SQLFire.</span>
            </p>
          </div>
        </div>
    </div>
    <div class="content">
    
        <div class="content-left">    


			<h2>Configure Test</h2>

            <fieldset title="Unique id for this test that will be associated with all transactions." id="requestId-ctrl">
            	<label>Request Group:</label>
            	<input type="text" value="" id="requestId">
            	<button id="refreshIdButton">Refresh</button>
            	<button id="findIdButton">Find</button>
            </fieldset>
            
            <fieldset title="Total number of transactions to be executed by this test." id="numberOfRequest-ctrl">
            	<label>Number of Requests:</label>
            	<input type="text" value="" id="numberOfRequest" class="center-input">
            	<div id="numberOfRequestSlider" class="center-slider"></div>
            </fieldset>
            
            <fieldset title="Number of orders to be executed per each request." id="numberOfOrders-ctrl">
            	<label>Orders per Request:</label>
            	<input type="text" value="" id="numberOfOrders" class="center-input">
            	<div id="numberOfOrdersSlider" class="center-slider"></div>
            </fieldset>
            
            <fieldset title="Number of items to be executed per each order." id="numberOfOrderItems-ctrl">
            	<label>Items per Order:</label>
            	<input type="text" value="" id="numberOfOrderItems" class="center-input">
            	<div id="numberOfOrderItemsSlider" class="center-slider"></div>
            </fieldset>
            
	          
        </div> 
        
        <div class="content-center" title="Once test is configured, you can execute it using one of the predefined data stores.">      
        
        	<h2>Execute Test</h2>
        
        	<label>Data Stores</label>
        	
        	<div id="button-ctrl">
				<div>              
	      			<button id="goRdbmsButton">Classic SQL (MySQL)</button>
	      		</div>
	      		<div class="spacer-10"></div>
	      		<div>
	      			<button id="goSqlFireButton">NextGen SQL (SQLFire)</button>
	      		</div> 
      		</div>     
      		
      		<div id="progressbarCtrl">
      			<label>Progress</label>
      			<div id="progressbar"></div>
      		</div>
            
            
        </div> 
        
        <div class="content-right" title="When the test is being executed, its current status is displayed here.">
        
        	<h2>Monitor Results</h2>
        	
        	<div id="resultChartCtrl">
        		<label class="long">Average transaction duration (ms.)</label>
        		<div id="resultChart"></div>
			</div>
			
			<div id="resultTableCtrl">
				<table id="resultTable">
					<thead>
						<tr>
							<th>Performed On</th>
							<th>Type</th>
							<th>Req/Order/Item</th>
							<th>Duration</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>

        </div>

    </div> 
    
    <div class="spacer-20"></div>
    
    <div id="tabs-container">
	    <div id="tabs">
	    
			<ul>
				<li><a href="#tabs-1">Introduction</a></li>
				<li><a href="#tabs-4">How to</a></li>
				<li><a href="#tabs-2">Architecture</a></li>
				<li><a href="#tabs-3">Data Model</a></li>
				<li><a href="#tabs-5">SQLFire</a></li>
			</ul>
			
			<div id="tabs-1">
				<img src="css/img/sqlf-logo.png" class="align-right" />
				
				
				<h4>Demo Overview:</h4>
				<p>
				   The purpose of this application is to demonstrate the ease of integration SQLFire
				   into an existent solution while continuing to support legacy applications. 
				</p>
 			    <p>
				   While the specific performance metrics of an in-memory or relational databases  
				   can depend on many variables, this demo aims to focus on the relative performance 
				   gains in a generic Web application while illustrate value-add
				   benefits of SQLFire in your solution: 
				</p>
				
				<ul>
			   		<li>Immediate overall performance improvement</li>
			   		<li>Scale-out data-tier enablement</li>
			   		<li>Real-time Cloud data synchronization support</li>
			    </ul>
					
			</div>
			
			
			<div id="tabs-4">
				
				<h4>To execute a sample test:</h4>
					<ol>
						<li>Enter a test name, or, use the one provided <span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="requestId-ctrl">Highlight</span></li>
						<li>Select the total number of request to send using the Number of Request slider <span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfRequest-ctrl">H</span></li>
						<li>Select the number of orders per each requests using the Orders per Request slider <span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfOrders-ctrl">H</span></li>
						<li>Select the number of items per each order using the Items per Order slider <span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfOrderItems-ctrl">H</span></li>
						<li>Click on the "Relational (RDBMS)" or "NextGen (SQLFire)"  button <span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="button-ctrl">H</span></li>
					</ol>
				<p>
					This test will perform the following tasks: 
				</p>
				
				<img src="css/img/request-msg.png" class="align-right" />
				<ol>
					<li>Asynchronously send to the server the selected number of requests, each configured with:
						<ul>
							<li>Data store type</li>
							<li>Number of orders</li>
							<li>Number of items for each order</li>
						</ul>
					</li>
					<li>As each one of the request transactions is performed on the server, 
					its total duration on the server is being measured. (Note, we are mainly after the 
					performance of each data store, thus, only the act of committing each 
					transaction to the database is included and it does not include the time it takes to 
					send that requests to the server or parse each request into a message)</li>
					<li>When each one of the previously sent requests is returned asynchronously to the UI, 
					their duration is drawn on the chart 
					<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="resultChartCtrl">H</span></li>
					<li>When the last request is returned, the entire duration of the test, 
					as defined by the sum of the individual server durations, is added to the result table 
					<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="resultTableCtrl">H</span></li> 
				</ol>
			</div>
			
			<div id="tabs-2">
				<div class="align-right">
				   	<img src="css/img/architecture.png" />
				</div>
				
				<h4>Application Architecture:</h4>
				<p>
					This is Java application developed using Spring framework. 
				   	Each one of its components is entirely DB-agnostic. Depending on its configuration
				   	the application can use any number of data sources simultaneously. This demonstration
				   	has been configured with two data sources: relational (MySQL) and in-memory (SQLFire). 
				</p>
				
				<p>  
				   <ol>
				   	
				   	<li>
				   		The view is build entirely in jQuery. After the application launched, 
				   	    the Home page is loaded and from then on, all its interaction with the
				   	    server is performed using Ajax calls to the server REST services.
				   	</li>
				   	
				   	<li>
				   		The server side of the application receives all its commands over REST interface 
				   		which is configured with a map of data store services. 
				   		Upon receiving each request, the service maps them to one of the data store services 
				   		using the request storeType: "/{storeType}/request/{requestId}"
				   	</li>
				   	
				   	<li>
				   		The data service is responsible for creation of the request model and executing 
				   		it inside of a transaction against predefined DAO. The service has no knowledge of
				   		the type of data store type with which it is configured.
				   	</li>
				   	
				   	<li>
				   		The DAO uses PreparedStatement to optimize its execution plan against the data store. 
				   		Just like the data service, the DAO is configured with a generic data source and 
				   		is not aware of the underlining data store.
				   	</li>
				   	
				   	<li>
				   		The relational data store is configured with two exactly same databases:
				   		<ul>
				   			<li>Database which will be used for the test itself</li>
				   			<li>Another database which the SQLFire databases will replicate to as part of its 
				   			    write-behind replication use-case</li>
				   		</ul>
				   	</li>
				   	
				   	<li>
				   		The SQLFire instance is configured to persist its data to local drive and to replicate each 
				   		INSERT, UPDATE, DELTE to the second databases in the relational data store.
				   	</li>
				   	
				   </ol>
				</p>
				<p>
					<!-- Spacer -->
				</p>
			</div>
			
			<div id="tabs-3">
				<h4>Demo data model:</h4>
				<p>
					To simulate the databases workloads, this application uses very simple data model based on fictional shop. 
				   	Users submit purchase request, requests have multiple orders, and each order has multiple purchase items.
				   	Not the most complicated data model, but, more than sufficient to simulate every-day Web application. 
				</p>
				<p>
					Note, that while SQLFire does support auto-generated table keys 
					(or <a href="https://en.wikipedia.org/wiki/Surrogate_key" target="_new" class="dark">surrogate key</a>) 
					for purposes of demonstrating cross-datacenter synchronization, the demo data model expects the application
					to generate its globally unique Ids. 
				</p>
				<p><img src="css/img/data-model.png" /></p>
			</div>
			
			
			<div id="tabs-5">
				
				<img src="css/img/sqlf-logo.png" class="align-right" />
			
				<h4>What is VMware SQLFire:</h4>
				<p>
				VMware's SQLFire is designed to address both parts of this challenge -- 
				the speed and Cloud-scale data synchronization. While traditional 
				relational databases increasingly are being used to do things for which 
				they were never designed; like supporting thousands of concurrent users 
				in Web applications. SQLFire, as an in-memory NewSQL database, delivers 
				maximum throughput and minimum latency. And, as I am going to demonstrate 
				in this post, it can do this with a minimal disruption to your solution 
				due to its standard SQL interface. Furthermore, due to its robust 
				asynchronous data replication technology, SQLFire can also offer 
				near-linear horizontal scalability across Wide Area Network (WAN).
				</p>
				
				<h4>VMware SQLFire Highlights</h4>
				
				<img src="css/img/scan-to-learn.png" class="align-right" />
				<ul>
					<li><b>Low latency</b>: Memory-based data management maintains consistently high 
					application performance by eliminating lookup, read/write, and network round-trip latencies.</li>
					<li><b>Extreme write performance</b>: Memory-speed write performance is ideal for large-scale 
					databases with high transaction volumes and demanding Service Level Agreements.</li>
					<li><b>Simplified scale-out and scale-back</b>: Repartitions, replicates, and balances data 
					across independent nodes to accommodate shifting loads or new resources.</li>
					<li><b>Standard SQL interface</b>: Integrates fast, memory-based data management into 
					applications using current database administration skills and tools.</li>
					<li><b>Flexible HA and DR options</b>: Ensures continuous availability within or across 
					data centers, with partial or full, synchronous or asynchronous writes to disk 
					to meet disaster recovery or regulatory requirements.</li>
				</ul>
				
			</div>
			
		</div>
	</div>
</div>
</body>
</html>