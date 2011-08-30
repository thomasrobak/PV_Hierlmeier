<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title><g:layoutTitle default="Patientenverwaltung Hierlmeier"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/png"/>
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}"/>
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.menubar.css')}" type="text/css"/>
    <g:javascript library="jquery" plugin="jquery"/>
    <g:javascript src="jquery-ui-1.8.16.custom.min.js"/>
    <g:javascript src="jquery.dataTables.js"/>
    <g:javascript src="jquery.menubar.js"/>
    <g:javascript src="application.js"/>
    <script type="text/javascript">
      $(function(){
        $("#navbar").menubar({
          items: [
            {
              name: "Home",
              selecton: function(){document.location='${createLink(uri:"/")}'},
              attr: { title: "Startseite." }
            },{
              name: "Zeige Alle...", 
              items: [
                { 
                  name: "Kunden", 
                  attr: { title: "${message(code: 'kunde.list')}"},
                  selecton: function(){document.location='${createLink(controller:"kunde", action:"list")}'} 
                },{ 
                  name: "Tiere", 
                  attr: { title: "Zeigt eine Liste aller Tiere." },
                  selecton: function(){document.location='${createLink(controller:"tier", action:"list")}'} 
                },{ 
                  name: "Medikamente/Leistungen", 
                  attr: { title: "Zeigt eine Liste aller Medikamente/Leistungen." },
                  selecton: function(){document.location='${createLink(controller:"positionstyp", action:"list")}'} 
                },{ 
                  name: "Positionen", 
                  attr: { title: "Zeigt eine Liste aller Positionen." },
                  selecton: function(){document.location='${createLink(controller:"position", action:"list")}'} 
                },{ 
                  name: "Belege", 
                  attr: { title: "Zeigt eine Liste aller Belege." },
                  selecton: function(){document.location='${createLink(controller:"beleg", action:"list")}'} 
                },{ 
                  name: "Zahlungen", 
                  attr: { title: "Zeigt eine Liste aller Zahlungen." },
                  selecton: function(){document.location='${createLink(controller:"zahlung", action:"list")}'} 
                }
              ]
            },{
              name: "Neu Erfassen...", 
              items: [
                { 
                  name: "Kunden", 
                  attr: { title: "Erstelle neuen Kunden." },
                  selecton: function(){document.location='${createLink(controller:"kunde", action:"create")}'} 
                },{ 
                  name: "Tiere", 
                  attr: { title: "Erstelle neues Tier." },
                  selecton: function(){document.location='${createLink(controller:"tier", action:"create")}'} 
                },{ 
                  name: "Medikamente", 
                  attr: { title: "Erstelle neues Medikament." },
                  selecton: function(){document.location='${createLink(controller:"medikament", action:"create")}'} 
                },{ 
                  name: "Leistungen", 
                  attr: { title: "Erstelle neue Leistung." },
                  selecton: function(){document.location='${createLink(controller:"leistung", action:"create")}'} 
                },{ 
                  name: "Positionen", 
                  attr: { title: "Erstelle neue Positione." },
                  selecton: function(){document.location='${createLink(controller:"position", action:"create")}'} 
                },{ 
                  name: "Belege", 
                  attr: { title: "Erstelle neuen Beleg." },
                  selecton: function(){document.location='${createLink(controller:"beleg", action:"create")}'} 
                },{ 
                  name: "Zahlungen", 
                  attr: { title: "Erstelle neue Zahlung." },
                  selecton: function(){document.location='${createLink(controller:"zahlung", action:"create")}'} 
                }
              ]
            },{
              name: "Statistiken...", 
              items: [
                { 
                  name: "Tagesbericht NYI", 
                  attr: { title: "NYI" },
                  selecton: function(){} 
                },{ 
                  name: "Leistungen NYI", 
                  attr: { title: "NYI" },
                  selecton: function(){} 
                },{ 
                  name: "Medikamente NYI", 
                  attr: { title: "NYI" },
                  selecton: function(){} 
                },{ 
                  name: "Kunden NYI", 
                  attr: { title: "NYI" },
                  selecton: function(){} 
                },{ 
                  name: "Offene Positionen NYI", 
                  attr: { title: "NYI" },
                  selecton: function(){} 
                }
              ]
            }
          ]
        });
      });
    </script>
  <g:layoutHead/>
</head>
<body>
  <div id="pvhmLogo" role="banner">
    <a href="${createLink(uri: '/')}">
      <img src="${resource(dir: 'images', file: 'pvhm_logo.png')}" alt="Patientenverwaltung Hierlmeier"/>
      <a/>
    <h1 style="font-size:1.5em;font-weight:bold;">Patientenverwaltung Hierlmeier</h1>
  </div><a href="#page-body" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div id="navbar" role="navigation"></div>
  <div id="page-body">
    <g:layoutBody/>
  </div>
  <div class="footer" role="contentinfo"></div>
  <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
  
</body>
</html>