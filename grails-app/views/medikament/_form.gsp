<%@ page import="hierlmeier.Medikament" %>



<div class="fieldcontain ${hasErrors(bean: medikamentInstance, field: 'bezeichnung', 'error')} required">
	<label for="bezeichnung">
		<g:message code="medikament.bezeichnung.label" default="Bezeichnung" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="bezeichnung" required="" value="${medikamentInstance?.bezeichnung}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: medikamentInstance, field: 'preis', 'error')} required">
	<label for="preis">
		<g:message code="medikament.preis.label" default="Preis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="preis" required="" value="${fieldValue(bean: medikamentInstance, field: 'preis')}"/>
</div>

