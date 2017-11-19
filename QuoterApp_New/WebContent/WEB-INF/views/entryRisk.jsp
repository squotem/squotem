<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>


<div class="content-detail">
	<div class="use-sidebar sidebar-at-left" id="entry">
	    
	    <tiles:insertAttribute name="sidebar" />
	    
		<div class="content risk-list">
			<form:form commandName="command" action="entryRisk.htm" method="post">

				<div class="save-quote">
			  	    <button type="submit" title="<spring:message code="saveMetric"/>"><spring:message code="saveNext" /></button>
				</div>
				
				<h3><spring:message code="risk.indicateAreas" /></h3>

				<c:forEach items="${command.categorizedQuoteRiskAnalysisItems}" var="category" varStatus="catStatus">
					<input type="hidden" name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].id" value="${category.id}"/>
					<div class="content-box">
						<div class="block-header"><c:out value="${category.description}"></c:out></div>
						<ul>
						    <c:forEach items="${category.quoteRiskAnalysisItems}" var="riskItem" varStatus="riskStatus">
						    	<li>
						    		<div>
										<input type="hidden" name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].quoteRiskAnalysisItems[${riskStatus.index}].id" value="${riskItem.id}"/>
										<input type="hidden" name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].quoteRiskAnalysisItems[${riskStatus.index}].riskAnalysis.id" value="${riskItem.riskAnalysis.id}"/>

						    			<select name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].quoteRiskAnalysisItems[${riskStatus.index}].enabled" class="">
											<option value="" <c:if test="${riskItem.enabled == null}">selected</c:if>></option>
											<option value="TRUE" <c:if test="${riskItem.enabled == true}">selected</c:if>><spring:message code="quote.yes" /></option>
											<option value="FALSE" <c:if test="${riskItem.enabled == false}">selected</c:if>><spring:message code="quote.no" /></option>
										</select>
						    			<c:out value="${riskItem.riskAnalysis.description}"></c:out>
						    			<span class="float-r">
											<a href="#" class="riskCollapse">
												<span class="button ui-state-default ui-corner-all">
													<span class="ui-icon ui-icon-plus"></span>
												</span>
											</a>
											<span class="icon-script ${empty riskItem.comment ? 'hide' : ''}">
												<span class="ui-icon ui-icon-script"></span>
											</span>
										</span>
						    		</div>
						    		
					    			<div class="risk-comment">
										<spring:message code="risk.riskLevel"/>&nbsp;<label class="risk-level">(${empty riskItem.riskLevel ? 1 : riskItem.riskLevel})</label>:<div class="risk-slider"></div>
										<input type="hidden" name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].quoteRiskAnalysisItems[${riskStatus.index}].riskLevel" value="${empty riskItem.riskLevel ? 1 : riskItem.riskLevel}"/>
										
										
										<textarea rows="5" name="categorizedQuoteRiskAnalysisItems[${catStatus.index}].quoteRiskAnalysisItems[${riskStatus.index}].comment" class="width100 riskComment hide"><c:out value="${riskItem.comment}"></c:out></textarea>
									</div>
						    	</li>
						    </c:forEach>
						</ul>
					</div>
				
				</c:forEach>
				

				<div class="save-quote">
			  	    <button type="submit" title="<spring:message code="saveMetric"/>"><spring:message code="saveNext" /></button>
				</div>
			</form:form>
		</div>
		
	    <div class="clear">&nbsp;</div>

	</div>
</div>
<script type="text/javascript" src="js/entryRisk.js"></script>