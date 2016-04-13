package impl;

import java.io.IOException;
import java.util.Iterator;

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
	private InputVariable posX;
	private OutputVariable motorFrente, motorFundo;
	
	private Action action;
	private double dPosx, dPosy, minDistance = 0;
	private int nearUid;

	public Action process(GameState gamestate) {
//		GameObject object = findNearRock(gamestate);
//		if(object != null) {
//			if(gamestate != null) {
//				gamestate.log("nearUid = " + nearUid + " e Distance = " + minDistance);
				gamestate.log("x = " + getPosx() + ", y = " + getPosy());
				gamestate.log("Velx = "  + getVelx() + ", Vely = " + getVely());
//				gamestate.log("radius = " + getRadius() + ", getAng = " + getAng() + ", getVelang = " + getVelang());
//			}
//			action = new Action(0, 1, 0, 0); 
//		}
//		else {
//			gamestate.log("ABC");
//			action = new Action(0, 0, 0, 0); 
//		}
				
				
		return process();
	}
	
	private Action process() {
		posX.setInputValue(getPosx());
		engine.process();
		return new Action(0, Math.round(motorFrente.getOutputValue()), Math.round(motorFundo.getOutputValue()), 0);
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
        
        posX = new InputVariable();
        posX.setName("posX");
        posX.setRange(-30.0, 30.0);
        posX.addTerm(new Triangle("EXTREMO_ESQUERDA", -30.0, -20.0, -10.0));
        posX.addTerm(new Triangle("ESQUERDA", -20.0, -10.0, 0.0));
        posX.addTerm(new Triangle("MEIO", -10.0, 0.0, 10.0));
        posX.addTerm(new Triangle("DIREITA", 0.0, 10.0, 20.0));
        posX.addTerm(new Triangle("EXTREMO_DIREITA", 10.0, 20.0, 30.0));
        engine.addInputVariable(posX);
        
        motorFrente = new OutputVariable();
        motorFrente.setName("motorFrente");
        motorFrente.setRange(-1.2, 1.2);
        motorFrente.setDefaultValue(0.0);
        motorFrente.addTerm(new Triangle("ESQUERDA", -1.2, -0.6, 0.0));
        motorFrente.addTerm(new Triangle("NADA", -0.6, 0.0, 0.6));
        motorFrente.addTerm(new Triangle("DIREITA", 0.0, 0.6, 1.2));
        engine.addOutputVariable(motorFrente);
        
        motorFundo = new OutputVariable();
        motorFundo.setName("motorFundo");
        motorFundo.setRange(-1.2, 1.2);
        motorFundo.setDefaultValue(0.0);
        motorFundo.addTerm(new Triangle("ESQUERDA", -1.2, -0.6, 0));
        motorFundo.addTerm(new Triangle("NADA", -0.6, 0.0, 0.6));
        motorFundo.addTerm(new Triangle("DIREITA", 0.0, 0.6, 1.2));
        engine.addOutputVariable(motorFundo);
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.addRule(Rule.parse("if posX is EXTREMO_ESQUERDA then motorFrente is ESQUERDA and motorFundo is ESQUERDA", engine));
        ruleBlock.addRule(Rule.parse("if posX is ESQUERDA then motorFrente is NADA and motorFundo is NADA", engine));
        ruleBlock.addRule(Rule.parse("if posX is MEIO then motorFrente is DIREITA and motorFundo is DIREITA", engine));
        ruleBlock.addRule(Rule.parse("if posX is DIREITA then motorFrente is NADA and motorFundo is NADA", engine));
        ruleBlock.addRule(Rule.parse("if posX is EXTREMO_DIREITA then motorFrente is DIREITA and motorFundo is DIREITA", engine));
        
        engine.addRuleBlock(ruleBlock);
        
        engine.configure("Minimum", "", "Minimum", "Maximum", "Centroid");
        
        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status)) {
            throw new RuntimeException("Engine not ready. "
                    + "The following errors were encountered:\n" + status.toString());
        }
	}
	
	public static void main(String[] args) throws IOException {        
		GameState game = new GameState(new AllanArielBot());
		game.connect();
	}
}
