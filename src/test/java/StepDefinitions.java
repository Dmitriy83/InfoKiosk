import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;

import javax.swing.*;

import static org.assertj.swing.timing.Pause.pause;

public class StepDefinitions {
    private FrameFixture frame;
    private Robot robot;

    @Before
    public void preStepsTest() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        InfoKiosk.main(null);

        frame = WindowFinder.findFrame(JFrame.class).using(robot);
    }

    @After
    public void cleanup() {
        if (robot != null) {
            System.out.println("Cleanup");
            robot.cleanUp();
        }
        if (frame != null) {
            System.out.println("frameSettingsCleanUp");
            frame.cleanUp();
        }
    }

    @When("I input {string} in field {string}")
    public void iInputInField(String text, String fieldName) {
        frame.textBox(fieldName).setText(text);
    }

    @Given("I click button {string}")
    public void iClickButton(String btnName) {
        robot.waitForIdle();
        frame.button(btnName).click();
    }

    @Then("I see text {string}")
    public void iSeeText(String text) {
        frame.label(JLabelMatcher.withText(text).andShowing());
    }


    @Then("Frame {string} displayed")
    public void frameDisplayed(String frameName) {
        frame = WindowFinder.findFrame(frameName).using(robot);
    }

    @And("I wait {int} seconds")
    public void iWaitSeconds(int seconds) {
        pause(seconds * 1000);
    }
}