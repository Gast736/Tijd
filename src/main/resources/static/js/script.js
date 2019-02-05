/*
Dit script bestaat nu uit 3 delen:
1) Berekening van de start en einddatum van de weekstaat o.b.v. gekozen jaar- en weeknummer
2) berekening kolomtotalen (per dag)
3) berekening rijtotalen

*/

/*
BLOK BEREKENING START- EN EINDDATUM (WERKT NOG NIET.... MORGEN NIEUWE RONDE NIEUWE KANSEN)
*/


$('.periodSelect').click(function () {
    var begindatum = new Date();
    var begindatum = getDateOfISOWeek($('#selWeek').val,$('#selYear').val);
    console.log(begindatum);
    
var einddatum = new Date();
    einddatum.setDate(begindatum.getDate()+8);
    console.log(einddatum);
var returnString = "Van " + begindatum.getDate() + "-" + (begindatum.getMonth()+1) + "-" + begindatum.getFullYear() + " tot en met " + einddatum.getDate() + "-" + (einddatum.getMonth()+1) + "-" + einddatum.getFullYear();
    console.log(returnString);
$('#fromUntil').val(returnString);
})

function getDateOfISOWeek(w, y) {
    var datevar = new Date(y, 0, 1 + (w - 1) * 7);
    var dow = datevar.getDay();
    var ISOweekStart = datevar;
    if (dow <= 4)
        ISOweekStart.setDate(datevar.getDate() - datevar.getDay() + 1);
    else
        ISOweekStart.setDate(datevar.getDate() + 8 - datevar.getDay());
    return ISOweekStart;
}

/*
BLOK BEREKENING VAN KOLOMTOTALEN
*/

// Bereken totaal voor maandag
$('.monday').keyup(function () {
    var sum = 0;
    $('.monday').each(function() {
        sum += Number($(this).val());
    });
    $('#totMonday').val(sum);
})

// Bereken totaal voor dinsdag
$('.tuesday').keyup(function () {
    var sum = 0;
    $('.tuesday').each(function() {
        sum += Number($(this).val());
    });
    $('#totTuesday').val(sum);
})

// Bereken totaal voor woensdag
$('.wednesday').keyup(function () {
    var sum = 0;
    $('.wednesday').each(function() {
        sum += Number($(this).val());
    });
    $('#totWednesday').val(sum);
})

// Bereken totaal voor donderdag
$('.thursday').keyup(function () {
    var sum = 0;
    $('.thursday').each(function() {
        sum += Number($(this).val());
    });
    $('#totThursday').val(sum);
})

// Bereken totaal voor vrijdag
$('.friday').keyup(function () {
    var sum = 0;
    $('.friday').each(function() {
        sum += Number($(this).val());
    });
    $('#totFriday').val(sum);
})

/*
BLOK BEREKENING VAN RIJTOTALEN
*/

// Bereken totaal voor rij1
$('.row1').keyup(function () {
    var sum = 0;
    $('.row1').each(function() {
        sum += Number($(this).val());
    });
    $('#r1total').val(sum);
})

// Bereken totaal voor rij2
$('.row2').keyup(function () {
    var sum = 0;
    $('.row2').each(function() {
        sum += Number($(this).val());
    });
    $('#r2total').val(sum);
})

// Bereken totaal voor rij3
$('.row3').keyup(function () {
    var sum = 0;
    $('.row3').each(function() {
        sum += Number($(this).val());
    });
    $('#r3total').val(sum);
})

// Bereken totaal voor rij4
$('.row4').keyup(function () {
    var sum = 0;
    $('.row4').each(function() {
        sum += Number($(this).val());
    });
    $('#r4total').val(sum);
})

// Bereken totaal voor rij5
$('.row5').keyup(function () {
    var sum = 0;
    $('.row5').each(function() {
        sum += Number($(this).val());
    });
    $('#r5total').val(sum);
})

// Bereken totaal voor totalen
$('.form-control').keyup(function () {
    var sum = 0;
    $('.rowtotals').each(function() {
        sum += Number($(this).val());
    });
    $('#totTotal').val(sum);
})