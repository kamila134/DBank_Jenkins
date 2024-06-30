package co.wedevx.digitalbank.automation.api.steps;

import co.wedevx.digitalbank.automation.api.models.api_response.UserFromApiResponse;
import co.wedevx.digitalbank.automation.api.models.datatable.UserFromDataTable;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.api.utils.ObjectMapperUtils.objectMapper;
import static co.wedevx.digitalbank.automation.api.utils.RestHttpRequest.requestSpecificationDB;
import static org.junit.jupiter.api.Assertions.*;

public class db_userAccountSteps {
    public static int userId;
    List<Map<String,Object>> nextValList = new ArrayList<>();

    @Given("the user {string} doesn't exist in the DB")
    public void theUserDoes_not_Exist(String email) {
        DBUtils.runSQLDeleteQuery("DELETE from user_profile WHERE email_address = '" + email + "';");
        DBUtils.runSQLDeleteQuery("DELETE from users WHERE username = '" + email + "';");

        nextValList = DBUtils.runSQLSelectQuery("select * from hibernate_sequence");
    }

    @Given("a {string} banking account doesn't exist in the DB")
    public void account_Does_not_Exist(String bankingAccount) {
        //delete acc in account table
        DBUtils.runSQLDeleteQuery("DELETE from account WHERE name = '" + bankingAccount + "';");

        //create temporary table
        DBUtils.runSQLUpdateQuery("create temporary table temp_tab as\n" +
                "select ans.id from account_number_seq ans\n" +
                "left JOIN account a\n" +
                "on a.account_number = ans.id\n" +
                "WHERE a.name is null;");

        //from account_number_seq table delete account numbers of the accounts deleted above
        DBUtils.runSQLDeleteQuery("Delete from account_number_seq where id IN (select id from temp_tab);");

        //drop the temp table
        DBUtils.runSQLDeleteQuery("drop table temp_tab;");

        assertFalse(DBUtils.existsInTheDB("select * from account where name = '" + bankingAccount + "';"), "Account " + bankingAccount + " hasn't been deleted from the DB");
    }



    @Given("a new user is created in the DB")
    public void the_user_is_in_the_db(List<UserFromDataTable> userFromDataTableList) throws JsonProcessingException {
        String newUserJson = objectMapper.writeValueAsString(userFromDataTableList.get(0));

        Response postNewUser = requestSpecificationDB
                .body(newUserJson)
                .when()
                .post("/user");

        UserFromApiResponse user = objectMapper.readValue(postNewUser.asString(), UserFromApiResponse.class);
        userId = user.getId();
    }

    @Given("the user {string} exists in th DB")
    public void theUser_Exists_in_the_DB(String email) {
        assertTrue(DBUtils.existsInTheDB("select * from users where username = '" + email + "'"));
        Map<String,Object> userMap= DBUtils.runSQLSelectQuery("select * from users where username = '" + email + "'").get(0);
        userId = Integer.parseInt(String.valueOf(userMap.get("id")));
        nextValList = DBUtils.runSQLSelectQuery("select * from hibernate_sequence");
    }

    @And("the following info should be saved in the DB:")
    public void theFollowingInfoShouldBeSavedInTheDB(List<Map<String,String>> expectedResultsList) {
        Map<String,String> expectedResultsMap = expectedResultsList.get(0);
        String queryUsers = String.format("select * from users where username = '%s'", expectedResultsMap.get("email"));
        String queryUserProfile = String.format("select * from user_profile where email_address = '%s'", expectedResultsMap.get("email"));

        List<Map<String,Object>> actualInfoUsersList = DBUtils.runSQLSelectQuery(queryUsers);
        List<Map<String,Object>> actualInfoUserProfileList  = DBUtils.runSQLSelectQuery(queryUserProfile);

        assertEquals(1, actualInfoUsersList.size(), "registration generated unexpected number of users");
        assertEquals(1, actualInfoUserProfileList.size(), "registration generated unexpected number of user profiles");

        Map<String,Object> actualInfoUsersMap = actualInfoUsersList.get(0);
        Map<String,Object> actualInfoUserProfileMap = actualInfoUserProfileList.get(0);

        assertEquals(expectedResultsMap.get("title"),actualInfoUserProfileMap.get("title"), "registration generated wrong title");
        assertEquals(expectedResultsMap.get("firstName"),actualInfoUserProfileMap.get("first_name"), "registration generated wrong firstName");
        assertEquals(expectedResultsMap.get("lastName"),actualInfoUserProfileMap.get("last_name"), "registration generated wrong lastName");
        assertEquals(expectedResultsMap.get("gender"),actualInfoUserProfileMap.get("gender"), "registration generated wrong gender");
//        assertEquals(expectedResultsMap.get("dateOfBirth"),actualInfoUserProfileMap.get("dob"), "registration generated wrong dob");
        assertEquals(expectedResultsMap.get("ssn"),actualInfoUserProfileMap.get("ssn"), "registration generated wrong ssn");
        assertEquals(expectedResultsMap.get("email"),actualInfoUserProfileMap.get("email_address"), "registration generated wrong email");
        assertEquals(expectedResultsMap.get("address"),actualInfoUserProfileMap.get("address"), "registration generated wrong address");
        assertEquals(expectedResultsMap.get("locality"),actualInfoUserProfileMap.get("locality"), "registration generated wrong locality");
        assertEquals(expectedResultsMap.get("region"),actualInfoUserProfileMap.get("region"), "registration generated wrong region");
        assertEquals(expectedResultsMap.get("postalCode"),actualInfoUserProfileMap.get("postal_code"), "registration generated wrong postalCode");
        assertEquals(expectedResultsMap.get("country"),actualInfoUserProfileMap.get("country"), "registration generated wrong country");
        assertEquals(expectedResultsMap.get("homePhone"),actualInfoUserProfileMap.get("home_phone"), "registration generated wrong homePhone");

        assertEquals(Boolean.parseBoolean(expectedResultsMap.get("accountNonExpired")), actualInfoUsersMap.get("account_non_expired"), "accountNonExpired mismatch upon registration");
        assertEquals(Boolean.parseBoolean(expectedResultsMap.get("accountNonLocked")), actualInfoUsersMap.get("account_non_locked"), "accountNonLocked mismatch upon registration");
        assertEquals(Boolean.parseBoolean(expectedResultsMap.get("credentialsNonExpired")), actualInfoUsersMap.get("credentials_non_expired"), "credentialsNonExpired mismatch upon registration");
        assertEquals(expectedResultsMap.get("enabled"), String.valueOf(actualInfoUsersMap.get("enabled")), "account enabled mismatch upon registration");
        assertEquals(expectedResultsMap.get("email"), actualInfoUsersMap.get("username"), "email mismatch upon registration");

        long nextVal = (long) nextValList.get(0).get("next_val");
        assertEquals(nextVal,actualInfoUsersMap.get("id"), "users id mismatch upon registration");
        assertEquals(++nextVal,actualInfoUserProfileMap.get("id"), "user profile id mismatch upon registration");

    }
}
