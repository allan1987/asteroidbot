package impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import bot_interface.Action;
import bot_interface.BotBase;
import bot_interface.GameObject;
import bot_interface.GameState;
import bot_interface.Laser;
import bot_interface.Rock;
import bot_interface.Ship;

public class DestroyerBot extends BotBase {
	
	private double posX, posY, diffVelX, diffVelY, diffX, diffY, velAng, shipAngle, angleToTarget, 
	diffAngle, expectedObjectPosX, expectedObjectPosY, diffExpectedFromPosX, diffExpectedFromPosY,
	senAng5 = 0.08715, cosAng5 = 0.99619, cosAngx = -0.08715, cosAng10 = 0.98480, senAng100 = 0.98480;
	private float motorPrincipal, motorLadoFrente, motorLadoFundo, tiro;
	
	public Action process(GameState gamestate) {
		GameObject nearShip = findNearObject(gamestate, gamestate.getShips());
		GameObject nearLaser = findNearObject(gamestate, gamestate.getLasers());
		GameObject nearRock = findNearObject(gamestate, gamestate.getRocks());
		
		gamestate.log("gamestate.getTick() = " + gamestate.getTick());
		
//		gamestate.log("nearShip null? " + (nearShip == null));
		
//		gamestate.log("meu uid = " + getUid() + " e arenaRadius = " + gamestate.getArenaRadius());
		
//				gamestate.log("adv x = " + nearShip.getPosx() + ", y = " + nearShip.getPosy() + ", r = " + nearShip.getRadius());
//				gamestate.log("x = " + getPosx() + ", y = " + getPosy() + ", r = " + getRadius());
//				gamestate.log("Velx = "  + getVelx() + ", Vely = " + getVely());
//				gamestate.log("getAng = " + getAng() + ", getVelang = " + getVelang());
//				gamestate.log("Math.cos(" + getAng() + ") = "  + Math.cos(Math.toRadians(getAng())));
			return process(gamestate, nearShip, nearLaser, nearRock);
	}
	
	private void calcDiffXYLaserBalance(GameState gameState, Laser nearLaser) {
		//S = So + Vt
		for(int i = 0; i <= nearLaser.getLifetime(); i++) {
			expectedObjectPosX = nearLaser.getPosx() + nearLaser.getVelx() * i;
			expectedObjectPosY = nearLaser.getPosy() + nearLaser.getVely() * i;
			
			diffExpectedFromPosX = expectedObjectPosX - getPosx();
			diffExpectedFromPosY = expectedObjectPosY - getPosy();
			
//			gameState.log("expectedLaserPosX = " + expectedObjectPosX + ", expectedLaserPosY = " + expectedObjectPosY);
//			gameState.log("getPosx() = " + getPosx() + ", getPosy() = " + getPosy());
			
			if(Math.abs(diffExpectedFromPosX) <= gameState.getArenaRadius()/3 && Math.abs(diffExpectedFromPosY) <= gameState.getArenaRadius()/3) {
				if(diffExpectedFromPosX < 0 || diffExpectedFromPosY < 0) {
					motorLadoFrente = 1;
					motorLadoFundo = 1;
				}
				else {
					motorLadoFrente = -1;
					motorLadoFundo = -1;
				}
				
//				gameState.log("cos(" + getAng() + ") = " + Math.round(Math.cos(Math.toRadians(getAng()))));
				motorLadoFrente *= Math.round(Math.cos(Math.toRadians(getAng())));
				motorLadoFundo *= Math.round(Math.cos(Math.toRadians(getAng())));
				
//				gameState.log("deu break");
				break;
			}
		}
			
//		gameState.log("laser pertence a uid = " + nearLaser.getOwner() + ", nearLaser.getUid() = " + nearLaser.getUid());
//		gameState.log("nearLaser.getVelx() = " + nearLaser.getVelx() + ", getVelx() = " + getVelx());
//		gameState.log("diffVelX = " + diffVelX);
//		gameState.log("nearLaser.getVely() = " + nearLaser.getVely() + ", getVely() = " + getVely());
//		gameState.log("diffVelY = " + diffVelY);
//		gameState.log("nearLaser.getPosx() = " + nearLaser.getPosx() + ", getPosx() = " + getPosx());
//		gameState.log("nearLaser.getPosy() = " + nearLaser.getPosy() + ", getPosy() = " + getPosy());
//		gameState.log("diffX = " + diffX);
//		gameState.log("diffY = " + diffY);
	}
	
