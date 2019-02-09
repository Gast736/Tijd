/* 
ALGEMENE TOELICHTING OP DE CODE

Dit javasscript zorgt voor de functionaliteit op de inlogpagina.
1) Ophalen medewerkersnamen
2) Tonen in het selectieveld inputName
3) Checken of het ingevoerde wachtwoord overeenkomt met het geregistreerde wachtwoord
*/

function toonMedewerkers(jsonrecordset) {
    /* Deze functie toont alle medewerkers:
    - doorloopt het json object
    - pakt daaruit de namen
    - en plaatst deze in het div-element ophalenMdw.*/
    var s = "";
    // LOGGING AAN
    console.log("Het JSON object met " + jsonrecordset.length + " records, is ontvangen.");

    for (var i = 0; i < jsonrecordset.length; i++) {
        s = s + "<option>" + jsonrecordset[i].naam + "</option>";
        // LOGGING AAN
        console.log("Medewerker: " + jsonrecordset[i].naam + " toegevoegd.");
    }
    s = '<select id="inputName" class="form-control" placeholder="Naam medewerker" required autofocus>' + s + "</select>";
    document.getElementById("ophalenMdw").innerHTML = s;
    return false;
}



function getData(surl, callback) {
    /*
    Deze functie begeleidt de aanroep van de restcontroller. Er moet nog wel wat aan exception handling worden toegevoegd.
    */
    $.ajax({
        url: surl,

        method: 'GET',
        dataType: 'json',
        success: callback,
        error: function (requestObject, error, errorThrown) {

            console.log("error thrown, add handler to exit gracefully");
        },
        timeout: 3000 //to do: research and develop further in combination with error handling
    });
    return false;
}


// De code die bij het gereed zijn van de pagina wordt uitgevoerd. Maakt gebruik van de twee hierboven gedefinieerde functies
$(document).ready(function () {



    getData("http://localhost:8080/medewerkers", toonMedewerkers);

    console.log("document is ready");
});

/* OUD CODEBLOK DAT NIET WERKTE.
Dit gaf de volgende error: Uncaught ReferenceError: Invalid left-hand side in assignment op de regel waarin het id submitResult wordt gevuld
$('#submitBtn').click(function (e) {
    e.preventDefault();
    $.ajax('/medewerker/wachtwoord', {
        data: {
            naam: $('#inputName').val(),
            wachtwoord: $('#inputPassword').val()
        }
    }).done(function (data) {
        $('#submitResult') = text(data);
    });
});
*/

$('#submitBtn').click(function (e) {
            console.log("Er is geklikt op de Submit button");
            console.log("We checken medewerker: " + $('#inputName').val() +" met wachtwoord: " + $('#inputPassword').val());
            e.preventDefault();
            $.ajax({
                url: "/medewerker/wachtwoord",
                data: {
                    naam: $('#inputName').val(),
                    wachtwoord: $('#inputPassword').val()
                },
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    if (data) {alert("Wachtwoord is juist");} else {alert("Wachtwoord is fout");}},
                error: function (requestObject, error, errorThrown) {

                    console.log("error thrown, add handler to exit gracefully");
                },
                timeout: 3000 //to do: research and develop further in combination with error handling
            });
            return false;
        });
