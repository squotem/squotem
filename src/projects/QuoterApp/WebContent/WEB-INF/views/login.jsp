<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="content-detail">

    <springform:form commandName="commandLogin" action="login.htm" method="post">
        <table>
            <tr>
                <td>
                    <spring:message code="userName" />:
                </td>
                <td>
                    <springform:input path="userName" />
                    <springform:errors path="userName" />
                </td>                
            </tr>
            <tr>
                <td>
                    <spring:message code="userPassword" />:
                </td>
                <td>
                    <springform:password path="userPassword" />
                    <springform:errors path="userPassword" />
                </td>                
            </tr>            
            <tr>
                <td colspan="2">
                    <button id="login"><spring:message code="login" /></button>
                </td>                
            </tr>
        </table>
    </springform:form>
		    
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$('#login').button({
		      icons: {
			        primary: "ui-icon-key"
		      }
		});
	});

</script>