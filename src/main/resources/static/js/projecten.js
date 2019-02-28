var medewerkerid;
var contracturen;
var rol;

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
 * Functie om de categorie van een project in een listbox te zetten.
 * De huidige categorie wordt het actieve item
 */
function createSelectCategorie(idproject, categorie) {
    let options = ["Project", "Intern project", "Afwezigheid"];
    let select = `<td><select class='form-control' id='categorie-` + idproject +`'>`;
    for (let i = 0; i < options.length; i++) {
	let s = "";
	if (categorie.toUpperCase() == options[i].toUpperCase()) {
	    s = `selected`;
	}
	select = select + `<option ` + s + `>` + options[i] + `</option>`;

    }
    select = select + `</select></td>`;
    return select;
}

function haalProjecten() {
    $.ajax("/projecten").done(function(data) {
	$.each(data, function(idx, elem) {
	    let project = {
		    idproject : elem.idProject,
		    naam : elem.naam,
		    categorie : elem.categorie,
		    opdrachtgever: elem.opdrachtgever,
		    directie: elem.directie,
		    startdatum : elem.startdatum,
		    einddatum : elem.einddatum
	    };
	    let row = `
		<div class="row"  id='rij-` + project.idproject +`'>
		<div class="col-2">` + createSelectCategorie(project.idproject, project.categorie) + `</div>
		<div class="col-2"><input class="form-control" id="naam-` + project.idproject + `" type="text" required maxlength="45" value="` + project.naam + `"></div>
		<div class="col-2"><input class="form-control" id="opdrachtgever-` + project.idproject + `" type="text" required maxlength="45" value="` + project.opdrachtgever + `"></div>
		<div class="col-1"><input class="form-control" id="directie-` + project.idproject + `" type="text" required maxlength="45" value="` + project.directie + `"></div>
		<div class="col-2"><input class="form-control" id="startdatum-` + project.idproject + `" type="date" required value="` + project.startdatum + `"></div>
		<div class="col-2"><input class="form-control" id="einddatum-` + project.idproject + `" type="date" value="` + project.einddatum + `"></div>
		<div class="col"><button type="button" class="btn btn-primary btnOpslaan" id='` + project.idproject + `'>Opslaan</button></div>
		</div>
		`;
	    $("#projTbody").html($("#projTbody").html() + row);

	})
    });
}

function nieuwProject() {
    $.ajax('/nieuwProject', {
	data: {
	    naam: $('#naam').val(),
	    categorie: $('#categorie').val(),
	    opdrachtgever: $('#opdrachtgever').val(),
	    directie: $('#directie').val(),
	    startdatum: $('#startdatum').val(),
	    einddatum: $('#einddatum').val()
	},
	method: 'POST'
    }).done(function (res) {
	console.log('res', res);
	if (!res) {
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De gegevens konden niet worden verzonden.");
	} else {
	    $('#submitResult').addClass("alert alert-success");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De gegevens zijn opgeslagen");
	    let row = `
		<div class="row"  id='rij-` + res +`'>
		<div class="col-2">` + createSelectCategorie(res, $('#categorie').val()) + `</div>
		<div class="col-2"><input class="form-control" id="naam-` + res + `" type="text" required maxlength="45" value="` + $('#naam').val() + `"></div>
		<div class="col-2"><input class="form-control" id="opdrachtgever-` + res + `" type="text" required maxlength="45" value="` + $('#opdrachtgever').val() + `"></div>
		<div class="col-1"><input class="form-control" id="directie-` + res + `" type="text" required maxlength="45" value="` + $('#directie').val() + `"></div>
		<div class="col-2"><input class="form-control" id="startdatum-` + res + `" type="date" required value="` + $('#startdatum').val() + `"></div>
		<div class="col-2"><input class="form-control" id="einddatum-` + res + `" type="date" value="` + $('#einddatum').val() + `"></div>
		<div class="col"><button type="button" class="btn btn-primary btnOpslaan" id='` + res + `'>Opslaan</button></div>
		</div>
		`;
	    $('#projTbody').append(row);
	}
    })
}

$('#btnNewProject').click(function(){
    nieuwProject();
});

$('#projTbody').on('click', '.btnOpslaan', function(e){
    let idproject = e.target.id;
    let naam = $("#naam-"+idproject).val();
    let categorie = $("#categorie-"+idproject).val();
    let opdrachtgever = $("#opdrachtgever-"+idproject).val();
    let directie = $("#directie-"+idproject).val();
    let startdatum = $("#startdatum-"+idproject).val();
    let einddatum = $("#einddatum-"+idproject).val();
    $.ajax('/changeProject', {
	data: {
	    idproject: idproject,
	    naam: naam,
	    categorie: categorie,
	    opdrachtgever: opdrachtgever,
	    directie: directie,
	    startdatum: startdatum,
	    einddatum: einddatum
	},
	method: 'PUT'
    }).done(function (res) {
	console.log('res', res);
	if (!res) {
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De gegevens konden niet worden verzonden.");
	} else {
	    $('#submitResult').addClass("alert alert-success");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De gegevens zijn opgeslagen");
	}
    })
})

$("document").ready(function() {
    checkCookie();
    addBeheerMenu();
    haalProjecten();
});
