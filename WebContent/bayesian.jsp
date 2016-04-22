<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE HTML>
<html>

<head></head>

<body>



    <p>
        <% String predictedval=request.getAttribute("predictedval").toString() ;
       // String selection=request.getAttribute("selection").toString() ;
        String date=request.getAttribute("date").toString() ;
        //out.print("User selected: "+selection+" on "+ date +" and predicted value is:" +predictedval);
        out.print("predicted value for "+ date +" is :" +predictedval);
        %>
        
    </p>
    

</body>

</html>