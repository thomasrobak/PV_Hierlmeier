<%@ page import="hierlmeier.Positionstyp" %>



<div class="fieldcontain ${hasErrors(bean: positionstypInstance, field: 'bezeichnung', 'error')} ">
	<label for="bezeichnung">
		<g:message code="positionstyp.bezeichnung.label" default="Bezeichnung" />
		
	</label>
	<g:textField name="bezeichnung" value="${positionstypInstance?.bezeichnung}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: positionstypInstance, field: 'preis', 'error')} required">
	<label for="preis">
		<g:message code="positionstyp.preis.label" default="Preis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="preis" required="" value="${fieldValue(bean: positionstypInstance, field: 'preis')}"/>
</div>

