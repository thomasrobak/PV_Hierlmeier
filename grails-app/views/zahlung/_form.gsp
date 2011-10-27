<%@ page import="hierlmeier.Zahlung" %>



<div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'betrag', 'error')} required">
	<label for="betrag">
		<g:message code="zahlung.betrag.label" default="Betrag" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="betrag" required="" value="${fieldValue(bean: zahlungInstance, field: 'betrag')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'kunde', 'error')} required">
	<label for="kunde">
		<g:message code="zahlung.kunde.label" default="Kunde" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="kunde" name="kunde.id" from="${hierlmeier.Kunde.list()}" optionKey="id" required="" value="${zahlungInstance?.kunde?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'datum', 'error')} required">
	<label for="datum">
		<g:message code="zahlung.datum.label" default="Datum" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField id="datepicker" name="datum" value="${zahlungInstance?.datum}" />
</div>

<div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'zahlungsteile', 'error')} ">
	<label for="zahlungsteile">
		<g:message code="zahlung.zahlungsteile.label" default="Zahlungsteile" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${zahlungInstance?.zahlungsteile?}" var="z">
    <li><g:link controller="zahlungsteil" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="zahlungsteil" action="create" params="['zahlung.id': zahlungInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil')])}</g:link>
</li>
</ul>

</div>

