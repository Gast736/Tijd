/*
Het script is opgedeeld in blokken:
1) Declaratie globale variabelen
2) Geschreven functies
3) Events

*/

// 1) DECLARATIE GLOBALE VARIABELEN

var projecten = [];
var medewerkerid;
var begindatum;
var einddatum;
var day0; // maandag
var day1; // dinsdag
var day2; // woensdag
var day3; // donderdag
var day4; // vrijdag

// GESCHREVEN FUNCTIES

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
    }; // en voor de kolomtotalen
    for (var i = 0; i < 5; i++) {
        var sum = 0;
        $('.col' + i).each(function () {
            sum += Number($(this).val());
        });
        $('#totcol' + i).val(sum);
    }; // en voor het eindtotaal
    var sum = 0;
    $('.rowtotals').each(function () {
        sum += Number($(this).val());
    });
    $('#totTotal').val(sum);
    console.log("Totalen zijn berekend...");
}






function checkCookie() {
    var user = getCookie("user");

    if (user != "") {
        medewerkerid = getCookie("id");
        console.log("De id van ingelogde medewerker is: " + medewerkerid);
        $('#employeeName').text(user);
    } else {
        alert("U bent nog niet ingelogd en wordt teruggeleid naar de inlogpagina");
        window.location.href = "index.html"
    }
}

function vulWeken() {
    var w = "";
    for (var i = 1; i < 54; i++) {
        w = w + "<option>" + i + "</option>";
        $('#selWeek').html(w);
    }
}

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
                    idmedewerker: medewerkerid,
                    idproject: projecten[i].projectid,
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
Onderstaand script bouwt een editable table op op basis van de projectenquery
*/
function bouwTabelOp() {
    var s = "<table id=test><tr><th>Project</th><th>Maandag</th><th>Dinsdag</th><th>Woensdag</th><th>Donderdag</th><th>Vrijdag</th></tr>";
    for (var i = 0; i < projecten.length; i++) {
        s = s + "<tr><td>" + projecten[i].naam + "</td><td><div contenteditable>0</div></td><td><div contenteditable>0</div></td><td><div contenteditable>0</div></td><td><div contenteditable>0</div></td><td><div contenteditable>0</div></td></tr>";
    }
    s = s + "</table>";
    console.log(s);
    document.getElementById("tabelruimte").innerHTML = s;
}


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
                    <strong>` + projecten[i].naam + `</strong>
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
                    <input type="text" class="form-control coltotals" id="totcol0" placeholder="0">
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
            <div class="row" id ="submitResult">
            </div>`;
    //console.log(s);
    document.getElementById("regform1").innerHTML = s;
}

/*
EVENTS
*/

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
    vulWeken();
    haalDatums();
    haalProjecten();
    setTimeout(bouwFormulierOp, 500);
    console.log(projecten);
    setTimeout(haalRegistratie, 750);
    setTimeout(berekenTotalen, 1000);
});