	private void calcDiffXYRockBalance(GameState gameState, Rock nearRock) {
		shipAngle = getAng();// % 360;
		
		if(nearRock != null) {
			//S = So + Vt
			expectedObjectPosX = nearRock.getPosx() + nearRock.getVelx();
			expectedObjectPosY = nearRock.getPosy() + nearRock.getVely();
			
			diffExpectedFromPosX = expectedObjectPosX - (getPosx() + getVelx());
			diffExpectedFromPosY = expectedObjectPosY - (getPosy() + getVely());
			
//			gameState.log("expectedRockPosX = " + expectedObjectPosX + ", expectedRockPosY = " + expectedObjectPosY);
//			gameState.log("nearRock.getPosx() = " + nearRock.getPosx() + ", nearRock.getPosy() = " + nearRock.getPosy());
//			gameState.log("getPosx() = " + getPosx() + ", getPosy() = " + getPosy());
//			gameState.log("nearRock.getVelx() = " + nearRock.getVelx() + ", nearRock.getVely() = " + nearRock.getVely());
//			gameState.log("getVelx() = " + getVelx() + ", getVely() = " + getVely());
			
			angleToTarget = (Math.toDegrees(Math.atan2(diffExpectedFromPosY, diffExpectedFromPosX)) - 90);// % 360;
			
			diffAngle = (shipAngle - angleToTarget);// % 360;
			
//			gameState.log("shipAngle = " + shipAngle + ", angleToTarget = " + angleToTarget + " e diffAngle = " + diffAngle);
//			gameState.log("dEFPX = " + diffExpectedFromPosX + ", dEFPY = " + diffExpectedFromPosY);
			
			if(Math.abs(diffExpectedFromPosX) <= gameState.getArenaRadius()/2 && Math.abs(diffExpectedFromPosY) <= gameState.getArenaRadius()/2) {
				
//				gameState.log("cos(" + diffAngle + ") = " + Math.cos(Math.toRadians(diffAngle)));
//				gameState.log("diffExpectedFromPosX = " + diffExpectedFromPosX + ", diffExpectedFromPosY = " + diffExpectedFromPosY);
				
//				if(gameState.getArenaRadius() - Math.abs(getPosx()) <= gameState.getArenaRadius()/10
//				&& ((Math.cos(Math.toRadians(getAng())) >= cosAng10) 
//				|| (Math.cos(Math.toRadians(getAng())) <= -cosAng10))) {
//					
//					if(diffExpectedFromPosX < diffExpectedFromPosY) {
//						motorPrincipal = -1;
//					}
//					else {
//						if(diffExpectedFromPosY < 0) {
//							motorPrincipal = 1;
//						}
//						else {
//							motorPrincipal = -1;
//						}
//					}
//				}
//				else if(gameState.getArenaRadius() - Math.abs(getPosy()) <= gameState.getArenaRadius()/10
//				&& ((Math.sin(Math.toRadians(getAng())) >= senAng100) 
//				|| (Math.sin(Math.toRadians(getAng())) <= -senAng100))) {
//					
//					if(diffExpectedFromPosY < diffExpectedFromPosX) {
//						motorPrincipal = -1;
//					}
//					else {
//						if(diffExpectedFromPosX < 0) {
//							motorPrincipal = 1;
//						}
//						else {
//							motorPrincipal = -1;
//						}
//					}
//				}
				if(false) {
				}
				else {
					
					if(Math.abs(diffExpectedFromPosX) < Math.abs(diffExpectedFromPosY)) {
						if(diffExpectedFromPosX < 0) {
							gameState.log("A");
							motorPrincipal = 1;
							motorLadoFrente = 1;
							motorLadoFundo = 1;
						}
						else {
							gameState.log("B");
							motorPrincipal = -1;
							motorLadoFrente = -1;
							motorLadoFundo = -1;
						}
					}
					else {
						if(diffExpectedFromPosY < 0) {
							gameState.log("C");
							motorPrincipal = -1;
							motorLadoFrente = -1;
							motorLadoFundo = -1;
						}
						else {
							gameState.log("D");
							motorPrincipal = 1;
							motorLadoFrente = 1;
							motorLadoFundo = 1;
						}
					}
				}
			}
			else {
				angleToTarget = (Math.toDegrees(Math.atan2(getPosy() + getVely(), getPosx() + getVelx())) - 90);// % 360;
				diffAngle = (shipAngle - angleToTarget);// % 360;
				
				double xyAoQuadrado = getPosx() * getPosx() + getPosy() * getPosy();
				double raioAoQuadrado = gameState.getArenaRadius() * gameState.getArenaRadius();
				
/*				if(raioAoQuadrado - xyAoQuadrado < 4) {
//					
//				}
				else */if(getVelx() > 0 || getVely() > 0) {
					gameState.log("E");
					motorPrincipal = -1;
					motorLadoFrente = -1;
					motorLadoFundo = -1;
				}
				else if(getVelx() < 0 || getVely() < 0) {
					gameState.log("F");
					motorPrincipal = 1;
					motorLadoFrente = 1;
					motorLadoFundo = 1;
				}
			}
			
			motorPrincipal *= Math.round(Math.cos(Math.toRadians(diffAngle)));
			motorLadoFrente *= Math.round(Math.sin(Math.toRadians(diffAngle)));
			motorLadoFundo *= Math.round(Math.sin(Math.toRadians(diffAngle)));
		}
		
//		gameState.log("laser pertence a uid = " + nearLaser.getOwner() + ", nearLaser.getUid() = " + nearLaser.getUid());
//		gameState.log("nearLaser.getVelx() = " + nearLaser.getVelx() + ", getVelx() = " + getVelx());
//		gameState.log("diffVelX = " + diffVelX);
//		gameState.log("nearLaser.getVely() = " + nearLaser.getVely() + ", getVely() = " + getVely());
//		gameState.log("diffVelY = " + diffVelY);
//		gameState.log("nearLaser.getPosx() = " + nearLaser.getPosx() + ", getPosx() = " + getPosx());
//		gameState.log("nearLaser.getPosy() = " + nearLaser.getPosy() + ", getPosy() = " + getPosy());
//		gameState.log("diffX = " + diffX);
//		gameState.log("diffY = " + diffY);
	}
	
