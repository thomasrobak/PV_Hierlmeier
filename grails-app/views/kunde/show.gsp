
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'kunde.label', default: 'Kunde')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#show-kunde" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="show-kunde" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list kunde">

      <g:if test="${kundeInstance?.nachname}">
        <li class="fieldcontain">
          <span id="nachname-label" class="property-label"><g:message code="kunde.nachname.label" default="Nachname" /></span>

          <span class="property-value" aria-labelledby="nachname-label"><g:fieldValue bean="${kundeInstance}" field="nachname"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.vorname}">
        <li class="fieldcontain">
          <span id="vorname-label" class="property-label"><g:message code="kunde.vorname.label" default="Vorname" /></span>

          <span class="property-value" aria-labelledby="vorname-label"><g:fieldValue bean="${kundeInstance}" field="vorname"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.adresse}">
        <li class="fieldcontain">
          <span id="adresse-label" class="property-label"><g:message code="kunde.adresse.label" default="Adresse" /></span>

          <span class="property-value" aria-labelledby="adresse-label"><g:fieldValue bean="${kundeInstance}" field="adresse"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.wohnort}">
        <li class="fieldcontain">
          <span id="wohnort-label" class="property-label"><g:message code="kunde.wohnort.label" default="Wohnort" /></span>

          <span class="property-value" aria-labelledby="wohnort-label"><g:fieldValue bean="${kundeInstance}" field="wohnort"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.telefonnummer}">
        <li class="fieldcontain">
          <span id="telefonnummer-label" class="property-label"><g:message code="kunde.telefonnummer.label" default="Telefonnummer" /></span>

          <span class="property-value" aria-labelledby="telefonnummer-label"><g:fieldValue bean="${kundeInstance}" field="telefonnummer"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.beruf}">
        <li class="fieldcontain">
          <span id="beruf-label" class="property-label"><g:message code="kunde.beruf.label" default="Beruf" /></span>

          <span class="property-value" aria-labelledby="beruf-label"><g:fieldValue bean="${kundeInstance}" field="beruf"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.mwst}">
        <li class="fieldcontain">
          <span id="mwst-label" class="property-label"><g:message code="kunde.mwst.label" default="Mwst" /></span>

          <span class="property-value" aria-labelledby="mwst-label"><g:formatBoolean boolean="${kundeInstance?.mwst}" /></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.bemerkung}">
        <li class="fieldcontain">
          <span id="bemerkung-label" class="property-label"><g:message code="kunde.bemerkung.label" default="Bemerkung" /></span>

          <span class="property-value" aria-labelledby="bemerkung-label"><g:fieldValue bean="${kundeInstance}" field="bemerkung"/></span>

        </li>
      </g:if>

      <g:if test="${kundeInstance?.positionen}">
        <li class="fieldcontain">
        <tmpl:/shared/positionList positionen="${kundeInstance?.positionen}" listlabel="Positionen" />
          </li>
      </g:if>
      <g:else>
        <li class="fieldcontain">
          Kunde ${kundeInstance?.nachname} ${kundeInstance?.vorname} hat keine Positionen!
        </li>
      </g:else>

      <g:if test="${kundeInstance?.belege}">
        <li class="fieldcontain">
          <span id="belege-label" class="property-label"><g:message code="kunde.belege.label" default="Belege" /></span>

        <g:each in="${kundeInstance.belege}" var="b">
          <span class="property-value" aria-labelledby="belege-label"><g:link controller="beleg" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></span>
        </g:each>

        </li>
      </g:if>


      <g:if test="${kundeInstance?.zahlungen}">
        <li class="fieldcontain">
          <span id="zahlungen-label" class="property-label"><g:message code="kunde.zahlungen.label" default="Zahlungen" /></span>

        <g:each in="${kundeInstance.zahlungen}" var="z">
          <span class="property-value" aria-labelledby="zahlungen-label"><g:link controller="zahlung" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></span>
        </g:each>

        </li>
      </g:if>

    </ol>
    <g:form>
      <fieldset class="buttons">
        <g:hiddenField name="id" value="${kundeInstance?.id}" />
        <g:link class="edit" action="edit" id="${kundeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
      </fieldset>
    </g:form>
  </div>
</body>
</html>
