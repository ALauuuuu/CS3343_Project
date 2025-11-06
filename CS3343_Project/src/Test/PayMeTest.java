package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Payment.PayMe;

public class PayMeTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PayMe payMe;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        payMe = new PayMe("MK123456");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorAndGetAccountId() {
        assertEquals("MK123456", payMe.getAccountId());
    }

    @Test
    public void PayPrintsCorrectMessage() {
        payMe.pay(100.50);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.5 using PayMe MK123456"));
    }

    @Test
    public void PayWithZeroAmount() {
        boolean result = payMe.pay(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 0.0 using PayMe MK123456"));
    }

    @Test
    public void PayWithLargeAmount() {
        boolean result = payMe.pay(999999.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 999999.99 using PayMe MK123456"));
    }

    @Test
    public void PayWithDecimalAmount() {
        boolean result = payMe.pay(50.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 50.99 using PayMe MK123456"));
    }

    @Test
    public void RefundReturnsTrue() {
        boolean result = payMe.refund(50.25);
        assertTrue(result);
    }

    @Test
    public void RefundPrintsCorrectMessage() {
        payMe.refund(50.25);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.25 to PayMe MK123456"));
    }

    @Test
    public void RefundWithZeroAmount() {
        boolean result = payMe.refund(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 0.0 to PayMe MK123456"));
    }

    @Test
    public void RefundWithLargeAmount() {
        boolean result = payMe.refund(888888.88);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 888888.88 to PayMe MK123456"));
    }

    @Test
    public void MultiplePayments() {
        payMe.pay(100.0);
        payMe.pay(200.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using PayMe MK123456"));
        assertTrue(output.contains("Paid 200.0 using PayMe MK123456"));
    }

    @Test
    public void MultipleRefunds() {
        payMe.refund(50.0);
        payMe.refund(75.0);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 50.0 to PayMe MK123456"));
        assertTrue(output.contains("Refunded 75.0 to PayMe MK123456"));
    }

    @Test
    public void PayAndRefund() {
        payMe.pay(100.0);
        payMe.refund(50.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using PayMe MK123456"));
        assertTrue(output.contains("Refunded 50.0 to PayMe MK123456"));
    }

    @Test
    public void DifferentAccountIds() {
        PayMe account1 = new PayMe("MK001");
        PayMe account2 = new PayMe("MK002");
        
        assertEquals("MK001", account1.getAccountId());
        assertEquals("MK002", account2.getAccountId());
        assertNotEquals(account1.getAccountId(), account2.getAccountId());
    }

    @Test
    public void AccountIdWithPhoneNumber() {
        PayMe phonePayMe = new PayMe("91234567");
        assertEquals("91234567", phonePayMe.getAccountId());
        
        phonePayMe.pay(50.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 50.0 using PayMe 91234567"));
    }

    @Test
    public void AccountIdWithEmail() {
        PayMe emailPayMe = new PayMe("meki@example.com");
        assertEquals("meki@example.com", emailPayMe.getAccountId());
        
        emailPayMe.pay(75.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 75.0 using PayMe meki@example.com"));
    }

    @Test
    public void PaymentSequence() {
        payMe.pay(500.0);
        String output1 = outContent.toString();
        assertTrue(output1.contains("Paid 500.0 using PayMe MK123456"));
        
        outContent.reset();
        
        payMe.refund(100.0);
        String output2 = outContent.toString();
        assertTrue(output2.contains("Refunded 100.0 to PayMe MK123456"));
    }

    @Test
    public void SmallAmountPayment() {
        boolean result = payMe.pay(0.01);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 0.01 using PayMe MK123456"));
    }

    @Test
    public void SmallAmountRefund() {
        boolean result = payMe.refund(0.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 0.99 to PayMe MK123456"));
    }

    @Test
    public void PayWithRoundedAmount() {
        payMe.pay(99.99);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 99.99 using PayMe MK123456"));
    }

    @Test
    public void MultipleAccountsIndependentOperations() {
        PayMe account1 = new PayMe("MKK001");
        PayMe account2 = new PayMe("MKK002");
        
        account1.pay(100.0);
        account2.pay(200.0);
        
        String output = outContent.toString();
        assertTrue(output.contains("Paid 100.0 using PayMe MKK001"));
        assertTrue(output.contains("Paid 200.0 using PayMe MKK002"));
    }
}