	private void calcDiffAngleBalance(GameState gamestate, Ship nearShip) {
		shipAngle = getAng() % 360;
		
		if(nearShip != null) {
			expectedObjectPosX = nearShip.getPosx();
			expectedObjectPosY = nearShip.getPosy();
			
			diffExpectedFromPosX = expectedObjectPosX - getPosx();
			diffExpectedFromPosY = expectedObjectPosY - getPosy();
			
			if(Math.abs(diffExpectedFromPosX) > gamestate.getArenaRadius()) {
				expectedObjectPosY += nearShip.getVely() * 3;
			}
			if(Math.abs(diffExpectedFromPosY) > gamestate.getArenaRadius()) {
				expectedObjectPosX += nearShip.getVelx() * 3;
			}
			
			angleToTarget = (Math.toDegrees(Math.atan2(expectedObjectPosY - getPosy(), expectedObjectPosX - getPosx())) - 90) % 360;
		}
		
		diffAngle = (shipAngle - angleToTarget) % 360;
		
//		gamestate.log("S shipAngle = " + shipAngle + ", velAng = " + getVelang());
//		gamestate.log("angleToTarget = " + angleToTarget + ", diffAngle = " + diffAngle);
		
		if(Math.sin(Math.toRadians(diffAngle)) < -senAng5) {
			motorLadoFrente = -1;
		}
		else if(Math.sin(Math.toRadians(diffAngle)) > senAng5) {
			motorLadoFrente = 1;
		}
		else {
			if(getVelang() < -30) {
				motorLadoFrente = -1;
			}
			else if(getVelang() > 30) {
				motorLadoFrente = 1;
			}
		}
	}
	
