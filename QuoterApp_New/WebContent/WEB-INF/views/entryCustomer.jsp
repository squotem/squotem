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
			<form:form commandName="command" action="entryCustomer.htm" method="post">
				<div class="save-quote">
					<!-- <c:choose>
						<c:when test="${empty sessionScope.editQuote}">
					  	    <button type="submit" title="<spring:message code="createCustomer"/>"><spring:message code="saveNext" /></button>
					  	</c:when>
				        <c:otherwise> 
				        	<button type="submit" title="<spring:message code="saveCustomer"/>"><spring:message code="saveNext" /></button>
				        </c:otherwise>
		            </c:choose> -->
		            <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
		            <button type="submit" title="<spring:message code="nextCustomer"/>" class="btn-next"><spring:message code="next" /></button>
		            <input type="hidden" id="save" name="save"/>
				</div>
				
				<div class="matrix">
		    		<form:label path="matrix"><spring:message code="customer.information.pricingModel" /></form:label>
		    	    <form:select path="matrix.id">
	    			    <c:forEach var="matrix" items="${matrices}" >
	    			         <form:option value="${matrix.id}">${matrix.name}</form:option>   
	    			    </c:forEach>
	    			</form:select>  
				</div>
				
			    <form:hidden path="id"/>
			    <form:hidden path="oldMatrix.id"/>
				<div class="content-box customer-box">
					<div class="block-header"><spring:message code="customer.description" /></div>

					<table class="width100">
						<tr>
							<td class="width30">
					    		<form:label path="customer.id"><spring:message code="customer.selectExisting" /></form:label>
					    	</td>
					    	<td>
					    	    <form:select path="customer.id">
									<form:option value="-1"><spring:message code="customer.createNew" /></form:option>
				    			    <c:forEach var="entry" items="${customers}" >
				    			         <form:option value="${entry.id}">${entry.name}</form:option>   
				    			    </c:forEach>
				    			</form:select>
				    			<!-- this should be temporal and be replaced by an ajax call -->
				    			<script type="text/javascript">
				    			var customers = new Array();
				    			    <c:forEach var="entry" items="${customers}" >
					    			    customers["${entry.id}"]                = {};    
					    			    customers["${entry.id}"].id             = ${entry.id};   
				    			        customers["${entry.id}"].name           = "${entry.name}";
				    			        customers["${entry.id}"].customerType   = "${entry.customerType}";
				    			        customers["${entry.id}"].businessSector = "${entry.businessSector.description}";   
				    			        customers["${entry.id}"].city           = "${entry.city}";   
				    			        customers["${entry.id}"].state          = "${entry.state.stateName}";
				    			        customers["${entry.id}"].countryCode    = "${entry.country.countryCode}";
				    			        customers["${entry.id}"].country        = "${entry.country.countryName}";
				    			        customers["${entry.id}"].projectSponsor = "${entry.projectSponsor}";   
				    			        customers["${entry.id}"].sponsorPhone   = "${entry.sponsorPhone}";   
				    			    </c:forEach>
				    			var states = new Array();
			    			    	<c:forEach var="entry" items="${states}" >
			    			    		if (typeof states["${entry.countryCode}"] == 'undefined')
			    			    			states["${entry.countryCode}"] = new Array();
			    			    		states["${entry.countryCode}"].push({shortName: "${entry.stateShortName}", name:"${entry.stateName}"});
			    			    	</c:forEach>
			    			    	
			    			    var matrices = new Array();
			  						<c:forEach var="matrix" items="${matrices}" >
			  							matrices["${matrix.id}"]        = {};
			  							matrices["${matrix.id}"].id     = ${matrix.id};
			  							matrices["${matrix.id}"].hasMts = ${matrix.mtsMatrixId != null ? 'true' : 'false'};   
				    			    </c:forEach>

				    			</script>
				    			<!-- until here -->
							</td>
						</tr>
						<tr>
							<td>
								<form:label path="customer.name"><spring:message code="customer.customerName" /><span class="red">&nbsp;*</span></form:label>
					    	</td>
					    	<td>
								<form:input path="customer.name" maxlength="50"/> 
								<label id="name-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customerTypeStr"><spring:message code="customer.customerType" /></form:label>
					    	</td>
					    	<td>
								<form:select path="customerTypeStr">
									<c:forEach var="entry" items="${customerTypes}" >
				    			         <option value="${entry.name}"><spring:message code="${entry.name}" /></option>   
				    				</c:forEach>
				    			</form:select> 
								<label id="customerType-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.businessSector.id"><spring:message code="customer.businessSector" /></form:label>
					    	</td>
					    	<td>
								<form:select path="customer.businessSector.id">
									<c:forEach var="entry" items="${businessSectors}" >
				    			         <form:option value="${entry.id}">${entry.description}</form:option>   
				    				</c:forEach>
				    			</form:select> 
								<label id="businessSector-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<label ><spring:message code="customer.country" /></label>
					    	</td>
					    	<td>
								<form:select path="customer.country.countryCode">
									<form:option value=""></form:option>
									<c:forEach var="entry" items="${countries}" >
				    			         <form:option value="${entry.countryCode}">${entry.countryName}</form:option>   
				    				</c:forEach>
				    			</form:select> 
								<label id="country-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.state.stateShortName"><spring:message code="customer.state" /></form:label>
					    	</td>
					    	<td>
								<form:select path="customer.state.stateShortName">
				    			</form:select> 
								<label id="state-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.city"><spring:message code="customer.city" /></form:label>
					    	</td>
					    	<td>
								<form:input path="customer.city" maxlength="150"/> 
								<label id="city-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.projectSponsor"><spring:message code="customer.projectSponsor" /></form:label>
					    	</td>
					    	<td>
								<form:input path="customer.projectSponsor" maxlength="50"/> 
								<label id="projectSponsor-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.sponsorPhone"><spring:message code="customer.sponsorPhone" /></form:label>
					    	</td>
					    	<td>
								<form:input path="customer.sponsorPhone" maxlength="50"/> 
								<label id="sponsorPhone-lbl" class="hide"></label>
					    	</td>
						</tr>
						<tr>
					    	<td>
								<form:label path="customer.sfdcOpportunity"><spring:message code="customer.sfdcOpportunity" /></form:label>
					    	</td>
					    	<td>
								<form:input path="customer.sfdcOpportunity" maxlength="100"/> 
								<label id="sfdcOpportunity-lbl" class="hide"></label>
					    	</td>
						</tr>
					</table>
 				
				</div>
		
				<div class="content-box ll-box">
					<div class="block-header"><spring:message code="customer.blujaysolution.description" /></div>

					<table class="width100">
						<tr>
							<td class="width30">
					    		<form:label path="salesDirector"><spring:message code="customer.leanlogistics.salesDirector" /></form:label>
					    	</td>
					    	<td>
								<!-- form :input path="salesDirector" maxlength="50"/ -->
	                            <form:select path="salesDirector.id">
	                                <c:forEach var="usr" items="${users}" >
	                                     <form:option value="${usr.id}">${usr.firstName}&nbsp;${usr.lastName}</form:option>   
	                                </c:forEach>
	                            </form:select>
	                            <!-- ${usr.id == command.salesDirector.id ? "selected='selected'" : "" } -->
							</td>
						</tr>
						<tr>
							<td>
					    		<form:label path="businessConsultant"><spring:message code="customer.leanlogistics.businessConsultant" /></form:label>
					    	</td>
					    	<td>
								<form:input path="businessConsultant" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<td>
					    		<form:label path="partnerReferenced"><spring:message code="customer.leanlogistics.partnerReferenced" /></form:label>
					    	</td>
					    	<td>
								<form:input path="partnerReferenced" maxlength="50"/>
							</td>
						</tr>
					</table>
				</div>
				
				<div class="content-box quote-type-box">
					<div class="block-header"><spring:message code="customer.information.description" /></div>
						<table class="width100">
						<tr>
						<c:forEach var="productCategory" items="${productCategories}" >
											<input type="hidden" name="productCategoryIds" value="${productCategory.id}"/>
										</c:forEach>
						    	<td>
										
										<!-- label><form:checkbox path="hasMts" value="${productCategory.hasMts}"/><spring:message code="customer.information.mts" /></label-->
								</td>
						</tr>
							<tr>
								<td> 
						    		<form:label path="effectiveDate"><spring:message code="customer.information.effectiveDate" /></form:label>
						    	</td>
						    	<td>
						    		<fmt:formatDate pattern="MM/dd/yyyy" value="${command.effectiveDate}" var="myEffectiveDate"/>
						    		<form:input path="effectiveDate" value="${myEffectiveDate}"/>
								</td>
							</tr>
							<tr>
								<td>
						    		<form:label path="expireDate"><spring:message code="customer.information.expireDate" /></form:label>
						    	</td>
						    	<td>
						    		<fmt:formatDate pattern="MM/dd/yyyy" value="${command.expireDate}" var="myExpireDate"/>
						    		<form:input path="expireDate" value="${myExpireDate}"/>
								</td>
							</tr>
						</table>
						
				</div>
				
	    		<div class="clear"><i class="red">*&nbsp;<spring:message code="NotEmpty" /></i></div>
	    		
				<div class="save-quote">
					<!-- <c:choose>
						<c:when test="${empty sessionScope.editQuote}">
					  	    <button type="submit" title="<spring:message code="createCustomer"/>"><spring:message code="saveNext" /></button>
					  	</c:when>
				        <c:otherwise> 
				        	<button type="submit" title="<spring:message code="saveCustomer"/>"><spring:message code="saveNext" /></button>
				        </c:otherwise>
		            </c:choose> -->
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="nextCustomer"/>" class="btn-next"><spring:message code="next" /></button>
				</div>
			</form:form>
			
		</div>
	 
	    <div class="clear"></div>
	 
	</div>
</div>

<script type="text/javascript" src="js/entryCustomer.js"></script>
