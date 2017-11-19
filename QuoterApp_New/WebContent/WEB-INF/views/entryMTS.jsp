<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>


<div class="content-detail">
	<div class="use-sidebar sidebar-at-left" id="entry">
	    
	    <tiles:insertAttribute name="sidebar" />
	    
		<div class="content">
			<form:form commandName="command" action="entryMTS.htm" method="post">
				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="nextMTS"/>" class="btn-next"><spring:message code="next" /></button>
                    <input type="hidden" id="save" name="save"/>
				</div>
				<div class="content-box mts-input">
					<div class="block-header"><spring:message code="mts.inputs" /></div>
					<label><spring:message code="mts.annualLoadCount" /> <input type="text" name="annualLoadCount" class="numeric" value="${command.annualLoadCount}"/></label>
				</div>
				<div class="content-box">
					<div class="block-header"><spring:message code="mts.scopeInfluences" /></div>
					
					<c:forEach items="${command.categorizedQuoteMTSScopeQuestions}" var="scopeCategory" varStatus="statusCategory">
						<h3>${scopeCategory.category.description}</h3>
						<table class="width100">
							<tbody>
								<c:forEach items="${scopeCategory.categoryQuestions}" var="categoryQuestion" varStatus="statusQuestion">
									<tr>
										<td>
					 						<select name="categorizedQuoteMTSScopeQuestions[${statusCategory.index}].categoryQuestions[${statusQuestion.index}].answer" class="">
												<option value=""      <c:if test="${empty categoryQuestion.answer}">selected='selected'</c:if>></option>
												<c:forEach items="${scopeAnswers}" var="answer">
													<option value="${answer.name}"  <c:if test="${categoryQuestion.answer == answer.name}">selected='selected'</c:if>><spring:message code="${answer.name}" /></option>
												</c:forEach>
											</select>
											<c:if test="${categoryQuestion.quoteAnswer != null}">
												<input type="hidden" name="categorizedQuoteMTSScopeQuestions[${statusCategory.index}].categoryQuestions[${statusQuestion.index}].quoteAnswer.id" value="${categoryQuestion.quoteAnswer.id}"/>
											</c:if>
											<input type="hidden" name="categorizedQuoteMTSScopeQuestions[${statusCategory.index}].categoryQuestions[${statusQuestion.index}].question.id" value="${categoryQuestion.question.id}"/>
											<input type="hidden" name="categorizedQuoteMTSScopeQuestions[${statusCategory.index}].categoryQuestions[${statusQuestion.index}].question.baselineScopeImpact" value="${categoryQuestion.question.baselineScopeImpact}"/>
											<input type="hidden" name="categorizedQuoteMTSScopeQuestions[${statusCategory.index}].categoryQuestions[${statusQuestion.index}].question.headcountImpact" value="${categoryQuestion.question.headcountImpact}"/>
										</td>
										<td>
										<c:out value="${categoryQuestion.question.question}"/>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${!statusCategory.last}"><hr /></c:if>
					</c:forEach>
					
				</div>
				<div class="content-box mini-box float-l">
					<div class="block-header"><spring:message code="mts.team.description" /></div>
					<table id="team-composition">
						<thead>
							<tr>
								<th><spring:message code="mts.team.managed" /></th>
								<th><spring:message code="mts.team.associates" /></th>
								<th><spring:message code="mts.team.additional" /></th>
								<th><spring:message code="mts.team.total" /></th>
							</tr>
						</thead>				
						<tbody>
							<c:forEach items="${command.rolesForQuote}" var="rolesForQuote" varStatus="statusRole">
								<tr>
									<td>${rolesForQuote.role.description} <input type="hidden" name="rolesForQuote[${statusRole.index}].quoteRoleCost.id" value="${rolesForQuote.quoteRoleCost.id}" />
									<input type="hidden" name="rolesForQuote[${statusRole.index}].quoteRoleCost.mtsRoleCost.id" value="${rolesForQuote.quoteRoleCost.mtsRoleCost.id}" />
									<input type="hidden" name="rolesForQuote[${statusRole.index}].quoteRoleCost.mtsRole.id" value="${rolesForQuote.quoteRoleCost.mtsRole.id}" />
									</td>
									<td><fmt:formatNumber value="${rolesForQuote.quoteRoleCost.roleCountCalculated}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
									<td><input type="text" id="roleCountAdditional${statusRole.index}" name="rolesForQuote[${statusRole.index}].quoteRoleCost.roleCountAdditional" class="width50p right" value="<fmt:formatNumber value="${rolesForQuote.quoteRoleCost.roleCountAdditional}" type="number" maxFractionDigits="2" minFractionDigits="2"/>"/></td>
									<td><fmt:formatNumber value="${rolesForQuote.quoteRoleCost.roleCountCalculated + rolesForQuote.quoteRoleCost.roleCountAdditional}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="content-box mini-box float-r">
					<div class="block-header"><spring:message code="mts.general.description" /></div>
					<div class="title"><spring:message code="mts.general.parameters" /></div>
					<div class="mts-general-item"><spring:message code="mts.general.weeklyLoad" />&nbsp;<span id="weekly-load"><fmt:formatNumber value="${sessionScope.editQuote.quoteMTSCosts.baselineWeeklyLoadCount}" type="number"/></span></div>
					<div class="block-header mts-costs">
						<div class="title"><spring:message code="mts.general.costs" /></div>
						<table class="mts-general-item">
							<tbody>
								<tr>
									<td><spring:message code="mts.general.monthlyPrice" />: </td>
									<td><div id="monthly-price" class="right red font-bold"><fmt:formatNumber value="${sessionScope.editQuote.quoteMTSCosts.monthlyTotal}" type="currency"/></div></td>
								</tr>
								<tr>
									<td><spring:message code="mts.general.annualPrice" />: </td>
									<td><div id="annual-price" class="right red font-bold"><fmt:formatNumber value="${sessionScope.editQuote.quoteMTSCosts.anualTotal}" type="currency"/></div></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="clear"></div>

				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="nextMTS"/>" class="btn-next"><spring:message code="next" /></button>
				</div>
			</form:form>
		</div>
	</div>

	<div class="clear"></div>
</div>
<script type="text/javascript" src="js/entryMTS.js"></script>