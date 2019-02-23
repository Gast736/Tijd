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
				<div>` + project.idproject + `</div>
				<div class="col-1">` + createSelectCategorie(project.idproject, project.categorie) + `</div>
				<div class="col-2"><input class="form-control" id="naam-` + project.idproject + `" type="text" required maxlength="45" value="` + project.naam + `"></div>
				<div class="col-2"><input class="form-control" id="opdrachtgever-` + project.idproject + `" type="text" required maxlength="45" value="` + project.opdrachtgever + `"></div>
				<div class="col-2"><input class="form-control" id="directie-` + project.idproject + `" type="text" required maxlength="45" value="` + project.directie + `"></div>
				<div class="col-2"><input class="form-control" id="startdatum-` + project.idproject + `" type="date" required value="` + project.startdatum + `"></div>
				<div class="col-2"><input class="form-control" id="einddatum-` + project.idproject + `" type="date" value="` + project.einddatum + `"></div>
				<div><button type="button" class="btn btn-primary btnOpslaan" id='` + project.idproject + `'>Opslaan</button></div>
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
	}).done(function(){
		location.reload();
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
	}).done(function(){
		console.log("Project" + idproject + " is opgeslagen");
	})
})

$("document").ready(function() {
	haalProjecten();
});
