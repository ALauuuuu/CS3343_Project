package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import Payment.CreditCard;

public class CreditCardTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CreditCard creditCard;
    private Date expiryDate;

    @BeforeEach
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        expiryDate = sdf.parse("12/25");
        creditCard = new CreditCard("1111-2222-3333-4444", expiryDate);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorAndGetCardNumber() {
        assertEquals("1111-2222-3333-4444", creditCard.getCardNumber());
    }

    @Test
    public void ConstructorAndGetExpiryDate() {
        assertEquals(expiryDate, creditCard.getExpiryDate());
    }

    @Test
    public void PayReturnsTrue() {
        boolean result = creditCard.pay(250.75);
        assertTrue(result);
    }

    @Test
    public void PayPrintsCorrectMessage() {
        creditCard.pay(250.75);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 250.75 using Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void PayWithZeroAmount() {
        boolean result = creditCard.pay(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 0.0 using Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void PayWithLargeAmount() {
        boolean result = creditCard.pay(10000.99);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 10000.99 using Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void RefundReturnsTrue() {
        boolean result = creditCard.refund(100.50);
        assertTrue(result);
    }

    @Test
    public void RefundPrintsCorrectMessage() {
        creditCard.refund(100.50);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 100.5 to Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void RefundWithZeroAmount() {
        boolean result = creditCard.refund(0.0);
        assertTrue(result);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 0.0 to Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void MultiplePayments() {
        creditCard.pay(150.0);
        creditCard.pay(300.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 150.0 using Credit Card 1111-2222-3333-4444"));
        assertTrue(output.contains("Paid 300.0 using Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void MultipleRefunds() {
        creditCard.refund(75.0);
        creditCard.refund(125.0);
        String output = outContent.toString();
        assertTrue(output.contains("Refunded 75.0 to Credit Card 1111-2222-3333-4444"));
        assertTrue(output.contains("Refunded 125.0 to Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void PayAndRefund() {
        creditCard.pay(500.0);
        creditCard.refund(100.0);
        String output = outContent.toString();
        assertTrue(output.contains("Paid 500.0 using Credit Card 1111-2222-3333-4444"));
        assertTrue(output.contains("Refunded 100.0 to Credit Card 1111-2222-3333-4444"));
    }

    @Test
    public void DifferentCardNumbers() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date date1 = sdf.parse("10/26");
        Date date2 = sdf.parse("11/27");
        
        CreditCard card1 = new CreditCard("1111-2222-3333-5555", date1);
        CreditCard card2 = new CreditCard("5555-6666-7777-8888", date2);
        
        assertEquals("1111-2222-3333-5555", card1.getCardNumber());
        assertEquals("5555-6666-7777-8888", card2.getCardNumber());
        assertNotEquals(card1.getCardNumber(), card2.getCardNumber());
    }

    @Test
    public void DifferentExpiryDates() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date date1 = sdf.parse("01/25");
        Date date2 = sdf.parse("12/26");
        
        CreditCard card1 = new CreditCard("1234-5678-9012-3456", date1);
        CreditCard card2 = new CreditCard("1234-5678-9012-3456", date2);
        
        assertNotEquals(card1.getExpiryDate(), card2.getExpiryDate());
    }

    @Test
    public void ExpiryDateFormat() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date testDate = sdf.parse("06/28");
        CreditCard card = new CreditCard("9999-8888-7777-6666", testDate);
        
        assertEquals(testDate, card.getExpiryDate());
        String formattedDate = sdf.format(card.getExpiryDate());
        assertEquals("06/28", formattedDate);
    }
}
