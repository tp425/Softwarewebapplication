<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        /* var data = google.visualization.arrayToDataTable([
          ['Days', 'Price'],
          ['Mon', 10],
          ['Tue',  20 ],
          ['Wed',  70],
          ['thu',  60],
          ['fri',  30]
        ]); */
        
        var data = new google.visualization.DataTable();
        
        data.addColumn('Days', 'Price');
        data.addColumn('Mon', 'GOOG');
        
         
        var x;
        for (x = 0; x <= 10; x++) {
          data.addRow([x, 2 * x]);
        }

        var options = {
          title: 'Indicator',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="curve_chart" style="width: 900px; height: 500px"></div>
  </body>
</html>