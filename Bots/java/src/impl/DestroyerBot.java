package impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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

public class DestroyerBot extends BotBase {
	
	private Engine engine;
	private double posX, posY, velX, velY, diffX, diffY, velAng, shipAngle, angleToTarget, diffAngle;
	private float motorPrincipal, motorLadoFrente, motorLadoFundo;
	private int tiro;
	
	public Action process(GameState gamestate) {
//		GameObject rock = findNearObject(gamestate.getRocks());
		GameObject nearShip = findNearObject(gamestate, gamestate.getShips());
		
//		gamestate.log("nearShip null? " + (nearShip == null));
		
//		gamestate.log("meu uid = " + getUid() + " e arenaRadius = " + gamestate.getArenaRadius());
		
		if(nearShip != null) {
//				gamestate.log("adv x = " + nearShip.getPosx() + ", y = " + nearShip.getPosy() + ", r = " + nearShip.getRadius());
//				gamestate.log("x = " + getPosx() + ", y = " + getPosy() + ", r = " + getRadius());
//				gamestate.log("Velx = "  + getVelx() + ", Vely = " + getVely());
//				gamestate.log("getAng = " + getAng() + ", getVelang = " + getVelang());
//				gamestate.log("Math.cos(" + getAng() + ") = "  + Math.cos(Math.toRadians(getAng())));
			return process(gamestate, nearShip);
		}
		else {
//			gamestate.log("ABC");
//			gamestate.log("x = " + getPosx() + ", y = " + getPosy() + ", r = " + getRadius());
//			gamestate.log("getAng = " + getAng() + ", getVelang = " + getVelang());
//			gamestate.log("Math.cos(" + getAng() + ") = "  + Math.cos(Math.toRadians(getAng())));
//			if(count < 5) {
//				count++;
//				return new Action(0, 1, 0, 1); 
//			}
//			else {
				return new Action(0, 0, 0, 1); 
//			}
		}
				
//			return action;	
		
	}
	
//	private void a(GameObject gameObject) {
//		//Homing algorithm
//		double angle;
//		if (getAng() > Math.PI)
//		    angle -= 2*Math.PI;
//		else if (getAng() < -Math.PI)
//		    angle += 2*Math.PI;
//		 
//		double angleToTarget = Math.atan2(gameObject.getPosy() - getPosy(), gameObject.getPosx() - getPosx()); 
//		double relativeAngleToTarget = angleToTarget - angle;
//		 
//		if (relativeAngleToTarget > Math.PI)
//		    relativeAngleToTarget -= 2*Math.PI;
//		else if (relativeAngleToTarget < -Math.PI)
//		    relativeAngleToTarget += 2*Math.PI;
//		                 
//		angle += relativeAngleToTarget * rotationSpeed;
//		x += Math.cos(angle) * moveSpeed;
//		y += Math.sin(angle) * moveSpeed;
//	}
	
//	private void calcVelAngBalance() {
//		if(getVelang() > 50) {
//			motorLadoFrente = 1;
//		}
//		else if(getVelang() < -50) {
//			motorLadoFrente = -1;
//		}
//	}
	
	private void calcDiffAngleBalance() {
		if(diffAngle <= -5) {
			motorLadoFrente = -1;
		}
		else if(diffAngle >= 5) {
			motorLadoFrente = 1;
		}
//		else {
//			if(getVelang() < -50) {
//				motorLadoFrente = 1;
//			}
//			else if(getVelang() > 50) {
//				motorLadoFrente = -1;
//			}
//		}
	}
	
