
<%@ page import="hierlmeier.Beleg" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
                <gui:resources components="dataTable"/>
		<g:set var="entityName" value="${message(code: 'beleg.label', default: 'Beleg')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-beleg" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list beleg">
			
				<g:if test="${belegInstance?.belegnummer}">
				<li class="fieldcontain">
					<span id="belegnummer-label" class="property-label"><g:message code="beleg.belegnummer.label" default="Belegnummer" /></span>
					
						<span class="property-value" aria-labelledby="belegnummer-label"><g:fieldValue bean="${belegInstance}" field="belegnummer"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${belegInstance?.kunde}">
				<li class="fieldcontain">
					<span id="kunde-label" class="property-label"><g:message code="beleg.kunde.label" default="Kunde" /></span>
					
						<span class="property-value" aria-labelledby="kunde-label"><g:link controller="kunde" action="show" id="${belegInstance?.kunde?.id}">${belegInstance?.kunde?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${belegInstance?.positionen}">
                                  <div id="list-positionen" class="yui-skin-sam">
                                    <gui:dataTable
                                      controller="position" action="dataTableJSONByBeleg"
                                      columnDefs="[
                                      [key:'datum', label:'Datum'],
                                      [key:'typ', label:'Typ'],
                                      [key:'tier', label:'Tier'],
                                      [key:'menge', label:'Menge'],
                                      [key:'beleg', label:'Beleg']
                                      ]"
                                      params="[belegId:belegInstance.id]"
                                      sortedBy="datum"
                                      rowsPerPage="12"
                                      paginatorConfig="[
                                      template:'{PreviousPageLink} {PageLinks} {NextPageLink} {CurrentPageReport}',
                                      pageReportTemplate:'{totalRecords} results'
                                      ]"
                                      />
                                  </div>
                                </g:if>
                                <g:else>
                                  <li class="fieldcontain">
                                    Beleg mit der Nummer ${belegInstance?.belegnummer} hat keine Positionen!
                                  </li>
                                </g:else>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${belegInstance?.id}" />
					<g:link class="edit" action="edit" id="${belegInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                                        <g:actionSubmit class="print" action="print" value="${message(code: 'default.button.print.label', default: 'Drucken')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
