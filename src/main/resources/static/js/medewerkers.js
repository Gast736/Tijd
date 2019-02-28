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
 *  Functie om de rol van de medewerker in een listbox te zetten
 *  De huidige rol wordt het actieve item in de lijst
 */
function createSelectRole(idmedewerker, rol) {
    let options = ["Beheerder", "Afdelingshoofd", "Medewerker"];
    let select = `<td><select class='form-control' id='rol-` + idmedewerker +`'>`;
    for (let i = 0; i < options.length; i++) {
	let s = "";
	if (rol.toUpperCase() == options[i].toUpperCase()) {
	    s = `selected`;
	}
	select = select + `<option ` + s + `>` + options[i] + `</option>`;

    }
    select = select + `</select></td>`;
    return select;
}

function haalMedewerkers() {
    $.ajax("/medewerkers").done(function(data) {
	$.each(data, function(idx, elem) {
	    let medewerker = {
		    idmedewerker : elem.idmedewerker,
		    emailadres : elem.emailadres,
		    naam : elem.naam,
		    wachtwoord : elem.wachtwoord,
		    team: elem.team,
		    rol: elem.rol,
		    contracturen : elem.contracturen,
		    startdatum : elem.startdatum,
		    einddatum : elem.einddatum
	    };
	    let row = `
		<div class="row"  id='rij-` + medewerker.idmedewerker +`'>
		<div class="col-2">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.emailadres + `" id='emailadres-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.naam + `" id='naam-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.wachtwoord + `" id='wachtwoord-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.team + `" id='team-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-1">` + createSelectRole(medewerker.idmedewerker, medewerker.rol) + `
		</div>
		<div class="col-1">
		<input class="form-control" type="number" required maxlength="45"
		value="` + medewerker.contracturen + `" id='contracturen-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-2">
		<input class="form-control" type="date" required maxlength="45"
		value="` + medewerker.startdatum + `" id='startdatum-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-2">
		<input class="form-control" type="date" required maxlength="45"
		value="` + medewerker.einddatum + `" id='einddatum-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col"><button type="button" class="btn btn-primary btnOpslaan" id='` + medewerker.idmedewerker + `'>Opslaan</button></div>
		</div>
		`;
	    $("#mdwTbody").html($("#mdwTbody").html() + row);

	})
    });
}

function nieuweMedewerker() {
    $.ajax('/newMedewerker', {
	data: {
	    emailadres: $('#emailadres').val(),
	    naam: $('#naam').val(),
	    wachtwoord: $('#wachtwoord').val(),
	    team: $('#team').val(),
	    rol: $('#rol').val(),
	    contracturen: $('#contracturen').val(),
	    startdatum: $('#startdatum').val(),
	    einddatum: $('#einddatum').val()
	},
	method: 'POST'
    }).done(function (res) {
	console.log('res', res);
	if (!res) {
	    $('#submitResult').removeClass("alert alert-success");
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De data kon niet worden verzonden.");
	} else {
	    let row = `
		<div class="row"  id='rij-` + res +`'>
		<div class="col-2">
		<input class="form-control" type="text" required maxlength="45"
		value="` + $('#emailadres').val() + `" id='emailadres-` + res +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + $('#naam').val() + `" id='naam-` + res +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + $('#wachtwoord').val() + `" id='wachtwoord-` + res +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="text" required maxlength="45"
		value="` + $('#team').val() + `" id='team-` + res +`'>
		</div>
		<div class="col-1">` + createSelectRole(res, $('#rol').val()) + `
		</div>
		<div class="col-1">
		<input class="form-control" type="number" required maxlength="45"
		value="` + $('#contracturen').val() + `" id='contracturen-` + res +`'>
		</div>
		<div class="col-2">
		<input class="form-control" type="date" required maxlength="45"
		value="` + $('#startdatum').val() + `" id='startdatum-` + res +`'>
		</div>
		<div class="col-2">
		<input class="form-control" type="date" required maxlength="45"
		value="` + $('#einddatum').val() + `" id='einddatum-` + res +`'>
		</div>
		<div class="col"><button type="button" class="btn btn-primary btnOpslaan" id='` + res + `'>Opslaan</button></div>
		</div>
		`;
	    $('#mdwTbody').append(row);
	    $('#submitResult').removeClass("alert alert-danger");
	    $('#submitResult').addClass("alert alert-success");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De ingevulde data is opgeslagen");
	}
    })
}

$('#btnNewMdw').click(function(){
    nieuweMedewerker();
});

$('#mdwTbody').on('click', '.btnOpslaan', function(e){
    let idmedewerker = e.target.id;
    let emailadres = $("#emailadres-"+idmedewerker).val();
    let naam = $("#naam-"+idmedewerker).val();
    let wachtwoord = $("#wachtwoord-"+idmedewerker).val();
    let team = $("#team-"+idmedewerker).val();
    let rol = $("#rol-"+idmedewerker).val();
    let contracturen = $("#contracturen-"+idmedewerker).val();
    let startdatum = $("#startdatum-"+idmedewerker).val();
    let einddatum = $("#einddatum-"+idmedewerker).val();
    $.ajax('/changeMedewerker', {
	data: {
	    idmedewerker: idmedewerker,
	    emailadres: emailadres,
	    naam: naam,
	    wachtwoord: wachtwoord,
	    team: team,
	    rol: rol,
	    contracturen: contracturen,
	    startdatum: startdatum,
	    einddatum: einddatum
	},
	method: 'PUT'
    }).done(function (res) {
	console.log('res', res);
	if (!res) {
	    $('#submitResult').removeClass("alert alert-success");
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De data kon niet worden verzonden.");
	} else {
	    $('#submitResult').removeClass("alert alert-danger");
	    $('#submitResult').addClass("alert alert-success");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De ingevulde data is opgeslagen");
	}
    })
})

$("document").ready(function() {
    checkCookie();
    addBeheerMenu();
    haalMedewerkers();
});
