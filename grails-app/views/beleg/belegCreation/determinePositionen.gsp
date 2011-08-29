
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Positionen wählen</title>
  </head>
  <body>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      </ul>
    </div>
    <div id="show-kunde" class="content scaffold-show" role="main">
      <h1>Neuen Beleg erstellen für Kunde:</h1>
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
  <g:form action="belegCreation">
    <div id="show-applicable-positionen" class="content fieldset" role="main">
      <h1>Neuen Beleg erstellen mit folgenden Daten:</h1>

      <div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'belegnummer', 'error')} required">
        <label for="belegnummer">
          <g:message code="beleg.belegnummer.label" default="Belegnummer" />
          <span class="required-indicator">*</span>
        </label>
        <g:textField name="belegnummer" value="${fieldValue(bean: belegInstance, field: 'belegnummer')}" />

        <div class="fieldcontain ${hasErrors(bean: belegInstance, field: 'datum', 'error')} required">
          <label for="datum">
            <g:message code="beleg.datum.label" default="Datum" />
            <span class="required-indicator">*</span>
          </label>
          <g:textField id="datepicker" name="datum" />
        </div>

      </div>
    </div>
    <tmpl:/shared/positionListInteractive positionen="${kundePositionenList}" />
    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="save" value="${message(code: 'default.beleg.create.label', default: 'Beleg Erstellen')}" />
      </span>
    </div>
  </g:form>
<g:javascript src="datepicker.js"/>
<g:javascript src="jquery.ui.datepicker-de.js"/>
</body>
</html>
