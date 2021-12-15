package behaviortest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class baselineStepDefinitions {

    private static int number;

    @Given("the number {int}")
    public void theNumber(int input) {
        number = input;
    }

    @When("when adding {int}")
    public void whenAdding(int numberToAdd) {
        number += numberToAdd;
    }

    @Then("we should get {int}")
    public void weShouldGet(int expected) {
        assertEquals(expected, number);
    }

}
