
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Positionen wählen (hardcoded)</title>
  </head>
  <body>
    <div id="show-kunde" class="content scaffold-show">
      <h1>Neuen Beleg erstellen für Kunde: (hardcoded)</h1>
      <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list kunde">

        <g:if test="${chosenKunde?.nachname}">
          <li class="fieldcontain">
            <span id="nachname-label" class="property-label"><g:message code="kunde.nachname.label" default="Nachname" /></span>

            <span class="property-value" aria-labelledby="nachname-label"><g:fieldValue bean="${chosenKunde}" field="nachname"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.vorname}">
          <li class="fieldcontain">
            <span id="vorname-label" class="property-label"><g:message code="kunde.vorname.label" default="Vorname" /></span>

            <span class="property-value" aria-labelledby="vorname-label"><g:fieldValue bean="${chosenKunde}" field="vorname"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.adresse}">
          <li class="fieldcontain">
            <span id="adresse-label" class="property-label"><g:message code="kunde.adresse.label" default="Adresse" /></span>

            <span class="property-value" aria-labelledby="adresse-label"><g:fieldValue bean="${chosenKunde}" field="adresse"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.wohnort}">
          <li class="fieldcontain">
            <span id="wohnort-label" class="property-label"><g:message code="kunde.wohnort.label" default="Wohnort" /></span>

            <span class="property-value" aria-labelledby="wohnort-label"><g:fieldValue bean="${chosenKunde}" field="wohnort"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.telefonnummer}">
          <li class="fieldcontain">
            <span id="telefonnummer-label" class="property-label"><g:message code="kunde.telefonnummer.label" default="Telefonnummer" /></span>

            <span class="property-value" aria-labelledby="telefonnummer-label"><g:fieldValue bean="${chosenKunde}" field="telefonnummer"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.beruf}">
          <li class="fieldcontain">
            <span id="beruf-label" class="property-label"><g:message code="kunde.beruf.label" default="Beruf" /></span>

            <span class="property-value" aria-labelledby="beruf-label"><g:fieldValue bean="${chosenKunde}" field="beruf"/></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.mwst != null}">
          <li class="fieldcontain">
            <span id="mwst-label" class="property-label"><g:message code="kunde.mwst.label" default="Mwst" /></span>

            <span class="property-value" aria-labelledby="mwst-label"><g:formatBoolean boolean="${chosenKunde?.mwst}" /></span>

          </li>
        </g:if>

        <g:if test="${chosenKunde?.bemerkung}">
          <li class="fieldcontain">
            <span id="bemerkung-label" class="property-label"><g:message code="kunde.bemerkung.label" default="Bemerkung" /></span>

            <span class="property-value" aria-labelledby="bemerkung-label"><g:fieldValue bean="${chosenKunde}" field="bemerkung"/></span>

          </li>
        </g:if>
      </ol>

    </div>
  <g:form name="formBeleg" action="createBeleg">
    <div id="show-applicable-positionen" class="content fieldset">
      <h1>Neuen Beleg erstellen mit folgenden Daten: (hardcoded)</h1>
      <g:hasErrors bean="${belegInstance}">
      <ul class="errors" role="alert">
        <g:eachError bean="${belegInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
      </g:hasErrors>
      <div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'belegnummer', 'error')} required">
        <label for="belegnummer">
          <g:message code="beleg.belegnummer.label" default="Belegnummer" />
          <span class="required-indicator">*</span>
        </label>
        <g:textField name="belegnummer" value="${fieldValue(bean: belegInstance, field: 'belegnummer')}" />
      </div>

      <div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'datum', 'error')} required">
        <label for="datum">
          <g:message code="beleg.datum.label" default="Datum" />
          <span class="required-indicator">*</span>
        </label>
        <g:textField id="datepicker" name="datum" value="${fieldValue(bean: belegInstance, field: 'datum')}"/>
      </div>
      
    </div>
    <div style="margin: 1em">
      <table id="dt-position" class="display"
             datasource="${createLink(controller:'position', action:'dataTableJSON')}"
             filter="${message(code: 'filter.UPP')}"
             kundeId="${chosenKunde.id}">
        <thead>
          <tr>
            <th class="dt-position-th-checkbox"><input id="checkall" type="checkbox" /></th>
            <th class="dt-position-th-typ">Typ</th>
            <th class="dt-position-th-anmerkung">Anmerkung</th>
            <th class="dt-position-th-tier">Tier</th>
            <th class="dt-position-th-menge">Menge</th>
            <th class="dt-position-th-betrag">Betrag (€)</th>
            <th class="dt-position-th-datum">Datum</th>
          </tr>
        </thead>
        <tbody></tbody>
      </table>
    </div>
    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="save" value="${message(code: 'default.beleg.create.label', default: 'Beleg Erstellen')}" />
      </span>
    </div>
  </g:form>
<g:javascript src="pvhm-datatables.js"/>
<g:javascript src="pvhm-datepicker.js"/>
</body>
</html>
