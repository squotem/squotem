<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

<div class="content-detail">

    <div class="text-r menu-section">
    	<form action="entryCustomer.htm" method="get">
			<input type="hidden" name="q" value="-1" />
			<button id="btn-new-quote"><spring:message code="main.newQuote" /></button>
        </form>
    </div>
    <div class="search-block content-box">
    	<div class="block-header"><spring:message code="main.search" /></div>
		<form:form commandName="quoteQuery" action="quoteQuery.htm" method="post">
		    <table>
		    	<tbody>
		    		<tr>
		    			<td><spring:message code="main.quoteNumRev" /></td>
		    			<td>
		    				<form:input path="quoteNumber" type="text"/>
						</td>
						<td><spring:message code="main.salesDirector" /></td>
		    			<td>
                            <form:select path="salesDirector">
                                <form:option value=""><spring:message code="main.all" /></form:option>
                                <c:forEach var="usr" items="${users}" >
                                     <form:option value="${usr.id}">${usr.firstName}&nbsp;${usr.lastName}</form:option>   
                                </c:forEach>
                            </form:select>
						</td>
						<td></td>
		    			<td>
						    <label><form:checkbox path="onlyActive" value="${quoteQuery.onlyActive}" /><spring:message code="main.onlyActiveRevision" /></label>
						</td>
					</tr>
		    		<tr>
		    			<td><spring:message code="main.status" /></td>
		    			<td>
						    <form:select path="status">
								<form:option value=""><spring:message code="main.all" /></form:option>
			    			    <c:forEach var="entry" items="${quoteStatuses}" >
			    			         <form:option value="${entry.name}" ><spring:message code="${entry.name}" /></form:option>   
			    			    </c:forEach>
			    			</form:select>  
						</td>
						<td><spring:message code="main.modifiedDate" /></td>
		    			<td>
						    <spring:message code="main.startDate" />&nbsp;<form:input type="text" path="from" cssClass="date" value=""/>&nbsp;&nbsp;<spring:message code="main.endDate" />&nbsp;<form:input type="text" path="to" cssClass="date" value=""/>
						</td>
						<td></td>
		    			<td>
						</td>
					</tr>
		    		<tr>
		    			<td><spring:message code="main.customer" /></td>
		    			<td>
						    <form:select path="customerId">
								<form:option value=""><spring:message code="main.all" /></form:option>
			    			    <c:forEach var="customer" items="${customers}" >
			    			         <form:option value="${customer.id}">${customer.name}</form:option>   
			    			    </c:forEach>
			    			</form:select>  
						</td>
						<td>
							<spring:message code="main.modifiedBy" />
						</td>
		    			<td>
							<form:select path="ownerId">
								<form:option value=""><spring:message code="main.all" /></form:option>
			    			    <c:forEach var="usr" items="${users}" >
			    			         <form:option value="${usr.id}">${usr.firstName}&nbsp;${usr.lastName}</form:option>   
			    			    </c:forEach>
			    			</form:select>
						</td>
						<td></td>
		    			<td class="text-r">
		    				<button id="btn-search"><spring:message code="main.search" /></button>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
    
    </div>
    
 	<div id="dummy" style="margin:5px;">
		<table class="display" id="quoter-search-result" >
			<thead>
				<tr>
					<th></th> <!-- hidden -->
					<th></th> <!-- hidden -->
					<th></th> <!-- hidden -->
                    <th></th> <!-- hidden -->
                    <th></th> <!-- hidden -->
					<th><spring:message code="main.quoteNumRev" /></th>
					<th><spring:message code="main.customer" /></th>
					<th><spring:message code="main.modifiedBy" /></th>
					<th><spring:message code="main.modifiedDate" /></th>
					<th><spring:message code="main.pricingModel" /></th>
					<th><spring:message code="main.salesDirector" /></th>
					<th><spring:message code="main.status" /></th>
					<th><spring:message code="main.action" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="quote" items="${quotes}" >                    				
					<tr class=" ">
						<td><c:out value="${quote.id}"/></td>
						<td><c:out value="${quote.status}"/></td>					    					    
						<td><c:out value="${quote.quoteNumber}"/></td>					    					    
						<td><c:out value="${quote.revisionNumber}"/></td>
                        <td><c:out value="${quote.activeRevision}"/></td>
						<td>
							<c:choose>
								<c:when test="${quote.activeRevision}">
									<a href="entryCustomer.htm?q=${quote.id}" title="<spring:message code="main.linkEdit" arguments="${quote.quoteNumber}" htmlEscape="false"/>">${quote.quoteNumber}.${quote.revisionNumber}</a>
								</c:when>
								<c:otherwise>
									${quote.quoteNumber}.${quote.revisionNumber}
								</c:otherwise>
							</c:choose>
						</td>
						<td>${quote.customer.name}</td>
						<td>${quote.modifiedBy.firstName}&nbsp;${quote.modifiedBy.lastName}</td>
						<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${quote.modifiedDate}"/></td>
						<td>${quote.matrix.name}</td>
                        <td>${quote.salesDirector.firstName}&nbsp;${quote.salesDirector.lastName}</td>
						<td><spring:message code="${quote.status}" /></td>
						<td class="center">
							<a href="entryCustomer.htm?q=${quote.id}" class="editQuote ${quote.activeRevision ? '' : 'hide'}" title="<spring:message code="main.linkEdit" arguments="${quote.quoteNumber}" htmlEscape="false"/>">
								<span class="button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
								</span>
							</a>
							
							<a href="businessPlan.htm?q=${quote.id}"  target="_blank" title="<spring:message code="main.linkBusinessPlan" arguments="${quote.quoteNumber},${quote.revisionNumber}" htmlEscape="false"/>">
								<span class="button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-print"></span>
								</span>
							</a>
                            
                            <c:set var="canSubmit" value="FALSE"/>
                            <script>
                            	var approvalLevel = '${user.approvalLevel}';
                            	
                            </script>
                            <c:choose>
                                <c:when test="${user.approvalLevel == 'BASELINE'}">
                                    <c:if test="${quote.status == 'DRAFT'}">
                                        <c:set var="canSubmit" value="TRUE"/>
                                    </c:if>
                                </c:when>
                                <c:when test="${user.approvalLevel == 'BUSINESS_PLAN'}">
                                    <c:if test="${quote.status == 'SUBMITTED'}">
                                        <c:set var="canSubmit" value="TRUE"/>
                                    </c:if>
                                </c:when>
                                <c:when test="${user.approvalLevel == 'ALL'}">
                                    <c:if test="${quote.status == 'DRAFT' || quote.status == 'SUBMITTED'}">
                                        <c:set var="canSubmit" value="TRUE"/>
                                    </c:if>
                                </c:when>
                            </c:choose>
							<c:if test="${quote.activeRevision && canSubmit}">
								<a href="#" id="changeStatus" class="changeStatus" title="<spring:message code="main.linkChangeStatus" arguments="${quote.quoteNumber}" htmlEscape="false"/>">
									<span class="button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-check"></span>
									</span>
								</a>
							</c:if>
							
							<c:if test="${quote.activeRevision}">
								<a href="#" class="addComment" title="<spring:message code="main.linkAddComment" arguments="${quote.quoteNumber}" htmlEscape="false"/>">
									<span class="button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-comment"></span>
									</span>
								</a>
							</c:if>
							
						</td>
					</tr>
					
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th></th> <!-- hidden -->
					<th></th> <!-- hidden -->
					<th></th> <!-- hidden -->
					<th></th> <!-- hidden -->
                    <th></th> <!-- hidden -->
					<th><spring:message code="main.quoteNumRev" /></th>
					<th><spring:message code="main.customer" /></th>
					<th><spring:message code="main.author" /></th>
					<th><spring:message code="main.date" /></th>
					<th><spring:message code="main.pricingModel" /></th>
					<th><spring:message code="main.salesDirector" /></th>
					<th><spring:message code="main.status" /></th>
					<th><spring:message code="main.action" /></th>
				</tr>
			</tfoot>
		</table>
	</div>
    
    <script type="text/javascript" src="js/quoteSearch.js"></script>
