<%@ page import="hierlmeier.Tier" %>



<div class="fieldcontain ${hasErrors(bean: tierInstance, field: 'bezeichnung', 'error')} ">
	<label for="bezeichnung">
		<g:message code="tier.bezeichnung.label" default="Bezeichnung" />
		
	</label>
	<g:textField name="bezeichnung" value="${tierInstance?.bezeichnung}"/>
</div>

