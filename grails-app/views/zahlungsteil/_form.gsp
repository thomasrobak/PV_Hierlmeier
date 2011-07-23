<%@ page import="hierlmeier.Zahlungsteil" %>



<div class="fieldcontain ${hasErrors(bean: zahlungsteilInstance, field: 'beleg', 'error')} required">
	<label for="beleg">
		<g:message code="zahlungsteil.beleg.label" default="Beleg" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="beleg" name="beleg.id" from="${hierlmeier.Beleg.list()}" optionKey="id" required="" value="${zahlungsteilInstance?.beleg?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: zahlungsteilInstance, field: 'betrag', 'error')} required">
	<label for="betrag">
		<g:message code="zahlungsteil.betrag.label" default="Betrag" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="betrag" required="" value="${fieldValue(bean: zahlungsteilInstance, field: 'betrag')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: zahlungsteilInstance, field: 'zahlung', 'error')} required">
	<label for="zahlung">
		<g:message code="zahlungsteil.zahlung.label" default="Zahlung" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="zahlung" name="zahlung.id" from="${hierlmeier.Zahlung.list()}" optionKey="id" required="" value="${zahlungsteilInstance?.zahlung?.id}" class="many-to-one"/>
</div>

