
<%@ page import="hierlmeier.Position" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-position" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list position">
			
				<g:if test="${positionInstance?.beleg}">
				<li class="fieldcontain">
					<span id="beleg-label" class="property-label"><g:message code="position.beleg.label" default="Beleg" /></span>
					
						<span class="property-value" aria-labelledby="beleg-label"><g:link controller="beleg" action="show" id="${positionInstance?.beleg?.id}">${positionInstance?.beleg?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.anmerkung}">
				<li class="fieldcontain">
					<span id="anmerkung-label" class="property-label"><g:message code="position.anmerkung.label" default="Anmerkung" /></span>
					
						<span class="property-value" aria-labelledby="anmerkung-label"><g:fieldValue bean="${positionInstance}" field="anmerkung"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.menge}">
				<li class="fieldcontain">
					<span id="menge-label" class="property-label"><g:message code="position.menge.label" default="Menge" /></span>
					
						<span class="property-value" aria-labelledby="menge-label"><g:fieldValue bean="${positionInstance}" field="menge"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.datum}">
				<li class="fieldcontain">
					<span id="datum-label" class="property-label"><g:message code="position.datum.label" default="Datum" /></span>
					
						<span class="property-value" aria-labelledby="datum-label"><g:formatDate date="${positionInstance?.datum}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.kunde}">
				<li class="fieldcontain">
					<span id="kunde-label" class="property-label"><g:message code="position.kunde.label" default="Kunde" /></span>
					
						<span class="property-value" aria-labelledby="kunde-label"><g:link controller="kunde" action="show" id="${positionInstance?.kunde?.id}">${positionInstance?.kunde?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.preis}">
				<li class="fieldcontain">
					<span id="preis-label" class="property-label"><g:message code="position.preis.label" default="Preis" /></span>
					
						<span class="property-value" aria-labelledby="preis-label"><g:fieldValue bean="${positionInstance}" field="preis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.tier}">
				<li class="fieldcontain">
					<span id="tier-label" class="property-label"><g:message code="position.tier.label" default="Tier" /></span>
					
						<span class="property-value" aria-labelledby="tier-label"><g:link controller="tier" action="show" id="${positionInstance?.tier?.id}">${positionInstance?.tier?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionInstance?.typ}">
				<li class="fieldcontain">
					<span id="typ-label" class="property-label"><g:message code="position.typ.label" default="Typ" /></span>
					
						<span class="property-value" aria-labelledby="typ-label"><g:link controller="positionstyp" action="show" id="${positionInstance?.typ?.id}">${positionInstance?.typ?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${positionInstance?.id}" />
					<g:link class="edit" action="edit" id="${positionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
