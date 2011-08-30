
<%@ page import="hierlmeier.Beleg" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="beleg.create" default="Create Beleg" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="beleg.create" default="Create Beleg" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <g:hasErrors bean="${belegInstance}">
            <div class="errors">
                <g:renderErrors bean="${belegInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <label for="errors"><g:message code="beleg.errors" default="Errors" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: belegInstance, field: 'errors', 'errors')}">
                                    

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'create', 'default': 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
