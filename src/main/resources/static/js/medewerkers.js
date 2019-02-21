function createSelectRole(idmedewerker, rol) {
	let options = ["Beheerder", "Afdelingshoofd", "Medewerker"];
	let select = `<td><select class='form-control' id='rollen-idmedewerker'>`;
	for (i=0; i<options.length;i++) {
		let s = "";
		if (rol.toUpperCase() == options[i].toUpperCase()) {
			s = `selected`;
			}
		select = select + `<option ` + s + `>` + options[i] + `</option>`;
		
	}
	select = select + `</select></td>`;
	return select;
}

function readMedewerkers() {
		$.ajax('/medewerkers').done(function(data) {
			$.each(data, function(idx, el) {
				let h = `
				<tr class="medewerker">
					<td>${el.idmedewerker}</td>
					<td><input class="form-control" id="emailadres-${el.idmedewerker}" type="text" required maxlength="45" value="${el.emailadres}"></td>
					<td><input class="form-control" id="naam-${el.idmedewerker}" type="text" required maxlength="45" value="${el.naam}"></td>
					<td><input class="form-control" id="wachtwoord-${el.idmedewerker}" type="text" required maxlength="45" value="${el.wachtwoord}"></td>
					<td><input class="form-control" id="team-${el.idmedewerker}" type="text" required maxlength="45" value="${el.team}"></td>` + createSelectRole(el.idmedewerker, el.rol) + `
					<td><input class="form-control" id="contracturen-${el.idmedewerker}" type="text" required maxlength="45" value="${el.contracturen}"></td>
					<td><input class="form-control" id="startdatum-${el.idmedewerker}" type="date" required maxlength="45" value="${el.startdatum}"></td>
					<td><input class="form-control" id="einddatum-${el.idmedewerker}" type="date" required maxlength="45" value="${el.einddatum}"></td>
					<td><button type="button" class="btn btn-primary btnOpslaan" id="${el.idmedewerker}">Opslaan</button></td>
				</tr>
				`;
				$("#mdwTbody").html($("#mdwTbody").html() + h);
			})
		}).fail(function(e) {
			console.log(e)
		})
	};

function insertMedewerker() {
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
    }).done(function(){
    	$("#mdwTbody").html($(""));
    }).done(function(){
    	readMedewerkers();
    })
};

$('#btnNewMdw').click(function(){
		insertMedewerker();
});

$('#mdwTbody').on('click', '.btnOpslaan', function(el){
	let idmedewerker = el.currentTarget.id;
	let email = $('#emailadres-' + idmedewerker).val();
	let naam = $('#naam-' + idmedewerker).val(); 
	let wachtwoord = $('#wachtwoord-' + idmedewerker).val();
	let team = $('#team-' + idmedewerker).val();
	let rol = $('select').val(); 
	let contracturen = $('#contracturen-' + idmedewerker).val(); 
	let startdatum = $('#startdatum-' + idmedewerker).val(); 
	let einddatum = $('#einddatum-' + idmedewerker).val();
	
	$.ajax('/changeMedewerker', {
		data: {
			idmedewerker: idmedewerker, 
			emailadres: email, 
			naam: naam, 
			wachtwoord: wachtwoord,
		    team: team, 
		    rol: rol, 
		    contracturen: contracturen, 
		    startdatum: startdatum, 
		    einddatum: einddatum
		},
		method: 'PUT'
	}).done(function(){
		console.log("medewerker " + idmedewerker + " is opgeslagen...");
	})
});

$('#mdwTbody').on('change', 'select', function(el){
	let targetId = el.currentTarget.id;
	let id = targetId.substr(targetId.indexOf("-"))
	console.log(id);
	createSelectRole(id, $(this).val());
})

$(document).ready(function(){
	readMedewerkers();
});

