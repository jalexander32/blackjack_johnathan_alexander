class Card{
	
	constructor(cardFace, cardSuit, mainValue, altValue){
		this.cardFace = cardFace;
		this.cardSuit = cardSuit;
		this.mainValue = mainValue;
		this.altValue = altValue;
		
		if(this.cardFace == 'A')
			this.altValue = null;
	}
	
	render(loc){
		var card = document.createElement('div')
		var value = document.createElement('div');
		var suit = document.createElement('div');
		
		card.className = 'card';
		value.className = 'value';
		suit.className = 'suit ' + this.cardSuit;
		
		value.innerHTML = this.cardFace;
		card.appendChild(value);
		card.appendChild(suit);
		
		document.getElementById(loc).appendChild(card);
	}
}