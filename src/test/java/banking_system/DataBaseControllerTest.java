package banking_system;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@Category({TestEntity.class})
@RunWith(Parameterized.class)
public class DataBaseControllerTest {

    Data data;
    DataBaseController con;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init(){
        data = new Data();
        con = DataBaseController.getInstance();
    }

    @Parameterized.Parameters
    public static Collection transfers(){
        return Arrays.asList(new Object[][]{{100000001L, 100000002L}, {100000002L, 100000001L}});
    }

    @Parameterized.Parameter
    public Long number1;

    @Parameterized.Parameter(1)
    public Long number2;



    @Test
    public void testCheckLogin(){
        for(int i = 0; i<data.logins.length; i++){
            String result = con.checkLogin(data.logins[1], data.passwords[i]);
            assertTrue(result.equals("User"));
        }
        for(int i = 0; i<data.logins.length; i++){
            String result = con.checkLogin(data.logins[1], data.badPasswords[i]);
            assertTrue(result.equals("Invalid"));
        }
    }

    @Test
    public void testMakeTransfer(){
        con.changeBalance(number1, 200.0);
        con.changeBalance(number2, 200.0);
            boolean success1 = con.makeTransfer(number1, number2, 100.0);
            boolean success2 = con.makeTransfer(number2, number1, 100.0);
            assertTrue(success1);
            assertTrue(success2);
    }
}