package banking_system;

import java.util.Date;

public class TransactionData {
    private Long source;
    private Long destination;
    private Date date;
    private double amount;

    public TransactionData(Long source, Long destination, Date date, double amount) {
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.amount = amount;
    }

    public Long getSource() {
        return source;
    }

    public Long getDestination() {
        return destination;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "source:" + (source == null?"Income":source) +
                ", destination:" + (destination==null?"Withdrawal" : destination) +
                ", date:" + date +
                ", amount:" + amount +
                '}';
    }
}
