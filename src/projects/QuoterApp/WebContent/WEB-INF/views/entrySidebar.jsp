<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="sidebar">
	<div class="sidebar-menu">
		<c:if test="${not empty sessionScope.editQuote}">
			<div class="block-header"><spring:message code="sidebar.quoteNum" />${sessionScope.editQuote.quoteNumber}&nbsp;-&nbsp;<spring:message code="sidebar.revisionNum" />&nbsp;${sessionScope.editQuote.revisionNumber}<br /><spring:message code="sidebar.customer" />&nbsp;-&nbsp;${sessionScope.editQuote.customer.name}</div>
		</c:if>
	
	   	<ul>
	   		<c:choose>
			  <c:when test="${sidebar == 'customer'}">
			  	 <li class="current-item"><spring:message code="sidebar.customer" /></li>
			  </c:when>
			  <c:otherwise> 
			  	 <li><a href="entryCustomer.htm"><spring:message code="sidebar.customer" /></a></li>
			  </c:otherwise>
			</c:choose>
	   	
	   		<c:choose>
			  <c:when test="${sidebar == 'metric'}">
			  	 <li class="current-item"><spring:message code="sidebar.metric" /></li>
			  </c:when>
			  <c:otherwise> 
			  	 <li>
			  	 <c:choose>
				  	 <c:when test="${not empty sessionScope.editQuote}">
				  	   <a href="entryMetric.htm"><spring:message code="sidebar.metric" /></a>
				  	 </c:when>
			          <c:otherwise> 
			             <span class="disabled-link"><spring:message code="sidebar.metric" /></span>
			          </c:otherwise>
	              </c:choose>	          		  	 
			  	 </li>
			  </c:otherwise>
			</c:choose>
	   	
	   		<c:choose>
			  <c:when test="${sidebar == 'quote'}">
			  	 <li class="current-item"><spring:message code="sidebar.quote" /></li>
			  </c:when>
			  <c:otherwise> 
	             <li>
	             <c:choose>
	                 <c:when test="${not empty sessionScope.editQuote}">
	                   <a href="entryQuote.htm"><spring:message code="sidebar.quote" /></a>
	                 </c:when>
	                  <c:otherwise> 
	                     <span class="disabled-link"><spring:message code="sidebar.quote" /></span>
	                  </c:otherwise>
	              </c:choose>                        
	             </li>		  	 
			  </c:otherwise>
			</c:choose>
	   	
	   		<c:choose>
			  <c:when test="${sidebar == 'mts'}">
			  	 <li class="current-item"><spring:message code="sidebar.mts" /></li>
			  </c:when>
			  <c:otherwise> 
	             <li>
	             <c:choose>
	                 <c:when test="${not empty sessionScope.editQuote}">
	                 	<c:if test="${not empty sessionScope.editQuote.matrix.mtsMatrixId && sessionScope.editQuote.hasMts}">
	                    	<a href="entryMTS.htm"><spring:message code="sidebar.mts" /></a>
	                    </c:if>
	                 </c:when>
	                  <c:otherwise> 
	                     <!-- span class="disabled-link"><spring:message code="sidebar.mts" /></span-->
	                  </c:otherwise>
	              </c:choose>                        
	             </li>		  	 
			  </c:otherwise>
			</c:choose>
	   	
	   		<c:choose>
			  <c:when test="${sidebar == 'customerQuote'}">
			  	 <li class="current-item"><spring:message code="sidebar.customerQuote" /></li>
			  </c:when>
			  <c:otherwise> 
	             <li>
	             <c:choose>
	                 <c:when test="${not empty sessionScope.editQuote}">
	                   <a href="customerQuote.htm"><spring:message code="sidebar.customerQuote" /></a>
	                 </c:when>
	                  <c:otherwise> 
	                     <span class="disabled-link"><spring:message code="sidebar.customerQuote" /></span>
	                  </c:otherwise>
	              </c:choose>                        
	             </li>		  	 
			  </c:otherwise>
			  
			</c:choose>
			
	   		<!-- 
	   		<c:choose>
			  <c:when test="${sidebar == 'risk'}">
			  	 <li class="current-item"><spring:message code="sidebar.risk" /></li>
			  </c:when>
			  <c:otherwise> 
	             <li>
	             <c:choose>
	                 <c:when test="${not empty sessionScope.editQuote}">
	                   <a href="entryRisk.htm"><spring:message code="sidebar.risk" /></a>
	                 </c:when>
	                  <c:otherwise> 
	                     <span class="disabled-link"><spring:message code="sidebar.risk" /></span>
	                  </c:otherwise>
	              </c:choose>                        
	             </li>		  	 
			  </c:otherwise>
			  
			</c:choose>
			-->
	
			<li>&nbsp;</li>
			
			<li><hr></li>
			
			<c:choose>
			  <c:when test="${sidebar == 'businessPlan'}">
			  	 <li class="current-item"><spring:message code="sidebar.businessPlan" /></li>
			  </c:when>
			  <c:otherwise> 
	             <li>
	             <c:choose>
	                 <c:when test="${not empty sessionScope.editQuote && not empty sessionScope.editQuote.id}">
	                   <a href="businessPlan.htm?q=${sessionScope.editQuote.id}" target="_BLANK"><spring:message code="sidebar.businessPlan" /></a>
	                 </c:when>
	                  <c:otherwise> 
	                     <span class="disabled-link"><spring:message code="sidebar.businessPlan" /></span>
	                  </c:otherwise>
	              </c:choose>                        
	             </li>		  	 
			  </c:otherwise>
			  
			</c:choose>
					
		  	<li class="back-home"><a href="main.htm"><spring:message code="sidebar.backHome" /></a></li>
	   	
	   	</ul>
	</div>
	
	<c:if test="${not empty sessionScope.editQuote}">
	
        <c:set var="canSubmit" value="FALSE"/>
        <c:choose>
            <c:when test="${user.approvalLevel == 'BASELINE'}">
                <c:if test="${sessionScope.editQuote.status == 'DRAFT'}">
                    <c:set var="canSubmit" value="TRUE"/>
                </c:if>
            </c:when>
            <c:when test="${user.approvalLevel == 'BUSINESS_PLAN'}">
                <c:if test="${sessionScope.editQuote.status == 'SUBMITTED'}">
                    <c:set var="canSubmit" value="TRUE"/>
                </c:if>
            </c:when>
            <c:when test="${user.approvalLevel == 'ALL'}">
                <c:if test="${sessionScope.editQuote.status == 'DRAFT' || sessionScope.editQuote.status == 'SUBMITTED'}">
                    <c:set var="canSubmit" value="TRUE"/>
                </c:if>
            </c:when>
        </c:choose>
	
		<c:if test="${canSubmit}">
			<div class="sidebar-actions">
				<div class="block-header"><spring:message code="sidebar.actions" /></div>
			
			   	<div class="action-buttons">
		            <c:if test="${sessionScope.editQuote.activeRevision && canSubmit}">   
					   	<form action="submitQuote.htm" method="get" class="form-button">
							<input type="hidden" name="q" value="${sessionScope.editQuote.id}" />
							<c:choose>
							      <c:when test="${sessionScope.editQuote.status == 'DRAFT'}">
	                                    <button id="sidebar-submit"><spring:message code="sidebar.linkSubmitQualification"/></button>
							      </c:when>
	                              <c:when test="${sessionScope.editQuote.status == 'SUBMITTED'}">
	                                    <button id="sidebar-submit"><spring:message code="sidebar.linkSubmitBusinesPlanReview"/></button>
	                              </c:when>
							</c:choose>
					    </form>
		            </c:if>
			   	</div>
			   	
			</div>
		</c:if>
	</c:if>
</div>

<script type="text/javascript">

var currentQuote = 0;

	<c:if test="${not empty sessionScope.editQuote && not empty sessionScope.editQuote.id}">
		currentQuote = ${sessionScope.editQuote.id};
	</c:if>
</script>