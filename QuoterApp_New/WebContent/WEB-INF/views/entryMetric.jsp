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
			<form:form commandName="command" action="entryMetric.htm" method="post">

				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="nextMetric"/>" class="btn-next"><spring:message code="next" /></button>
                    <input type="hidden" id="save" name="save"/>
				</div>
				
			    <tiles:insertAttribute name="categorizedQuoteMetrics" />
	    		
				<div class="save-quote">
                    <button type="submit" title="<spring:message code="saveChanges"/>" class="btn-save"><spring:message code="save" /></button>
                    <button type="submit" title="<spring:message code="nextMetric"/>" class="btn-next"><spring:message code="next" /></button>
				</div>
			</form:form>
			
		</div>
	 
	    <div class="clear">&nbsp;</div>
	 
	</div>
</div>
<script type="text/javascript" src="js/entryMetric.js"></script>