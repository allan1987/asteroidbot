package fuzzylogic;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.defuzzifier.MeanOfMaximum;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Discrete;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class Example {

	public static void main(String[] args){
		Engine engine = new Engine();
		engine.setName("Laundry");

		InputVariable inputVariable1 = new InputVariable();
		inputVariable1.setEnabled(true);
		inputVariable1.setName("Load");
		inputVariable1.setRange(0.000, 6.000);
		inputVariable1.addTerm(Discrete.create("small", 0.000, 1.000, 1.000, 1.000, 2.000, 0.800, 5.000, 0.000));
		inputVariable1.addTerm(Discrete.create("normal", 3.000, 0.000, 4.000, 1.000, 6.000, 0.000));
		engine.addInputVariable(inputVariable1);

		InputVariable inputVariable2 = new InputVariable();
		inputVariable2.setEnabled(true);
		inputVariable2.setName("Dirt");
		inputVariable2.setRange(0.000, 6.000);
		inputVariable2.addTerm(Discrete.create("low", 0.000, 1.000, 2.000, 0.800, 5.000, 0.000));
		inputVariable2.addTerm(Discrete.create("high", 1.000, 0.000, 2.000, 0.200, 4.000, 0.800, 6.000, 1.000));
		engine.addInputVariable(inputVariable2);

		OutputVariable outputVariable1 = new OutputVariable();
		outputVariable1.setEnabled(true);
		outputVariable1.setName("Detergent");
		outputVariable1.setRange(0.000, 80.000);
		outputVariable1.fuzzyOutput().setAccumulation(new Maximum());
		outputVariable1.setDefuzzifier(new MeanOfMaximum(500));
		outputVariable1.setDefaultValue(Double.NaN);
		outputVariable1.setLockPreviousOutputValue(false);
		outputVariable1.setLockOutputValueInRange(false);
		outputVariable1.addTerm(Discrete.create("less", 10.000, 0.000, 40.000, 1.000, 50.000, 0.000));
		outputVariable1.addTerm(Discrete.create("normal", 40.000, 0.000, 50.000, 1.000, 60.000, 1.000, 80.000, 0.000));
		outputVariable1.addTerm(Discrete.create("more", 50.000, 0.000, 80.000, 1.000));
		engine.addOutputVariable(outputVariable1);

		OutputVariable outputVariable2 = new OutputVariable();
		outputVariable2.setEnabled(true);
		outputVariable2.setName("Cycle");
		outputVariable2.setRange(0.000, 20.000);
		outputVariable2.fuzzyOutput().setAccumulation(new Maximum());
		outputVariable2.setDefuzzifier(new MeanOfMaximum(500));
		outputVariable2.setDefaultValue(Double.NaN);
		outputVariable2.setLockPreviousOutputValue(false);
		outputVariable2.setLockOutputValueInRange(false);
		outputVariable2.addTerm(Discrete.create("short", 0.000, 1.000, 10.000, 1.000, 20.000, 0.000));
		outputVariable2.addTerm(Discrete.create("long", 10.000, 0.000, 20.000, 1.000));
		engine.addOutputVariable(outputVariable2);

		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setEnabled(true);
		ruleBlock.setName("");
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setActivation(new Minimum());
		ruleBlock.addRule(Rule.parse("if Load is small and Dirt is not high then Detergent is less", engine));
		ruleBlock.addRule(Rule.parse("if Load is small and Dirt is high then  Detergent is normal", engine));
		ruleBlock.addRule(Rule.parse("if Load is normal and Dirt is low then Detergent is less", engine));
		ruleBlock.addRule(Rule.parse("if Load is normal and Dirt is high then Detergent is more", engine));
		ruleBlock.addRule(Rule.parse("if Detergent is normal or Detergent is less then Cycle is short", engine));
		ruleBlock.addRule(Rule.parse("if Detergent is more then Cycle is long", engine));
		engine.addRuleBlock(ruleBlock);

        for (int i = 0; i < 50; ++i) {
            double var1 = inputVariable1.getMinimum() + i * (inputVariable1.range() / 50);
            inputVariable1.setInputValue(var1);
            double var2 = inputVariable2.getMinimum() + i * (inputVariable2.range() / 50);
            inputVariable1.setInputValue(var2);
            engine.process();
            FuzzyLite.logger().info(String.format(
                    "var1 = %s, var2 = %s <==> out1 = %s, out2 = %s",
                    Op.str(var1), Op.str(var2), Op.str(outputVariable1.getOutputValue()), Op.str(outputVariable2.getOutputValue())));
        }
    }
}
