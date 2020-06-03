import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import javax.swing.*;
import java.util.regex.Pattern;
import static org.assertj.swing.timing.Pause.pause;

@SuppressWarnings("CucumberJavaStepDefClassInDefaultPackage")
public class StepDefinitions {
    private FrameFixture frame;
    private Robot robot;

    @Before
    public void preStepsTest() {
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
            System.out.println("frameCleanUp");
            frame.cleanUp();
        }

        InfoKiosk.Dispose();
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
        frame.label(JLabelMatcher.withText(Pattern.compile(".*" + text + ".*", Pattern.CASE_INSENSITIVE)).andShowing());
    }

    @Then("Frame {string} displayed")
    public void frameDisplayed(String frameName) {
        frame = WindowFinder.findFrame(frameName).using(robot);
    }

    @And("I wait {int} seconds")
    public void iWaitSeconds(int seconds) {
        pause(seconds * 1000);
    }

    @And("I press button with key {int} on keyboard")
    public void iPressButtonWithKeyOnKeyboard(int key) {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(key));
    }

    @Then("I don't see text {string}")
    public void iDonTSeeText(String text) {
        try {
            frame.label(JLabelMatcher.withText(Pattern.compile(".*" + text + ".*", Pattern.CASE_INSENSITIVE)).andShowing());
        } catch (Exception e) {
            // Control isn't found. Test complete successful
            return;
        }
        // Control is found. Execute exception
        throw new RuntimeException("Text \"" + text + "\" is found in frame.");
    }
}