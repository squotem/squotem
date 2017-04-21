<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

		
		<c:set var="boolTotal" value="false"></c:set>
		<c:forEach items="${command.categorizedQuoteMetrics}" var="category" varStatus="catStatus">
			<div class="content-box metric-box">
				<div class="block-header">${category.description}</div>
				<input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].optionType" value="${category.optionType}"/>
				
				<table class="width100">
					<c:set var="total" value="0"></c:set>
					<c:forEach items="${category.quoteMetrics}" var="quoteMetric">
						<c:if test="${(category.optionType == 'TOTAL_AND_PCT' || category.optionType == 'TOTAL') && (quoteMetric.metric.dataType == 'NUMERIC' || quoteMetric.metric.dataType == 'BOOL_PLUS_NUMERIC')}">
							<c:if test="${!quoteMetric.metric.isTotal}">
								<c:set var="total" value="${total + (empty quoteMetric.metricValue ? 0 : quoteMetric.metricValue) }"></c:set>
							</c:if>
						</c:if>
					</c:forEach>
				    <c:forEach items="${category.quoteMetrics}" var="quoteMetric" varStatus="metricStatus">
				    	<tr>
				    		<td class="width40">
				    			<label class="${quoteMetric.metric.isTotal ? 'total' : ''}">${quoteMetric.metric.description}</label>
				    			<c:if test="${quoteMetric.required}">
				    				<span class="red">&nbsp;*</span>
				    			</c:if>
				    		</td>
				    		<td>
				    		    <input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].id" value="${quoteMetric.id}"/>
				    		    <input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metric.id" value="${quoteMetric.metric.id}"/>
				    		    <input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metric.mnemonic" value="${quoteMetric.metric.mnemonic}"/>
				    		    <input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metric.dataType" value="${quoteMetric.metric.dataType}"/>
				    		    <input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metric.isTotal" value="${quoteMetric.metric.isTotal}"/>
				    			
				    			<c:choose>
				    				<c:when test="${quoteMetric.metric.dataType == 'BOOLEAN'}">
			    						<select name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metricValue" class="${quoteMetric.required ? 'required' : ''}">
											<option value="" <c:if test="${quoteMetric.metricValue == null}">selected</c:if>></option>
											<option value="TRUE" <c:if test="${quoteMetric.metricValue == true}">selected</c:if>><spring:message code="quote.yes" /></option>
											<option value="FALSE" <c:if test="${quoteMetric.metricValue == false}">selected</c:if>><spring:message code="quote.no" /></option>
										</select>
				    				</c:when>
				    				<c:when test="${quoteMetric.metric.dataType == 'BOOL_PLUS_NUMERIC'}">
			    						<select name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].booleanValue" class="${quoteMetric.required ? 'required' : ''} boolPlus">
											<option value="" <c:if test="${quoteMetric.booleanValue == null}">selected</c:if>></option>
											<option value="TRUE" <c:if test="${quoteMetric.booleanValue == true}">selected</c:if>><spring:message code="quote.yes" /></option>
											<option value="FALSE" <c:if test="${quoteMetric.booleanValue == false}">selected</c:if>><spring:message code="quote.no" /></option>
										</select>
										<!-- here should be a hidden input for the second part -->
										<span class="${quoteMetric.booleanValue != true ? 'hide' : ''} float-r">
											<label class="">${quoteMetric.metric.secDescription}<span class="red">&nbsp;*</span></label>
						    				<input type="text" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metricValue" maxlength="12" class="right numeric required" value="${quoteMetric.metricValue}" />
										</span>
										
										<c:if test="${(category.optionType == 'TOTAL_AND_PCT' || category.optionType == 'TOTAL')}">
											<c:set var="boolTotal" value="true"></c:set>
										</c:if>
									</c:when>
				    				<c:when test="${quoteMetric.metric.dataType == 'BOOL_PLUS_STRING'}">
			    						<select name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].booleanValue" class="${quoteMetric.required ? 'required' : ''} boolPlus float-l">
											<option value="" <c:if test="${quoteMetric.booleanValue == null}">selected</c:if>></option>
											<option value="TRUE" <c:if test="${quoteMetric.booleanValue == true}">selected</c:if>><spring:message code="quote.yes" /></option>
											<option value="FALSE" <c:if test="${quoteMetric.booleanValue == false}">selected</c:if>><spring:message code="quote.no" /></option>
										</select>
										<!-- here should be a hidden input for the second part -->
										<span class="${quoteMetric.booleanValue != true ? 'hide' : ''} float-r inline-flex width90">
											<label class="">${quoteMetric.metric.secDescription}<span class="red">&nbsp;*</span></label>
						    				<input type="text" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metricValue" maxlength="250" class="width85 required" value="${quoteMetric.metricValue}" />
										</span>
									</c:when>
				    				<c:otherwise>
						    			<input type="text" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metricValue" maxlength="${quoteMetric.metric.dataType == 'NUMERIC' ? '12' : '250'}" class="${quoteMetric.metric.dataType == 'NUMERIC' ? 'right numeric' : 'string'} ${quoteMetric.metric.isTotal ? 'total' : ''} ${quoteMetric.required ? 'required' : ''} ${(quoteMetric.metric.isTotal && boolTotal) ? 'float-r' : ''} " 
						    			    <c:choose>
							    				<c:when test="${quoteMetric.metric.isTotal}">
							    					value="<fmt:formatNumber value="${total}" type="number"/>" disabled="disabled" isTotal="1" class="total"
							    				</c:when>
							    			    <c:otherwise>
							    					value="${quoteMetric.metricValue}"
							    				</c:otherwise>
							    			</c:choose> 
						    			/>
				    				</c:otherwise>
				    			</c:choose>
				    		</td>
							<c:if test="${category.optionType == 'TOTAL_AND_PCT'}">
							<td class="${boolTotal ? 'width5' : 'width30' } ">
								<div class="right width10">
									<label class="${quoteMetric.metric.isTotal ? 'total' : ''}">
									<c:if test="${total > 0 || quoteMetric.metric.isTotal}">
					    			    <c:choose>
						    				<c:when test="${quoteMetric.metric.isTotal}">
						    					100% 
						    				</c:when>
						    			    <c:otherwise>
						    			    	<c:if test="${(quoteMetric.metricValue/total) > 0}">
													<fmt:formatNumber value="${quoteMetric.metricValue/total}" type="percent"/>
												</c:if> 
						    				</c:otherwise>
						    			</c:choose> 
									</c:if>
									</label>
								</div>
								<input type="hidden" name="categorizedQuoteMetrics[${catStatus.index}].quoteMetrics[${metricStatus.index}].metricPct" 
				    			    <c:choose>
					    				<c:when test="${quoteMetric.metric.isTotal}">
					    					value="100" 
					    				</c:when>
					    			    <c:otherwise>
											value="<fmt:formatNumber maxFractionDigits="0" value="${total > 0 ? (quoteMetric.metricValue/total)*100 : 0}" type="number"/>" 
					    				</c:otherwise>
					    			</c:choose> 
								/>
							</td>
							</c:if>
				    	</tr>
				    </c:forEach>
				</table> 
		    </div>
	    </c:forEach>
		
   		<div class="clear foot-note"><i class="red">*&nbsp;<spring:message code="NotEmpty" /></i></div>
	    		
<script type="text/javascript" src="js/categorizedQuoteMetrics.js"></script>
