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
	private static InputVariable deltaXFromLeft, deltaXFromRight, deltaYFromTop, deltaYFromBottom;
	private static OutputVariable sideThrustFront, sideThrustBack;
	
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
        
        deltaXFromLeft = new InputVariable();
        deltaXFromLeft.setName("deltaXFromLeft");
        deltaXFromLeft.setRange(-40.0, 0.0);
        deltaXFromLeft.addTerm(new Triangle("LOW", 0.0, -10.0, -20.0));
        deltaXFromLeft.addTerm(new Triangle("MEDIUM", -10.0, -20.0, -30.0));
        deltaXFromLeft.addTerm(new Triangle("HIGH", -20.0, -30.0, -40.0));
        engine.addInputVariable(deltaXFromLeft);
        
        deltaXFromRight = new InputVariable();
        deltaXFromRight.setName("deltaXFromRight");
        deltaXFromRight.setRange(0.0, 40.0);
        deltaXFromRight.addTerm(new Triangle("LOW", 0.0, 10.0, 20.0));
        deltaXFromRight.addTerm(new Triangle("MEDIUM", 10.0, 20.0, 30.0));
        deltaXFromRight.addTerm(new Triangle("HIGH", 20.0, 30.0, 40.0));
        engine.addInputVariable(deltaXFromRight);
        
        deltaYFromTop = new InputVariable();
        deltaYFromTop.setName("deltaYFromTop");
        deltaYFromTop.setRange(-40.0, 0.0);
        deltaYFromTop.addTerm(new Triangle("LOW", 0.0, -10.0, -20.0));
        deltaYFromTop.addTerm(new Triangle("MEDIUM", -10.0, -20.0, -30.0));
        deltaYFromTop.addTerm(new Triangle("HIGH", -20.0, -30.0, -40.0));
        engine.addInputVariable(deltaYFromTop);
        
        deltaYFromBottom = new InputVariable();
        deltaYFromBottom.setName("deltaYFromBottom");
        deltaYFromBottom.setRange(0.0, 40.0);
        deltaYFromBottom.addTerm(new Triangle("LOW", 0.0, 10.0, 20.0));
        deltaYFromBottom.addTerm(new Triangle("MEDIUM", 10.0, 20.0, 30.0));
        deltaYFromBottom.addTerm(new Triangle("HIGH", 20.0, 30.0, 40.0));
        engine.addInputVariable(deltaYFromBottom);
        
        sideThrustFront = new OutputVariable();
        sideThrustFront.setName("sideThrustFront");
        sideThrustFront.setRange(-1.0, 1.0);
        sideThrustFront.setDefaultValue(Double.NaN);
        sideThrustFront.addTerm(new Triangle("LEFT", -1.0, -0.5, 0.0));
        sideThrustFront.addTerm(new Triangle("NONE", -0.5, 0.0, 0.5));
        sideThrustFront.addTerm(new Triangle("RIGHT", 0.0, 0.5, 1.0));
        engine.addOutputVariable(sideThrustFront);
        
//        sideThrustBack = new OutputVariable();
//        sideThrustBack.setName("sideThrustBack");
//        sideThrustBack.setRange(-1.0, 1.0);
//        sideThrustBack.setDefaultValue(Double.NaN);
//        sideThrustBack.addTerm(new Triangle("LOW", -1.0, -0.5, 0));
//        sideThrustBack.addTerm(new Triangle("MEDIUM", -0.5, 0.0, 0.5));
//        sideThrustBack.addTerm(new Triangle("HIGH", 0.0, 0.5, 1.0));
//        engine.addOutputVariable(sideThrustBack);
        
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(null);
        ruleBlock.addRule(Rule.parse("if deltaXFromRight is LOW and deltaYFromBottom is LOW then sideThrustFront is LEFT", engine));
        ruleBlock.addRule(Rule.parse("if deltaXFromRight is MEDIUM then sideThrustFront is NONE", engine));
        ruleBlock.addRule(Rule.parse("if deltaXFromRight is HIGH then sideThrustFront is NONE", engine));
//        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
//        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);
        
        engine.configure("", "", "Minimum", "Maximum", "Centroid");
    	
    	        StringBuilder status = new StringBuilder();
    	        if (!engine.isReady(status)) {
    	            throw new RuntimeException("Engine not ready. "
    	                    + "The following errors were encountered:\n" + status.toString());
    	        }
    	
    	        for (int i = 0; i < 50; ++i) {
    	        	double right = deltaXFromRight.getMinimum() + i * (deltaXFromRight.range() / 50);
//    	        	double top = deltaYFromTop.getMinimum() + i * (deltaYFromTop.range() / 50);
    	        	deltaXFromRight.setInputValue(right);
//    	            deltaYFromTop.setInputValue(top);
    	            engine.process();
    	            FuzzyLite.logger().info(String.format(
    	                    "right = %s, output = %s",
    	                    Op.str(right), Op.str(sideThrustFront.getOutputValue())));
    	        }
	}
}

