<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

<div class="quote-message-status title">
	<c:choose>
		<c:when test="${empty mainMessage}">
		    <c:choose>
		          <c:when test="${quote.status == 'SUBMITTED' }">
                        <spring:message code="confirm.submitBusinessPlanReviewSuccess" arguments="${quote.quoteNumber}, ${quote.revisionNumber}" htmlEscape="false"/>
		          </c:when>
                  <c:when test="${quote.status == 'APPROVED' }">
                        <spring:message code="confirm.submitBusinessPlanApprovedSuccess" arguments="${quote.quoteNumber}, ${quote.revisionNumber}" htmlEscape="false"/>
                  </c:when>
		    </c:choose>
		</c:when>
		<c:otherwise>
			<c:out value="${mainMessage}" />
		</c:otherwise>
	</c:choose>
	
</div>

<div class="back-home"><a href="entryCustomer.htm?q=${quote.id}"><spring:message code="main.linkEdit" arguments="${quote.quoteNumber}" htmlEscape="false"/></a> | <a href="main.htm"><spring:message code="sidebar.backHome" /></a></div>
