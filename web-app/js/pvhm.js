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

dt_formatDate = function(parsethis) {
    if(parsethis == null)
        return null;
    if(app_locale == 'de')
        return $.datepicker.formatDate($.datepicker.regional['de'].dateFormat, new Date(Date.parse(parsethis)));
    else
        return $.datepicker.formatDate($.datepicker.regional[''].dateFormat, new Date(Date.parse(parsethis)));
}

$(function() {

 /**************
 * Datepicker Config and init
 *************/

    $.datepicker.regional['de'] = {
        closeText: 'schließen',
        prevText: '&#x3c;zurück',
        nextText: 'Vor&#x3e;',
        currentText: 'heute',
        monthNames: ['Januar','Februar','März','April','Mai','Juni',
        'Juli','August','September','Oktober','November','Dezember'],
        monthNamesShort: ['Jan','Feb','Mär','Apr','Mai','Jun',
        'Jul','Aug','Sep','Okt','Nov','Dez'],
        dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
        dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
        dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
        weekHeader: 'Wo',
        dateFormat: 'dd.mm.yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
   
    var datepicker_locale

    switch(app_locale) {
        case 'de':
            datepicker_locale = $.datepicker.regional['de'];
            break;
        case 'en':
            datepicker_locale = $.datepicker.regional[''];
            break;
        default:
            datepicker_locale = $.datepicker.regional['de'];
            break;
    }
    
    $.datepicker.setDefaults({
        showOn: 'both'
    });
    $.datepicker.setDefaults(datepicker_locale);
    
    if ($("#datepicker").length) {
        $("#datepicker").datepicker();
    }
    
 /**************
 * misc configs and inits
 *************/
    if ($("#typ").length) {
        if ($("#preis").val() == "" || $("#preis").val() == null) {
            $("#typ").change(function() {
                $.ajax({
                    "dataType": 'json', 
                    "type": "GET", 
                    "url": selectbox_datasource,
                    "data": {
                        id: $("#typ").val()
                        },
                    "success": function(json) {
                        $("#preis").val(json.preis)
                    }
                });
            });
        }
    }
    

 /********************
 * datatable config vars
 **********/
    var dt_locale_file = app_base_dir + "txt/datatable_?.txt".replace("?", app_locale);
    var table_datasource
    var table_row_click_action
    var table_filter
    var table_request_params

    
    /**********************************
 *
 *  DataTable Konfiguration für Kunden Table
 *                                
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

            { /* kunde.telefonnummer */
                "mDataProp": "telefonnummer", 
                "bSortable": false, 
                "sWidth": "60px", 
                "aTargets": ["dt-kunde-th-telefonnummer"]
            }      
            ]
        });
    
        if(table_row_click_action != null) {   
            $("#dt-kunde tbody tr").live("click",function(){
                var row_obj = table_kunde.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    
    /**********************************
 *
 *  DataTable Konfiguration für Positionen Table
 *                                
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
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
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
                "mDataProp": "checkbox",
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
            { /* position.preis */
                "mDataProp": "preis", 
                "bSortable": false,
                "sWidth": "30px",
                "aTargets": ["dt-position-th-preis"]
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
                    return dt_formatDate(oObj.aData['datum'])
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
                $("input", table_position_interactive.fnGetNodes()).each(function(){
                    if($(this).is(':checked')) {
                        $(this).appendTo('#formBeleg');
                    }
                });
            });
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
 *
 *  DataTable Konfiguration für Belege Table
 *                                
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
                        $("#remaining").html(json.remaining)
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
                "mDataProp": "checkbox",
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
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
                "aTargets": ["dt-beleg-th-betrag"]
            },
            { /* beleg.summeBezahlt */
                "mDataProp": "summeBezahlt", 
                "sWidth": "30px", 
                "aTargets": ["dt-beleg-th-summebezahlt"]
            },
            { /* beleg.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "40px", 
                "fnRender": function(oObj){
                    return dt_formatDate(oObj.aData['datum'])
                },
                "aTargets": ["dt-beleg-th-datum"]
            },
            { /* beleg.kunde */
                "mDataProp": "kunde", 
                "sWidth": "60px", 
                "aTargets": ["dt-beleg-th-kunde"]
            }
            ]
        });
        
        if ($("#formZahlung").length) {
            $('#formZahlung').submit(function() {
                var betraginput = $("#betrag").val()
                var offenbetrag = $("#remaining").text()
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
});


