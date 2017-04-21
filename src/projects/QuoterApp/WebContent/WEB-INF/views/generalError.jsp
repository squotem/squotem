<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>


<div style="margin-top: 30px;">
An error occurred while trying to process your request. Please go back, review your parameters and try again.
</div>
<c:if test="${not empty message}">
	<div style="margin-top: 30px;">
		Description:<c:out value="${message}"></c:out>
	</div>
</c:if>
<c:if test="${not empty cause}">
	<div style="margin-top: 30px;">
		Cause:<c:out value="${cause}"></c:out>
	</div>
</c:if>

<div class="back-home"><a href="${backTo}"><spring:message code="main.linkBackToEdit" arguments="${quote.quoteNumber}" htmlEscape="false"/></a> | <a href="main.htm"><spring:message code="sidebar.backHome" /></a></div>
