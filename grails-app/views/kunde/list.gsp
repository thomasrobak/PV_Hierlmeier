
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'kunde.label', default: 'Kunde')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#list-kunde" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="list-kunde" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
      <thead>
        <tr>

      <g:sortableColumn property="adresse" title="${message(code: 'kunde.adresse.label', default: 'Adresse')}" />

      <g:sortableColumn property="bemerkung" title="${message(code: 'kunde.bemerkung.label', default: 'Bemerkung')}" />

      <g:sortableColumn property="beruf" title="${message(code: 'kunde.beruf.label', default: 'Beruf')}" />

      <g:sortableColumn property="mwst" title="${message(code: 'kunde.mwst.label', default: 'Mwst')}" />

      <g:sortableColumn property="nachname" title="${message(code: 'kunde.nachname.label', default: 'Nachname')}" />

      <g:sortableColumn property="telefonnummer" title="${message(code: 'kunde.telefonnummer.label', default: 'Telefonnummer')}" />

      </tr>
      </thead>
      <tbody>
      <g:each in="${kundeInstanceList}" status="i" var="kundeInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

          <td><g:link action="show" id="${kundeInstance.id}">${fieldValue(bean: kundeInstance, field: "adresse")}</g:link></td>

        <td>${fieldValue(bean: kundeInstance, field: "bemerkung")}</td>

        <td>${fieldValue(bean: kundeInstance, field: "beruf")}</td>

        <td><g:formatBoolean boolean="${kundeInstance.mwst}" /></td>

        <td>${fieldValue(bean: kundeInstance, field: "nachname")}</td>

        <td>${fieldValue(bean: kundeInstance, field: "telefonnummer")}</td>

        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${kundeInstanceTotal}" />
    </div>
  </div>
</body>
</html>
