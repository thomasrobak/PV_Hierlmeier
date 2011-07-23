
<%@ page import="hierlmeier.Beleg" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'beleg.label', default: 'Beleg')}" />
  <title><g:message code="default.beleg.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#list-beleg" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="list-beleg" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
      <thead>
        <tr>

          <g:sortableColumn property="id" title="Id" titleKey="beleg.id" />

          <g:sortableColumn property="belegnummer" title="Belegnummer" titleKey="beleg.belegnummer" />

          <th><g:message code="beleg.kunde" default="Kunde" /></th>

          <g:sortableColumn property="errors" title="Errors" titleKey="beleg.errors" />

      </tr>
      </thead>
      <tbody>
      <g:each in="${belegInstanceList}" status="i" var="belegInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <td><g:link action="show" id="${belegInstance.id}">${fieldValue(bean: belegInstance, field: "id")}</g:link></td>

        <td>${fieldValue(bean: belegInstance, field: "belegnummer")}</td>

        <td>${fieldValue(bean: belegInstance, field: "kunde")}</td>

        <td>${fieldValue(bean: belegInstance, field: "errors")}</td>

        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${belegInstanceTotal}" />
    </div>
  </div>
</body>
</html>
