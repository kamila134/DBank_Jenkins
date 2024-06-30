package co.wedevx.digitalbank.automation.api.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("api.features")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME, value ="co.wedevx.digitalbank.automation.api.steps")
@IncludeTags("NegativeBankingAccount")
public class APIRegressionRunner {


}
