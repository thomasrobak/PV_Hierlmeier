<%@ page import="hierlmeier.Beleg" %>



<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'belegnummer', 'error')} ">
  <label for="belegnummer">
    <g:message code="beleg.belegnummer.label" default="Belegnummer" />

  </label>
  <g:textField name="belegnummer" value="${belegInstance?.belegnummer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'kunde', 'error')} required">
  <label for="kunde">
    <g:message code="beleg.kunde.label" default="Kunde" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="kunde" name="kunde.id" from="${hierlmeier.Kunde.list()}" optionKey="id" required="" value="${belegInstance?.kunde?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'positionen', 'error')} ">
  <label for="positionen">
    <g:message code="beleg.positionen.label" default="Positionen" />

  </label>

  <ul class="one-to-many">
    <g:each in="${belegInstance?.positionen?}" var="p">
      <li><g:link controller="position" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
    <g:link controller="position" action="create" params="['beleg.id': belegInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'position.label', default: 'Position')])}</g:link>
    </li>
  </ul>

</div>

<div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'zahlungsteile', 'error')} ">
  <label for="zahlungsteile">
    <g:message code="beleg.zahlungsteile.label" default="Zahlungsteile" />

  </label>

  <ul class="one-to-many">
    <g:each in="${belegInstance?.zahlungsteile?}" var="z">
      <li><g:link controller="zahlungsteil" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></li>
    </g:each>
    <li class="add">
    <g:link controller="zahlungsteil" action="create" params="['beleg.id': belegInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'zahlungsteil.label', default: 'Zahlungsteil')])}</g:link>
    </li>
  </ul>

</div>

