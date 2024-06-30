package co.wedevx.digitalbank.automation.api.steps;

import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.*;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;

public class Hooks {

    @BeforeAll
    public static void establishConnectionToDB(){
        DBUtils.establishSQLConnection();
    }

    @Before()
    public void beforeEach(){
        DBUtils.establishSQLConnection();
//        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.ui.registration.url"));
    }

    @Before(value = "@GeneralDB")
    public void beforeLogin(){
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.ui.login.url"));
    }

    @After(value = "@GeneralDB")
    public void afterLogin(){

    }

    @After
    public void afterEachCloseDriver(Scenario scenario){
        Driver.takeScreenshot(scenario);
        Driver.closeDriver();
    }

    @AfterAll
    public static void closeConnectionToDB(){
        DBUtils.closeConnection();
    }


}
