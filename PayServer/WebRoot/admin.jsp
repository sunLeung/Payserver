<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
     <title>Learning AngularJS</title>
     <script src="scripts/angular.min.js" type="text/javascript"></script>
     <script src="scripts/app.js" type="text/javascript"></script>
     <script src="scripts/controllers/maincontroller.js" type="text/javascript"></script>
</head>
<body>
   <% 
      String ip  =  request.getHeader( "x-forwarded-for" );  
      out.print(ip);
       if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {  
          ip  =  request.getHeader( "Proxy-Client-IP" );  
           out.print(ip);
      }   
       if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {  
          ip  =  request.getHeader( "WL-Proxy-Client-IP" );  
           out.print(ip);
      }   
       if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {  
         ip  =  request.getRemoteAddr();  
     }   
      out.print(ip);   %>
</body>
</html>