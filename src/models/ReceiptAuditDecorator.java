package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptAuditDecorator extends PaymentDecorator {
    private static final String RECEIPT_LOG = "data" + File.separator + "receipts.txt";
    private static final DateTimeFormatter LOG_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReceiptAuditDecorator(PaymentProcess wrappedPayment) {
        super(wrappedPayment);
    }

    @Override
    public void processPayment() throws Exception {
        super.processPayment();
        logReceipt();
    }

    private void logReceipt() {
        new File("data").mkdirs();
        String timestamp = LocalDateTime.now().format(LOG_FMT);
        String line = "[" + timestamp + "] " + wrappedPayment.toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECEIPT_LOG, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Warning: could not write receipt log: " + e.getMessage());
        }
    }
}
