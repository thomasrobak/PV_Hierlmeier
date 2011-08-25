
<div id="list-position-readonly" class="yui-skin-sam" role="main">
  <h1><g:message code="default.list.label" args="[PositionenXXX]" /></h1>
  <gui:dataTable
    controller="position" action="dataTableJSONForKunde"
    columnDefs="[
    [key:'datum', label:'Datum'],
    [key:'typ', label:'Typ'],
    [key:'tier', label:'Tier'],
    [key:'menge', label:'Menge'],
    [key:'beleg', label:'Beleg']
    ]"
    sortedBy="datum"
    rowsPerPage="12"
    paginatorConfig="[
    template:'{PreviousPageLink} {PageLinks} {NextPageLink} {CurrentPageReport}',
    pageReportTemplate:'{totalRecords} Results'
    ]"
    />
</div>