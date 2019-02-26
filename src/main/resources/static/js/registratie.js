/*
Het script is opgedeeld in blokken:
1) Declaratie globale variabelen
2) Geschreven functies
3) Events

*/

// 1) DECLARATIE GLOBALE VARIABELEN

var projecten = [];
var medewerkerid;
var contracturen;
var rol;
var begindatum;
var einddatum;
var day0; // maandag
var day1; // dinsdag
var day2; // woensdag
var day3; // donderdag
var day4; // vrijdag
var eersteopenweek;

// GESCHREVEN FUNCTIES

/*
Deze functie checkt of er een cookie is. Zo niet, dan zorgt deze voor een redirect naar index.html. Zo ja, dan worden medewerkerID, rol en contracturen opgeslagen als globale variabelen.
*/
function checkCookie() {
    var user = getCookie("user");

    if (user != "") {
        medewerkerid = getCookie("id");
        rol = getCookie("rol");
        contracturen = getCookie("contracturen");
        console.log("De id van ingelogde medewerker is: " + medewerkerid);
        $('#employeeName').text(user);
    } else {
        alert("U bent nog niet ingelogd en wordt teruggeleid naar de inlogpagina");
        window.location.href = "index.html"
    }
}
/*
Algemene functie voor het uitlezen van aanwezige cookies
*/
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}


/*
Functie voor het berekenen van alle rij- en kolomtotalen. Kolommen zijn vast, projecten zijn variabel
*/
function berekenTotalen() { // eerst de rijtotalen
    for (var i = 0; i < projecten.length; i++) {
        var sum = 0;
        $('.row' + i).each(function () {
            sum += Number($(this).val());
        });
        $('#r' + i + 'total').val(sum);
    } // en voor de kolomtotalen
    for (var i = 0; i < 5; i++) {
        var sum = 0;
        $('.col' + i).each(function () {
            sum += Number($(this).val());
        });
        $('#totcol' + i).val(sum);
    } // en voor het eindtotaal
    var sum = 0;
    $('.rowtotals').each(function () {
        sum += Number($(this).val());
    });
    $('#totTotal').val(sum);
    $('#resterend').val(contracturen - sum);
    console.log("Totalen zijn berekend...");
}

/*
Deze functie zorgt voor toevoeging van het beheerdersmenu als de gebruiker de juiste rol heeft
*/
function addBeheerMenu() {
    if (rol.toLowerCase() == "beheerder") {
        $('#navbarList').append(`<li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg> Beheer</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown02">
                        <a class="dropdown-item" href="/projecten.html"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-folder-plus"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path><line x1="12" y1="11" x2="12" y2="17"></line><line x1="9" y1="14" x2="15" y2="14"></line></svg> Projecten</a>
                        <a class="dropdown-item" href="/medewerkers.html"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user-plus"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="8.5" cy="7" r="4"></circle><line x1="20" y1="8" x2="20" y2="14"></line><line x1="23" y1="11" x2="17" y2="11"></line></svg> Medewerkers</a>
                    </div>
                </li>`);
    } else {
        console.log("Op grond van de rol: " + rol + " wordt het beheermenu niet getoond.")
    }
}

