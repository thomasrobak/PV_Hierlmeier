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

$(function() {
    var dt_locale_file
    switch(app_locale) {
        case "de":
            dt_locale_file = String("../txt/datatable_de.txt");
            break;
        case "en":
            dt_locale_file = String("../txt/datatable_en.txt");
            break;
        default:
            dt_locale_file = String("../txt/datatable_en.txt");
            break;
    }
    
    var table_kunde_datasource = $("#dt-kunde").attr("datasource");
    var table_kunde_row_click_action = $("#dt-kunde").attr("rowclickaction");
    var table_kunde_filter = $("#dt-kunde").attr("filter");
    
    var table_kunde = $("#dt-kunde").dataTable({
        "bAutoWidth": true,
        "bDeferRender": true,
        //"bStateSave": true,  @todo this enables the cookie
        "bProcessing": true,
        "sPaginationType": "two_button",
        "iCookieDuration": 60*60*12,
        "sCookiePrefix": "pvhm_datatable_",
        "sAjaxSource": table_kunde_datasource,
        "oLanguage": {
            "sUrl": dt_locale_file
        },
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            aoData.push( {
                "name": "filter", 
                "value": table_kunde_filter
            } );
            $.ajax( {
                "dataType": 'json', 
                "type": "POST", 
                "url": sSource, 
                "data": aoData, 
                "success": fnCallback
            } );
        },
        "aoColumnDefs": [
            {   /* Kunde.id */
            "mDataProp": "id",
            "bSearchable": false,
            "aTargets": ["dt-kunde-th-id"]
            },
            {   /* Kunde.nachname */
            "mDataProp": "nachname", 
            "sWidth": "70px", 
            "aTargets": ["dt-kunde-th-nachname"]
            },

            { /* Kunde.bemerkung */
            "mDataProp": "bemerkung", 
            "bSortable": false,
            "sWidth": "150px", 
            "aTargets": ["dt-kunde-th-bemerkung"]
            },

            { /* Kunde.wohnort */
            "mDataProp": "wohnort", 
            "bSortable": false, 
            "sWidth": "70px", 
            "aTargets": ["dt-kunde-th-wohnort"]
            },

            { /* Kunde.mwst */
            "mDataProp": "mwst", 
            "bSortable": false,
            "sWidth": "10px",
            "aTargets": ["dt-kunde-th-mwst"]
            },

            { /* Kunde.telefonnummer */
            "mDataProp": "telefonnummer", 
            "bSortable": false, 
            "sWidth": "60px", 
            "aTargets": ["dt-kunde-th-telefonnummer"]
            }      
        ]
    });
    
    $("#dt-kunde tbody tr").live("click",function(){
        var row_obj = table_kunde.fnGetData(this);
        var link = table_kunde_row_click_action.replace("_x_", row_obj.id);
        window.location.href = link;
    });
});


