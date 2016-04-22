
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE HTML>
<html>
<head>

</head>

<body>


<form  method="post"
action="bayesiancontroller" >

Select ticker: <select id ="ticker" name="ticker">  
  <option value="YHOO">YHOO</option>
  <option value="AMZN">AMZN</option>
  <option value="GOOG">GOOG</option> 
  <option value="EBAY">EBAY</option>
  <option value="BBY">BBY</option>
  <option value="GS">GS</option>
  <option value="FB">FB</option>
  <option value="AAPL">AAPL</option>
  <option value="RHT">RHT</option>
  <option value="BAC">BAC</option> 
</select><br>
<%-- Select option: <select id ="selection" name="selection">  
  <option value="open">open</option>
  <option value="close">close</option>
  <option value="high">high</option> 
  <option value="low">low</option>
  <option value="volume">volume</option>
</select> <br> --%>
Select period for prediction<input type="date" id="dateperiod" name="dateperiod" value="2016-01-22">
<input id="button" type="submit" value="submit" /><br>
<tr>Result: <%= request.getAttribute("shorttermprediction") %></tr> 
</form><br>



<form  method="post"
action="indicatorcontroller" >
 Indicator:<select id="indicator" name="indicator">
	<option value="sma">SimpleMovingAverage</option>
  	<option value="ema">ExponentialMovingAverage</option>
</select>
 Ticker: <select id ="iticker" name="iticker">  
  <option value="YHOO">YHOO</option>
  <option value="AMZN">AMZN</option>
  <option value="GOOG">GOOG</option> 
  <option value="EBAY">EBAY</option>
  <option value="BBY">BBY</option>
  <option value="GS">GS</option>
  <option value="FB">FB</option>
  <option value="AAPL">AAPL</option>
  <option value="RHT">RHT</option>
  <option value="BAC">BAC</option>
</select><br>
<input type="date" id="fromdate" name="fromdate" value="2016-01-01">
<input type="date" id="todate" name="todate" value="2016-01-11">
<input id="button" type="submit" value="submit1" /><br>
</form>

<p>Select Ticker to view graph </p><br>

<form  method="post"
action="graphcontroller" > 
 Ticker: <select id ="gticker" name="gticker">  
  <option value="YHOO">YHOO</option>
  <option value="AMZN">AMZN</option>
  <option value="GOOG">GOOG</option> 
  <option value="EBAY">EBAY</option>
  <option value="BBY">BBY</option>
  <option value="GS">GS</option>
  <option value="FB">FB</option>
  <option value="AAPL">AAPL</option>
  <option value="RHT">RHT</option>
  <option value="BAC">BAC</option>
</select><br>
<input type="date" id="gfromdate" name="gfromdate" value="2016-01-01">
<input type="date" id="gtodate" name="gtodate" value="2016-01-11">
<input id="button" type="submit" value="submit2" /><br>
</form>

<form  method="post" action="querycontroller" > 
<p >Queries</p>
Ticker: <select id ="qticker" name="qticker">  
  <option value="YHOO">YHOO</option>
  <option value="AMZN">AMZN</option>
  <option value="GOOG">GOOG</option> 
  <option value="EBAY">EBAY</option>
  <option value="BBY">BBY</option>
  <option value="GS">GS</option>
  <option value="FB">FB</option>
  <option value="AAPL">AAPL</option>
  <option value="RHT">RHT</option>
  <option value="BAC">BAC</option>
</select>

<input type="checkbox" id="all" name="all" value="all"> Select all companies  <br>

Price Option: <select id ="priceoption" name="priceoption">  
  <option value="Latest">Latest Price</option>
  <option value="Highest">Highest Price</option>
  <option value="Average">Average Price</option> 
  <option value="Lowest">Lowest Price</option> 
  <option value="lessAverage">Less than Avg Price</option>  
</select><br>
Date: <select id ="daterange" name="daterange">  
  <option value="10 DAY">Last 10 days</option>
  <option value="1 MONTH">Last 1 month </option>
  <option value="1 YEAR">Last 1 year</option>     
</select><br>


<input  id="button"  type="submit" value="run" /><br>
</form>

<tr>Result: <%= request.getAttribute("resp") %></tr> 




</body>

</html>