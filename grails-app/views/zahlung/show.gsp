
<%@ page import="hierlmeier.Zahlung" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zahlung.label', default: 'Zahlung')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-zahlung" class="content scaffold-show">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list zahlung">
			
				<g:if test="${zahlungInstance?.betrag}">
				<li class="fieldcontain">
					<span id="betrag-label" class="property-label"><g:message code="zahlung.betrag.label" default="Betrag" /></span>
					
						<span class="property-value" aria-labelledby="betrag-label"><g:fieldValue bean="${zahlungInstance}" field="betrag"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${zahlungInstance?.kunde}">
				<li class="fieldcontain">
					<span id="kunde-label" class="property-label"><g:message code="zahlung.kunde.label" default="Kunde" /></span>
					
						<span class="property-value" aria-labelledby="kunde-label"><g:link controller="kunde" action="show" id="${zahlungInstance?.kunde?.id}">${zahlungInstance?.kunde?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${zahlungInstance?.zahlungsteile}">
				<li class="fieldcontain">
					<span id="zahlungsteile-label" class="property-label"><g:message code="zahlung.zahlungsteile.label" default="Zahlungsteile" /></span>
					
						<g:each in="${zahlungInstance.zahlungsteile}" var="z">
						<span class="property-value" aria-labelledby="zahlungsteile-label"><g:link controller="zahlungsteil" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${zahlungInstance?.id}" />
					<g:link class="edit" action="edit" id="${zahlungInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
