
<%@ page import="hierlmeier.Beleg" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="beleg.edit" default="Edit Beleg" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="beleg.edit" default="Edit Beleg" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <g:hasErrors bean="${belegInstance}">
            <div class="errors">
                <g:renderErrors bean="${belegInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${belegInstance?.id}" />
                <g:hiddenField name="version" value="${belegInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="belegnummer"><g:message code="beleg.belegnummer" default="Belegnummer" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'belegnummer', 'errors')}">
                                    <g:textField name="belegnummer" value="${fieldValue(bean: belegInstance, field: 'belegnummer')}" />

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kunde"><g:message code="beleg.kunde" default="Kunde" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'kunde', 'errors')}">
                                    <g:select name="kunde.id" from="${hierlmeier.Kunde.list()}" optionKey="id" value="${belegInstance?.kunde?.id}"  />

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="positionen"><g:message code="beleg.positionen" default="Positionen" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'positionen', 'errors')}">
                                    
<ul>
<g:each in="${belegInstance?.positionen}" var="positionInstance">
    <li><g:link controller="position" action="show" id="${positionInstance.id}">${positionInstance?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="position" params="['beleg.id': belegInstance?.id]" action="create"><g:message code="position.new" default="New Position" /></g:link>


                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="zahlungsteile"><g:message code="beleg.zahlungsteile" default="Zahlungsteile" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'zahlungsteile', 'errors')}">
                                    
<ul>
<g:each in="${belegInstance?.zahlungsteile}" var="zahlungsteilInstance">
    <li><g:link controller="zahlungsteil" action="show" id="${zahlungsteilInstance.id}">${zahlungsteilInstance?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="zahlungsteil" params="['beleg.id': belegInstance?.id]" action="create"><g:message code="zahlungsteil.new" default="New Zahlungsteil" /></g:link>


                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="errors"><g:message code="beleg.errors" default="Errors" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'errors', 'errors')}">
                                    

                                </td>
                            </tr>
                        
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
