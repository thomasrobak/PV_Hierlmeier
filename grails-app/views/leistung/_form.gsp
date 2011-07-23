<%@ page import="hierlmeier.Leistung" %>



<div class="fieldcontain ${hasErrors(bean: leistungInstance, field: 'bezeichnung', 'error')} ">
	<label for="bezeichnung">
		<g:message code="leistung.bezeichnung.label" default="Bezeichnung" />
		
	</label>
	<g:textField name="bezeichnung" value="${leistungInstance?.bezeichnung}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: leistungInstance, field: 'preis', 'error')} required">
	<label for="preis">
		<g:message code="leistung.preis.label" default="Preis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="preis" required="" value="${fieldValue(bean: leistungInstance, field: 'preis')}"/>
</div>

