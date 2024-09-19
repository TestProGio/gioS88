import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        // Specify both feature files associated with PlaylistCreationTests and PositiveTests
        features = {
                "src/test/resources/features/Search.feature",
               //"src/test/resources/features/PlaylistCreation.feature",
               // "src/test/resources/features/PositiveLogin.feature"
        },
        glue = {"stepDefinitions"}, // Step definitions package
        plugin = {"pretty", "html:target/cucumber-reports"}, // Reports generation
        tags = "not @skip" // Exclude @skip tagged scenarios
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
}