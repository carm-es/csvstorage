// funciones javascript para la el análisis de los tamaños de los ficheros

function loadScan() {

    $.ajax({
        url : "admin/scan",
        dataType : 'html',
        type : 'POST',
        success : function(model) {

            $("#tab5").html(model);

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });

}

//Guarda los tamaños de lso ficheros
function scanAll() {

    $('#confirm_message').hide();

    $.ajax({
        url : "admin/scan/scanAll",
        type : 'POST',
        dataType : 'html',
        data : $("#form_scan").serialize(),
        async : true,
        success : function(data) {
            $("#edit-scan").html(data);
            reloadUsers();
            limpiarCamposUsuario();

            $("#tipoMensaje").val(1);
            $("#valorMensaje").val("Analisis lanzado con \u00E9xito");
            showMessage();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
        }
    });

}
