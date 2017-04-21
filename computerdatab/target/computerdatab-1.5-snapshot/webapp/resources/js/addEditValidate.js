$(function() {
	console.log("Welcome to the jungle ! Just for Chrome");
});

function introD(e) {
	$('#discontinued').attr("min", $('#introduced').val())
}

function discD(e) {
	$('#introduced').attr("max", $('#discontinued').val())
	
	if ($('#discontinued').val() != "") {
		$('#introduced').attr("required", "")
	} else {
		$('#introduced').prop("required", "")
	}
}
