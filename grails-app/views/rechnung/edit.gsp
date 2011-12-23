
<%@ page import="hierlmeier.Rechnung" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="rechnung.edit" default="Edit Rechnung" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}"><g:message code="home" default="Home" /></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="rechnung.list" default="Rechnung List" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="rechnung.new" default="New Rechnung" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="rechnung.edit" default="Edit Rechnung" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <g:hasErrors bean="${rechnungInstance}">
            <div class="errors">
                <g:renderErrors bean="${rechnungInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${rechnungInstance?.id}" />
                <g:hiddenField name="version" value="${rechnungInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'update', 'default': 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('${message(code: 'delete.confirm', 'default': 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
