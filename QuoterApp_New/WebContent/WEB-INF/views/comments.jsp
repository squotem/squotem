<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

<c:if test="${not empty quote.quoteNotes}">
	<div id="comments">
		<div class="content-box">
			<div class="block-header"><spring:message code="comments.description" /></div>
			<dl>
				<c:forEach items="${quote.quoteNotes}" var="quoteNote">
					<dt><c:out value="${quoteNote.user.firstName}"/>&nbsp;<c:out value="${quoteNote.user.lastName}" /> <span class="float-r "><fmt:formatDate pattern="MM/dd/yyyy hh:mm a" value="${quoteNote.createDate}"/></span><span class="float-r width30"><spring:message code="comments.revisionNumber" arguments="${quoteNote.quote.revisionNumber}" htmlEscape="false" /></span></dt>
					<dd><p><c:out value="${quoteNote.comment}"/></p></dd>
				</c:forEach>
			</dl>
			
		</div>
		
	</div>
</c:if>