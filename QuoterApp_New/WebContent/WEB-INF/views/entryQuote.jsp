<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

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
					
					<div class="product-category-tables">

					<c:set var="lastCategory" value=""></c:set>
					
					<c:forEach items="${command.categorizedQuoteCostItems}" var="quoteCostItem" varStatus="quoteCostItemStatus">
						<div class="content-box quote-box">
							<div class="block-header">${quoteCostItem.productCategory.description} <spring:message code="quote.quote" /></div>
							<div class="block-table">
								<table id="quote-table-${quoteCostItem.productCategory.id}">
									<thead>
										<tr>
											<th><spring:message code="quote.enable" /></th>
											<th><spring:message code="quote.special" /></th>
											<th><spring:message code="quote.category" /></th>
											<th><spring:message code="quote.functionality" /></th>
											<th>enabled-internal</th>
											<th>impl-internal</th>
											<th>mon-internal</th>
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
                                                    <c:choose>
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
													</c:if>
												</td>
												<td>
													<select id="special-${entryCostItem.costItem.id}" name="categorizedQuoteCostItems[${quoteCostItemStatus.index}].entryCostItems[${entryCostItemStatus.index}].special" >
														<option value=""></option>
														<c:forEach var="entry" items="${specialValues}" >
									    			         <option value="${entry.name}" <c:if test="${entryCostItem.special == entry.name}">selected</c:if>><spring:message code="${entry.name}" /></option>   
									    			    </c:forEach>
														
													</select>
												</td>
   												<td>
   													<c:if test="${lastCategory != entryCostItem.costItem.itemCategory.description}">
   														<c:set var="lastCategory" value="${entryCostItem.costItem.itemCategory.description}"/>
   														<c:out value="${entryCostItem.costItem.itemCategory.description}"/>
   													</c:if>
   												</td>
												<td class="functionality"><span <c:if test="${entryCostItem.costItem.descriptionDetail != '' && entryCostItem.costItem.descriptionDetail != null}">class="tooltip" title="${entryCostItem.costItem.descriptionDetail}"</c:if>>${entryCostItem.costItem.description}</span></td>
												
												<td><c:if test="${entryCostItem.enabled == true}">true</c:if></td> <!-- internal -->
												<td>${entryCostItem.implCost == 'NaN' ? '<span class="error" title=\''.concat(entryCostItem.implQuoteCostError).concat('\'>').concat(entryCostItem.implQuoteCostErrorDisplay).concat('</span>') : entryCostItem.implCost }</td> <!-- internal -->
												<td>${entryCostItem.monthlyCost == 'NaN' ? '<span class="error" title=\''.concat(entryCostItem.monthlyQuoteCostError).concat('\'>').concat(entryCostItem.monthlyQuoteCostErrorDisplay).concat('</span>') : entryCostItem.monthlyCost}</td> <!-- internal -->
												<!-- entryCostItem.implQuoteCostError != null && entryCostItem.monthlyQuoteCostError != null -->
												
												<c:if test="${user.priceVisibility == VISIBILITY_ALL}">
													<td>
														<c:if test="${entryCostItem.enabled == true}">
															<c:choose>
																<c:when test="${entryCostItem.implCost == 'NaN'}">
																	<span class="error" title='${entryCostItem.implQuoteCostError}'>${entryCostItem.implQuoteCostErrorDisplay}</span>
																</c:when>
																<c:otherwise>
																	<c:if test="${entryCostItem.implCost > 0}">
																		<fmt:formatNumber value="${entryCostItem.implCost}" type="number" maxFractionDigits="0"/>
																	</c:if>
																</c:otherwise>
															</c:choose>
														</c:if>
													</td>
													<td>
														<c:if test="${entryCostItem.enabled == true}">
															<c:choose>
																<c:when test="${entryCostItem.monthlyCost == 'NaN'}">
																	<span class="error" title='${entryCostItem.monthlyQuoteCostError}'>${entryCostItem.monthlyQuoteCostErrorDisplay}</span>
																</c:when>
																<c:otherwise>
																	<c:if test="${entryCostItem.monthlyCost > 0}">
																		<fmt:formatNumber value="${entryCostItem.monthlyCost}" type="number" maxFractionDigits="0"/>
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
												<th class="right" colspan="7"><spring:message code="quote.subtotal" /></th>
												<th class="right" colspan="1">0.0</th>
												<th class="right" colspan="1">0.0</th>
											</tr>
										</tfoot>
									</c:if>
								</table>
							</div>
		
						</div>
					</c:forEach>
					</div>
										
		    		<div class="clear"></div>
		    		<div class="clear foot-note"><i class="red">*&nbsp;<spring:message code="NotEmpty" /></i></div>
		    		
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
    <script type="text/javascript" src="js/entryQuote.js"></script>

