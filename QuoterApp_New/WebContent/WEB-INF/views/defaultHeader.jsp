<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header-detail">
	<h1><span><a href="main.htm"><img alt="" src="images/logo.png"></a></span></h1>
	<div class="header-profile">

		<c:choose>
		  <c:when test="${user != null}">
		  	 <div>Hello  ${user.firstName} ${user.lastName}</div>
		  	 <div class="logout"><a href="logout.htm">Logout</a></div>
		  </c:when>
		  <c:otherwise>Please login </c:otherwise>
		</c:choose>
		
	</div>
	
	<div class="clear"></div>
</div>

