$(function() {
	console.log("Welcome to the jungle !");
});

function introD(e) {
	$('#discontinued').attr("min", $('#introduced').val())
}

function discD(e) {
	$('#introduced').attr("max", $('#discontinued').val())
}
