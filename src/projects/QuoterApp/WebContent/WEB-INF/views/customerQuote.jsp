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
			<form:form commandName="command" action="customerQuote.htm" method="post">
				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="refreshQuote"/>" class="btn-refresh"><spring:message code="refresh" /></button>
                    <input type="hidden" id="save" name="save"/>
				</div>

                <div class="terms"><spring:message code="customer.information.terms" />:&nbsp;<span id="terms">${empty sessionScope.editQuote ? 0 : sessionScope.editQuote.terms}</span>&nbsp;<spring:message code="customer.information.termsMonths" /></div>

                <tiles:insertAttribute name="pricing" />
				
                <div class="content-box">
                    <div class="block-header"><spring:message code="customerQuote.pricingContingent" /></div>
                    <table id="" class="pricing-contingent">
                        <tbody>
                            <tr>
                                <td>1.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn1" /></td>
                            </tr>
                            <tr>
                                <td>2.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn2" /></td>
                            </tr>
                            <tr>
                                <td>3.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn3" /></td>
                            </tr>
                            <tr>
                                <td>4.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn4" /></td>
                            </tr>
                            <tr>
                                <td>5.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn5" /></td>
                            </tr>
                            <tr>
                                <td>6.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn6" /></td>
                            </tr>
                            <tr>
                                <td>7.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn7" /></td>
                            </tr>
                            <tr>
                                <td>8.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn8" /></td>
                            </tr>
                            <tr>
                                <td>9.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn9" /></td>
                            </tr>
                            <tr>
                                <td>10.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn10" /></td>
                            </tr>
                            <tr>
                                <td>11.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn11" /></td>
                            </tr>
                            <tr>
                                <td>12.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn12" /></td>
                            </tr>
                            <tr>
                                <td>13.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn13" /></td>
                            </tr>
                            <tr>
                                <td>14.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn14" /></td>
                            </tr>
                            <tr>
                                <td>15.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn15" /></td>
                            </tr>
                            <tr>
                                <td>16.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn16" /></td>
                            </tr>
                            <tr>
                                <td>17.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn17" /></td>
                            </tr>
                            <tr>
                                <td>18.</td>
                                <td><spring:message code="customerQuote.pricingContingentLn18" /></td>
                            </tr>
                        </tbody>
                    </table>
                    
                </div>
                
				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="refreshQuote"/>" class="btn-refresh"><spring:message code="refresh" /></button>
				</div>
			</form:form>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript" src="js/customerQuote.js"></script>
