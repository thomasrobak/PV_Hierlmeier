$(function() {
    $("#datatable-example").dataTable({
        "bProcessing": true,
        "sAjaxSource": $("#datatable-example").attr("datasource")
    });
} );


