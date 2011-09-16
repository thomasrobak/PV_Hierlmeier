<%@ page import="hierlmeier.Position" %>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'kunde', 'error')} required">
	<label for="kunde">
		<g:message code="position.kunde.label" default="Kunde" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="kunde" name="kunde.id" from="${hierlmeier.Kunde.list()}" 
                  optionKey="id" required="" 
                  value="${positionInstance?.kunde?.id}" 
                  class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'typ', 'error')} required">
	<label for="typ">
		<g:message code="position.typ.label" default="Typ" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="typ" name="typ.id" from="${hierlmeier.Positionstyp.list()}" 
                  optionKey="id" required="" 
                  noSelection="${['null':'Select One...']}" 
                  value="${positionInstance?.typ?.id}" 
                  class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'tier', 'error')} required">
	<label for="tier">
		<g:message code="position.tier.label" default="Tier" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tier" name="tier.id" from="${hierlmeier.Tier.list()}"
                  optionKey="id" required=""
                  noSelection="${['null':'Select One...']}"
                  value="${positionInstance?.tier?.id}" 
                  class="many-to-one"
                  />
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'anmerkung', 'error')} ">
	<label for="anmerkung">
		<g:message code="position.anmerkung.label" default="Anmerkung" />
		
	</label>
	<g:textField name="anmerkung" value="${positionInstance?.anmerkung}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'datum', 'error')} required">
	<label for="datum">
		<g:message code="position.datum.label" default="Datum" />
		<span class="required-indicator">*</span>
        </label>
        <g:textField id="datepicker" name="datum" value="${positionInstance?.datum}" />
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'menge', 'error')} ">
	<label for="menge">
		<g:message code="position.menge.label" default="Menge" />
		
	</label>
        <g:field type="number" name="menge" value="${fieldValue(bean: positionInstance, field: 'menge')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: positionInstance, field: 'preis', 'error')} required">
	<label for="preis">
		<g:message code="position.preis.label" default="Einzelpreis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="preis" required="" value="${fieldValue(bean: positionInstance, field: 'preis')}"/>
</div>

