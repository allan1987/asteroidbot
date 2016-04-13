package impl;

import java.io.IOException;

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
	private InputVariable posX, posY, velX, velY, diffX, diffY;
	private OutputVariable motorFrente, motorFundo;
	
	private Action action;
	private double dPosx, dPosy, minDistance = 0;
	private int nearUid = 0;

	public Action process(GameState gamestate) {
		GameObject object = findNearRock(gamestate);
		if(object != null) {
			if(gamestate != null) {
				gamestate.log("nearUid = " + nearUid + " e Distance = " + minDistance);
				gamestate.log("menor x = " + object.getPosx() + ", y = " + object.getPosy());
//				gamestate.log("ship x = " + getPosx());
//				gamestate.log("Velx = "  + getVelx() + ", Vely = " + getVely());
//				gamestate.log("getAng = " + getAng() + ", getVelang = " + getVelang());
			}
			return process(object);
		}
		else {
//			gamestate.log("ABC");
			return new Action(0, 0, 0, 0); 
		}
				
//			return action;	
		
	}
	
	private Action process(GameObject object) {
		posX.setInputValue(getPosx());
		velX.setInputValue(getVelx());
		diffX.setInputValue(object.getPosx() + object.getRadius() -  getPosx());
		diffY.setInputValue(object.getPosy() + object.getRadius() -  getPosy());
		engine.process();
		return new Action(0, Math.round(motorFrente.getOutputValue()), Math.round(motorFundo.getOutputValue()), 0);
	}
	
	private GameObject findNearRock(final GameState gamestate) {
		minDistance = 0;
		nearUid = 0;
		
		if(gamestate.getRocks().size() > 0) {
			
			for(Integer key : gamestate.getRocks().keySet()) {
				Rock rock = gamestate.getRocks().get(key);
				dPosx = Math.abs(rock.getPosx() - getPosx());
				dPosy = Math.abs(rock.getPosy() - getPosy());
				
				gamestate.log("rock uid = " + rock.getUid() + ", x = " + rock.getPosx() + ", y = " + rock.getPosy());
				gamestate.log("bot x = " + getPosx() + ", y = " + getPosy());
				
				if(minDistance == 0 || minDistance > dPosx * dPosx + dPosy * dPosy) {
					minDistance = dPosx * dPosx + dPosy * dPosy;
					nearUid = rock.getUid();
				}
			}
			
//			Iterator<Integer> it = gamestate.getRocks().keySet().iterator();
//			while(it.hasNext()) {
//				Integer key = it.next();
//				
//				Rock rock = gamestate.getRocks().get(key);
//				dPosx = (rock.getPosx()) - getPosx();
//				dPosy = (rock.getPosy() + rock.getRadius()) - getPosy();
//				
////				if(minDistance == 0 || minDistance > dPosx * dPosx + dPosy * dPosy) {
////					minDistance = Math.sqrt(dPosx * dPosx + dPosy * dPosy);
////					nearUid = rock.getUid();
////				}
//				
//				if(minDistance == 0 || minDistance > dPosx) {
//					minDistance = dPosx;
//					nearUid = rock.getUid();
//				}
//			} 
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
        
        velX = new InputVariable();
        velX.setName("velX");
        velX.setRange(-9.0, 9.0);
        velX.addTerm(new Triangle("ALTA_PARA_DIREITA", -9.0, -6.0, -3.0));
        velX.addTerm(new Triangle("PARA_DIREITA", -6.0, -3.0, 0.0));
        velX.addTerm(new Triangle("INERCIA", -3.0, 0.0, 3.0));
        velX.addTerm(new Triangle("PARA_ESQUERDA", 0.0, 3.0, 6.0));
        velX.addTerm(new Triangle("ALTA_PARA_ESQUERDA", 3.0, 6.0, 9.0));
        engine.addInputVariable(velX);
        
        diffX = new InputVariable();
        diffX.setName("diffX");
        diffX.setRange(-30.0, 30.0);
        diffX.addTerm(new Triangle("PROXIMO_ESQUERDA", -30.0, -20.0, -10.0));
        diffX.addTerm(new Triangle("MUITO_PROXIMO_ESQUERDA", -20.0, -10.0, 0.0));
        diffX.addTerm(new Triangle("COLADO", -10.0, 0.0, 10.0));
        diffX.addTerm(new Triangle("MUITO_PROXIMO_DIREITA", 0.0, 10.0, 20.0));
        diffX.addTerm(new Triangle("PROXIMO_DIREITA", 10.0, 20.0, 30.0));
        engine.addInputVariable(diffX);
        
        diffY = new InputVariable();
        diffY.setName("diffY");
        diffY.setRange(-30.0, 30.0);
        diffY.addTerm(new Triangle("PROXIMO_ABAIXO", -30.0, -20.0, -10.0));
        diffY.addTerm(new Triangle("MUITO_PROXIMO_ABAIXO", -20.0, -10.0, 0.0));
        diffX.addTerm(new Triangle("COLADO", -10.0, 0.0, 10.0));
        diffY.addTerm(new Triangle("MUITO_PROXIMO_ACIMA", 0.0, 10.0, 20.0));
        diffY.addTerm(new Triangle("PROXIMO_ACIMA", 10.0, 20.0, 30.0));
        engine.addInputVariable(diffY);
        
        motorFrente = new OutputVariable();
        motorFrente.setName("motorFrente");
        motorFrente.setRange(-1.2, 1.2);
        motorFrente.setDefaultValue(0.0);
        motorFrente.addTerm(new Triangle("PARA_DIREITA", -1.2, -0.6, 0.0));
        motorFrente.addTerm(new Triangle("NADA", -0.6, 0.0, 0.6));
        motorFrente.addTerm(new Triangle("PARA_ESQUERDA", 0.0, 0.6, 1.2));
        engine.addOutputVariable(motorFrente);
        
        motorFundo = new OutputVariable();
        motorFundo.setName("motorFundo");
        motorFundo.setRange(-1.2, 1.2);
        motorFundo.setDefaultValue(0.0);
        motorFundo.addTerm(new Triangle("PARA_DIREITA", -1.2, -0.6, 0));
        motorFundo.addTerm(new Triangle("NADA", -0.6, 0.0, 0.6));
        motorFundo.addTerm(new Triangle("PARA_ESQUERDA", 0.0, 0.6, 1.2));
        engine.addOutputVariable(motorFundo);
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        
        String[] rules = {
        		//posX
//        		"if posX is EXTREMO_ESQUERDA then motorFrente is PARA_DIREITA and motorFundo is PARA_DIREITA",
//        		"if posX is ESQUERDA then motorFrente is NADA and motorFundo is NADA",
//        		"if posX is DIREITA then motorFrente is NADA and motorFundo is NADA",
//        		"if posX is EXTREMO_DIREITA then motorFrente is PARA_ESQUERDA and motorFundo is PARA_ESQUERDA",
        		
        		//velX
        		"if velX is ALTA_PARA_ESQUERDA then motorFrente is PARA_DIREITA and motorFundo is PARA_DIREITA",
        		"if velX is PARA_ESQUERDA then motorFrente is NADA and motorFundo is NADA",
        		"if velX is PARA_DIREITA then motorFrente is NADA and motorFundo is NADA",
        		"if velX is ALTA_PARA_DIREITA then motorFrente is PARA_ESQUERDA and motorFundo is PARA_ESQUERDA",
        		
        		//diffX
        		"if diffX is MUITO_PROXIMO_DIREITA and diffY is not PROXIMO_ABAIXO and diffY is not PROXIMO_ACIMA then motorFrente is PARA_ESQUERDA and motorFundo is PARA_ESQUERDA",
        		"if diffX is MUITO_PROXIMO_ESQUERDA and diffY is not PROXIMO_ABAIXO and diffY is not PROXIMO_ACIMA then motorFrente is PARA_DIREITA and motorFundo is PARA_DIREITA"
        };
        
        for(String rule : rules) {
        	ruleBlock.addRule(Rule.parse(rule, engine));
        }
        
        engine.addRuleBlock(ruleBlock);
        
        engine.configure("Minimum", "Maximum", "Minimum", "Maximum", "Centroid");
        
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
