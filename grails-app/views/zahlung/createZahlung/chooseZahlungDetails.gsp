
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Zahlung erfassen (hardcoded)</title>
  </head>
  <body>
    <div id="show-zahlung" class="content scaffold-show">
      <h1>Neue Zahlung erfassen für Kunde: (hardcoded)</h1>
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
  <g:form name="formZahlung" action="createZahlung">
    <div id="show-applicable-belege" class="content fieldset">
      <h1>Neue Zahlung erfassen mit folgenden Daten: (hardcoded)</h1>
      <g:hasErrors bean="${zahlungInstance}">
      <ul class="errors" role="alert">
        <g:eachError bean="${zahlungInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
      </g:hasErrors>
      <div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'betrag', 'error')} required">
        <label for="betrag">
          <g:message code="zahlung.betrag.label" default="Betrag (€)" />
          <span class="required-indicator">*</span>
        </label>
        <span>
          <g:textField name="betrag" value="${fieldValue(bean: zahlungInstance, field: 'betrag')}" />
        </span>
        <span>      
          insgesamt zu Zahlen:
          <span id="remaining">
            Restbetrag missing!
          </span>
        </span>

      </div>
      <div class="fieldcontain ${hasErrors(bean: zahlungInstance, field: 'datum', 'error')} required">
        <label for="datum">
          <g:message code="zahlung.datum.label" default="Datum" />
          <span class="required-indicator">*</span>
        </label>
        <g:textField id="datepicker" name="datum" value="${fieldValue(bean: zahlungInstance, field: 'datum')}"/>
      </div>

      </div>
    </div>
    <div style="margin: 1em">
      <table id="dt-beleg" class="display"
             datasource="${createLink(controller:'beleg', action:'dataTableJSON')}"
             filter="${message(code: 'filter.NPB')}"
             kundeId="${chosenKunde.id}">
        <thead>
          <tr>
            <th class="dt-beleg-th-belegnummer">Belegnummer</th>
            <th class="dt-beleg-th-datum">Datum</th>
            <th class="dt-beleg-th-betrag">Betrag</th>
            <th class="dt-beleg-th-summebezahlt">davon Bezahlt</th>
          </tr>
        </thead>
        <tbody></tbody>
      </table>
    </div>
    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="save" value="${message(code: 'default.zahlung.create.label', default: 'Zahlung erfassen')}" />
      </span>
      <span class="button" style="right: 0">
        <g:submitButton name="payall" class="save" value="${message(code: 'zahlung.pay.all.label', default: 'ALLE OFFENEN BEZAHLEN')}" />
      </span>
    </div>
  </g:form>
<g:javascript src="pvhm.js"/>
</body>
</html>
