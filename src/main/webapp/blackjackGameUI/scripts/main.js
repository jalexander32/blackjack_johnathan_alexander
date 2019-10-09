var player, dealer;





function hit(player){
	/*var dataObject = m.request({
		method: "GET",
		url: "http://localhost:8080/blackjackGame/main"
	})
	.then(function(data){
		console.log("there is data.  it is the following: ");
		console.log(data);
	});*/
//	console.log("requesting");
//	var xhttp = new XMLHttpRequest();
//	xhttp.open("POST", "http://localhost:8080/hit");
//	xhttp.setRequestHeader('Content-Type', 'text/html');
//	xhttp.send();
//	
//	console.log(xhttp);
	
	xhttp = new XMLHttpRequest();
	xhttp.open("POST", "http://localhost:8080/blackjackGame");
	xhttp.send();
	
	console.log(xhttp);
	
//	xhttp = new XMLHttpRequest();
//	xhttp.open("POST", "http://localhost:8080/blackjackGame/");
//	xhttp.send();
//	
//	console.log(xhttp);
	
//	xhttp = new XMLHttpRequest();
//	xhttp.open("POST", "http://localhost:8080/");
//	xhttp.send();
//	
//	console.log(xhttp);
}
function stand(player){
	
}
function createCard(){
	var card = new Card('A', 'diamonds', 11, 1);
	card.render("dealerCards");
}
/*window.onload = function() {
	this.createCard();
};*/
window.onload = function(){
	hit(null);
}