/*
Deze functie vult de weken 1 t/m 53 voor het selectieveld op het registratieformulier
*/
function vulWeken() {
    var w = "";
    console.log("Vulweken start met variabele eersteopenweek: " + eersteopenweek)
    for (var i = 1; i < 54; i++) {
        if (i==eersteopenweek) {
            console.log("vulweken: zet veld op: " + i);
            w = w + "<option selected>" + i + "</option>";

        } else {
            w = w + "<option>" + i + "</option>";
        }
        $('#selWeek').html(w);
    }
}
/*
Deze functie haalt de datums op die horen bij de geselecteerde week
*/
function haalDatums() {
    $.ajax({
        url: "/daysOfWeek",
        method: 'GET',
        data: {
            week: $('#selWeek').val(),
            jaar: $('#selYear').val(),
        },
        dataType: 'json',
        success: function (data) {
            day0 = data[0].substring(0, 10);
            day1 = data[1].substring(0, 10);
            day2 = data[2].substring(0, 10);
            day3 = data[3].substring(0, 10);
            day4 = data[4].substring(0, 10);
            var d = "Van " + day0 + " tot " + day4;
            $('#fromUntil').text(d);
        },
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}

/*
Met deze functie wordt de eerste onvolledige week opgehaald. Deze wordt gebruikt bij het opstarten van registratie.html
*/
function haalEersteOpenRegistratie() {
    $.ajax({
        url: "/registraties/EersteOnvolledigeWeekPerMedewerker",
        method: 'GET',
        data: {
            idmedewerker: medewerkerid,
        },
        dataType: 'text',
        async: false,
        success: function (data) {
            console.log("Haal eerste onvolledige week: " +data)
            eersteopenweek = parseInt(data.substring(5, 7), 10); // Converteer de laatste twee tekens van string '2019-01' naar getal
        },
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}

/*
Met deze functie worden de projecten uit de database gehaald. We kiezen ervoor om alle projecten te tonen.
*/
function haalProjecten() {
    $.ajax({
        url: "/projecten",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // LOGGING AAN
            console.log("haalProjecten: Het JSON object met " + data.length + " records, is ontvangen.");
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                var project = {
                    projectid: data[i].idProject,
                    naam: data[i].naam

                };
                projecten.push(project); // Met push voeg je het element toe aan de array projecten
            }
        },
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}

/*
Met deze functies worden op basis van de medewerker ID en de startdatum van de week de aanwezige tijdregistraties in de database opgezocht.
*/
function haalRegistratie() {
    $.ajax({
        url: "/registraties/dezeweek",
        method: 'GET',
        data: {
            datum: day0,
            idmedewerker: medewerkerid,
        },
        dataType: 'json',
        success: function (data) {
            // LOGGING AAN
            console.log("HaalRegistratie: Het JSON object met " + data.length + " records, is ontvangen.");
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                // Hier vormen we de JSON om naar de velden uit het formulier. Het projectid wordt omgerekend (-1).
                var veldId = "r" + (data[i].idProject - 1) + data[i].startdatum;
                console.log("We schrijven naar veld " + veldId + " het volgende aantal uren: " + data[i].uren);
                $('#' + veldId).val(data[i].uren);
            }
        },
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}
/*
Met onderstaand script wordt het ingevulde formulier in JSON format naar de controller gestuurd.
*/
$('#submitBtn').click(function (e) {
    var rs = "";
    var registratie = [];
    console.log("Er is geklikt op de Submit button van het formulier");
    e.preventDefault();
    console.log("Start loop...");
    for (var i = 0; i < projecten.length; i++) {
        $('.inputfield' + i).each(function () {
            if ($(this).val()) {
                rs = rs + "Onder project " + projecten[i].naam + " met id " + projecten[i].projectid + ", zijn door medewerker met id " + medewerkerid + " op veld met id " + $(this).attr('id').substring(2, 13) + ", " + $(this).val() + " uren geschreven.\n";
                console.log(rs);

                var model = {
                    idmedewerker: medewerkerid.toString(),
                    idproject: projecten[i].projectid.toString(),
                    startdatum: $(this).attr('id').substring(2, 13),
                    uren: $(this).val()
                };
                registratie.push(model);

            }
        });
    }

    var regJson = JSON.stringify(registratie);
    console.log("We gaan versturen: " + regJson);

    $.ajax({
        type: 'POST',
        data: regJson,
        url: "/registratieUpdate",
        contentType: "application/json"
    }).done(function (res) {
        console.log('res', res);
        if (!res) {
            $('#submitResult').addClass("alert alert-danger");
            $('#submitResult').attr("role", "alert");
            $('#submitResult').text("De data kon niet worden verzonden.");
        } else {
            $('#submitResult').addClass("alert alert-success");
            $('#submitResult').attr("role", "alert");
            $('#submitResult').text("De ingevulde data is opgeslagen");
        }
    });


});

/*
Onderstaand script bouwt een formulier op op basis van de projectenquery
*/
function bouwFormulierOp() {
    var s = `            <div id="formheaders" class="row">
                <div class="col-sm-3">
                    <strong>Project</strong>
                </div>
                <div class="col-sm">
                    <strong>Maandag</strong>
                </div>
                <div class="col-sm">
                    <strong>Dinsdag</strong>
                </div>
                <div class="col-sm">
                    <strong>Woensdag</strong>
                </div>
                <div class="col-sm">
                    <strong>Donderdag</strong>
                </div>
                <div class="col-sm">
                    <strong>Vrijdag</strong>
                </div>
                <div class="col-sm">
                    <strong>Totaal</strong>
                </div>
            </div>`;
    for (var i = 0; i < projecten.length; i++) {
        // per project een rij aanmaken d.m.v. lus
        s = s + `<div id="row` + i + `" class="row">
                <div class="col-sm-3">
                    ` + projecten[i].naam + `
                </div>
                <div style="display: none;">
                    <input type="hidden" class="form-control row` + i + ` hidden" id="r` + i + `hidden"` + projecten[i].id + `>
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` monday col0" id="r` + i + day0 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` tuesday col1" id="r` + i + day1 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` wednesday col2" id="r` + i + day2 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` thursday col3" id="r` + i + day3 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` friday col4" id="r` + i + day4 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control rowtotals" id="r` + i + `total" placeholder="0">
                </div>
            </div>`;

    }
    // totaalrij toevoegen
    s = s + `<br>
            <div id="coltotal" class="row">
                <div class="col-sm-3">
                    <strong>Totaal</strong>
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totcol0" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totcol1" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totcol2" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totcol3" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totcol4" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totTotal" placeholder="0">
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-sm-3">
                    <strong>Nog te schrijven</strong>
                </div>
                <div class="col-sm">
                </div>
                <div class="col-sm">
                </div>
                <div class="col-sm">
                </div>
                <div class="col-sm">
                </div>
                <div class="col-sm">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control restant" id="resterend" placeholder="0">
                </div>
             </div>   
            <br>
            <div class="row" id ="submitResult">
            </div>`;
    //console.log(s);
    document.getElementById("regform1").innerHTML = s;
}

/*
EVENTS
*/

/* 
Event voor wijzigingen in het formulier
*/
$('#regform1').keyup(function () {
    berekenTotalen();
})

/* 
Event voor wijzigen data na aanpassing jaar en/of datum
*/
$('.periodSelect').change(function () {
    haalDatums();
    // en bouw vervolgens het formulier op.... Ook hier moet ik iets met callbacks... die setTimeouts werken vertragend...
    setTimeout(bouwFormulierOp, 500);
    setTimeout(haalRegistratie, 750);
    setTimeout(berekenTotalen, 1000);
})
/* 
Event bij de eerste keer laden van de pagina
*/

$(document).ready(function () {
    console.log("pagina opnieuw geladen (document.ready)");
    checkCookie();
    haalEersteOpenRegistratie();
    addBeheerMenu();
    vulWeken();
    setTimeout(haalDatums, 250);
    haalProjecten();
    setTimeout(bouwFormulierOp, 500);
    setTimeout(haalRegistratie, 750);
    setTimeout(berekenTotalen, 1000);
});
