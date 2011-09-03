
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <gui:resources components="dataTable, accordion"/>
    <g:set var="entityName" value="${message(code: 'kunde.label', default: 'Kunde')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
<body>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <div id="show-kunde" class="content scaffold-show">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <div class="yui-skin-sam">
        <gui:accordion multiple="true">

        <gui:accordionElement title="${message(code: 'kunde.details', default: 'Hardcoded Stammdaten Label')}" 
                              selected="true">
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

            <g:if test="${kundeInstance?.mwst != null}">
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
          </ol>
        </gui:accordionElement>

        <gui:accordionElement title="${message(code: 'kunde.positionen', default: 'Positionen Hardcoded Label')}">
          <g:if test="${kundeInstance?.positionen}">
            <div id="list-positionen">
              <gui:dataTable
                controller="position" action="dataTableJSONByKunde"
                columnDefs="[
                [key:'datum', label:'Datum'],
                [key:'typ', label:'Typ'],
                [key:'tier', label:'Tier'],
                [key:'menge', label:'Menge'],
                [key:'beleg', label:'Beleg']
                ]"
                params="[kundeId:kundeInstance.id]"
                sortedBy="datum"
                rowsPerPage="12"
                paginatorConfig="[
                template:'{PreviousPageLink} {PageLinks} {NextPageLink} {CurrentPageReport}',
                pageReportTemplate:'{totalRecords} results'
                ]"
                />
            </div>
          </g:if>
          <g:else>Kunde ${kundeInstance?.nachname} ${kundeInstance?.vorname} hat keine Positionen!</g:else>
        </gui:accordionElement>

        <gui:accordionElement title="${message(code: 'kunde.belege', default: 'Belege Hardcoded Label')}">
          <g:if test="${kundeInstance?.belege}">
            <div id="list-belege">
              <gui:dataTable
                controller="beleg" action="dataTableJSONByKunde"
                columnDefs="[
                [key:'datum', label:'Datum'],
                [key:'belegnummer', label:'Belegnummer']
                ]"
                params="[kundeId:kundeInstance.id]"
                sortedBy="datum"
                rowsPerPage="12"
                paginatorConfig="[
                template:'{PreviousPageLink} {PageLinks} {NextPageLink} {CurrentPageReport}',
                pageReportTemplate:'{totalRecords} results'
                ]"
                />
            </div>
          </g:if>
          <g:else>Kunde ${kundeInstance?.nachname} ${kundeInstance?.vorname} hat keine Belege!</g:else>
        </gui:accordionElement>

        <gui:accordionElement title="${message(code: 'kunde.zahlungen', default: 'Zahlungen Hardcoded Label')}">
          <g:if test="${kundeInstance?.zahlungen}">
            <div id="list-zahlungen">
              <g:each in="${kundeInstance.zahlungen}" var="z">
                <span class="property-value" aria-labelledby="zahlungen-label"><g:link controller="zahlung" action="show" id="${z.id}">${z?.encodeAsHTML()}</g:link></span>
              </g:each>
            </div>
          </g:if>
          <g:else>Kunde ${kundeInstance?.nachname} ${kundeInstance?.vorname} hat keine Zahlungen!</g:else>
        </gui:accordionElement>

      </gui:accordion>
    </div>

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
