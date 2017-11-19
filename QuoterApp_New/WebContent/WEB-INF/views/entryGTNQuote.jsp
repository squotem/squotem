<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"    prefix="fn" %>


<!-- meanwhile I find out how to pass the enum to here :( -->
<c:set var="VISIBILITY_SALES_DIRECTOR_ONLY" value="0" />
<c:set var="VISIBILITY_ALL" value="1" />

    
<div class="content-detail">
	<div class="use-sidebar sidebar-at-left" id="entry">
	    
	    <tiles:insertAttribute name="sidebar" />
	    
		<div class="content">
			
			
			<div class="quote-tables">
				<form:form commandName="command" action="entryQuote.htm" method="post">
					<div class="save-quote">
                        <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                        <button type="submit" title="<spring:message code="refreshQuote"/>" class="btn-refresh"><spring:message code="refresh" /></button>
	   					<c:choose>
							<c:when test="${not empty sessionScope.editQuote && sessionScope.editQuote.hasMts}">
                                <button type="submit" title="<spring:message code="nextQuoteMTS"/>" class="btn-next"><spring:message code="next" /></button>
						  	</c:when>
					        <c:otherwise> 
                                <button type="submit" title="<spring:message code="nextQuote"/>" class="btn-next"><spring:message code="next" /></button>
					        </c:otherwise>
			            </c:choose>
	                    <input type="hidden" id="save" name="save"/>
					</div>

				<div class="matrix">
		    		<form:label path="terms"><spring:message code="customer.information.terms" /></form:label>
					<form:input path="terms" value="12"/>&nbsp;<spring:message code="customer.information.termsMonths" />
				</div>
	
<!--  -->

				    <tiles:insertAttribute name="categorizedQuoteMetrics" />

<!--  -->					
					
					<c:forEach var="subscriptionPricingItem" items="${command.subscriptionPricingList}" >
						   <c:set var="minTier" value="${subscriptionPricingItem.minAnnualFrieghtSpend}" />
						   <c:set var="maxTier" value="${subscriptionPricingItem.maxAnnualFrieghtSpend}" />
						   <input type="hidden" id="${subscriptionPricingItem.costItemId}${subscriptionPricingItem.tier}" value="${minTier}" class="minTier${subscriptionPricingItem.costItemId}" />
						   <input type="hidden" id="maxTier${subscriptionPricingItem.costItemId}${subscriptionPricingItem.tier}" value="${maxTier}" class="maxTier${subscriptionPricingItem.costItemId}" />
						    <input type="hidden" id="annualFee${subscriptionPricingItem.costItemId}${subscriptionPricingItem.tier}" value="${subscriptionPricingItem.annualFee}" />
					</c:forEach>
					
					<c:forEach var="licensePricingItem" items="${command.licensePricingList}" >
						   <c:set var="minLicenseTier" value="${licensePricingItem.minAnnualFrieghtSpend}" />
						   <c:set var="maxLicenseTier" value="${licensePricingItem.maxAnnualFrieghtSpend}" />
						   <input type="hidden" id="${licensePricingItem.costItemId}${licensePricingItem.tier}" value="${minLicenseTier}" class="minLicenseTier${licensePricingItem.costItemId}"/>
						   <input type="hidden" id="maxLicense${licensePricingItem.costItemId}${licensePricingItem.tier}" value="${maxLicenseTier}" class="maxLicenseTier${licensePricingItem.costItemId}"/>
						    <input type="hidden" id="licenseFee${licensePricingItem.costItemId}${licensePricingItem.tier}" value="${licensePricingItem.licenseFee}" />
					</c:forEach>
					
					<c:forEach var="tieredPricingItem" items="${command.tieredPricingList}" >
						   <c:set var="minTierPrice" value="${tieredPricingItem.minTransactions}" />
						   <c:set var="maxTierPrice" value="${tieredPricingItem.maxTransactions}" />
						   <input type="hidden" id="${tieredPricingItem.costItemId}${tieredPricingItem.tier}" value="${minTierPrice}" class="minTierPrice${tieredPricingItem.costItemId}"/>
						   <input type="hidden" id="maxTierPrice${tieredPricingItem.costItemId}${tieredPricingItem.tier}" value="${maxTierPrice}" class="maxTierPrice${tieredPricingItem.costItemId}"/>
						    <input type="hidden" id="feePerTransaction${tieredPricingItem.costItemId}${tieredPricingItem.tier}" value="${tieredPricingItem.feePerTransaction}" />
						    <input type="hidden" id="annualPricingFee${tieredPricingItem.costItemId}${tieredPricingItem.tier}" value="${tieredPricingItem.annualFee}" />
							<input type="hidden" id="tier${tieredPricingItem.costItemId}${tieredPricingItem.tier}" value="${tieredPricingItem.tier}" />
					</c:forEach>
					
					<c:forEach var="perTransactionPricingItem" items="${command.perTransactionPricingList}" >
						   <c:set var="minFreight" value="${perTransactionPricingItem.minFreight}" />
						   <c:set var="maxFreight" value="${perTransactionPricingItem.maxFreight}" />
						   <input type="hidden" id="${perTransactionPricingItem.costItemId}${perTransactionPricingItem.tier}" value="${minFreight}" class="minFreight${perTransactionPricingItem.costItemId}"/>
						   <input type="hidden" id="maxFreight${perTransactionPricingItem.costItemId}${perTransactionPricingItem.tier}" value="${maxFreight}" class="maxFreight${perTransactionPricingItem.costItemId}"/>
						    <input type="hidden" id="annualPrice${perTransactionPricingItem.costItemId}${perTransactionPricingItem.tier}" value="${perTransactionPricingItem.annualPrice}" />
					       <input type="hidden" id="pricePerTrans${perTransactionPricingItem.costItemId}${perTransactionPricingItem.tier}" value="${perTransactionPricingItem.pricePerTransaction}" />
					       <input type="hidden" id="tierTrans${perTransactionPricingItem.costItemId}${perTransactionPricingItem.tier}" value="${perTransactionPricingItem.tier}" />
					       
					</c:forEach>
					<div class="product-category-tables">

					<c:set var="lastCategory" value=""></c:set>
					
					
					<c:forEach items="${command.categorizedQuoteCostItems}" var="quoteCostItem" varStatus="quoteCostItemStatus">
					<c:if test="${quoteCostItem.productCategory.id eq 3}">
						<div class="content-box quote-box">
						
							<div class="block-header">${quoteCostItem.productCategory.description} <spring:message code="quote.quote" /></div>
							<div class="block-table">
								<table id="quote-table-${quoteCostItem.productCategory.id}">
									<thead >
										<tr>
											<th><spring:message code="quote.enable"/></th>
											<th><spring:message code="product.family"/></th>
											<th><spring:message code="product.name"/></th>
												<th>impl-internal</th>
											<th>mon-internal</th>
											<th><spring:message code="product.pricing.optionone"/></th>
											<th><spring:message code="product.pricing.optiontwo"/></th>
											<th><spring:message code="product.pricing.optionthree"/></th>
											<th><spring:message code="product.pricing.optionfour"/></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${quoteCostItem.entryCostItems}" var="entryCostItem" varStatus="entryCostItemStatus">
											<tr>
												<td>
                                                    <c:if test="${entryCostItem.costItem != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].costItem.id" value="${entryCostItem.costItem.id}" />
                                                    </c:if>
                                                    <c:if test="${entryCostItem.implCostModel != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].implCostModel.id" value="${entryCostItem.implCostModel.id}" />
                                                    </c:if>
                                                    <c:if test="${entryCostItem.monthlyCostModel != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].monthlyCostModel.id" value="${entryCostItem.monthlyCostModel.id}" />
                                                    </c:if>                                                                                                                                                     												
												    <c:if test="${entryCostItem.implQuoteCostItem != null}">
												       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].implQuoteCostItem.id" value="${entryCostItem.implQuoteCostItem.id}" />
												    </c:if>
                                                    <c:if test="${entryCostItem.monthlyQuoteCostItem != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].monthlyQuoteCostItem.id" value="${entryCostItem.monthlyQuoteCostItem.id}" />
                                                    </c:if>
                                                    <c:if test="${entryCostItem.implCost != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].implCost" value="${entryCostItem.implCost}" />
                                                    </c:if>
                                                    <c:if test="${entryCostItem.monthlyCost != null}">
                                                       <input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].monthlyCost" value="${entryCostItem.monthlyCost}" />
                                                    </c:if>
                                                  <%--   <c:choose>
                                                    	<c:when test="${empty entryCostItem.forced}">
                                                    		<c:set var="enabled" value="${entryCostItem.enabled}"></c:set>
                                                    	</c:when>
                                                    	<c:otherwise>
                                                    		<c:set var="enabled" value="${entryCostItem.forced}"></c:set>
                                                    	</c:otherwise>
                                                    </c:choose>                                                    												    
													<select id="enabled-${entryCostItem.costItem.id}" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].enabled" class="enabled-combo ${entryCostItem.required ? 'required' : '' }" ${(empty entryCostItem.forced) ? "" : "disabled='disabled'"} alternateColor="${entryCostItem.alternateColor}">
														<option value="" <c:if test="${enabled == null}">selected</c:if>></option>
														<option value="true" <c:if test="${enabled == true}">selected</c:if>><spring:message code="quote.yes" /></option>
														<option value="false" <c:if test="${enabled == false}">selected</c:if>><spring:message code="quote.no" /></option>
													</select>
													<c:if test="${entryCostItem.required}">
													   <span class="red">*</span>
													</c:if>
													<c:if test="${!empty entryCostItem.forced}">
														<input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].enabled" value="${entryCostItem.enabled}" />
														<input type="hidden" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].forced" value="${entryCostItem.forced}" />
													</c:if> --%>
													<input type="checkbox" value="false" class="pricing-checkbox" id="${entryCostItem.costItem.id}" />
												</td>
												<td>
													
													<c:out value="${entryCostItem.costItem.itemCategory.description}"/>
													
												</td>
   												<td>
   													<c:out value="${entryCostItem.costItem.description}"/>
   												</td>
												<td class="functionality"><span <c:if test="${entryCostItem.costItem.descriptionDetail != '' && entryCostItem.costItem.descriptionDetail != null}">class="tooltip" title="${entryCostItem.costItem.descriptionDetail}"</c:if>>${entryCostItem.costItem.description}</span></td>
												
												<td><c:if test="${entryCostItem.enabled == true}">true</c:if></td> <!-- internal -->
												<td id="perTransactionMinimumPrice${entryCostItem.costItem.id}"></td>
												<td id="tieredPrice${entryCostItem.costItem.id}"></td> <!-- internal -->
												<!-- entryCostItem.implQuoteCostError != null && entryCostItem.monthlyQuoteCostError != null -->
												
												<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
													<td id="subscriptionPrice${entryCostItem.costItem.id}">
													</td>
													<td id="licensePrice${entryCostItem.costItem.id}">
													</td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
									<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
										<tfoot>
											<tr>
												<th class="right" colspan="7"><spring:message code="quote.subtotal" /></th>
												<th class="right" colspan="1">0.0</th>
												<th class="right" colspan="1">0.0</th>
											</tr>
										</tfoot>
									</c:if>
								</table>
							</div>
						</div>
						</c:if>
					</c:forEach>
				
					</div>
										
		    		<div class="clear"></div>
		    		
					<div class="save-quote">
                        <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                        <button type="submit" title="<spring:message code="refreshQuote"/>" class="btn-refresh"><spring:message code="refresh" /></button>
                        <c:choose>
                            <c:when test="${not empty sessionScope.editQuote && sessionScope.editQuote.hasMts}">
                                <button type="submit" title="<spring:message code="nextQuoteMTS"/>" class="btn-next"><spring:message code="next" /></button>
                            </c:when>
                            <c:otherwise> 
                                <button type="submit" title="<spring:message code="nextQuote"/>" class="btn-next"><spring:message code="next" /></button>
                            </c:otherwise>
                        </c:choose>
					</div>
				</form:form>
			</div>
			
		</div>
	 
	    <div class="clear">&nbsp;</div>
	 
	</div>
</div>
	<script type="text/javascript">
	var isAdmin = false;
	<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
		isAdmin = true;
	</c:if>
    var implementationLbl = '<spring:message code="quote.implementation" />'; 
    var monthlyLbl = '<spring:message code="quote.monthly" />';

	</script>
    <script type="text/javascript" src="js/entryGTNQuote.js"></script>

