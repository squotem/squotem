<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"       prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags"      prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"        prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"         prefix="fmt" %>

            <c:forEach items="${categorizedQuoteMetrics}" var="category" varStatus="catStatus">
                <div class="content-box metric-box">
                    <div class="block-header">${category.description}</div>
                    
                    <table class="width100">
                        <c:set var="total" value="0"></c:set>
                        <c:set var="extraCol" value="false"></c:set>
                        <c:forEach items="${category.quoteMetrics}" var="quoteMetric">
                            <c:if test="${(category.optionType == 'TOTAL_AND_PCT' || category.optionType == 'TOTAL') && (quoteMetric.metric.dataType == 'NUMERIC'|| quoteMetric.metric.dataType == 'BOOL_PLUS_NUMERIC')}">
                                <c:if test="${!quoteMetric.metric.isTotal}">
                                    <c:set var="total" value="${total + (empty quoteMetric.metricValue ? 0 : quoteMetric.metricValue) }"></c:set>
                                </c:if>
                                <c:if test="${not extraCol}">
                                	<c:set var="extraCol" value="${quoteMetric.metric.dataType == 'BOOL_PLUS_NUMERIC'}"></c:set>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        
                        <c:forEach items="${category.quoteMetrics}" var="quoteMetric" varStatus="metricStatus">
                            <tr>
                                <td class="width40">
                                    <label class="${quoteMetric.metric.isTotal ? 'total' : ''}">${quoteMetric.metric.description}</label>
                                </td>
                                <c:choose>
                                    <c:when test="${quoteMetric.metric.dataType == 'BOOLEAN'}">
                                    	<td>
	                                        <c:choose>
	                                            <c:when test="${quoteMetric.metricValue == true}">
	                                                <spring:message code="quote.yes" />
	                                            </c:when>
	                                            <c:when test="${quoteMetric.metricValue == false}">
	                                                <spring:message code="quote.no" />
	                                            </c:when>
	                                        </c:choose>
	                                    </td>
	                                    <c:if test="${extraCol}">
	                                    	<td></td>
	                                    </c:if>
                                    </c:when>
                                    <c:when test="${quoteMetric.metric.dataType == 'NUMERIC'}">
	                                    <c:if test="${extraCol}">
	                                    	<td></td>
	                                    </c:if>
                                    	<td>
	                                        <c:choose>
	                                            <c:when test="${quoteMetric.metric.isTotal}">
	                                                <span  class="total"><fmt:formatNumber value="${total}" type="number"/></span>
	                                            </c:when>
	                                            <c:otherwise>
	                                                <fmt:formatNumber value="${quoteMetric.metricValue}" type="number"/>
	                                            </c:otherwise>
	                                        </c:choose>
                                        </td> 
                                    </c:when>
                                    <c:otherwise>
                                    	<td>
	                                        <c:choose>
	                                            <c:when test="${quoteMetric.booleanValue == true}">
	                                                <spring:message code="quote.yes" />
	                                            </c:when>
	                                            <c:when test="${quoteMetric.booleanValue == false}">
	                                                <spring:message code="quote.no" />
	                                            </c:when>
	                                        </c:choose>
                                        </td>
                                        <td>
                                        	<c:out value="${quoteMetric.metricValue}" />
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${category.optionType == 'TOTAL_AND_PCT'}">
	                                <td class="width40">
	                                    <div class="right width10">
	                                        <label class="${quoteMetric.metric.isTotal ? 'total' : ''}">
	                                        <c:if test="${total > 0 || quoteMetric.metric.isTotal}">
	                                            <c:choose>
	                                                <c:when test="${quoteMetric.metric.isTotal}">
	                                                    100% 
	                                                </c:when>
	                                                <c:otherwise>
	                                                    <fmt:formatNumber value="${quoteMetric.metricValue/total}" type="percent"/> 
	                                                </c:otherwise>
	                                            </c:choose> 
	                                        </c:if>
	                                        </label>
	                                    </div>
	                                </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table> 
                </div>
            </c:forEach>
