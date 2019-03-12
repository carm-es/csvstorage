// funciones javascript para la administraci�n de usuarios

//Para indicar si la pesta�a de usuarios est� cargada
var isloadCerts = false;

// Carga los usuarios
function loadCerts() {
    if (!isloadCerts) {

        $.ajax({
            url : "admin/truststore",
            dataType : 'html',
            type : 'POST',
            success : function(model) {

                $("#tab4").html(model);
                limpiarCamposCertificado();

            },
            error : function error() {
                $("#tipoMensaje").val(4);
                $("#valorMensaje").val($("#generic_error").val());
                showMessage();

            }
        });

        isloadCerts = true;
    }

}

// Recarga los usuarios
function reloadCerts() {

    $.ajax({
        url : "admin/truststore/reload",
        dataType : 'html',
        type : 'POST',
        data : {},
        async : true,
        success : function(model) {

            $("#list-alias").html(model);
            limpiarCamposCertificado();
            loadCertificateDatatable();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });
    //}

}

function limpiarCamposCertificado() {

    $("#hiddenIdCert").val("");
    $("[id='id']").val("");
    $("[id='certificado']").val("");
    //$("[id='passwordKS']").val("");
    $("[id='alias']").val("");

}

function saveCert() {
    //var pass = $("[id='passwordKS']").val();
    var alias = $("[id='alias']").val();
    var certificado = $("#certificado")[0];
    $('#confirm_message').hide();

    //var pass = $("[id='passwordKS']").val();

    /*	console.log("error");
    	$("#tipoMensaje").val(4);
    	$("#valorMensaje").val("Es necesario introducir la password del almacén de certificados");
    	showMessage();
    }else{*/
    $.ajax({
        url : "admin/truststore/save",
        type : 'POST',
        processData : false,
        contentType : false,
        cache : false,
        enctype : 'multipart/form-data',
        data : new FormData($("#form_save_cert")[0]),
        async : true,
        success : function(data) {

            $("#edit-cert").html(data);
            reloadCerts();

            $("#tipoMensaje").val(1);
            $("#valorMensaje").val("Certificado guardado con \u00E9xito");
            showMessage();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
        }
    });
    //}

}

function prepareDeleteCert(index, alias) {
    $('#question_type').html($("#btn_delete_cert_" + index).data('confirm-question-type') + alias);
    $('#yes_button').attr('onclick', "deleteCert('" + alias + "');");
    $('#confirm_message').show();
}

function deleteCert(alias) {
    //var pass = $("[id='passwordKS']").val();

    /*if(pass == ""){
    	console.log("error");
    	$("#tipoMensaje").val(4);
    	$("#valorMensaje").val("Es necesario introducir la password del almacén de certificados");
    	showMessage();
    }else{*/

    $('#confirm_message').hide();
    $.ajax({
        url : "admin/truststore/delete",
        type : 'POST',
        dataType : 'json',
        data : {
            'alias' : alias
        //, 'pass' : pass
        },
        async : true,
        success : function(data) {

            var mensaje = eval(data);

            var error_level = mensaje.level;
            var error_message = mensaje.message;

            if (error_level != '' && error_message != '') {
                $("#tipoMensaje").val(error_level);
                $("#valorMensaje").val(error_message);
                showMessage();
            }

            if (error_level != '' && error_level == '1') {
                reloadCerts();
                $("#alias").prop('disabled', true);
            }

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });
    //}
}

function showCertificate(alias) {

    $.ajax({
        url : "admin/truststore/detail",
        type : 'GET',
        contentType : "application/json; charset=utf-8",
        dataType : 'json',
        data : {
            'alias' : alias
        },
        async : false,
        success : function(data) {

            if (data.alias == "null") {
                $("#popupAlias").val("");
            } else {
                $("#popupAlias").val(data.alias);
            }

            if (data.asunto == "null") {
                $("#popupAsunto").val("");
            } else {
                $("#popupAsunto").val(data.asunto);
            }

            if (data.emisor == "null") {
                $("#popupEmisor").val("");
            } else {
                $("#popupEmisor").val(data.emisor);
            }

            if (data.serialNumbre == "null") {
                $("#popupSerialNumber").val("");
            } else {
                $("#popupSerialNumber").val(data.serialNumbre);
            }

            if (data.validoDesde == "null") {
                $("#popupValidoDesde").val("");
            } else {
                var fechaDesde = new Date(data.validoDesde);
                var fechaDesdeFormat = fechaDesde.getDate() + '/' + (fechaDesde.getMonth() + 1) + '/'
                        + fechaDesde.getFullYear();
                $("#popupValidoDesde").val(fechaDesdeFormat);
            }

            if (data.validoHasta == "null") {
                $("#popupValidoHasta").val("");
            } else {
                var fechaHasta = new Date(data.validoHasta);
                var fechaHastaFormat = fechaHasta.getDate() + '/' + (fechaHasta.getMonth() + 1) + '/'
                        + fechaHasta.getFullYear();
                $("#popupValidoHasta").val(fechaHastaFormat);
            }

            $("popupAlias").attr("readonly", "readonly");
            $("#popupAsunto").attr("readonly", "readonly");
            $("#popupEmisor").attr("readonly", "readonly");
            $("#popupSerialNumber").attr("readonly", "readonly");
            $("#popupValidoDesde").attr("readonly", "readonly");
            $("#popupValidoHasta").attr("readonly", "readonly");

            $("#popupCertificate").removeClass("hidden");

            dialog = $("#popupCertificate").dialog({
                autoOpen : false,
                dialogClass : "no-close",
                height : 360,
                width : 490,
                modal : true,
                buttons : {
                    "Aceptar" : function() {
                        dialog.dialog("close");
                    }
                },
            });
            dialog.dialog("open");

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });
}