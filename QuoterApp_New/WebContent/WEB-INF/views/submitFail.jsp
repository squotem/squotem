<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

<div class="quote-message-status">
	<div class="title"><spring:message code="confirm.submitReviewFail" /></div> <!-- arguments="${quote.quoteNumber}, ${quote.revisionNumber}" htmlEscape="false" -->
	<div><c:out value="${generalMessage}"/></div>
	<ul>
		<c:forEach items="${detailMessages}" var="message">
			<li>
				<c:out value="${message}" />
			</li>
		</c:forEach>
	</ul>
	
</div>

<div class="back-home"><a href="main.htm"><spring:message code="sidebar.backHome" /></a></div>
