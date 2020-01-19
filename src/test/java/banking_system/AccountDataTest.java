package banking_system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

@Category({TestEntity.class, TestControl.class})
public class AccountDataTest {

    public AccountDataTest(){

    }

    Data data;

    @Before
    public void init(){
        data = new Data();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testChangeBalance(){
        AccountData data = new AccountData(100000001, 200.0, "USD", "ROR");
        double bal = data.getBalance();
        boolean success = data.changeBalance(-100.0);
        Assert.assertTrue(success);
        Assert.assertTrue(bal - 100 == data.getBalance());
        Assert.assertTrue(data.getBalance() == 100.0);
        success = data.changeBalance(200.0);
        Assert.assertTrue(success);
        success = data.changeBalance(-2000.0);
        Assert.assertTrue(!success);
    }

    @Test
    public void testTransfer(){
        AccountData data1 = new AccountData(100000001, 200.0, "USD", "ROR");
        AccountData data2 = new AccountData(100000002, 200.0, "USD", "ROR");
        boolean success = data1.transfer(data2, 100.0);
        assertTrue(success);
        assertTrue(data1.getBalance() == 100.0 && data2.getBalance() == 300.0);
        success = data2.transfer(data1, 1000.0);
        assertTrue(!success);
    }

}