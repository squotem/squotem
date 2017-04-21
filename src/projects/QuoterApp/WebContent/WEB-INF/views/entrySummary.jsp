<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty sessionScope.editQuote}" >
			<div id="summaryWrapper">
				<div id="summary">
					<div class="content-box summary-box">
						<div class="block-header"><spring:message code="quote.summary" /></div>
						<div class="block-table">
							<table id="summary-table">
								<thead>
									<tr>
										<th><spring:message code="quote.type" /></th>
										<th><spring:message code="quote.impl" /></th>
										<th><spring:message code="quote.monthly" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${sessionScope.editQuote.quoteCosts.subtotals}" var="subtotal">
										<tr id="quote-table-${subtotal.productCategory.id}-summary">
											<td>${subtotal.productCategory.description}</td>
											<td>
												<c:if test="${subtotal.subtotals['IMPL'] == 'NaN'}">
													<span class="error"><spring:message code="quote.error.missing" /></span>
												</c:if>
												<c:if test="${subtotal.subtotals['IMPL'] != 'NaN'}">
													${empty subtotal.subtotals['IMPL'] ? '0': subtotal.subtotals['IMPL']}
												</c:if>
											</td>
											<td>
												<c:if test="${subtotal.subtotals['MONTHLY'] == 'NaN'}">
													<span class="error"><spring:message code="quote.error.missing" /></span>
												</c:if>
												<c:if test="${subtotal.subtotals['MONTHLY'] != 'NaN'}">
													${empty subtotal.subtotals['MONTHLY'] ? '0': subtotal.subtotals['MONTHLY']}
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<tr id="adjustment-table-summary">
										<td><spring:message code="quote.adjustment.description" /></td>
										<td>
											<c:if test="${subtotal.adjustments['IMPL'] == 'NaN'}">
												<span class="error"><spring:message code="quote.error.missing" /></span>
											</c:if>
											<c:if test="${subtotal.adjustments['IMPL'] != 'NaN'}">
												${empty subtotal.adjustments['IMPL'] ? '0': subtotal.adjustments['IMPL']}
											</c:if>
										</td>
										<td>
											<c:if test="${subtotal.adjustments['MONTHLY'] == 'NaN'}">
												<span class="error"><spring:message code="quote.error.missing" /></span>
											</c:if>
											<c:if test="${subtotal.adjustments['MONTHLY'] != 'NaN'}">
												${empty subtotal.adjustments['MONTHLY'] ? '0': subtotal.adjustments['MONTHLY']}
											</c:if>
										</td>
									</tr>
									<tr id="total-summary" class="bold">
										<td><spring:message code="quote.total" /></td>
										<td>${empty subtotal.totals['IMPL'] ? '0' : subtotal.totals['IMPL']}</td>
										<td>${empty subtotal.totals['MONTHLY'] ? '0' : subtotal.totals['MONTHLY']}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
</c:if>