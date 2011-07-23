<%@ page import="hierlmeier.Kunde" %>



<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'nachname', 'error')} ">
	<label for="nachname">
		<g:message code="kunde.nachname.label" default="Nachname" />
		
	</label>
	<g:textField name="nachname" value="${kundeInstance?.nachname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'vorname', 'error')} ">
	<label for="vorname">
		<g:message code="kunde.vorname.label" default="Vorname" />
		
	</label>
	<g:textField name="vorname" value="${kundeInstance?.vorname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'adresse', 'error')} ">
	<label for="adresse">
		<g:message code="kunde.adresse.label" default="Adresse" />
		
	</label>
	<g:textField name="adresse" value="${kundeInstance?.adresse}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'wohnort', 'error')} ">
	<label for="wohnort">
		<g:message code="kunde.wohnort.label" default="Wohnort" />
		
	</label>
	<g:textField name="wohnort" value="${kundeInstance?.wohnort}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'telefonnummer', 'error')} ">
	<label for="telefonnummer">
		<g:message code="kunde.telefonnummer.label" default="Telefonnummer" />
		
	</label>
	<g:textField name="telefonnummer" value="${kundeInstance?.telefonnummer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'beruf', 'error')} ">
	<label for="beruf">
		<g:message code="kunde.beruf.label" default="Beruf" />
		
	</label>
	<g:textField name="beruf" value="${kundeInstance?.beruf}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'mwst', 'error')} ">
	<label for="mwst">
		<g:message code="kunde.mwst.label" default="Mwst" />
		
	</label>
	<g:checkBox name="mwst" value="${kundeInstance?.mwst}" />
</div>

<div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'bemerkung', 'error')} ">
	<label for="bemerkung">
		<g:message code="kunde.bemerkung.label" default="Bemerkung" />
		
	</label>
	<g:textArea name="bemerkung" value="${kundeInstance?.bemerkung}"/>
</div>

<!--div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'belege', 'error')} ">
	<label for="belege">
		<g:message code="kunde.belege.label" default="Belege" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${kundeInstance?.belege?}" var="b">
    <li><g:link controller="beleg" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="beleg" action="create" params="['kunde.id': kundeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'beleg.label', default: 'Beleg')])}</g:link>
</li>
</ul>

</div-->

<!--div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'positionen', 'error')} ">
	<label for="positionen">
		<g:message code="kunde.positionen.label" default="Positionen" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${kundeInstance?.positionen?}" var="p">
    <li><g:link controller="position" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="position" action="create" params="['kunde.id': kundeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'position.label', default: 'Position')])}</g:link>
</li>
</ul>

</div-->

<!--div class="fieldcontain ${hasErrors(bean: kundeInstance, field: 'zahlungen', 'error')} ">
	<label for="zahlungen">
		<g:message code="kunde.zahlungen.label" default="Zahlungen" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${kundeInstance?.zahlungen?}" var="z">
    <li><g:link controller="zahlung" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="zahlung" action="create" params="['kunde.id': kundeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'zahlung.label', default: 'Zahlung')])}</g:link>
</li>
</ul>

</div-->

