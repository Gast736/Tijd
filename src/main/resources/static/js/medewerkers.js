var medewerkers = [];

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

function vulMedewerkers(m) {
    medewerkers.push(m);
}

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
		<div  id='medewerker-` + medewerker.idmedewerker +`'>` + medewerker.idmedewerker + `</div>
		<div class="col-2">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.emailadres + `" id='emailadres-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-2">
		<input class="form-control" type="text" required maxlength="45"
		value="` + medewerker.naam + `" id='naam-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-2">
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
		<div class="col-1">
		<input class="form-control" type="date" required maxlength="45"
		value="` + medewerker.startdatum + `" id='startdatum-` + medewerker.idmedewerker +`'>
		</div>
		<div class="col-1">
		<input class="form-control" type="date" required maxlength="45"
		value="` + medewerker.einddatum + `" id='einddatum-` + medewerker.idmedewerker +`'>
		</div>
		<div><button type="button" class="btn btn-primary btnOpslaan" id='` + medewerker.idmedewerker + `'>Opslaan</button></div>
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
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De data kon niet worden verzonden.");
	} else {
	    location.reload();
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
	    $('#submitResult').addClass("alert alert-danger");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De data kon niet worden verzonden.");
	} else {
	    location.reload();
	    $('#submitResult').addClass("alert alert-success");
	    $('#submitResult').attr("role", "alert");
	    $('#submitResult').text("De ingevulde data is opgeslagen");
	}
    })
})

$("document").ready(function() {
    checkCookie();
    haalMedewerkers();
});
