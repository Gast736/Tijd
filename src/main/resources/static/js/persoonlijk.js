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
var periodes = ['201901', '201902', '201903', '201904', '201905', '201906', '201907', '201908', '201909', '201910', '201911', '201912'];
var kleuren = ['#007bff', '#6610f2', '#6f42c1', '#e83e8c', '#dc3545', '#fd7e14', '#ffc107', '#28a745', '#20c997', '#17a2b8', '#6c757d', '#343a40'];
var chartArray = [];

var arjan = [{
    label: 'Verlof',
    backgroundColor: '#d9e6f2',
    data: [
					0,
					40,
					0,
					0,
					0,
					40,
					0,
					120,
                    0,
					0,
					0,
                    40
				]

            }, {
    label: 'Financiële Managementinfo',
    backgroundColor: '#4080bf',
    data: [
					40,
					0,
					40,
					20,
					20,
					40,
					40,
					0,
                    40,
					40,
					60,
                    40
				]
			}, {
    label: 'DMO',
    backgroundColor: '#264d73',
    data: [
					80,
					80,
					80,
					100,
					100,
					40,
					80,
					0,
                    80,
					80,
					60,
                    40
				]
			}];


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
Deze functie zorgt voor toevoeging van het beheerdersmenu als de gebruiker de juiste rol heeft
*/
function addBeheerMenu() {
    if (rol == "beheerder") {
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

function haalProjecten() {
    $.ajax({
        url: "/projecten",
        method: 'GET',
        dataType: 'json',
        async: false,
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

function maakChartArray() {
    for (var i = 0; i < projecten.length; i++) {
        var chartObject = {
            label: projecten[i].naam,
            backgroundColor: kleuren[i],
            data: [
					0,
					0,
					0,
					50,
					0,
					150,
					0,
					0,
                    100,
					0,
					0,
                    0
				]

        }
        chartArray.push(chartObject);

    }
    alert(JSON.stringify(chartArray));
}

function haalUrenPerProjectPerMaand() {
    $.ajax({
        url: "/registraties/TotaalUrenPerMedewerkerPerProjectPerMaand",
        method: 'GET',
        data: {
            idmedewerker: medewerkerid,
            begindatum: "2019-01-01",
            einddatum: "2019-12-31",
        },
        dataType: 'json',
        success: function (data) {
            // LOGGING AAN
            console.log("HaalUrenPerProjectPerMaand: Het JSON object met " + data.length + " records, is ontvangen.");
            console.log(JSON.stringify(data));
            // Haal maximum projectnr
            var res = Math.max.apply(Math, data.map(function (o) {
                return o.projectid;
            }))
            console.log("het maximum projectnummer is: " + res);

        },
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}

var barChartData = {
    labels: ['Januari', 'Februari', 'Maart', 'April', 'Mei', 'Juni', 'Juli', 'Augustus', 'September', 'Oktober', 'November', 'December'],
    datasets: chartArray

};

function maakGrafiek() {
    var ctx = document.getElementById('canvas').getContext('2d');
    window.myBar = new Chart(ctx, {
        type: 'bar',
        data: barChartData,
        options: {
            title: {
                display: true,
                text: 'Uren per maand per project'
            },
            tooltips: {
                mode: 'index',
                intersect: false
            },
            responsive: true,
            scales: {
                xAxes: [{
                    stacked: true,
						}],
                yAxes: [{
                    stacked: true
						}]
            }
        }
    });
};


/* 
Event bij de eerste keer laden van de pagina
*/

$(document).ready(function () {
    console.log("pagina opnieuw geladen (document.ready)");
    checkCookie();
    addBeheerMenu();
    haalProjecten();
    maakChartArray();
    haalUrenPerProjectPerMaand();
    maakGrafiek();
});


/* OPLOSSING
- We halen eerst alle projecten op via HaalProjecten()
- Voor alle projecten maken we objecten aan in de array chartArray
- We loopen door de met haalUrenPerProjectPerMaand() opgehaalde JSON en vullen de uren in arjan
*/