	private void equilibrateVelAng() {
		if(getVelang() < -150) {
			motorLadoFrente = -1;
		}
		else if(getVelang() > 150) {
			motorLadoFrente = 1;
		}
	}
	
	private void manageCharge(GameState gamestate, Ship nearShip) {
		if(nearShip != null) {
			expectedObjectPosX = nearShip.getPosx() + nearShip.getVelx();
			expectedObjectPosY = nearShip.getPosy() + nearShip.getVely();
			
			diffExpectedFromPosX = expectedObjectPosX - getPosx();
			diffExpectedFromPosY = expectedObjectPosY - getPosy();
			
			if(Math.abs(diffExpectedFromPosX) < gamestate.getArenaRadius()
			&& Math.abs(diffExpectedFromPosY) < gamestate.getArenaRadius()) {
				tiro = 1;
			}
			else {
				if(getCharge() >= 3) {
					tiro = getCharge();
				}
			}
		}
	}
	
	private Action process(final GameState gamestate, final GameObject nearShip, final GameObject nearLaser, final GameObject nearRock) {
		motorPrincipal = 0;
		motorLadoFrente = 0;
		motorLadoFundo = 0;
		tiro = 0;
		
		if(nearShip != null) {
			manageCharge(gamestate, (Ship)nearShip);
			
			if(nearRock != null) {
				calcDiffXYRockBalance(gamestate, (Rock)nearRock);
			}
			
			if(nearLaser != null) {
				calcDiffXYLaserBalance(gamestate, (Laser)nearLaser);
			}
			
			calcDiffAngleBalance(gamestate, (Ship)nearShip);
		}
		
		equilibrateVelAng();
		
//		gamestate.log("shipAngle = " + shipAngle + ", velAng = " + getVelang());
//		gamestate.log("angleToTarget = " + angleToTarget + ", diffAngle = " + diffAngle);
//		gamestate.log("tiro = " + tiro + ", motorPrincipal = " + motorPrincipal + ", motorLadoFrente = " + motorLadoFrente + ", motorLadoFundo = " + motorLadoFundo);
		
		return new Action(motorPrincipal, motorLadoFrente, motorLadoFundo, (int)tiro);
	}
	
	private GameObject findNearObject(GameState gamestate, Map<Integer, ? extends GameObject> list) {
		double dPosx, dPosy, minDistance = 0;
		GameObject nearObject = null;
		
		if(list.size() > 0) {
			
			Iterator<Integer> it = list.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				
				GameObject gameObject = list.get(key);
				
				if(gameObject instanceof Laser) {
					key = ((Laser)gameObject).getOwner();
				}
				
				if(getUid() != key) {
					
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
					
					if(minDistance == 0 || minDistance > dPosx * dPosx + dPosy * dPosy) {
						minDistance = dPosx * dPosx + dPosy * dPosy;
						nearObject = gameObject;
					}
				}
			}
		}
		return nearObject;
	}
	
	public DestroyerBot() {}
	
	public static void main(String[] args) throws IOException {        
		GameState game = new GameState(new DestroyerBot());
		game.connect();
	}
}
