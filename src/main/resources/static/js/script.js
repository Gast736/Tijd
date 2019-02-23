/*
Dit script bestaat nu uit 3 delen:
1) Berekening van de start en einddatum van de weekstaat o.b.v. gekozen jaar- en weeknummer
2) berekening kolomtotalen (per dag)
3) berekening rijtotalen

*/

// DECLARATIE GLOBALE VARIABELEN

var projecten = [];
var medewerkerid;
var begindatum;
var einddatum;
var day0; // maandag
var day1; // dinsdag
var day2; // woensdag
var day3; // donderdag
var day4; // vrijdag

/*
BLOK BEREKENING VAN KOLOMTOTALEN
*/

// Bereken totaal voor maandag
//$('#regform1').on('keyup', '.monday', function() {
$('#regform1').keyup(function () {
    var sum = 0;
    $('.monday').each(function () {
        sum += Number($(this).val());
    });
    $('#totMonday').val(sum);
})

// Bereken totaal voor dinsdag
$('#regform1').keyup(function () {
    var sum = 0;
    $('.tuesday').each(function () {
        sum += Number($(this).val());
    });
    $('#totTuesday').val(sum);
})

// Bereken totaal voor woensdag
$('#regform1').keyup(function () {
    var sum = 0;
    $('.wednesday').each(function () {
        sum += Number($(this).val());
    });
    $('#totWednesday').val(sum);
})

// Bereken totaal voor donderdag
$('#regform1').keyup(function () {
    var sum = 0;
    $('.thursday').each(function () {
        sum += Number($(this).val());
    });
    $('#totThursday').val(sum);
})

// Bereken totaal voor vrijdag
$('#regform1').keyup(function () {
    var sum = 0;
    $('.friday').each(function () {
        sum += Number($(this).val());
    });
    $('#totFriday').val(sum);
})

/*
BLOK BEREKENING VAN RIJTOTALEN
*/
// Bereken totaal voor rij0
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row0').each(function () {
        sum += Number($(this).val());
    });
    $('#r0total').val(sum);
})
// Bereken totaal voor rij1
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row1').each(function () {
        sum += Number($(this).val());
    });
    $('#r1total').val(sum);
})

// Bereken totaal voor rij2
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row2').each(function () {
        sum += Number($(this).val());
    });
    $('#r2total').val(sum);
})

// Bereken totaal voor rij3
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row3').each(function () {
        sum += Number($(this).val());
    });
    $('#r3total').val(sum);
})

// Bereken totaal voor rij4
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row4').each(function () {
        sum += Number($(this).val());
    });
    $('#r4total').val(sum);
})

// Bereken totaal voor rij5
$('#regform1').keyup(function () {
    var sum = 0;
    $('.row5').each(function () {
        sum += Number($(this).val());
    });
    $('#r5total').val(sum);
})

// Bereken totaal voor totalen
$('#regform1').keyup(function () {
    var sum = 0;
    $('.rowtotals').each(function () {
        sum += Number($(this).val());
    });
    $('#totTotal').val(sum);
})


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

/* Event voor wijzigen data na aanpassing jaar en/of datum
 */
$('.periodSelect').change(function () {
    haalDatums();
    // en bouw vervolgens het formulier op.... Ook hier moet ik iets met callbacks... die setTimeouts werken vertragend...
    setTimeout(bouwFormulierOp, 1500);
})

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
    var w= "";
    for (var i=1; i<54; i++) {
        w = w + "<option>"+i+"</option>";
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

                }; // we maken een project object. De ID mist nog in de controller
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


                /* DIT WERKTE NIET
                var o = '{"idmedewerker":"'+ medewerkerid + '","idproject":"' + projecten[i].projectid + '","startdatum":"' + $(this).attr("id").substring(2, 13) + '","uren":"' + $(this).val() +'"}';
                registratie.push(o);
                */
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
Een probeersel om de tabel met gevulde waarden om te zetten in JSON formaat. Gaat hier nu even  
via de invoeg toepassing: jquery.tabeltojson.min.js >> kan uiteraard ook anders. Ook afhankelijk
van keuze op welke wijze invoer formulier wordt opgebouwd (ErRe 13-2-2019, 20:16).
*/
$('#run').click(function () {
    var table = $('#test').tableToJSON();
    console.log(table);
    alert(JSON.stringify(table));
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
                    <strong>` + projecten[i].naam + `</strong>
                </div>
                <div style="display: none;">
                    <input type="hidden" class="form-control row` + i + ` hidden" id="r` + i + `hidden"` + projecten[i].id + `>
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` monday" id="r` + i + day0 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` tuesday" id="r` + i + day1 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` wednesday" id="r` + i + day2 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` thursday" id="r` + i + day3 + `" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control inputfield` + i + ` row` + i + ` friday" id="r` + i + day4 + `" placeholder="0">
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
                    <input type="text" class="form-control coltotals" id="totMonday" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totTuesday" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totWednesday" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totThursday" placeholder="0">
                </div>
                <div class="col-sm">
                    <input type="number" class="form-control coltotals" id="totFriday" placeholder="0">
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


$(document).ready(function () {
    console.log("pagina opnieuw geladen (document.ready)");
    checkCookie();
    vulWeken();
    haalDatums();
    haalProjecten();
    setTimeout(bouwFormulierOp, 500);
    console.log(projecten);
    setTimeout(haalRegistratie,1000);
});
