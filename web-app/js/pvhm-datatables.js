$.fn.dataTableExt.oSort['numeric-comma-asc']  = function(a,b) {
    var x = (a == "-") ? 0 : a.replace( /,/, "." );
    var y = (b == "-") ? 0 : b.replace( /,/, "." );
    x = parseFloat( x );
    y = parseFloat( y );
    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

$.fn.dataTableExt.oSort['numeric-comma-desc'] = function(a,b) {
    var x = (a == "-") ? 0 : a.replace( /,/, "." );
    var y = (b == "-") ? 0 : b.replace( /,/, "." );
    x = parseFloat( x );
    y = parseFloat( y );
    return ((x < y) ?  1 : ((x > y) ? -1 : 0));
};

$.fn.dataTableExt.oApi.fnGetFilteredNodes = function ( oSettings )
{
    var anRows = [];
    for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
    {
        var nRow = oSettings.aoData[ oSettings.aiDisplay[i] ].nTr;
        anRows.push( nRow );
    }
    return anRows;
};

$(function() {
    
    /********************
 * datatable config vars
 **********/
    var table_datasource
    var table_row_click_action
    var table_filter
    var table_request_params

    
    /**********************************
 * *  DataTable Konfiguration für Kunden Table
 *********************************/
    
    if ($("#dt-kunde").length) {
        table_datasource = $("#dt-kunde").attr("datasource");
        table_row_click_action = $("#dt-kunde").attr("rowclickaction");
        table_filter = $("#dt-kunde").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        var usethisfor_sDom = 'lfrtip'
        
        if ($("#minzahllast").length) {
            $.fn.dataTableExt.afnFiltering.push(
                function( oSettings, aData, iDataIndex ) {
                    var minzahllast
                    var mzl = $("#minzahllast").val()
                    if(mzl == null || mzl == "")
                        minzahllast = 0
                    else 
                        minzahllast = parseFloat(mzl.replace(",", "."))
                    if(!pvhm_isNumber(minzahllast))
                        minzahllast = 0
                    
                    if (minzahllast <= parseFloat(aData[5]))
                    {
                        return true;
                    }
                    return false;
                }
                );
                usethisfor_sDom = 'lrtip'
        }
        
        var table_kunde = $("#dt-kunde").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "bLengthChange": false,
            "iDisplayLength": -1,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "sDom": usethisfor_sDom,
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "aoColumnDefs": [
            {   /* kunde.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-kunde-th-id"]
            },
            {   /* kunde.nachname */
                "mDataProp": "nachname", 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-nachname"]
            },

            { /* kunde.bemerkung */
                "mDataProp": "bemerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-kunde-th-bemerkung"]
            },

            { /* kunde.wohnort */
                "mDataProp": "wohnort", 
                "bSortable": false, 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-wohnort"]
            },

            { /* kunde.mwst */
                "mDataProp": "mwst", 
                "bSortable": false,
                "sWidth": "10px",
                "aTargets": ["dt-kunde-th-mwst"]
            },
            
            { /* kunde.zahllast */
                "mDataProp": "zahllast", 
                "bSortable": true,
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['zahllast'])
                },
                "aTargets": ["dt-kunde-th-zahllast"]
            },

            { /* kunde.telefonnummer */
                "mDataProp": "telefonnummer", 
                "bSortable": false, 
                "sWidth": "60px", 
                "aTargets": ["dt-kunde-th-telefonnummer"]
            }      
            ]
        });
    
        if ($("#minzahllast").length) {
            $('#minzahllast').keyup( function() { table_kunde.fnDraw(); } );
        }
    
        if(table_row_click_action != null) {   
            $("#dt-kunde tbody tr").live("click",function(){
                var row_obj = table_kunde.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    
    /**********************************
     * *  DataTable Konfiguration für Positionen Table
     *********************************/
    if ($("#dt-position").length) {
        table_datasource = $("#dt-position").attr("datasource");
        table_row_click_action = $("#dt-position").attr("rowclickaction");
        table_filter = $("#dt-position").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        if($("#dt-position").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-position").attr("kundeId")
            });
        }
        if($("#dt-position").attr("belegId")) {
            table_request_params.push({
                "name": "belegId", 
                "value": $("#dt-position").attr("belegId")
            });
        }
        
        var table_position = $("#dt-position").dataTable({
            "bAutoWidth": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "bLengthChange": false,
            "iDisplayLength": -1,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "aoColumnDefs": [
            {   /* position.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-position-th-id"]
            },
            {   /* position.checkbox */
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "fnRender": function(oObj){
                    return "<input type='checkbox' name='selected' value='" + oObj.aData['id'] + "' />"
                },
                "aTargets": ["dt-position-th-checkbox"]
            },
            {   /* position.typ */
                "mDataProp": "typ.bezeichnung", 
                "sWidth": "70px", 
                "aTargets": ["dt-position-th-typ"]
            },
            { /* position.anmerkung */
                "mDataProp": "anmerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-position-th-anmerkung"]
            },
            { /* position.menge */
                "mDataProp": "menge", 
                "bSortable": false,
                "bSearchable": false,
                "sWidth": "30px", 
                "aTargets": ["dt-position-th-menge"]
            },
            { /* position.preis (einzelpreis) */
                "mDataProp": "preis", 
                "bSortable": false,
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['preis'])
                },
                "aTargets": ["dt-position-th-preis"]
            },
            { /* position.betrag (menge*einzelpreis) */
                "mDataProp": "betrag", 
                "bSortable": false,
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['betrag'])
                },
                "aTargets": ["dt-position-th-betrag"]
            },
            { /* position.tier */
                "mDataProp": "tier.bezeichnung", 
                "sWidth": "50px", 
                "aTargets": ["dt-position-th-tier"]
            },
            { /* position.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "40px",
                "fnRender": function(oObj){
                    return pvhm_formatDate(oObj.aData['datum'])
                },
                "aTargets": ["dt-position-th-datum"]
            },
            { /* position.beleg */
                "mDataProp": "beleg",
                "sDefaultContent": "keiner",
                "sWidth": "50px",
                "fnRender": function(oObj){
                    if(oObj.aData['beleg'] == null)
                        return null
                    return oObj.aData['beleg']['belegnummer'];
                },
                "aTargets": ["dt-position-th-beleg"]
            },
            { /* position.kunde */
                "mDataProp": "kunde.name", 
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-kunde"]
            }
            ]
        });
        
        if ($("#formBeleg").length) {
            $('#formBeleg').submit( function() {
                $("input", table_position.fnGetNodes()).each(function(){
                    if($(this).is(':checked')) {
                        $(this).appendTo('#formBeleg');
                    }
                });
            });
        }
        
        if ($("#checkall").length) {
            $('#checkall').click( function() {
                $('input', table_position.fnGetFilteredNodes()).attr('checked',this.checked);
            } );
        }
        
        if(table_row_click_action != null) {   
            $("#dt-position tbody tr").live("click",function(){
                var row_obj = table_position.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
        
        if ($("#from-date, #to-date").length) {
            var from_date = $('#from-date')
            $("#from-date").datepicker(); 
            $('#from-date').change( function() {
                table_position.fnDraw();
            } );
            
            var to_date = $('#to-date')
            $("#to-date").datepicker(); 
            $('#to-date').change( function() {
                table_position.fnDraw();
            } );
            
            if ($("#dt-datum-column").length) {
                var datum_column_index = $("#dt-datum-column").index("th")
                
                $.fn.dataTableExt.afnFiltering.push(
                    function (oSettings, aData, iDataIndex) {
                        var dStartDate = from_date.datepicker("getDate");
                        var dEndDate = to_date.datepicker("getDate");
                        var dCellDate = new Date(Date.parse(aData[datum_column_index]));
                        //var dCellDate = $.datepicker.parseDate(datepicker_locale.dateFormat, aData[datum_column_index]);
                    
                        if (dCellDate == null)
                            return false;
                    
                        if (dStartDate == null && dEndDate == null) {
                            return true;
                        }
                        else if (dStartDate == null && dCellDate < dEndDate) {
                            return true;
                        }
                        else if (dStartDate < dCellDate && dEndDate == null) {
                            return true;
                        }
                        else if (dStartDate < dCellDate && dCellDate < dEndDate) {
                            return true;
                        }
                        return false;
                    }
                    );
            }
        }
    }
    
            
    /**********************************
 * *  DataTable Konfiguration für Belege Table
 *********************************/
    
    if ($("#dt-beleg").length) {
        table_datasource = $("#dt-beleg").attr("datasource");
        table_row_click_action = $("#dt-beleg").attr("rowclickaction");
        table_filter = $("#dt-beleg").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        }); 
        if($("#dt-beleg").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-beleg").attr("kundeId")
            });
        }

        var table_beleg = $("#dt-beleg").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "bLengthChange": false,
            "iDisplayLength": -1,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "fnInitComplete": function(oSettings, json) {
                if ($("#remaining").length) {
                    if(json.remaining) {
                        $("#remaining").html(pvhm_formatNumber(json.remaining))
                    }
                }
            },
            "aoColumnDefs": [
            {   /* beleg.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-beleg-th-id"]
            },
            {   /* beleg.checkbox */
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "fnRender": function(oObj){
                    return "<input type='checkbox' name='selected' value='" + oObj.aData['id'] + "' />"
                },
                "aTargets": ["dt-beleg-th-checkbox"]
            },
            {   /* beleg.belegnummer */
                "mDataProp": "belegnummer", 
                "sWidth": "30px", 
                "aTargets": ["dt-beleg-th-belegnummer"]
            },
            { /* beleg.betrag */
                "mDataProp": "betrag",
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['betrag'])
                },
                "aTargets": ["dt-beleg-th-betrag"]
            },
            { /* beleg.bezahlt */
                "mDataProp": "bezahlt", 
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['bezahlt'])
                },
                "aTargets": ["dt-beleg-th-bezahlt"]
            },
            { /* beleg.offen (derived; not in model) */
                "sWidth": "30px",
                "bUseRendered": true,
                "fnRender": function(oObj){
                    var blgzbzln = parseFloat(oObj.aData['betrag']).toFixed(2) - parseFloat(oObj.aData['bezahlt']).toFixed(2)
                    return pvhm_formatNumber(blgzbzln.toString())
                },
                "aTargets": ["dt-beleg-th-offen"]
            },
            { /* beleg.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "40px", 
                "fnRender": function(oObj){
                    return pvhm_formatDate(oObj.aData['datum'])
                },
                "aTargets": ["dt-beleg-th-datum"]
            },
            { /* beleg.kunde */
                "sWidth": "60px",
                "bUseRendered": true,
                "fnRender": function(oObj){
                    return oObj.aData['kunde']['nachname'] + " " + oObj.aData['kunde']['vorname']
                },
                "aTargets": ["dt-beleg-th-kunde"]
            }
            ]
        });
        
        if ($("#formZahlung").length) {
            $('#formZahlung').submit(function() {
                var betraginput = parseFloat($("#betrag").val().replace( ",", "." ))
                var offenbetrag = parseFloat($("#remaining").text().replace( ",", "." ))
                if(betraginput > offenbetrag) {
                    alert('Zahlungsbetrag ist größer als der offene Gesamtbetrag!');
                    return false;
                }
                return true;
            });
        }
       
        if(table_row_click_action != null) {   
            $("#dt-beleg tbody tr").live("click",function(){
                var row_obj = table_beleg.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
        /**********************************
 * *  DataTable Konfiguration für Rechnungen Table
 *********************************/
    
    if ($("#dt-rechnung").length) {
        table_datasource = $("#dt-rechnung").attr("datasource");
        table_row_click_action = $("#dt-rechnung").attr("rowclickaction");
        table_filter = $("#dt-rechnung").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        }); 
        if($("#dt-rechnung").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-rechnung").attr("kundeId")
            });
        }

        var table_rechnung = $("#dt-rechnung").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "bLengthChange": false,
            "iDisplayLength": -1,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "fnInitComplete": function(oSettings, json) {
                if ($("#remaining").length) {
                    if(json.remaining) {
                        $("#remaining").html(pvhm_formatNumber(json.remaining))
                    }
                }
            },
            "aoColumnDefs": [
            {   /* rechnung.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-rechnung-th-id"]
            },
            {   /* rechnung.checkbox */
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "fnRender": function(oObj){
                    return "<input type='checkbox' name='selected' value='" + oObj.aData['id'] + "' />"
                },
                "aTargets": ["dt-rechnung-th-checkbox"]
            },
            {   /* rechnung.rechnungnummer */
                "mDataProp": "rechnungnummer", 
                "sWidth": "30px", 
                "aTargets": ["dt-rechnung-th-rechnungnummer"]
            },
            { /* rechnung.betrag */
                "mDataProp": "betrag",
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['betrag'])
                },
                "aTargets": ["dt-rechnung-th-betrag"]
            },
            { /* rechnung.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "40px", 
                "fnRender": function(oObj){
                    return pvhm_formatDate(oObj.aData['datum'])
                },
                "aTargets": ["dt-rechnung-th-datum"]
            },
            { /* rechnung.kunde */
                "sWidth": "60px",
                "bUseRendered": true,
                "fnRender": function(oObj){
                    return oObj.aData['kunde']['nachname'] + " " + oObj.aData['kunde']['vorname']
                },
                "aTargets": ["dt-rechnung-th-kunde"]
            }
            ]
        });
        
        if(table_row_click_action != null) {   
            $("#dt-rechnung tbody tr").live("click",function(){
                var row_obj = table_rechnung.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    /**********************************
 *  DataTable Konfiguration für Zahlung Table
 *********************************/
    
    if ($("#dt-zahlung").length) {
        table_datasource = $("#dt-zahlung").attr("datasource");
        table_row_click_action = $("#dt-zahlung").attr("rowclickaction");
        table_filter = $("#dt-zahlung").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        }); 
        if($("#dt-zahlung").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-zahlung").attr("kundeId")
            });
        }

        var table_zahlung = $("#dt-zahlung").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "bLengthChange": false,
            "iDisplayLength": -1,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "fnInitComplete": function(oSettings, json) {
                if ($("#paid").length) {
                    if(json.paid) {
                        $("#paid").html(pvhm_formatNumber(json.paid))
                    }
                }
            },
            "aoColumnDefs": [
            {   /* zahlung.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-zahlung-th-id"]
            },
            {   /* zahlung.checkbox */
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "fnRender": function(oObj){
                    return "<input type='checkbox' name='selected' value='" + oObj.aData['id'] + "' />"
                },
                "aTargets": ["dt-zahlung-th-checkbox"]
            },
            { /* zahlung.betrag */
                "mDataProp": "betrag",
                "sWidth": "30px",
                "bUseRendered": false,
                "fnRender": function(oObj){
                    return pvhm_formatNumber(oObj.aData['betrag'])
                },
                "aTargets": ["dt-zahlung-th-betrag"]
            },
            { /* zahlung.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "40px", 
                "fnRender": function(oObj){
                    return pvhm_formatDate(oObj.aData['datum'])
                },
                "aTargets": ["dt-zahlung-th-datum"]
            },
            { /* zahlung.kunde */
                "sWidth": "60px",
                "bUseRendered": true,
                "fnRender": function(oObj){
                    return oObj.aData['kunde']['nachname'] + " " + oObj.aData['kunde']['vorname']
                },
                "aTargets": ["dt-zahlung-th-kunde"]
            }
            ]
        });
       
        if(table_row_click_action != null) {   
            $("#dt-zahlung tbody tr").live("click",function(){
                var row_obj = table_zahlung.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    
    /**********************************
 *  DataTable Konfigurationen für Statistik "Erbrachte Leistungen/Medikamente"
 *********************************/
    
    if ($("#dt-erbracht-medikament").length) {
        var table_erbracht_medikament = $("#dt-erbracht-medikament").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "aaData": table_medikament_data,
            "bFilter": false,
            "iDisplayLength": -1,
            "bLengthChange": false,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "aoColumnDefs": [
            {
                "mDataProp": 2,
                "bSearchable": true,
                "aTargets": ["dt-erbracht-medikament-th-typ"]
            },
            {
                "mDataProp": 0,
                "bSortable": true,
                "sWidth": "70px", 
                "aTargets": ["dt-erbracht-medikament-th-menge"]
            },
            {
                "mDataProp": 1, 
                "bSortable": true,
                "sWidth": "150px", 
                "aTargets": ["dt-erbracht-medikament-th-summe"]
            }]
        });
    
        var table_erbracht_leistung = $("#dt-erbracht-leistung").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "aaData": table_leistung_data,
            "bFilter": false,
            "iDisplayLength": -1,
            "bLengthChange": false,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "aoColumnDefs": [
            {
                "mDataProp": 2,
                "bSearchable": true,
                "aTargets": ["dt-erbracht-leistung-th-typ"]
            },
            {
                "mDataProp": 0,
                "bSortable": true,
                "sWidth": "70px", 
                "aTargets": ["dt-erbracht-leistung-th-menge"]
            },
            {
                "mDataProp": 1, 
                "bSortable": true,
                "sWidth": "150px", 
                "aTargets": ["dt-erbracht-leistung-th-summe"]
            }]
        });
    }
    
    /**********************************
 *  DataTable Konfigurationen für Statistik "Tagesbericht"
 *********************************/

    if ($("#dt-tagesbericht-position").length) {
        var table_tb_position = $("#dt-tagesbericht-position").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "aaData": data_tagesbericht_position,
            "bFilter": false,
            "iDisplayLength": -1,
            "bLengthChange": false,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "aoColumnDefs": [
            {
                "mDataProp": 1,
                "bSearchable": true,
                "aTargets": ["dt-tagesbericht-position-th-typ"]
            },
            {
                "mDataProp": 0,
                "bSortable": true, 
                "aTargets": ["dt-tagesbericht-position-th-kunde"]
            },
            {
                "mDataProp": 2, 
                "bSortable": true,
                "aTargets": ["dt-tagesbericht-position-th-betrag"]
            }]
        });
        
        table_tb_position.fnAdjustColumnSizing();
    
        var table_tb_zahlung = $("#dt-tagesbericht-zahlung").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "aaData": data_tagesbericht_zahlung,
            "bFilter": false,
            "iDisplayLength": -1,
            "bLengthChange": false,
            "bScrollInfinite": true,
            "bScrollCollapse": true,
            "sScrollY": "400px",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "aoColumnDefs": [
            {
                "mDataProp": 0,
                "bSearchable": true,
                "aTargets": ["dt-tagesbericht-zahlung-th-kunde"]
            },
            {
                "mDataProp": 1,
                "bSortable": true,
                "aTargets": ["dt-tagesbericht-zahlung-th-betrag"]
            }]
        });
        
        table_tb_zahlung.fnAdjustColumnSizing();
    }

});


