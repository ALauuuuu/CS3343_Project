package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Payment.BankAccount;

public class BankAccountTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        bankAccount = new BankAccount("MEKI1234");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testConstructorAndGetAccountId() {
        assertEquals("MEKI1234", bankAccount.getAccountId());
    }

    @Test
    public void testPayReturnsTrue() {
        boolean result = bankAccount.pay(100.50);
        assertTrue(result);
    }

    @Test
    public void testPayPrintsCorrectMessage() {
        bankAccount.pay(100.50);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.5 using Bank Account MEKI1234"));
    }

    @Test
    public void testPayWithZeroAmount() {
        boolean result = bankAccount.pay(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 0.0 using Bank Account MEKI1234"));
    }

    @Test
    public void testPayWithLargeAmount() {
        boolean result = bankAccount.pay(999999.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 999999.99 using Bank Account MEKI1234"));
    }

    @Test
    public void testRefundReturnsTrue() {
        boolean result = bankAccount.refund(50.25);
        assertTrue(result);
    }

    @Test
    public void testRefundPrintsCorrectMessage() {
        bankAccount.refund(50.25);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.25 to Bank Account MEKI1234"));
    }

    @Test
    public void testRefundWithZeroAmount() {
        boolean result = bankAccount.refund(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 0.0 to Bank Account MEKI1234"));
    }

    @Test
    public void testMultiplePayments() {
        bankAccount.pay(100.0);
        bankAccount.pay(200.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using Bank Account MEKI1234"));
        assertTrue(output.contains("Paid 200.0 using Bank Account MEKI1234"));
    }

    @Test
    public void testMultipleRefunds() {
        bankAccount.refund(50.0);
        bankAccount.refund(75.0);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.0 to Bank Account MEKI1234"));
        assertTrue(output.contains("Refunded 75.0 to Bank Account MEKI1234"));
    }

    @Test
    public void testPayAndRefund() {
        bankAccount.pay(100.0);
        bankAccount.refund(50.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using Bank Account MEKI1234"));
        assertTrue(output.contains("Refunded 50.0 to Bank Account MEKI1234"));
    }

    @Test
    public void testDifferentAccountIds() {
        BankAccount account1 = new BankAccount("ACC001");
        BankAccount account2 = new BankAccount("ACC002");
        
        assertEquals("ACC001", account1.getAccountId());
        assertEquals("ACC002", account2.getAccountId());
        assertNotEquals(account1.getAccountId(), account2.getAccountId());
    }
}
