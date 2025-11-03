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
    public void Down() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorAndGetAccountId() {
        assertEquals("MEKI1234", bankAccount.getAccountId());
    }

    @Test
    public void PayReturnsTrue() {
        boolean result = bankAccount.pay(100.50);
        assertTrue(result);
    }

    @Test
    public void PayCorrectMessage() {
        bankAccount.pay(100.50);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.5 using Bank Account MEKI1234"));
    }

    @Test
    public void PayZeroAmount() {
        boolean result = bankAccount.pay(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 0.0 using Bank Account MEKI1234"));
    }

    @Test
    public void PayLargeAmount() {
        boolean result = bankAccount.pay(999999.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 999999.99 using Bank Account MEKI1234"));
    }

    @Test
    public void RefundReturnsTrue() {
        boolean result = bankAccount.refund(50.25);
        assertTrue(result);
    }

    @Test
    public void RefundCorrectMessage() {
        bankAccount.refund(50.25);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.25 to Bank Account MEKI1234"));
    }

    @Test
    public void RefundZeroAmount() {
        boolean result = bankAccount.refund(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 0.0 to Bank Account MEKI1234"));
    }

    @Test
    public void MultiplePayments() {
        bankAccount.pay(100.0);
        bankAccount.pay(200.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using Bank Account MEKI1234"));
        assertTrue(output.contains("Paid 200.0 using Bank Account MEKI1234"));
    }

    @Test
    public void MultipleRefunds() {
        bankAccount.refund(50.0);
        bankAccount.refund(75.0);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.0 to Bank Account MEKI1234"));
        assertTrue(output.contains("Refunded 75.0 to Bank Account MEKI1234"));
    }

    @Test
    public void PayAndRefund() {
        bankAccount.pay(100.0);
        bankAccount.refund(50.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using Bank Account MEKI1234"));
        assertTrue(output.contains("Refunded 50.0 to Bank Account MEKI1234"));
    }

    @Test
    public void DifferentAccountIds() {
        BankAccount account1 = new BankAccount("ACC001");
        BankAccount account2 = new BankAccount("ACC002");
        
        assertEquals("ACC001", account1.getAccountId());
        assertEquals("ACC002", account2.getAccountId());
        assertNotEquals(account1.getAccountId(), account2.getAccountId());
    }
}
