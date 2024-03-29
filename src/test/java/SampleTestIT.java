import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SampleTestIT extends Selenified {
    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "https://www.coveros.com/");
    }

    @DataProvider(name = "coveros search terms", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{"python"},
                new Object[]{"perl"}, new Object[]{"bash"},};
    }

    @Test(groups = {"sample", "coveros"}, description = "A sample selenium test to check a title")
    public void sampleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify the correct page title
        app.azzert().titleEquals("Coveros | Bringing together agile and security to deliver superior software");
        // verify no issues
        finish();
    }
    
    @Test(dataProvider = "coveros search terms", groups = {"sample", "coveros"},
            description = "A sample selenium test using a data provider to perform a search")
    public void sampleTestWDataProvider(String searchTerm) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // find the search box element and create the object
        Element searchBox = app.newElement(Locator.NAME, "s");
        //perform the search and submit
        searchBox.type(searchTerm);
        searchBox.submit();
        //wait for the page to return the results
        app.newElement(Locator.ID, "recent-posts-4").waitForState().present();
        // verify the correct page title
        app.azzert().titleEquals("You searched for " + searchTerm + " - Coveros");
        // verify no issues
        finish();
    }
}