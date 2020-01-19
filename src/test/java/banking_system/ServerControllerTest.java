package banking_system;

import org.junit.experimental.categories.Category;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@Category({TestControl.class, TestEntity.class})
@RunWith(JMockit.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerControllerTest {
    Data data;
    @Tested
    ServerController con;

    @Mocked
    HttpSession session;
    @Mocked
    Model model;

    public ServerControllerTest(){

    }
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init(){
        data = new Data();
        con = new ServerController();
    }

    @Test
    public void testLogin() {
        for (int i = 0; i<data.logins.length; i++) {
            String response = con.login(session, model, data.logins[i], data.passwords[i]);
            Assert.assertEquals(response, "login_user");
        }

        for (int i = 0; i<data.logins.length; i++) {
            String response = con.login(session, model, data.logins[i], data.badPasswords[i]);
            Assert.assertEquals(response, "invalid_login");
        }
        exception.expect(NullPointerException.class);
        con.login(session, model,98102700076L, null);
    }

    @Test
    public void makeOperationTest(){
        String response = con.makeOperation(data.accountNumber, 0);
        Assert.assertEquals(response, "operation_success");
        response = con.makeOperation(data.accountNumber, 100);
        Assert.assertEquals(response, "operation_success");
        response = con.makeOperation(data.accountNumber, -100);
        Assert.assertEquals(response, "operation_success");
        response = con.makeOperation(data.accountNumber, -1000000000);
        Assert.assertEquals(response, "operation_failure");
        response = con.makeOperation(0L, 200);
        Assert.assertEquals(response, "operation_failure");
    }

    @Test
    public void testAccount(){
        String response = con.account(session, model, 98102700076L);
        Assert.assertTrue(response.equals("account"));
    }

}