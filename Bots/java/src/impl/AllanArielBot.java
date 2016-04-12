package impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import com.fuzzylite.Engine;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import bot_interface.Action;
import bot_interface.BotBase;
import bot_interface.GameObject;
import bot_interface.GameState;
import bot_interface.Rock;

public class AllanArielBot extends BotBase {
	
	private Engine engine;
	private InputVariable deltaXFromLeft, deltaXFromRight, deltaYFromTop, deltaYFromBottom;
	private OutputVariable sideThrustFront, sideThrustBack;
	
	private Action action;
	private double dPosx, dPosy, minDistance = 0;
	private int nearUid;

	public Action process(GameState gamestate) {
		GameObject object = findNearRock(gamestate);
		if(object != null) {
//			action = new Action(0, 1, 1, 0);
			if(gamestate != null) {
				gamestate.log("nearUid = " + nearUid + " e Distance = " + minDistance);
			}
		}
		
//		gamestate.log("ABC");
		action = new Action(0, 0, 0, 0); 
		return action;
	}
	
	private GameObject findNearRock(final GameState gamestate) {
		minDistance = 0;
		
		if(gamestate.getRocks().size() > 0) {
			
			Iterator<Integer> it = gamestate.getRocks().keySet().iterator();
			while(it.hasNext()) {
				Integer key = it.next();
				
				Rock rock = gamestate.getRocks().get(key);
				dPosx = (rock.getPosx() + rock.getRadius()) - getPosx();
				dPosy = (rock.getPosy() + rock.getRadius()) - getPosy();
				
//				if(minDistance == 0 || minDistance > dPosx * dPosx + dPosy * dPosy) {
//					minDistance = Math.sqrt(dPosx * dPosx + dPosy * dPosy);
//					nearUid = rock.getUid();
//				}
				
				if(minDistance == 0 || minDistance > dPosx) {
					minDistance = dPosx;
					nearUid = rock.getUid();
				}
			} 
			return gamestate.getRocks().get(nearUid);
		}
		return null;
	}
	
	public AllanArielBot() {
		engine = new Engine();
        engine.setName("AllanArielBot");
        
        deltaXFromLeft = new InputVariable();
        deltaXFromLeft.setName("deltaXFromLeft");
        deltaXFromLeft.setRange(-60.0, 0.0);
        deltaXFromLeft.addTerm(new Triangle("LOW", -20.0, -10.0, 0.0));
        deltaXFromLeft.addTerm(new Triangle("MEDIUM", -40.0, -30.0, -20.0));
        deltaXFromLeft.addTerm(new Triangle("HIGHT", -60.0, -50.0, -40.0));
        engine.addInputVariable(deltaXFromLeft);
        
        deltaXFromRight = new InputVariable();
        deltaXFromRight.setName("deltaXFromRight");
        deltaXFromRight.setRange(0.0, 60.0);
        deltaXFromRight.addTerm(new Triangle("LOW", 0.0, 10.0, 20.0));
        deltaXFromRight.addTerm(new Triangle("MEDIUM", 20.0, 30.0, 40.0));
        deltaXFromRight.addTerm(new Triangle("HIGHT", 40.0, 50.0, 60.0));
        engine.addInputVariable(deltaXFromRight);
        
        deltaYFromTop = new InputVariable();
        deltaYFromTop.setName("deltaYFromTop");
        deltaYFromTop.setRange(-60.0, 0.0);
        deltaYFromTop.addTerm(new Triangle("LOW", -20.0, -10.0, 0.0));
        deltaYFromTop.addTerm(new Triangle("MEDIUM", -40.0, -30.0, -20.0));
        deltaYFromTop.addTerm(new Triangle("HIGHT", -60.0, -50.0, -40.0));
        engine.addInputVariable(deltaYFromTop);
        
        deltaYFromBottom = new InputVariable();
        deltaYFromBottom.setName("deltaYFromBottom");
        deltaYFromBottom.setRange(0.0, 60.0);
        deltaYFromBottom.addTerm(new Triangle("LOW", 0.0, 10.0, 20.0));
        deltaYFromBottom.addTerm(new Triangle("MEDIUM", 20.0, 30.0, 40.0));
        deltaYFromBottom.addTerm(new Triangle("HIGHT", 40.0, 50.0, 60.0));
        engine.addInputVariable(deltaYFromBottom);
        
        sideThrustFront = new OutputVariable();
        sideThrustFront.setName("sideThrustFront");
        sideThrustFront.setRange(-1.0, 1.0);
        sideThrustFront.setDefaultValue(Double.NaN);
        sideThrustFront.addTerm(new Triangle("LOW", -1.0, -1.0, 0));
        sideThrustFront.addTerm(new Triangle("MEDIUM", -1.0, 0.0, 1.0));
        sideThrustFront.addTerm(new Triangle("HIGH", 0.0, 1.0, 1.0));
        engine.addOutputVariable(sideThrustFront);
        
        sideThrustBack = new OutputVariable();
        sideThrustBack.setName("sideThrustBack");
        sideThrustBack.setRange(-1.0, 1.0);
        sideThrustBack.setDefaultValue(Double.NaN);
        sideThrustBack.addTerm(new Triangle("LOW", -1.0, -1.0, 0));
        sideThrustBack.addTerm(new Triangle("MEDIUM", -1.0, 0.0, 1.0));
        sideThrustBack.addTerm(new Triangle("HIGH", 0.0, 1.0, 1.0));
        engine.addOutputVariable(sideThrustBack);
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.addRule(Rule.parse("if deltaXFromLeft is LOW and (deltaYFromTop is LOW or deltaYFromBottom is LOW then sideThrustFront is HIGH and sideThrustBack is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);
	}
	
	public static void main(String[] args) throws IOException {        
		GameState game = new GameState(new AllanArielBot());
		game.connect();
	}
}