	private Action process(GameState gamestate, GameObject object) {
		motorPrincipal = 0;
		motorLadoFrente = 0;
		motorLadoFundo = 0;
		tiro = 0;
		
		shipAngle = Math.abs(getAng()) % 360;
		angleToTarget = Math.toDegrees(Math.atan2(object.getPosy() - getPosy(), object.getPosx() - getPosx()));
		diffAngle = 90 - angleToTarget - shipAngle;
		
//		calcVelAngBalance();
		calcDiffAngleBalance();
		
		gamestate.log("shipAngle = " + shipAngle + ", velAng = " + getVelang());
		gamestate.log("angleToTarget = " + angleToTarget + ", diffAngle = " + diffAngle);
		gamestate.log("motorPrincipal = " + motorPrincipal + ", motorLadoFrente = " + motorLadoFrente + ", motorLadoFundo = " + motorLadoFundo);
		
//		posX.setInputValue(getPosx());
//		posY.setInputValue(getPosy());
//		velX.setInputValue(getVelx());
//		velY.setInputValue(getVely());
//		velAng.setInputValue(getVelang());
		
//		if(object.getPosx() < getPosx()) {
//			diffX.setInputValue((object.getPosx() + object.getRadius()) - (getPosx() - getRadius()));
//		}
//		else {
//			diffX.setInputValue((object.getPosx() - object.getRadius()) - (getPosx() + getRadius()));
//		}
		
//		if(object.getPosy() < getPosy()) {
//			diffY.setInputValue((object.getPosy() + object.getRadius()) - (getPosy() - getRadius()));
//		}
//		else {
//			diffY.setInputValue((object.getPosy() - object.getRadius()) - (getPosy() + getRadius()));
//		}
		
//		diffX.setInputValue((object.getPosx() - getPosx()));
//		diffY.setInputValue((object.getPosy() - getPosy()));
//		double hipotenusa = Math.sqrt(diffX.getInputValue() * diffX.getInputValue() + diffY.getInputValue() * diffY.getInputValue());
		
//		double att = Math.toDegrees(Math.atan2(object.getPosy(), object.getPosx()));
//		gamestate.log("att = " + att);
//		gamestate.log("atan = " + Math.toDegrees(Math.atan2(object.getPosy() - getPosy(), object.getPosx() - getPosx())));
//		gamestate.log("getAng() % 360 = " + Math.abs(getAng()) % 360);
		
//		angleToTarget.setInputValue(90 - Math.abs(Math.toDegrees(Math.atan2(object.getPosy() - getPosy(), object.getPosx() - getPosx()))) % 360);
		
//		engine.process();
		
//		motorPrincipal = Math.round(motorPrincipal.getOutputValue());// * Math.round(Math.sin(Math.toRadians(getAng()))));
//		motorLadoFrente = Math.round(motorLadoFrente.getOutputValue());// * Math.round(Math.cos(Math.toRadians(getAng()))));
//		motorLadoFundo = Math.round(motorLadoFundo.getOutputValue());// * Math.round(Math.cos(Math.toRadians(getAng()))));
//		int tiro = 1;
		
//		gamestate.log(/*"diffX = " + diffX.getInpu/tValue() + ", diffY = " + diffY.getInputValue() + */" e angleToTarget = " + angleToTarget.getInputValue());// + ", hipotenusa = " + hipotenusa + "seno = " + seno.getInputValue());
		
//		gamestate.log("Bruto: motorPrincipal = " + Math.round(motorPrincipal.getOutputValue()) + ", motorLadoFrente = " + Math.round(motorLadoFrente.getOutputValue()) + ", motorLadoFundo = " + Math.round(motorLadoFundo.getOutputValue()));
		
//		gamestate.log("RESULT: velX = " + velX.getInputValue() + ", velY = " + velY.getInputValue() + ", diffX = " + diffX.getInputValue() + ", diffY = " + diffY.getInputValue() + ", mPrincipal = " + mPrincipal + ", mLadoFrente = " + mLadoFrente + ", mLadoFundo = " + mLadoFundo);
//		return new Action(0, 0, 0, tiro);
		return new Action(motorPrincipal, motorLadoFrente, motorLadoFundo, tiro);
	}
	
