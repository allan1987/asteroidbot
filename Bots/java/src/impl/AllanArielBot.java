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
	private OutputVariable motorPrincipal, motorLadoFrente, motorLadoFundo;
	
	private Action action;
	private double dPosx, dPosy, minDistance = 0;
	private int nearUid = 0;

	public Action process(GameState gamestate) {
		GameObject object = findNearRock(gamestate);
		if(object != null) {
			if(gamestate != null) {
				gamestate.log("nearUid = " + nearUid + " e Distance = " + minDistance);
				gamestate.log("menor x = " + object.getPosx() + ", y = " + object.getPosy());
				gamestate.log("ship x = " + getPosx() + ", y = " + getPosy());
//				gamestate.log("Velx = "  + getVelx() + ", Vely = " + getVely());
//				gamestate.log("getAng = " + getAng() + ", getVelang = " + getVelang());
			}
			return process(gamestate, object);
		}
		else {
//			gamestate.log("ABC");
			return new Action(0, 0, 0, 0); 
		}
				
//			return action;	
		
	}
	
	private Action process(GameState gamestate, GameObject object) {
//		posX.setInputValue(getPosx());
		velX.setInputValue(getVelx());
		velY.setInputValue(getVely());
		
		if(object.getPosx() < getPosx()) {
			diffX.setInputValue((object.getPosx() + object.getRadius()) - (getPosx() - getRadius()));
		}
		else {
			diffX.setInputValue((object.getPosx() - object.getRadius()) - (getPosx() + getRadius()));
		}
		
		if(object.getPosy() < getPosy()) {
			diffY.setInputValue((object.getPosy() + object.getRadius()) - (getPosy() - getRadius()));
		}
		else {
			diffY.setInputValue((object.getPosy() - object.getRadius()) - (getPosy() + getRadius()));
		}
//		diffX.setInputValue(object.getPosx() + object.getRadius() -  getPosx());
//		diffY.setInputValue(object.getPosy() + object.getRadius() -  getPosy());
		
		engine.process();
		
		gamestate.log("RESULT: velX = " + velX.getInputValue() + ", velY = " + velY.getInputValue() + ", diffX = " + diffX.getInputValue() + ", diffY = " + diffY.getInputValue() + ", mPrincipal = " + Math.round(motorPrincipal.getOutputValue()) + ", mLadoFrente = " + Math.round(motorLadoFrente.getOutputValue()) + ", mLadoFundo = " + Math.round(motorLadoFundo.getOutputValue()));
		
		return new Action(Math.round(motorPrincipal.getOutputValue()), Math.round(motorLadoFrente.getOutputValue()), Math.round(motorLadoFundo.getOutputValue()), 0);
	}
	
	private GameObject findNearRock(final GameState gamestate) {
		minDistance = 0;
		nearUid = 0;
		
		if(gamestate.getRocks().size() > 0) {
			
			for(Integer key : gamestate.getRocks().keySet()) {
				Rock rock = gamestate.getRocks().get(key);
				
				if(rock.getPosx() < getPosx()) {
					dPosx =  (rock.getPosx() + rock.getRadius()) - (getPosx() - getRadius());
				}
				else {
					dPosx = (rock.getPosx() - rock.getRadius()) - (getPosx() + getRadius());
				}
				
				if(rock.getPosy() < getPosy()) {
					dPosy = (rock.getPosy() + rock.getRadius()) - (getPosy() - getRadius());
				}
				else {
					dPosy = (rock.getPosy() - rock.getRadius()) - (getPosy() + getRadius());
				}
				
				gamestate.log("rock uid = " + rock.getUid() + ", x = " + rock.getPosx() + ", y = " + rock.getPosy());
//				gamestate.log("bot x = " + getPosx() + ", y = " + getPosy());
				
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
        posX.addTerm(new Triangle("EXTREMO_ESQUERDA", 	-30.0, -20.0, -10.0));
        posX.addTerm(new Triangle("ESQUERDA", 			-20.0, -10.0,   0.0));
        posX.addTerm(new Triangle("MEIO", 				-10.0,   0.0,  10.0));
        posX.addTerm(new Triangle("DIREITA", 			  0.0,  10.0,  20.0));
        posX.addTerm(new Triangle("EXTREMO_DIREITA", 	 10.0,  20.0,  30.0));
        engine.addInputVariable(posX);
        
        velX = new InputVariable();
        velX.setName("velX");
        velX.setRange(-9.0, 9.0);
        velX.addTerm(new Triangle("ALTA_PARA_DIREITA", 	-9.0, -6.0, -3.0));
        velX.addTerm(new Triangle("PARA_DIREITA", 		-6.0, -3.0,  0.0));
        velX.addTerm(new Triangle("INERCIA", 			-3.0,  0.0,  3.0));
        velX.addTerm(new Triangle("PARA_ESQUERDA", 		 0.0,  3.0,  6.0));
        velX.addTerm(new Triangle("ALTA_PARA_ESQUERDA",  3.0,  6.0,  9.0));
        engine.addInputVariable(velX);
        
        velY = new InputVariable();
        velY.setName("velY");
        velY.setRange(-9.0, 9.0);
        velY.addTerm(new Triangle("ALTA_PARA_BAIXO", 	-9.0, -6.0, -3.0));
        velY.addTerm(new Triangle("PARA_BAIXO", 		-6.0, -3.0,  0.0));
        velY.addTerm(new Triangle("INERCIA", 			-3.0,  0.0,  3.0));
        velY.addTerm(new Triangle("PARA_CIMA", 			 0.0,  3.0,  6.0));
        velY.addTerm(new Triangle("ALTA_PARA_CIMA", 	 3.0,  6.0,  9.0));
        engine.addInputVariable(velY);
        
        diffX = new InputVariable();
        diffX.setName("diffX");
        diffX.setRange(-15.0, 15.0);
        diffX.addTerm(new Triangle("PROXIMO_ESQUERDA", 			-15.0, -10.0, -5.0));
        diffX.addTerm(new Triangle("MUITO_PROXIMO_ESQUERDA", 	-10.0,  -5.0,  0.0));
        diffX.addTerm(new Triangle("COLADO", 					 -5.0,   0.0,  5.0));
        diffX.addTerm(new Triangle("MUITO_PROXIMO_DIREITA", 	  0.0,   5.0, 10.0));
        diffX.addTerm(new Triangle("PROXIMO_DIREITA", 			  5.0,  10.0, 15.0));
        engine.addInputVariable(diffX);
        
        diffY = new InputVariable();
        diffY.setName("diffY");
        diffY.setRange(-15.0, 15.0);
        diffY.addTerm(new Triangle("PROXIMO_BAIXO", 		-15.0, -10.0, -5.0));
        diffY.addTerm(new Triangle("MUITO_PROXIMO_BAIXO", 	-10.0,  -5.0,  0.0));
        diffY.addTerm(new Triangle("COLADO", 				 -5.0,   0.0,  5.0));
        diffY.addTerm(new Triangle("MUITO_PROXIMO_CIMA", 	  0.0,   5.0, 10.0));
        diffY.addTerm(new Triangle("PROXIMO_CIMA", 			  5.0,  10.0, 15.0));
        engine.addInputVariable(diffY);
        
        motorPrincipal = new OutputVariable();
        motorPrincipal.setName("motorPrincipal");
        motorPrincipal.setRange(-1.2, 1.2);
        motorPrincipal.setDefaultValue(0.0);
        motorPrincipal.addTerm(new Triangle("PARA_FRENTE", 	-1.2, -0.6, 0.0));
        motorPrincipal.addTerm(new Triangle("NADA", 		-0.6,  0.0, 0.6));
        motorPrincipal.addTerm(new Triangle("PARA_TRAS", 	 0.0,  0.6, 1.2));
        engine.addOutputVariable(motorPrincipal);
        
        motorLadoFrente = new OutputVariable();
        motorLadoFrente.setName("motorLadoFrente");
        motorLadoFrente.setRange(-1.2, 1.2);
        motorLadoFrente.setDefaultValue(0.0);
        motorLadoFrente.addTerm(new Triangle("PARA_DIREITA", 	-1.2, -0.6, 0.0));
        motorLadoFrente.addTerm(new Triangle("NADA", 			-0.6,  0.0, 0.6));
        motorLadoFrente.addTerm(new Triangle("PARA_ESQUERDA", 	 0.0,  0.6, 1.2));
        engine.addOutputVariable(motorLadoFrente);
        
        motorLadoFundo = new OutputVariable();
        motorLadoFundo.setName("motorLadoFundo");
        motorLadoFundo.setRange(-1.2, 1.2);
        motorLadoFundo.setDefaultValue(0.0);
        motorLadoFundo.addTerm(new Triangle("PARA_DIREITA", 	-1.2, -0.6, 0.0));
        motorLadoFundo.addTerm(new Triangle("NADA", 			-0.6,  0.0, 0.6));
        motorLadoFundo.addTerm(new Triangle("PARA_ESQUERDA", 	 0.0,  0.6, 1.2));
        engine.addOutputVariable(motorLadoFundo);
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        
        String[] rules = {
        		//posX
//        		"if posX is EXTREMO_ESQUERDA then motorLadoFrente is PARA_DIREITA and motorLadoFundo is PARA_DIREITA",
//        		"if posX is ESQUERDA then motorLadoFrente is NADA and motorLadoFundo is NADA",
//        		"if posX is DIREITA then motorLadoFrente is NADA and motorLadoFundo is NADA",
//        		"if posX is EXTREMO_DIREITA then motorLadoFrente is PARA_ESQUERDA and motorLadoFundo is PARA_ESQUERDA",
        		
        		//velX
        		"if velX is ALTA_PARA_ESQUERDA then motorLadoFrente is PARA_DIREITA and motorLadoFundo is PARA_DIREITA",
//        		"if velX is PARA_ESQUERDA then motorLadoFrente is NADA and motorLadoFundo is NADA",
//        		"if velX is PARA_DIREITA then motorLadoFrente is NADA and motorLadoFundo is NADA",
        		"if velX is ALTA_PARA_DIREITA then motorLadoFrente is PARA_ESQUERDA and motorLadoFundo is PARA_ESQUERDA",
        		
        		//velY
        		"if velY is not ALTA_PARA_BAIXO then motorPrincipal is PARA_TRAS",
        		"if velY is not ALTA_PARA_CIMA then motorPrincipal is PARA_FRENTE",
        		
        		//diffX
        		"if diffX is MUITO_PROXIMO_ESQUERDA and (diffY is MUITO_PROXIMO_BAIXO or diffY is COLADO or diffY is MUITO_PROXIMO_CIMA) then motorLadoFrente is PARA_DIREITA and motorLadoFundo is PARA_DIREITA",
        		"if diffX is MUITO_PROXIMO_DIREITA and (diffY is MUITO_PROXIMO_BAIXO or diffY is COLADO or diffY is MUITO_PROXIMO_CIMA) then motorLadoFrente is PARA_ESQUERDA and motorLadoFundo is PARA_ESQUERDA",
        		
        		//diffY
        		"if diffY is MUITO_PROXIMO_BAIXO and (diffX is MUITO_PROXIMO_DIREITA or diffX is COLADO or diffX is MUITO_PROXIMO_ESQUERDA) then motorPrincipal is PARA_TRAS",
        		"if diffY is MUITO_PROXIMO_CIMA and (diffX is MUITO_PROXIMO_DIREITA or diffX is COLADO or diffX is MUITO_PROXIMO_ESQUERDA) then motorPrincipal is PARA_FRENTE"
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
