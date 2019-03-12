/* 
 * Funciones javascript para la pantalla principal de administraci�n.
 * 
 * Pantalla formada por las pesta�as: 
 * 
 * - Aplicaciones
 * - Unidades
 * 
 */

// -----------------------------> aqu� empieza el document.ready
$(document).ready(function() {

    // tab 1 - Aplicaciones
    $("#tab1_link").click(function() {
        $('#confirm_message').hide();
        loadApplications();
    });

    // tab 2 - Unidades
    $("#tab2_link").click(function() {
        $('#confirm_message').hide();
        loadUnits();
    });

    // tab 3 - Usuarios
    $("#tab3_link").click(function() {
        $('#confirm_message').hide();
        loadUsers();
    });

    // tab 4 - Truckstore
    $("#tab4_link").click(function() {
        $('#confirm_message').hide();
        loadCerts();
    });

    // tab 5 - Analisis
    $("#tab5_link").click(function() {
        $('#confirm_message').hide();
        loadScan();
    });

});
// ---> fin document.ready