</div>

<div id="dlg-change-status" class="hide" title="<spring:message code="main.changeStatus" />">
	<table>
		<tr>
			<td><spring:message code="main.quoteNum" /></td>
			<td><span id="dlg-quote-num"></span></td>
		</tr>
		<tr>
			<td><spring:message code="main.quoteRev" /></td>
			<td><span id="dlg-quote-rev"></span></td>
		</tr>
		<tr>
			<td><spring:message code="main.status" /></td>
			<td><span id="dlg-quote-status"></span></td>
		</tr>
		<tr>
			<td><spring:message code="main.newStatus" /></td>
			<td>
			    <span id="dlg-quote-new-status-lbl"></span>
			    <input type="hidden" id="dlg-quote-new-status"/>
	
			</td>
		</tr>
		<tr>
			<td><spring:message code="comments.description" /></td>
			<td>
			    <textarea id="dlg-status-comment" rows="5" cols="37" maxlength="250"></textarea>  
	
			</td>
		</tr>
		<tr>
			<td id="working" class="hide"><spring:message code="main.working"/></td>
		</tr>
	</table>	
</div>

<div id="dlg-add-comment" class="hide" title="<spring:message code="comments.addComment" />">
	<table>
		<tr>
			<td><spring:message code="comments.description" /></td>
			<td>
			    <textarea id="dlg-new-comment" rows="5" cols="37" maxlength="250"></textarea>  
	
			</td>
		</tr>
	</table>	
</div>

<script type="text/javascript">
var quoteStatuses = {};
	<c:forEach var="entry" items="${quoteStatuses}" >
		quoteStatuses['${entry.name}'] = '<spring:message code="${entry.name}" />';   
	</c:forEach>

</script>
