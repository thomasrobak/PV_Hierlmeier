
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
  <gui:resources components="dataTable"/>
  <g:set var="entityName" value="${message(code: 'beleg.label', default: 'Beleg')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <div id="table-beleg" class="content scaffold-list">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div class="yui-skin-sam">
      <gui:dataTable
        controller="beleg" action="dataTableJSON"
        columnDefs="[
        [key:'belegnummer', label:'Belegnummer'],
        [key:'kunde', label:'Kunde'],
        [key:'datum', label:'Datum']
        ]"
        sortedBy="belegnummer"
        rowClickNavigation='true'
        rowsPerPage="12"
        paginatorConfig="[
        template:'{PreviousPageLink} {PageLinks} {NextPageLink} {CurrentPageReport}',
        pageReportTemplate:'{totalRecords} results'
        ]"
        />
    </div>
  </div>
  
  <%--
  
  <div id="list-beleg" class="content scaffold-list">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    
    <table>
      <thead>
        <tr>

      <g:sortableColumn property="belegnummer" title="Belegnummer" titleKey="beleg.belegnummer" />

      <th><g:message code="beleg.kunde" default="Kunde" /></th>

      <g:sortableColumn property="datum" title="Datum" titleKey="beleg.datum" />

      </tr>
      </thead>
      <tbody>
      <g:each in="${belegInstanceList}" status="i" var="belegInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <td><g:link action="show" id="${belegInstance.id}">${fieldValue(bean: belegInstance, field: "belegnummer")}</g:link></td>

        <td>${fieldValue(bean: belegInstance, field: "kunde")}</td>

        <td>${fieldValue(bean: belegInstance, field: "datum")}</td>

        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${belegInstanceTotal}" />
    </div>
  </div>
  --%>
</body>
</html>
