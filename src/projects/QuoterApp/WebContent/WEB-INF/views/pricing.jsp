<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>


                <!-- SET VALUES -->
                <c:set var="budgetaryImpl" value="${command.budgetaryImplCost}"></c:set> 
                <c:set var="budgetaryMon"  value="${command.budgetaryMonthlyCost}"></c:set> 
                <c:set var="budgetaryProc" value="50000"></c:set>
                 
                <input type="hidden" name="implCosts[0].id" value="${command.implCosts[0].id}"/>
                <input type="hidden" name="implCosts[0].productCategory.id" value="${command.implCosts[0].productCategory.id}"/>
                <c:set var="marketImpl" value="${command.marketImplCost}"></c:set> <!-- command.implCosts[0].marketQuote -->
                <c:set var="marketMon"  value="${command.marketMonthlyCost}"></c:set> <!-- command.monthlyCosts[0].marketQuote -->
                <c:set var="marketProc" value="50000"></c:set> 

                <input type="hidden" name="monthlyCosts[0].id" value="${command.monthlyCosts[0].id}"/>
                <input type="hidden" name="monthlyCosts[0].productCategory.id" value="${command.monthlyCosts[0].productCategory.id}"/>
                <c:set var="quoteImpl" value="${command.implCosts[0].customerQuote}"></c:set> 
                <c:set var="quoteMon"  value="${command.monthlyCosts[0].customerQuote}"></c:set> 
                <c:set var="quoteProc" value="50000"></c:set> 
                
                <c:set var="firstYearFeeMonths" value="12"></c:set>
                <c:set var="contractLength" value="${command.terms - 4}"></c:set>
                
                <c:set var="budgetarySubtotal" value="${budgetaryImpl + (budgetaryMon * firstYearFeeMonths)}"></c:set>
                <c:set var="marketSubtotal"    value="${marketImpl + (marketMon * firstYearFeeMonths)}"></c:set>
                <c:set var="quoteSubtotal"     value="${quoteImpl + (quoteMon * firstYearFeeMonths)}"></c:set>
                
                <c:set var="budgetaryContractSubtotal" value="${budgetaryImpl + (budgetaryMon * contractLength)}"></c:set>
                <c:set var="marketContractSubtotal"    value="${marketImpl + (marketMon * contractLength)}"></c:set>
                <c:set var="quoteContractSubtotal"     value="${quoteImpl + (quoteMon * contractLength)}"></c:set>

                <c:set var="budgetaryCustomerSavings" value="${command.quoteMetrics['VA_CU_TOTAL']}"></c:set>
                <c:set var="marketCustomerSavings"    value="${command.quoteMetrics['VA_CU_TOTAL']}"></c:set>
                <c:set var="quoteCustomerSavings"     value="${command.quoteMetrics['VA_CU_TOTAL']}"></c:set>

                <c:set var="budgetaryLLSavings" value="${command.quoteMetrics['VA_BIZ_TOTAL']}"></c:set>
                <c:set var="marketLLSavings"    value="${command.quoteMetrics['VA_BIZ_TOTAL']}"></c:set>
                <c:set var="quoteLLSavings"     value="${command.quoteMetrics['VA_BIZ_TOTAL']}"></c:set>
                
                <c:set var="FUM"        value="${command.quoteMetrics['FS_TOTAL']}"></c:set>
                <c:set var="FUM_FACTOR" value="1000000"></c:set>
                
                <!--  -->

                <div class="content-box customer-quote">
                    <div class="block-header"><spring:message code="customerQuote.baselinePricing" /></div>
                    <table id="" class="analytics">
                        <thead>
                            <tr>
                                <th class="width25"></th>
                                <th class="text-r width10"><spring:message code="customerQuote.budgetary" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.market" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.quote" /></th>
                                <th class="text-r width10"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><spring:message code="customerQuote.implementation" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryImpl)}">
                                            <span class="red"><c:out value="${command.budgetaryImplCostError}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketImpl == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <fmt:formatNumber maxFractionDigits="0" groupingUsed="false" value="${quoteImpl}" var="num" type="number"/>
                                    <c:choose>
                                        <c:when test="${businessPlan}">
                                            <c:out value="${num}"></c:out>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="text-r width80p numeric" id="quoteImplementation" name="implCosts[0].customerQuote" value="${num}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.monthlyTechFee" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryMon == 'NaN'}">
                                            <span class="red"><c:out value="${command.budgetaryMonthlyCostError}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <fmt:formatNumber maxFractionDigits="0" groupingUsed="false" value="${quoteMon}" var="num" type="number"/>
                                    <c:choose>
                                        <c:when test="${businessPlan}">
                                            <c:out value="${num}"></c:out>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="text-r width80p numeric" id="quoteMonthlyFee" name="monthlyCosts[0].customerQuote" value="${num}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                            </tr>
                            <!-- tr>
                                <td><spring:message code="customerQuote.procurement" /></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${budgetaryProc}" type="number"/></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${marketProc}" type="number"/></td>
                                <td class="text-r"><input type="text" class="text-r numeric" name="quoteProcurement" value="${quoteProc}"/></td>
                            </tr-->
                        </tbody>
                    </table>
                </div>
                
                <div class="content-box customer-quote">
                    <div class="block-header"><spring:message code="customerQuote.dealAnalytics" /></div>
                    
                    <h3><spring:message code="customerQuote.firstYearFees" /></h3>
                    
                    <table id="first-year-fees" class="analytics">
                        <thead>
                            <tr>
                                <th class="width25"></th>
                                <th class="text-r width10"><spring:message code="customerQuote.budgetary" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalBudgetary" /></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.market" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalMarket" /></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.quote" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalQuote" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><spring:message code="customerQuote.implementation" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryImpl)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryImpl)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketImpl == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketImpl == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td></td>
                                <td class="text-r" id="firstQuoteImpl"><fmt:formatNumber maxFractionDigits="0" value="${quoteImpl}" type="number"/></td>
                                <td class="text-r" id="firstQuoteImplTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteImpl}" type="number"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.monthlyTechFee" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryMon * firstYearFeeMonths}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketMon * firstYearFeeMonths}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r" id="firstQuoteMon"><fmt:formatNumber maxFractionDigits="0" value="${quoteMon}" type="number"/></td>
                                <td class="text-r" id="firstQuoteMonTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteMon * firstYearFeeMonths}" type="number"/></td>
                            </tr>
                            <!-- tr>
                                <td><spring:message code="customerQuote.procurement" /></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${budgetaryProc}" type="number"/></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${budgetaryProc}" type="number"/></td>
                                <td></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${marketProc}" type="number"/></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${marketProc}" type="number"/></td>
                                <td></td>
                                <td class="text-r" id="firstQuoteProc"><fmt:formatNumber maxFractionDigits="0" value="${quoteProc}" type="number"/></td>
                                <td class="text-r" id="firstQuoteProcTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteProc}" type="number"/></td>
                            </tr-->
                            <tr>
                                <td><spring:message code="customerQuote.subtotal" /></td>
                                <td class="text-r"></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetarySubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetarySubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r"></td>
                                <td class="text-r" id="firstMarketSubTotal">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(marketSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r"></td>
                                <td class="text-r" id="firstQuoteSubTotal"><fmt:formatNumber maxFractionDigits="0" value="${quoteSubtotal}" type="number"/></td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <div class="text-r variance">
                        <span><i><spring:message code="customerQuote.varianceBudget" /></i> 
                            <b>
                                <span  
                                      <c:if test="${marketSubtotal > budgetarySubtotal}">
                                           class="green"
                                      </c:if>
                                      <c:if test="${marketSubtotal < budgetarySubtotal}">
                                           class="red"
                                      </c:if>
                                >
	                                <c:choose>
	                                    <c:when test="${Double.isNaN(budgetarySubtotal) || Double.isNaN(marketSubtotal)}">
	                                        <span class="error"><spring:message code="quote.error.missing" /></span>
	                                    </c:when>
	                                    <c:otherwise>
	                                        <fmt:formatNumber maxFractionDigits="0" value="${(budgetarySubtotal - marketSubtotal)/budgetarySubtotal}" type="percent"/>
	                                    </c:otherwise>
	                                </c:choose>
	                            </span>
                            </b>
                        </span>
                        <span>
                            <i><spring:message code="customerQuote.variaceMarket" /></i> 
                            <b>
                                <span id="firstMarketVariance" 
                                                <c:if test="${quoteSubtotal > marketSubtotal}">
                                                     class="green"
                                                </c:if>
                                                <c:if test="${quoteSubtotal < marketSubtotal}">
                                                     class="red"
                                                </c:if>
                                        >
                                            <c:choose>
                                                <c:when test="${Double.isNaN(marketSubtotal)}">
                                                    <span class="error"><spring:message code="quote.error.missing" /></span>
                                                </c:when>
                                                <c:when test="${quoteSubtotal > marketSubtotal}">
                                                    <fmt:formatNumber maxFractionDigits="0" value="${((marketSubtotal - quoteSubtotal)/marketSubtotal)*-1}" type="percent"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:formatNumber maxFractionDigits="0" value="${(marketSubtotal - quoteSubtotal)/marketSubtotal}" type="percent"/>
                                                </c:otherwise>
                                            </c:choose>
                                </span>
                            </b>
                        </span>
                    </div>
                    
                    
                    <h3><spring:message code="customerQuote.contractValue" /></h3>

                    <table id="" class="analytics">
                        <thead>
                            <tr>
                                <th class="width25"></th>
                                <th class="text-r width10"><spring:message code="customerQuote.budgetary" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalBudgetary" /></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.market" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalMarket" /></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.quote" /></th>
                                <th class="text-r width10"><spring:message code="customerQuote.totalQuote" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><spring:message code="customerQuote.implementation" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryImpl)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryImpl)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketImpl == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketImpl == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketImpl}" type="number"/>
                                        </c:otherwise>
                                    </c:choose> 
                                </td>
                                <td></td>
                                <td class="text-r" id="contractQuoteImpl"><fmt:formatNumber maxFractionDigits="0" value="${quoteImpl}" type="number"/></td>
                                <td class="text-r" id="contractQuoteImplTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteImpl}" type="number"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.monthlyTechFee" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryMon * contractLength}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketMon}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketMon == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketMon * contractLength}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r" id="contractQuoteMon"><fmt:formatNumber maxFractionDigits="0" value="${quoteMon}" type="number"/></td>
                                <td class="text-r" id="contractQuoteMonTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteMon * contractLength}" type="number"/></td>
                            </tr>
                            <!-- tr>
                                <td><spring:message code="customerQuote.procurement" /></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${budgetaryProc}" type="number"/></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${budgetaryProc}" type="number"/></td>
                                <td></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${marketProc}" type="number"/></td>
                                <td class="text-r"><fmt:formatNumber maxFractionDigits="0" value="${marketProc}" type="number"/></td>
                                <td></td>
                                <td class="text-r" id="contractQuoteProc"><fmt:formatNumber maxFractionDigits="0" value="${quoteProc}" type="number"/></td>
                                <td class="text-r" id="contractQuoteProcTot"><fmt:formatNumber maxFractionDigits="0" value="${quoteProc}" type="number"/></td>
                            </tr-->
                            <tr>
                                <td><spring:message code="customerQuote.subtotal" /></td>
                                <td class="text-r"></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetaryContractSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryContractSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r"></td>
                                <td class="text-r" id="contractMarketSubTotal">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(marketContractSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketContractSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td class="text-r"></td>
                                <td class="text-r" id="contractQuoteSubTotal"><fmt:formatNumber maxFractionDigits="0" value="${quoteContractSubtotal}" type="number"/></td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <div class="text-r variance">
                        <span>
                            <i><spring:message code="customerQuote.varianceBudget" /></i> 
                            <b>
                                <span  
                                      <c:if test="${marketContractSubtotal > budgetaryContractSubtotal}">
                                           class="green"
                                      </c:if>
                                      <c:if test="${marketContractSubtotal < budgetaryContractSubtotal}">
                                           class="red"
                                      </c:if>
                                >
	                                <c:choose>
	                                    <c:when test="${Double.isNaN(budgetaryContractSubtotal) || Double.isNaN(marketContractSubtotal)}">
	                                        <span class="error"><spring:message code="quote.error.missing" /></span>
	                                    </c:when>
	                                    <c:otherwise>
	                                        <fmt:formatNumber maxFractionDigits="0" value="${(budgetaryContractSubtotal - marketContractSubtotal)/budgetaryContractSubtotal}" type="percent"/>
	                                    </c:otherwise>
	                                </c:choose>
                                </span>
                            </b>
                        </span>
                        <span>
                            <i><spring:message code="customerQuote.variaceMarket" /></i> 
                            <b>
                                <span id="contractMarketVariance" 
                                                <c:if test="${quoteContractSubtotal > marketContractSubtotal}">
                                                     class="green"
                                                </c:if>
                                                <c:if test="${quoteContractSubtotal < marketContractSubtotal}">
                                                     class="red"
                                                </c:if>
                                        >
                                            <c:choose>
                                                <c:when test="${Double.isNaN(marketContractSubtotal)}">
                                                    <span class="error"><spring:message code="quote.error.missing" /></span>
                                                </c:when>
                                                <c:when test="${quoteContractSubtotal > marketContractSubtotal}">
                                                    <fmt:formatNumber maxFractionDigits="0" value="${((marketContractSubtotal - quoteContractSubtotal)/marketContractSubtotal)*-1}" type="percent"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:formatNumber maxFractionDigits="0" value="${(marketContractSubtotal - quoteContractSubtotal)/marketContractSubtotal}" type="percent"/>
                                                </c:otherwise>
                                            </c:choose>
                                </span>
                            </b>
                        </span>
                    </div>

                    <h3><spring:message code="customerQuote.customerValue" /></h3>
                    <table id="" class="analytics">
                        <thead>
                            <tr>
                                <th class="width25"></th>
                                <th class="text-r width10"><spring:message code="customerQuote.budgetary" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.market" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.quote" /></th>
                                <th class="text-r width10"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><spring:message code="customerQuote.savings" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryCustomerSavings == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryCustomerSavings}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketCustomerSavings == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketCustomerSavings}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="customerSavings"><fmt:formatNumber maxFractionDigits="0" value="${quoteCustomerSavings}" type="number"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.firstYearFees" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetarySubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetarySubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(marketSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="customerFirstYear"><fmt:formatNumber maxFractionDigits="0" value="${quoteSubtotal}" type="number"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.ratio" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryCustomerSavings == 'NaN' || Double.isNaN(budgetarySubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${budgetaryCustomerSavings/budgetarySubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketCustomerSavings == 'NaN' || Double.isNaN(marketSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${marketCustomerSavings/marketSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="customerRatio"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${quoteCustomerSavings/quoteSubtotal}" type="number"/></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="text-r variance">
                        <span> 
                            <c:choose>
                                <c:when test="${budgetaryCustomerSavings == 'NaN'}">
                                    <span class="error"><spring:message code="quote.error.missing" /></span>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber maxFractionDigits="0" value="${budgetaryCustomerSavings/(FUM * FUM_FACTOR)}" var="savingsPercent" type="percent"/>
                                    <fmt:formatNumber maxFractionDigits="0" value="${FUM}" var="totalFUM" type="number"/>
                                    
                                    <i><spring:message code="customerQuote.savingsBudget"/></i> <b><c:out value="${savingsPercent}"/></b> <i><spring:message code="customerQuote.savingsTotalFum"/></i> (<b>&#36;<c:out value="${totalFUM}"/>M</b>)
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    
                    <h3><spring:message code="customerQuote.leanLogisticsValue" /></h3>
                    <table id="" class="analytics">
                        <thead>
                            <tr>
                                <th class="width25"></th>
                                <th class="text-r width10"><spring:message code="customerQuote.budgetary" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.market" /></th>
                                <th class="text-r width10"></th>
                                <th>&nbsp;&nbsp;&nbsp;</th>
                                <th class="text-r width10"><spring:message code="customerQuote.quote" /></th>
                                <th class="text-r width10"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><spring:message code="customerQuote.savings" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryLLSavings == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetaryLLSavings}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketLLSavings == 'NaN'}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketLLSavings}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="llSavings"><fmt:formatNumber maxFractionDigits="0" value="${quoteLLSavings}" type="number"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.firstYearFees" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(budgetarySubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${budgetarySubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${Double.isNaN(marketSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="0" value="${marketSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="llFirstYear"><fmt:formatNumber maxFractionDigits="0" value="${quoteSubtotal}" type="number"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><spring:message code="customerQuote.ratio" /></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${budgetaryLLSavings == 'NaN' || Double.isNaN(budgetarySubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${budgetaryLLSavings/budgetarySubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r">
                                    <c:choose>
                                        <c:when test="${marketLLSavings == 'NaN' || Double.isNaN(marketSubtotal)}">
                                            <span class="error"><spring:message code="quote.error.missing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${marketLLSavings/marketSubtotal}" type="number"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="text-r" id="llRatio"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${quoteLLSavings/quoteSubtotal}" type="number"/></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="text-r variance">
                        <span> 
                            <c:choose>
                                <c:when test="${budgetaryLLSavings == 'NaN'}">
                                    <span class="error"><spring:message code="quote.error.missing" /></span>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber maxFractionDigits="0" value="${budgetaryLLSavings/(FUM * FUM_FACTOR)}" var="savingsPercent" type="percent"/>
                                    <fmt:formatNumber maxFractionDigits="0" value="${FUM}" var="totalFUM" type="number"/>
                                    <i><spring:message code="customerQuote.savingsBudget"/></i> <b><c:out value="${savingsPercent}"/></b> <i><spring:message code="customerQuote.savingsTotalFum"/></i> (<b>&#36;<c:out value="${totalFUM}"/>M</b>)
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    
                    
                </div>
                
<script type="text/javascript">
var firstYearFeeMonths = <c:out value="${firstYearFeeMonths}"/>;
var contractLength = <c:out value="${contractLength}" />;
var errorMissingMetric = '<spring:message code="quote.error.missing" />';
</script>
                