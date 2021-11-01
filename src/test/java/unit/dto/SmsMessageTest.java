package unit.dto;

import dto.SmsMessage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class SmsMessageTest {

    @Test
    public void recipientMustBeSatAfterInitialization() {
        String recipient = "+4512345678";
        String message = "";

        SmsMessage sms = new SmsMessage(recipient, message);

        assertEquals(recipient, sms.getRecipient());
    }

    @Test
    public void messageMustBeSatAfterInitialization() {
        String recipient = "";
        String message = "This is a confirmation of your booking!";

        SmsMessage sms = new SmsMessage(recipient, message);

        assertEquals(message, sms.getMessage());
    }

    @Test
    public void equalsMustReturnTrueIfTheObjectsAreIdentical() {
        String recipient = "+4512345678";
        String message = "This is a confirmation of your booking!";

        SmsMessage sms = new SmsMessage(recipient, message);
        SmsMessage identicalSms = new SmsMessage(recipient, message);

        assertTrue(sms.equals(identicalSms));
    }

    @Test
    public void equalsMustReturnFalseIfTheObjectsAreNotIdentical() {
        String recipient = "+4512345678";
        String message = "This is a confirmation of your booking!";

        String differentRecipient = "+451234567";
        String differentMessage = "This is a confirmation of your booking";

        SmsMessage sms = new SmsMessage(recipient, message);
        SmsMessage differentSms = new SmsMessage(differentRecipient, differentMessage);

        assertFalse(sms.equals(differentSms));
    }
}
