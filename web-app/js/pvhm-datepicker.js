$(function() {
  
    $.datepicker.setDefaults({
        showOn: 'both'
    });
    $.datepicker.setDefaults(datepicker_locale);
    
    if ($("#datepicker").length) {
        $("#datepicker").datepicker();
    }
});


