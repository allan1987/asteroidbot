package fuzzylogic;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class FuzzyTest {
	
	private static Engine engine;
	private static InputVariable posX, posY, angle, deltaXEsquerda, deltaXDireita, deltaYAcima, deltaYAbaixo;
	private static OutputVariable motorFrente, motorFundo;
	
	public static void main(String[] args){
//	        Engine engine = new Engine();
//	        engine.setName("simple-dimmer");
//	
//	        InputVariable ambient = new InputVariable();
//	        ambient.setName("Ambient");
//	        ambient.setRange(0.000, 1.000);
//	        ambient.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
//	        ambient.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
//	        ambient.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
//	        engine.addInputVariable(ambient);
//	
//	        OutputVariable power = new OutputVariable();
//	        power.setName("Power");
//	        power.setRange(0.000, 1.000);
//	        power.setDefaultValue(Double.NaN);
//	        power.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
//	        power.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
//	        power.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
//	        engine.addOutputVariable(power);
//	
//	        RuleBlock ruleBlock = new RuleBlock();
//	        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
//	        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
//	        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
//	        engine.addRuleBlock(ruleBlock);
//	
//	        engine.configure("", "", "Minimum", "Maximum", "Centroid");
//	
//	        StringBuilder status = new StringBuilder();
//	        if (!engine.isReady(status)) {
//	            throw new RuntimeException("Engine not ready. "
//	                    + "The following errors were encountered:\n" + status.toString());
//	        }
//	
//	        for (int i = 0; i < 50; ++i) {
//	            double light = ambient.getMinimum() + i * (ambient.range() / 50);
//	            ambient.setInputValue(light);
//	            engine.process();
//	            FuzzyLite.logger().info(String.format(
//	                    "Ambient.input = %s -> Power.output = %s",
//	                    Op.str(light), Op.str(power.getOutputValue())));
//	        }
		
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
        
//        posY = new InputVariable();
//        posY.setName("posY");
//        posY.setRange(-30.0, 30.0);
//        posY.addTerm(new Triangle("EXTREMO_ABAIXO", -30.0, -20.0, -10.0));
//        posY.addTerm(new Triangle("ABAIXO", -20.0, -10.0, 0.0));
//        posY.addTerm(new Triangle("MEIO", -10.0, 0.0, 10.0));
//        posY.addTerm(new Triangle("ACIMA", 0.0, 10.0, 20.0));
//        posY.addTerm(new Triangle("EXTREMO_ACIMA", 10.0, 20.0, 30.0));
//        engine.addInputVariable(posY);
        
//        angle = new InputVariable();
//        angle.setName("angle");
//        angle.setRange(0.0, 360.0);
//        angle.addTerm(new Triangle("0_TO_60", 0.0, 30.0, 60.0));
//        angle.addTerm(new Triangle("30_TO_90", 30.0, 60.0, 90.0));
//        angle.addTerm(new Triangle("60_TO_120", 60.0, 90.0, 120.0));
//        angle.addTerm(new Triangle("90_TO_150", 90.0, 120.0, 150.0));
//        angle.addTerm(new Triangle("120_TO_180", 120.0, 150.0, 180.0));
//        angle.addTerm(new Triangle("150_TO_210", 150.0, 180.0, 210.0));
//        angle.addTerm(new Triangle("180_TO_240", 180.0, 210.0, 240.0));
//        angle.addTerm(new Triangle("210_TO_270", 210.0, 240.0, 270.0));
//        angle.addTerm(new Triangle("240_TO_300", 240.0, 270.0, 300.0));
//        angle.addTerm(new Triangle("270_TO_330", 270.0, 300.0, 330.0));
//        angle.addTerm(new Triangle("300_TO_360", 300.0, 330.0, 360.0));
//        engine.addInputVariable(angle);
        
//        deltaXEsquerda = new InputVariable();
//        deltaXEsquerda.setName("deltaXEsquerda");
//        deltaXEsquerda.setRange(-40.0, 0.0);
//        deltaXEsquerda.addTerm(new Triangle("BAIXO", 0.0, -10.0, -20.0));
//        deltaXEsquerda.addTerm(new Triangle("MEDIO", -10.0, -20.0, -30.0));
//        deltaXEsquerda.addTerm(new Triangle("ALTO", -20.0, -30.0, -40.0));
//        engine.addInputVariable(deltaXEsquerda);
//        
//        deltaXDireita = new InputVariable();
//        deltaXDireita.setName("deltaXDireita");
//        deltaXDireita.setRange(0.0, 40.0);
//        deltaXDireita.addTerm(new Triangle("BAIXO", 0.0, 10.0, 20.0));
//        deltaXDireita.addTerm(new Triangle("MEDIO", 10.0, 20.0, 30.0));
//        deltaXDireita.addTerm(new Triangle("ALTO", 20.0, 30.0, 40.0));
//        engine.addInputVariable(deltaXDireita);
//        
//        deltaYAcima = new InputVariable();
//        deltaYAcima.setName("deltaYAcima");
//        deltaYAcima.setRange(-40.0, 0.0);
//        deltaYAcima.addTerm(new Triangle("BAIXO", 0.0, -10.0, -20.0));
//        deltaYAcima.addTerm(new Triangle("MEDIO", -10.0, -20.0, -30.0));
//        deltaYAcima.addTerm(new Triangle("ALTO", -20.0, -30.0, -40.0));
//        engine.addInputVariable(deltaYAcima);
//        
//        deltaYAbaixo = new InputVariable();
//        deltaYAbaixo.setName("deltaYAbaixo");
//        deltaYAbaixo.setRange(0.0, 40.0);
//        deltaYAbaixo.addTerm(new Triangle("BAIXO", 0.0, 10.0, 20.0));
//        deltaYAbaixo.addTerm(new Triangle("MEDIO", 10.0, 20.0, 30.0));
//        deltaYAbaixo.addTerm(new Triangle("ALTO", 20.0, 30.0, 40.0));
//        engine.addInputVariable(deltaYAbaixo);
        
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
        
//        Angulo da nave
//        Math.abs(nave.getangl()) % 360;
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.addRule(Rule.parse("if posX is EXTREMO_ESQUERDA and (posX is EXTREMO_ESQUERDA or posX is EXTREMO_ESQUERDA or posX is EXTREMO_ESQUERDA) then motorFrente is DIREITA and motorFundo is DIREITA", engine));
        ruleBlock.addRule(Rule.parse("if posX is ESQUERDA then motorFrente is NADA and motorFundo is NADA", engine));
        ruleBlock.addRule(Rule.parse("if posX is MEIO then motorFrente is DIREITA and motorFundo is DIREITA", engine));
        ruleBlock.addRule(Rule.parse("if posX is DIREITA then motorFrente is NADA and motorFundo is NADA", engine));
        ruleBlock.addRule(Rule.parse("if posX is EXTREMO_DIREITA then motorFrente is ESQUERDA and motorFundo is ESQUERDA", engine));
//        ruleBlock.addRule(Rule.parse("if deltaXDireita is BAIXO and (deltaYAbaixo is BAIXO) then motorFrente is ESQUERDA", engine));
//        ruleBlock.addRule(Rule.parse("if deltaXDireita is MEDIO then motorFrente is NADA", engine));
//        ruleBlock.addRule(Rule.parse("if deltaXDireita is ALTO then motorFrente is NADA", engine));
//        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
//        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);
        
        engine.configure("Minimum", "", "Minimum", "Maximum", "Centroid");
    	
    	        StringBuilder status = new StringBuilder();
    	        if (!engine.isReady(status)) {
    	            throw new RuntimeException("Engine not ready. "
    	                    + "The following errors were encountered:\n" + status.toString());
    	        }
    	
    	        for (int i = 0; i < 50; ++i) {
    	        	double x = posX.getMinimum() + i * (posX.range() / 50);
    	        	posX.setInputValue(x);
    	            engine.process();
    	            FuzzyLite.logger().info(String.format(
    	                    "posX = %s => motorFrente = %s, motorFundo = %s",
    	                    Op.str(x), Op.str(Math.round(motorFrente.getOutputValue())), Op.str(Math.round(motorFundo.getOutputValue()))));
    	        }
	}
}

