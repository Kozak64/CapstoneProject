package models;

public abstract class PaymentDecorator implements PaymentProcess {
    protected final PaymentProcess wrappedPayment;

    public PaymentDecorator(PaymentProcess wrappedPayment) {
        if (wrappedPayment == null) {
            throw new IllegalArgumentException("Wrapped payment cannot be null.");
        }
        this.wrappedPayment = wrappedPayment;
    }

    @Override
    public void processPayment() throws Exception {
        wrappedPayment.processPayment();
    }
}
