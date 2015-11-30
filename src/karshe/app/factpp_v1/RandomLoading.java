package karshe.app.factpp_v1;

import java.util.Random;

public class RandomLoading {
	String choices[] = {
			"Our servers are best known for facts.",
			"This fact looks like heavyweight. Bits are taking time.",
			"Factpp loading awesome fact for you.",
			"Shh... few packets were flewed. We bought them back.",
			"Loading. Till then think new features for our app."
			};
			int randNum;

	public RandomLoading() {
		Random randObj = new Random();
		this.randNum = randObj.nextInt(choices.length); 
	}
	public String giveMessage() {
		return choices[randNum];
	}
}
