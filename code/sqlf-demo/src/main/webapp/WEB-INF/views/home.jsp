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
							<th title="Each Request, Order and Item are Inserted, Updated and Delted.">Requests / Orders / Items</th>
							<th>Avg.</th>
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
				<li><a href="#tabs-6">Under the Hood</a></li>
				<li><a href="#tabs-2">Architecture</a></li>
				<li><a href="#tabs-3">Data Model</a></li>
				<li><a href="#tabs-7">Source Code</a></li>
				<li><a href="#tabs-5">About SQLFire</a></li>
			</ul>
			
			<div id="tabs-1">
				<img src="css/img/sqlf-logo.png" class="align-right" />
				
				
				<h4>Demo Overview:</h4>
				<p>The purpose of this application is to demonstrate the ease of
						integration SQLFire-based persistence into your current solution
						to gain performance while continuing to support your current
						database.</p>
 			    <p>While the specific performance metrics of an in-memory or
						relational databases can depend on many variables, this demo aims
						to focus on the relative performance gains in a generic Web
						application while in the same time illustrating the value-add
						benefits of SQLFire in your solution:</p>
				
				<ul>
			   		<li>Scale-out data-tier enablement</li>
			   		<li>Real-time Cloud-scale data synchronization support</li>
			    </ul>
					
			</div>
			
			
			<div id="tabs-4">
				
				<h4>To execute a sample test:</h4>
					<ol>
						<li>Enter test name, or, use the one provided 
							<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="requestId-ctrl">Highlight</span></li>
						<li>Select the total number of request to send using the Number of
							Request slider 
							<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfRequest-ctrl">H</span></li>
						<li>Select the number of orders per each requests using the Orders
							per Request slider 
							<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfOrders-ctrl">H</span></li>
						<li>Select the number of items per each order using the Items per
							Order slider 
							<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="numberOfOrderItems-ctrl">H</span></li>
						<li>Click on the "Relational (RDBMS)" or "NextGen (SQLFire)"
							button 
							<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="button-ctrl">H</span></li>
					</ol>
					
			</div>
			<div id="tabs-6">
			
				<img src="css/img/request-msg.png" class="align-right" />
			
				<p>
					When executted, this demo application will perform the following tasks: 
				</p>
				
				<ol>
					<li>Asynchronously send to the server the selected number of requests, each configured with:
						<ul>
							<li>Data store type</li>
							<li>Number of orders</li>
							<li>Number of items for each order</li>
						</ul>
					</li>
					<li>As each one of the requests is received on the server, the total duration of 
					each transactions is being measured and recorded. (Note, we are mainly after the 
					performance of each data store transaction, thus, only the act of committing each 
					transaction to the database is included and it does not include the time it takes to 
					send that requests or parse its content)</li>
					<li>When each one of the previously sent requests is returned asynchronously to the UI, 
					their duration is drawn on the chart 
					<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="resultChartCtrl">H</span></li>
					<li>Once all requests are processed, the entire duration of that test, 
					as defined by the sum of the individual server durations, is added to the result table 
					<span class="highlighter ui-icon ui-icon-lightbulb" data-ctrl-id="resultTableCtrl">H</span></li> 
				</ol>
			</div>
			
			<div id="tabs-2">
				<div class="align-right">
				   	<img src="css/img/architecture.png" />
				</div>
				
				<h4>Application Architecture:</h4>
				<p>This Spring-based Java demo application allows for definition of
						multiple DB-agnostic Data Access Objects (DAO) which we configured
						with MySql and SQLFire data stores to illustrate the performance
						gains. The following diagram outlines the main components of this
						application.</p>
			
			   <ol>
			   	
			   	<li>The view of the demo is developed in jQuery generates
						symbolic purchase requests and leverage server-side defined REST
						services to submit them.</li>
			   	
			   	<li>On the server side, the REST interface receives purchase
						requests and dispatches them to one of preconfigured data store
						services mapped using the request storeType:
						"/{storeType}/request/{requestId}"</li>
			   	
			   	<li>The data service is responsible for creation of the request
						model and execution of entire transaction against configured DAO.
						The service has no knowledge of the type of data store type with
						which it is configured which allows the entire application to be
						easily reconfigured for different JDBC-compatible data stores.</li>
			   	
			   	<li>The DAO uses PreparedStatement to optimize its execution
						plan against each data store. Just like in the case of the data
						service, the DAO is configured with a generic data source and is
						not aware of the underlying database type.</li>
			   	
			   	<li>
			   		The relational data store is configured with two exactly same databases:
			   		<ul>
			   			<li>Database which will be used for the test itself</li>
						<li>And another database which the SQLFire databases will
								synchronize to as part of its write-behind replication use-case
								we defined above.</li>
					</ul>
			   	</li>
			   	
			   	<li>The SQLFire instance is configured with two nodes which
						partition the data based on the request Id.</li>
			   	
			   </ol>

			</div>
			
			<div id="tabs-3">
				<h4>Demo data model:</h4>
				<p>
					To simulate the databases workloads, this application uses very simple data model based on fictional shop. 
				   	Users submit purchase request, requests have multiple orders, and each order has multiple purchase items.
				   	Not exactly the most complicated data model, but, more than sufficient to simulate database load in our 
				   	demo application. 
				</p>
				<p>
					Note, that while SQLFire does support auto-generated table keys 
					(or <a href="http://en.wikipedia.org/wiki/Surrogate_key" target="_new" class="dark">surrogate keys</a>) 
					for purposes of demonstrating distributed data synchronization, the demo data model expects the application
					to generate its globally unique Ids (GUIDs). 
				</p>
				<p><img src="css/img/data-model.png" /></p>
			</div>
			
			<div id="tabs-7">
			
				<div class="align-right">
				   	<img src="css/img/github.jpeg" />
				</div>
				
				<h4>Demo source code:</h4>
				<p>
					The source code for this sample application is available at GitHub 
					<a href="https://github.com/mchmarny/sqlf-demo" target="_new" class="dark">available at GitHub</a>. 
				</p>
				<p>
					Assuming you already have a Java development envirnemnt configured, this demo app has the following dependancies: 
					<ul>
						<li>SQLFire 1.0.2 - 
							<a href="https://github.com/mchmarny/sqlf-demo" target="_new" class="dark">Download</a>
							<a href="http://pubs.vmware.com/vfabric5/index.jsp?topic=/com.vmware.vfabric.sqlfire.1.0/getting_started/topics/install_intro.html" target="_new" class="dark">Setup</a>
						</li>
						<li>MySQL Community Server - 
							<a href="http://www.mysql.com/downloads/mysql/" target="_new" class="dark">Download</a>
							<a href="http://dev.mysql.com/doc/refman/5.6/en/installing.html" target="_new" class="dark">Setup</a>
						</li>
					</ul>
				</p>
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