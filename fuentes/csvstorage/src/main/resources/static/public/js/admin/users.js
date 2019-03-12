// funciones javascript para la administración de usuarios

//Para indicar si la pesta�a de usuarios está cargada
var isloadUsers = false;

// Carga los usuarios
function loadUsers() {
    if (!isloadUsers) {

        $.ajax({
            url : "admin/users",
            dataType : 'html',
            type : 'POST',
            success : function(model) {

                $("#tab3").html(model);
                limpiarCamposUsuario();

            },
            error : function error() {
                $("#tipoMensaje").val(4);
                $("#valorMensaje").val($("#generic_error").val());
                showMessage();

            }
        });

        isloadUsers = true;
    } else {
        reloadUsers();
    }
}

// Recarga los usuarios
function reloadUsers() {

    $.ajax({
        url : "admin/users/reload",
        dataType : 'html',
        type : 'POST',
        async : true,
        success : function(model) {

            $("#list-users").html(model);
            limpiarCamposUsuario();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });

}

//Guarda el usuario
function saveUser() {

    $('#confirm_message').hide();
    $("#usuario").prop('disabled', false);

    $.ajax({
        url : "admin/user/save",
        type : 'POST',
        dataType : 'html',
        data : $("#form_save_user").serialize(),
        async : true,
        success : function(data) {
            $("#usuario").prop('disabled', false);
            $("#edit-user").html(data);
            reloadUsers();
            limpiarCamposUsuario();

            $("#tipoMensaje").val(1);
            $("#valorMensaje").val("Usuario guardado con \u00E9xito");
            showMessage();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
            $("#usuario").prop('disabled', true);
        }
    });

}
function limpiarCamposUsuario() {

    $("#usuario").prop('disabled', false);
    $("#hiddenIdUser").val("");
    $("[id='id']").val("")
    $("[id='usuario']").val("")
    $("[id='password']").val("")
    $("[name='isAdmin']")[0].checked = false;

}

/**
 * Función que guarda el identificador del usuario y hace submit del formulario
 * para editar usuario.
 * 
 * @param idEndpoint
 */
function editUser(usuario) {

    $.ajax({
        url : "admin/users/edit",
        dataType : 'html',
        type : 'POST',
        async : true,
        data : {
            'usuario' : usuario
        },
        async : true,
        success : function(model) {
            $("#edit-user").html(model);
            $("#usuario").prop('disabled', true);
        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
            $("#usuario").prop('disabled', false);

        }
    });

}

/**
 * Preparación para el borrado de usuarios.
 * 
 * @param index
 * @param idUser
 */
function prepareDeleteUser(index, idUser) {
    $('#question_type').html(
            $("#btn_delete_user_" + index).data('confirm-question-type') + $("#usuario_user_" + index).text());
    $('#yes_button').attr('onclick', "deleteUser('" + idUser + "');");
    $('#confirm_message').show();
}

//Elimina el usuario 
// TODO A desarrollar, realizar mapping en AdminUserController 
function deleteUser(idUser) {

    $('#confirm_message').hide();
    $("#hiddenIdUser").val(idUser);
    $("#usuario").prop('disabled', false);
    $.ajax({
        url : "admin/user/delete",
        type : 'POST',
        dataType : 'json',
        data : {
            'id' : idUser
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
                reloadUsers();
                $("#usuario").prop('disabled', true);
            }

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });

}