	private GameObject findNearObject(GameState gamestate, Map<Integer, ? extends GameObject> list) {
		double dPosx, dPosy, minDistance = 0;
		long nearUid = 0;
		GameObject nearObject = null;
		
		if(list.size() > 0) {
			Iterator<Integer> it = list.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				
				if(getUid() != key) {
					GameObject gameObject = list.get(key);
					
					if(gameObject.getPosx() < getPosx()) {
						dPosx =  (gameObject.getPosx() + gameObject.getRadius()) - (getPosx() - getRadius());
					}
					else {
						dPosx = (gameObject.getPosx() - gameObject.getRadius()) - (getPosx() + getRadius());
					}
					
					if(gameObject.getPosy() < getPosy()) {
						dPosy = (gameObject.getPosy() + gameObject.getRadius()) - (getPosy() - getRadius());
					}
					else {
						dPosy = (gameObject.getPosy() - gameObject.getRadius()) - (getPosy() + getRadius());
					}
					
//					gamestate.log("OPA uid = " + gameObject.getUid() + ", x = " + gameObject.getPosx() + ", y = " + gameObject.getPosy());
//					gamestate.log("bot x = " + getPosx() + ", y = " + getPosy());
					
					if(minDistance == 0 || minDistance > dPosx * dPosx + dPosy * dPosy) {
						minDistance = dPosx * dPosx + dPosy * dPosy;
						nearObject = gameObject;
					}
				}
			}
			
//			gamestate.log("OPA uid = " + nearUid + " e list.size() = " + list.size());
		}
		return nearObject;
	}
	
	public DestroyerBot() {
//		engine = new Engine();
//        engine.setName("AllanArielBot");
//        
//        posX = new InputVariable();
//        posX.setName("posX");
//        posX.setRange(-36.0, 36.0);
//        posX.addTerm(new Triangle("MAXIMO_DIREITA",    -36.0, -27.0, -18.0));
//        posX.addTerm(new Triangle("EXTREMO_DIREITA", 	-27.0, -18.0,  -9.0));
//        posX.addTerm(new Triangle("DIREITA", 			-18.0,  -9.0,   0.0));
//        posX.addTerm(new Triangle("MEIO", 				 -9.0,   0.0,   9.0));
//        posX.addTerm(new Triangle("ESQUERDA", 			  0.0,   9.0,  18.0));
//        posX.addTerm(new Triangle("EXTREMO_ESQUERDA", 	  9.0,  18.0,  27.0));
//        posX.addTerm(new Triangle("MAXIMO_ESQUERDA", 	 18.0,  27.0,  36.0));
//        engine.addInputVariable(posX);
//        
//        posY = new InputVariable();
//        posY.setName("posY");
//        posY.setRange(-36.0, 36.0);
//        posY.addTerm(new Triangle("MAXIMO_CIMA", 	 -36.0, -27.0, -18.0));
//        posY.addTerm(new Triangle("EXTREMO_CIMA", 	 -27.0, -18.0,  -9.0));
//        posY.addTerm(new Triangle("CIMA", 			 -18.0,  -9.0,   0.0));
//        posY.addTerm(new Triangle("MEIO", 			  -9.0,   0.0,   9.0));
//        posY.addTerm(new Triangle("BAIXO", 			   0.0,   9.0,  18.0));
//        posY.addTerm(new Triangle("EXTREMO_BAIXO", 	   9.0,  18.0,  27.0));
//        posY.addTerm(new Triangle("MAXIMO_BAIXO", 	  18.0,  27.0,  36.0));
//        engine.addInputVariable(posY);
//        
//        velX = new InputVariable();
//        velX.setName("velX");
//        velX.setRange(-25.0, 25.0);
//        velX.addTerm(new Triangle("MAXIMA_PARA_DIREITA",	-25.0, -20.0, -15.0));
//        velX.addTerm(new Triangle("EXTREMA_PARA_DIREITA",	-20.0, -15.0, -10.0));
//        velX.addTerm(new Triangle("ALTA_PARA_DIREITA", 		-15.0, -10.0,  -5.0));
//        velX.addTerm(new Triangle("PARA_DIREITA", 			-10.0,  -5.0,   0.0));
//        velX.addTerm(new Triangle("INERCIA", 				 -5.0,   0.0,   5.0));
//        velX.addTerm(new Triangle("PARA_ESQUERDA", 		  	  0.0,   5.0,  10.0));
//        velX.addTerm(new Triangle("ALTA_PARA_ESQUERDA",  	  5.0,  10.0,  15.0));
//        velX.addTerm(new Triangle("EXTREMA_PARA_ESQUERDA",	 10.0,  15.0,  20.0));
//        velX.addTerm(new Triangle("MAXIMA_PARA_ESQUERDA",	 15.0,  20.0,  25.0));
//        engine.addInputVariable(velX);
//        
//        velY = new InputVariable();
//        velY.setName("velY");
//        velY.setRange(-25.0, 25.0);
//        velY.addTerm(new Triangle("MAXIMA_PARA_CIMA",	-25.0, -20.0, -15.0));
//        velY.addTerm(new Triangle("EXTREMA_PARA_CIMA",	-20.0, -15.0, -10.0));
//        velY.addTerm(new Triangle("ALTA_PARA_CIMA", 	-15.0, -10.0,  -5.0));
//        velY.addTerm(new Triangle("PARA_CIMA", 			-10.0,  -5.0,   0.0));
//        velY.addTerm(new Triangle("INERCIA", 			 -5.0,   0.0,   5.0));
//        velY.addTerm(new Triangle("PARA_BAIXO", 		  0.0,   5.0,  10.0));
//        velY.addTerm(new Triangle("ALTA_PARA_BAIXO", 	  5.0,  10.0,  15.0));
//        velY.addTerm(new Triangle("EXTREMA_PARA_BAIXO",	 10.0,  15.0,  20.0));
//        velY.addTerm(new Triangle("MAXIMA_PARA_BAIXO",	 15.0,  20.0,  25.0));
//        engine.addInputVariable(velY);
//        
//        velAng = new InputVariable();
//        velAng.setName("velAng");
//        velAng.setRange(-500.0, 500.0);
//        velAng.addTerm(new Triangle("450_PARA_DIREITA",	-500.0, -450.0, -400.0));
//        velAng.addTerm(new Triangle("400_PARA_DIREITA",	-450.0, -400.0, -350.0));
//        velAng.addTerm(new Triangle("350_PARA_DIREITA",	-400.0, -350.0, -300.0));
//        velAng.addTerm(new Triangle("300_PARA_DIREITA",	-350.0, -300.0, -250.0));
//        velAng.addTerm(new Triangle("250_PARA_DIREITA",	-300.0, -250.0, -200.0));
//        velAng.addTerm(new Triangle("200_PARA_DIREITA",	-250.0, -200.0, -150.0));
//        velAng.addTerm(new Triangle("150_PARA_DIREITA", -200.0, -150.0, -100.0));
//        velAng.addTerm(new Triangle("100_PARA_DIREITA", -150.0, -100.0,  -50.0));
//        velAng.addTerm(new Triangle("50_PARA_DIREITA", 	-100.0,  -50.0,    0.0));
//        velAng.addTerm(new Triangle("INERCIA", 			 -50.0,    0.0,   50.0));
//        velAng.addTerm(new Triangle("50_PARA_ESQUERDA",    0.0,   50.0,  100.0));
//        velAng.addTerm(new Triangle("100_PARA_ESQUERDA",  50.0,  100.0,  150.0));
//        velAng.addTerm(new Triangle("150_PARA_ESQUERDA", 100.0,  150.0,  200.0));
//        velAng.addTerm(new Triangle("200_PARA_ESQUERDA", 150.0,  200.0,  250.0));
//        velAng.addTerm(new Triangle("250_PARA_ESQUERDA", 200.0,  250.0,  300.0));
//        velAng.addTerm(new Triangle("300_PARA_ESQUERDA", 250.0,  300.0,  350.0));
//        velAng.addTerm(new Triangle("350_PARA_ESQUERDA", 300.0,  350.0,  400.0));
//        velAng.addTerm(new Triangle("400_PARA_ESQUERDA", 350.0,  400.0,  450.0));
//        velAng.addTerm(new Triangle("450_PARA_ESQUERDA", 400.0,  450.0,  500.0));
//        engine.addInputVariable(velAng);
//        
//        diffX = new InputVariable();
//        diffX.setName("diffX");
//        diffX.setRange(-40.0, 40.0);
//        diffX.addTerm(new Triangle("DIREITA", 					-40.0, -30.0, -20.0));
//        diffX.addTerm(new Triangle("PROXIMO_DIREITA", 			-30.0, -20.0,  -10.0));
//        diffX.addTerm(new Triangle("MUITO_PROXIMO_DIREITA", 	-20.0,  -10.0,   0.0));
//        diffX.addTerm(new Triangle("COLADO", 					 -10.0,   0.0,   10.0));
//        diffX.addTerm(new Triangle("MUITO_PROXIMO_ESQUERDA", 	  0.0,   10.0,  20.0));
//        diffX.addTerm(new Triangle("PROXIMO_ESQUERDA", 			  10.0,  20.0,  30.0));
//        diffX.addTerm(new Triangle("ESQUERDA", 			 		 20.0,  30.0,  40.0));
//        engine.addInputVariable(diffX);
//        
//        diffY = new InputVariable();
//        diffY.setName("diffY");
//        diffY.setRange(-40.0, 40.0);
//        diffY.addTerm(new Triangle("CIMA", 					-40.0, -30.0, -20.0));
//        diffY.addTerm(new Triangle("PROXIMO_CIMA", 			-30.0, -20.0, -10.0));
//        diffY.addTerm(new Triangle("MUITO_PROXIMO_CIMA", 	-20.0, -10.0,   0.0));
//        diffY.addTerm(new Triangle("COLADO", 				-10.0,   0.0,  10.0));
//        diffY.addTerm(new Triangle("MUITO_PROXIMO_BAIXO", 	  0.0,  10.0,  20.0));
//        diffY.addTerm(new Triangle("PROXIMO_BAIXO", 		 10.0,  20.0,  30.0));
//        diffY.addTerm(new Triangle("BAIXO", 			 	 20.0,  30.0,  40.0));
//        engine.addInputVariable(diffY);
//        
//        angleToTarget = new InputVariable();
//        angleToTarget.setName("angleToTarget");
//        angleToTarget.setRange(-90.0, 90.0);
//        angleToTarget.addTerm(new Triangle("MUITO_BAIXO",	   	   -90.0, -67.5, -45.0));
//        angleToTarget.addTerm(new Triangle("BAIXO", 			   -67.5, -45.0, -22.5));
//        angleToTarget.addTerm(new Triangle("POUCO_BAIXO", 	       -45.0, -22.5,   0.0));
//        angleToTarget.addTerm(new Triangle("INTERMEDIARIO", 	   -22.5,   0.0,  22.5));
//        angleToTarget.addTerm(new Triangle("POUCO_ALTO", 			 0.0,  22.5,  45.0));
//        angleToTarget.addTerm(new Triangle("ALTO", 	  				22.5,  45.0,  67.5));
//        angleToTarget.addTerm(new Triangle("MUITO_ALTO", 	   	    45.0,  67.5,  90.0));
//        engine.addInputVariable(angleToTarget);
//        
//        motorPrincipal = new OutputVariable();
//        motorPrincipal.setName("motorPrincipal");
//        motorPrincipal.setRange(-1.2, 1.2);
//        motorPrincipal.setDefaultValue(0.0);
//        motorPrincipal.addTerm(new Triangle("PARA_FRENTE", 	-1.2, -0.6, 0.0));
//        motorPrincipal.addTerm(new Triangle("PARADO", 		-0.6,  0.0, 0.6));
//        motorPrincipal.addTerm(new Triangle("PARA_TRAS", 	 0.0,  0.6, 1.2));
//        engine.addOutputVariable(motorPrincipal);
//        
//        motorLadoFrente = new OutputVariable();
//        motorLadoFrente.setName("motorLadoFrente");
//        motorLadoFrente.setRange(-1.2, 1.2);
//        motorLadoFrente.setDefaultValue(0.0);
//        motorLadoFrente.addTerm(new Triangle("PARA_DIREITA", 	-1.2, -0.6, 0.0));
//        motorLadoFrente.addTerm(new Triangle("PARADO", 			-0.6,  0.0, 0.6));
//        motorLadoFrente.addTerm(new Triangle("PARA_ESQUERDA", 	 0.0,  0.6, 1.2));
//        engine.addOutputVariable(motorLadoFrente);
//        
//        motorLadoFundo = new OutputVariable();
//        motorLadoFundo.setName("motorLadoFundo");
//        motorLadoFundo.setRange(-1.2, 1.2);
//        motorLadoFundo.setDefaultValue(0.0);
//        motorLadoFundo.addTerm(new Triangle("PARA_DIREITA", 	-1.2, -0.6, 0.0));
//        motorLadoFundo.addTerm(new Triangle("PARADO", 			-0.6,  0.0, 0.6));
//        motorLadoFundo.addTerm(new Triangle("PARA_ESQUERDA", 	 0.0,  0.6, 1.2));
//        engine.addOutputVariable(motorLadoFundo);
//        
//        RuleBlock ruleBlock = new RuleBlock();
//        ruleBlock.setEnabled(true);
        
//        String[] rules = {
        		
//        		"if velX is MAXIMA_PARA_ESQUERDA "
//        		+ "or velX is EXTREMA_PARA_ESQUERDA "
//        		+ "or velX is ALTA_PARA_ESQUERDA "
//        		+ "then motorLadoFrente is PARA_DIREITA "
//        		+ "and motorLadoFundo is PARA_DIREITA",
        		

        		
//        		"if (diffX is MUITO_PROXIMO_ESQUERDA "
//        		+ "or diffX is COLADO) "
//        		+ "and (diffY is MUITO_PROXIMO_BAIXO "
////        		+ "or diffY is COLADO "
//        		+ "or diffY is MUITO_PROXIMO_CIMA) "
//        		+ "and (velAng is 50_PARA_ESQUERDA "
//        		+ "or velAng is INERCIA "
//        		+ "or velAng is 50_PARA_DIREITA) "
//        		+ "then motorLadoFrente is PARA_DIREITA "
//        		+ "and motorLadoFundo is PARA_DIREITA",
        		
        		
        		
//        		"if velX is MAXIMA_PARA_DIREITA "
//                + "or velX is EXTREMA_PARA_DIREITA "
//        		+ "or velX is ALTA_PARA_DIREITA "
//        		+ "then motorLadoFrente is PARA_ESQUERDA "
//        		+ "and motorLadoFundo is PARA_ESQUERDA",
        		

        		
//				"if diffX is MUITO_PROXIMO_DIREITA "
//        		+ "and (diffY is MUITO_PROXIMO_BAIXO "
//        		+ "or diffY is COLADO "
//        		+ "or diffY is MUITO_PROXIMO_CIMA) "
//        		+ "and (velAng is 50_PARA_ESQUERDA "
//        		+ "or velAng is INERCIA "
//        		+ "or velAng is 50_PARA_DIREITA) "
//        		+ "then motorLadoFrente is PARA_ESQUERDA "
//        		+ "and motorLadoFundo is PARA_ESQUERDA",
        		
        		
        		
//        		"if velY is MAXIMA_PARA_BAIXO "
//        		+ "or velY is EXTREMA_PARA_BAIXO "
//				+ "or velY is ALTA_PARA_BAIXO "
//				+ "then motorPrincipal is PARA_FRENTE",
				

				
//				"if (diffY is MUITO_PROXIMO_BAIXO "
//				+ "or diffY is COLADO)"
//        		+ "and (diffX is MUITO_PROXIMO_DIREITA "
//        		+ "or diffX is COLADO "
//        		+ "or diffX is MUITO_PROXIMO_ESQUERDA) "
//        		+ "and (velAng is 50_PARA_ESQUERDA "
//        		+ "or velAng is INERCIA "
//        		+ "or velAng is 50_PARA_DIREITA) "
//        		+ "then motorPrincipal is PARA_FRENTE",
        		
        		
        		
//        		"if velY is MAXIMA_PARA_CIMA "
//                + "or velY is EXTREMA_PARA_CIMA "
//        		+ "or velY is ALTA_PARA_CIMA "
//        		+ "then motorPrincipal is PARA_TRAS",
        		

        		
//                "if diffY is MUITO_PROXIMO_CIMA "
//        		+ "and (diffX is MUITO_PROXIMO_DIREITA "
//        		+ "or diffX is COLADO "
//        		+ "or diffX is MUITO_PROXIMO_ESQUERDA) "
//        		+ "and (velAng is 50_PARA_ESQUERDA "
//        		+ "or velAng is INERCIA "
//        		+ "or velAng is 50_PARA_DIREITA) "
//        		+ "then motorPrincipal is PARA_TRAS",
        		
        		
        		
//        		"if angleToTarget is MUITO_BAIXO "
//        		+ "or angleToTarget is BAIXO "
//        		+ "or angleToTarget is POUCO_BAIXO "
//        		+ "then motorLadoFrente is PARA_ESQUERDA "
//        		+ "and motorLadoFundo is PARADO",
//        		
//        		"if angleToTarget is INTERMEDIARIO "
//        		+ "then motorLadoFrente is PARADO",
//        		
//        		"if angleToTarget is MUITO_ALTO "
//                + "or angleToTarget is ALTO "
//                + "or angleToTarget is POUCO_ALTO "
//                + "then motorLadoFrente is PARA_DIREITA "
//                + "and motorLadoFundo is PARADO",
//                
//
//                
//        		"if velAng is 450_PARA_ESQUERDA "
//        		+ "or velAng is 400_PARA_ESQUERDA "
//        		+ "or velAng is 350_PARA_ESQUERDA "
//        		+ "or velAng is 300_PARA_ESQUERDA "
//        		+ "or velAng is 250_PARA_ESQUERDA "
//        	    + "or velAng is 200_PARA_ESQUERDA "
//        	    + "or velAng is 150_PARA_ESQUERDA "
//        	    + "or velAng is 100_PARA_ESQUERDA "
//        	    + "or velAng is 50_PARA_ESQUERDA "
//        	    + "then motorLadoFrente is PARA_ESQUERDA "
//        	    + "and motorLadoFundo is PARADO",
//        	    
//        	    
//         		 
//         		"if velAng is 450_PARA_DIREITA "
//         		+ "or velAng is 400_PARA_DIREITA "
//         		+ "or velAng is 350_PARA_DIREITA "
//         		+ "or velAng is 300_PARA_DIREITA "
//        		+ "or velAng is 250_PARA_DIREITA "
//        	    + "or velAng is 200_PARA_DIREITA "
//        	    + "or velAng is 150_PARA_DIREITA "
//        	    + "or velAng is 100_PARA_DIREITA "
//        	    + "or velAng is 50_PARA_DIREITA "
//               	+ "then motorLadoFrente is PARA_DIREITA "
//               	+ "and motorLadoFundo is PARADO"
//        };
        
//        for(String rule : rules) {
//        	ruleBlock.addRule(Rule.parse(rule, engine));
//        }
//        
//        engine.addRuleBlock(ruleBlock);
//        
//        engine.configure("Minimum", "Maximum", "Minimum", "Maximum", "Centroid");
//        
//        StringBuilder status = new StringBuilder();
//        if (!engine.isReady(status)) {
//            throw new RuntimeException("Engine not ready. "
//                    + "The following errors were encountered:\n" + status.toString());
//        }
	}
	
	public static void main(String[] args) throws IOException {        
		GameState game = new GameState(new DestroyerBot());
		game.connect();
	}
}
