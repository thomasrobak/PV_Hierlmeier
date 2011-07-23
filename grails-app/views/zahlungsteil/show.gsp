
<%@ page import="hierlmeier.Zahlungsteil" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zahlungsteil.label', default: 'Zahlungsteil')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-zahlungsteil" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-zahlungsteil" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list zahlungsteil">
			
				<g:if test="${zahlungsteilInstance?.beleg}">
				<li class="fieldcontain">
					<span id="beleg-label" class="property-label"><g:message code="zahlungsteil.beleg.label" default="Beleg" /></span>
					
						<span class="property-value" aria-labelledby="beleg-label"><g:link controller="beleg" action="show" id="${zahlungsteilInstance?.beleg?.id}">${zahlungsteilInstance?.beleg?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${zahlungsteilInstance?.betrag}">
				<li class="fieldcontain">
					<span id="betrag-label" class="property-label"><g:message code="zahlungsteil.betrag.label" default="Betrag" /></span>
					
						<span class="property-value" aria-labelledby="betrag-label"><g:fieldValue bean="${zahlungsteilInstance}" field="betrag"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${zahlungsteilInstance?.zahlung}">
				<li class="fieldcontain">
					<span id="zahlung-label" class="property-label"><g:message code="zahlungsteil.zahlung.label" default="Zahlung" /></span>
					
						<span class="property-value" aria-labelledby="zahlung-label"><g:link controller="zahlung" action="show" id="${zahlungsteilInstance?.zahlung?.id}">${zahlungsteilInstance?.zahlung?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${zahlungsteilInstance?.id}" />
					<g:link class="edit" action="edit" id="${zahlungsteilInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
