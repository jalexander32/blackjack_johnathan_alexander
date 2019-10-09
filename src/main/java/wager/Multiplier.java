package wager;

import java.util.Map;
import utils.YamlParser;

public class Multiplier{
	//Retrieves the multipliers from configuration file
	static Map<String, Object> map = (Map<String, Object>) new YamlParser("game").getMapping().get("multipliers");
	
	public static enum Multi{
		BLACKJACK((double) map.get("blackjack")),
		WIN((double) map.get("win")),
		TIE((double) map.get("tie")),
		LOSS((double) map.get("loss"));
		
		private double multiplier;
		
		Multi(double multiplier){
			this.multiplier = multiplier;
		}
		
		public double getMultiplier(){
			return this.multiplier;
		}
	}
}