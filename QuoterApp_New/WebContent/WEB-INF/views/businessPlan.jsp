<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

<jsp:useBean id="now" class="java.util.Date" scope="page" />

<c:set var="VISIBILITY_SALES_DIRECTOR_ONLY" value="0" />
<c:set var="VISIBILITY_ALL" value="1" />

<div class="header-detail">
	<table class="width100">
		<tbody>
			<tr>
				<td><spring:message code="main.quoteNum" />&nbsp;${quote.quoteNumber}</td>
				<td><spring:message code="main.quoteRev" />:${quote.revisionNumber}</td>
				<td><spring:message code="print.pricingModel" />:&nbsp;${quote.matrix.name}</td>
				<td><spring:message code="${quote.status}" /></td>
				<td class="text-r"><fmt:formatDate value="${now}" pattern="MMMM dd,yyyy" /></td>
				<td class="text-r"><button id="btn-print" onClick="window.print()">Print</button></td>
			</tr>
		</tbody>
	</table>
</div>
<div class="content-detail">
	
	<div class="content-box"> <!-- customer -->
		<div class="block-header"><spring:message code="print.customerInformation" /></div>
		<div class="block-data">
			<div><c:out value="${quote.customer.name}"/></div>
			<div><spring:message code="${quote.customer.customerType}"/>&nbsp;/&nbsp;<c:out value="${quote.customer.businessSector.description}"></c:out></div>
			<div>
				<c:if test="${not empty quote.customer.city}">
					<c:out value="${quote.customer.city}"/>,
				</c:if> 
				<c:out value="${quote.customer.state.stateShortName}"/>, <c:out value="${quote.customer.country.countryName}"/>
			</div>
			<div><c:out value="${quote.customer.projectSponsor}"/> <c:out value="${quote.customer.sponsorPhone}"/></div>
		</div>
	</div>

    <div class="content-box"> <!-- LeanLogistics info -->
        <div class="block-header"><spring:message code="customer.blujaysolution.description" /></div>
        <div class="block-data">
            <div><!--<spring:message code="customer.leanlogistics.salesDirector" />&nbsp;--><c:out value="${quote.salesDirector.firstName}"/>&nbsp;<c:out value="${quote.salesDirector.lastName}"/></div>
            <div><!--<spring:message code="customer.leanlogistics.businessConsultant" />&nbsp;--><c:out value="${quote.businessConsultant}"></c:out></div>
            <div><!--<spring:message code="customer.leanlogistics.partnerReferenced" />&nbsp;--><c:out value="${quote.partnerReferenced}"></c:out></div>
            <div>
                <c:forEach var="productCategory" items="${quote.productCategories}" >
                      <c:out value="${productCategory.description}"></c:out>
                </c:forEach>            
            </div>
            <div><fmt:formatDate pattern="MM/dd/yyyy" value="${quote.effectiveDate}"/> - <fmt:formatDate pattern="MM/dd/yyyy" value="${quote.expireDate}"/></div>
        </div>
    </div>

	<c:if test="${not empty categorizedMetrics}">
		<div class="content-box"> <!-- categorized metrics -->
			<div class="block-header"><spring:message code="print.metric" /></div>

                <!-- c set var="categorizedQuoteMetrics" value="${categorizedMetrics}"></c set-->
                <% request.setAttribute("categorizedQuoteMetrics", request.getAttribute("categorizedMetrics")); %>
                <tiles:insertAttribute name="categorizedQuoteMetrics" />
			
		</div>
	</c:if>

	<!-- quotes -->
	<c:forEach items="${categorizedCostItems}" var="quoteCostItem" varStatus="quoteCostItemStatus">
		<div class="content-box quote-box">
            <div class="block-header"><spring:message code="print.quoteMts" /></div>
            
            <c:if test="${not empty categorizedMetricsBS}">
                <!--  c set var="categorizedQuoteMetrics" value="${categorizedMetricsBS}"></c set-->
                <% request.setAttribute("categorizedQuoteMetrics", request.getAttribute("categorizedMetricsBS")); %>
                <tiles:insertAttribute name="categorizedQuoteMetrics" />
            </c:if>
            
            <div class="content-box metric-box">
				<div class="block-header"><spring:message code="quote.quote" /> - <c:out value="${quoteCostItem.productCategory.description}"/></div>
				<c:set var="totalImpl" value="0"></c:set>
				<c:set var="totalMon" value="0"></c:set>
				<c:set var="lastDesc" value=""></c:set>
				<div class=""> <!-- block-data -->
					<table id="quote-table-${quoteCostItem.productCategory.id}" class="width100">
						<thead>
							<tr>
								<th><spring:message code="quote.enable" /></th>
								<th><spring:message code="quote.category" /></th>
								<th><spring:message code="quote.functionality" /></th>
								<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
									<th><spring:message code="quote.implementation" /></th>
									<th><spring:message code="quote.monthly" /></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${quoteCostItem.entryCostItems}" var="entryCostItem" varStatus="entryCostItemStatus">
								<tr>
									<td>
				    					<c:choose>
				    						<c:when test="${entryCostItem.enabled == true}">
				    							<spring:message code="quote.yes" />
				    						</c:when>
				    						<c:when test="${entryCostItem.enabled == false}">
				    							<spring:message code="quote.no" />
				    						</c:when>
				    					</c:choose>
									</td>
									<td>
			    						<c:if test="${lastDesc != entryCostItem.costItem.itemCategory.description}">
			    							<c:out value="${entryCostItem.costItem.itemCategory.description}" />
			    							<c:set var="lastDesc" value="${entryCostItem.costItem.itemCategory.description}"></c:set>
			    						</c:if>
									</td>
									<td style="color:${entryCostItem.alternateColor};"><span>${entryCostItem.costItem.description}</span></td>
	
									<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
										<td class="text-r">
											<c:if test="${entryCostItem.enabled == true}">
												<c:choose>
													<c:when test="${entryCostItem.implCost == 'NaN'}">
														<span class="error">${entryCostItem.implQuoteCostError}</span>
													</c:when>
													<c:otherwise>
														<c:if test="${entryCostItem.implCost > 0}">
															<fmt:formatNumber value="${entryCostItem.implCost}" type="number" maxFractionDigits="0"/>
															<c:set var="totalImpl" value="${totalImpl + entryCostItem.implCost}"></c:set>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:if>
										</td>
										<td class="text-r">
											<c:if test="${entryCostItem.enabled == true}">
												<c:choose>
													<c:when test="${entryCostItem.monthlyCost == 'NaN'}">
														<span class="error">${entryCostItem.monthlyQuoteCostError}</span>
													</c:when>
													<c:otherwise>
														<c:if test="${entryCostItem.monthlyCost > 0}">
															<fmt:formatNumber value="${entryCostItem.monthlyCost}" type="number" maxFractionDigits="0"/>
															<c:set var="totalMon" value="${totalMon + entryCostItem.monthlyCost}"></c:set>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:if>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
						<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
							<tfoot>
								<tr>
									<c:choose>
										<c:when test="${user.priceVisibility == VISIBILITY_ALL}">
											<th class="right" colspan="3"><spring:message code="quote.subtotal" /></th>
										</c:when>
										<c:otherwise>
											<th class="right" colspan="1"><spring:message code="quote.subtotal" /></th>
										</c:otherwise>
									</c:choose>
									<th class="right" colspan="1">
										<c:choose>
						    				<c:when test="${totalImpl == 'NaN'}">
						    					 <span class="error"><spring:message code="quote.error.missing" /></span>
						    				</c:when>
						    				<c:otherwise>
												<fmt:formatNumber value="${totalImpl}" type="number" maxFractionDigits="0"/>
						    				</c:otherwise>
						    			</c:choose>
									</th>
									<th class="right" colspan="1">
										<c:choose>
						    				<c:when test="${totalMon == 'NaN'}">
						    					 <span class="error"><spring:message code="quote.error.missing" /></span>
						    				</c:when>
						    				<c:otherwise>
												<fmt:formatNumber value="${totalMon}" type="number" maxFractionDigits="0"/>
						    				</c:otherwise>
						    			</c:choose>
									</th>
								</tr>
							</tfoot>
						</c:if>
					</table>
			    </div>
			</div>
		</div>
	</c:forEach>

	<div class="content-box"> <!-- Customer quote -->
		<div class="block-header"><spring:message code="print.customerQuote" /></div>
			<div class="block-data">
				<div class="terms"><spring:message code="customer.information.terms" />:&nbsp;<span id="terms">${quote.terms}</span>&nbsp;<spring:message code="customer.information.termsMonths" /></div>

                <% request.setAttribute("businessPlan", true); %>
                <tiles:insertAttribute name="pricing" />

			</div>
		
	</div>
	
	<tiles:insertAttribute name="comments" />

</div>
<script type="text/javascript">
	$(document).ready(function(){
		$('#btn-print').button({
		      icons: {
			        primary: "ui-icon-print"
		      }
		});
		
		$('.risk-slider').each(function(){
			$(this).progressbar({
				max: 10,
				value: $(this).parent().find('input').val()*1
			}).height(10);
		});
		
		$(document).attr('title', '<spring:message code="print.title" /> <c:out value="${quote.quoteNumber}"/>');
	});

</script>