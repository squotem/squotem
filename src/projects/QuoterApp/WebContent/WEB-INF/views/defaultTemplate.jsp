<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    
    <link href="css/main.css" type="text/css" rel="stylesheet"/>
    
    <link href="css/jquery-ui-1.10.3.custom.min.css" type="text/css" rel="stylesheet"/>
    <link href="css/jquery.dataTables.css" type="text/css" rel="stylesheet"/>
    <link href="css/dataTable.css" type="text/css" rel="stylesheet"/>
    
    <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/additional-methods.min.js"></script>
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="js/jshashtable-3.0.js"></script>
    <script type="text/javascript" src="js/jquery.numberformatter-1.2.4.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
    
    
</head>
<body>
	<div id="wrapper" class="hide">
	    <div id="header-container">
	   		<tiles:insertAttribute name="header" />
	    </div>
	    
	    <div id="content-container" >
	       	<tiles:insertAttribute name="content" />
	    </div>
	    
	    <div id="footer-container">
	   		<tiles:insertAttribute name="footer" />
	    </div>
    </div>
    
  
</body>
</html>