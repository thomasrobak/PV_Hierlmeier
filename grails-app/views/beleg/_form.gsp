<%@ page import="hierlmeier.Beleg" %>



<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'belegnummer', 'error')} required">
	<label for="belegnummer">
		<g:message code="beleg.belegnummer.label" default="Belegnummer" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="belegnummer" value="${fieldValue(bean: belegInstance, field: 'belegnummer')}" />

</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'kunde', 'error')} required">
	<label for="kunde">
		<g:message code="beleg.kunde.label" default="Kunde" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="kunde.id" from="${hierlmeier.Kunde.list()}" optionKey="id" value="${belegInstance?.kunde?.id}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'positionen', 'error')} ">
	<label for="positionen">
		<g:message code="beleg.positionen.label" default="Positionen" />
		
	</label>
	
<ul>
<g:each in="${belegInstance?.positionen}" var="positionInstance">
    <li><g:link controller="position" action="show" id="${positionInstance.id}">${positionInstance?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="position" params="['beleg.id': belegInstance?.id]" action="create"><g:message code="position.new" default="New Position" /></g:link>


</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'zahlungsteile', 'error')} ">
	<label for="zahlungsteile">
		<g:message code="beleg.zahlungsteile.label" default="Zahlungsteile" />
		
	</label>
	
<ul>
<g:each in="${belegInstance?.zahlungsteile}" var="zahlungsteilInstance">
    <li><g:link controller="zahlungsteil" action="show" id="${zahlungsteilInstance.id}">${zahlungsteilInstance?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="zahlungsteil" params="['beleg.id': belegInstance?.id]" action="create"><g:message code="zahlungsteil.new" default="New Zahlungsteil" /></g:link>


</div>

