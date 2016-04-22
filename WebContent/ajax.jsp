<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $('input[value="run1"]').click(function(){    	
        $("#div1").load("ajaxout.txt");
    });
});


</script>
</head>
<body>

<div id="div1"><h2>Let jQuery AJAX Change This Text</h2></div>
<input  id="button" type="submit" value="run" /><br>
<input  id="button" type="submit" value="run1" /><br>


</body>
</html>