<!DOCTYPE HTML>
<html>

<head></head>
<body>
<canvas id="c" width="500" height="500"></canvas>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js">
var data = {
      labels: [0,0,0,0,0],
      datasets: [{
        label: "Real time price",
        fillColor: "rgba(220,220,220,0.2)",
        strokeColor: "rgba(220,220,220,1)",
        pointColor: "rgba(220,220,220,1)",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(220,220,220,1)",
        data: [0,0,0,0,0]
      
      }]
    };
    var MyNewChart = new Chart(ctx).Line(data);
    </script>
    
 </body>