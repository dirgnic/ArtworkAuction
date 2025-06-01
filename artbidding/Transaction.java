package artbidding;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private Bid bid;
    private LocalDateTime date;

    public Transaction(Bid bid) {
        // id-ul va fi setat de TransactionService după inserare în DB
        this.bid = bid;
        this.date = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Bid getBid() { return bid; }
    public void setBid(Bid bid) { this.bid = bid; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    // Add this getter for compatibility with TransactionService
    public Artwork getArtwork() {
        return bid != null ? bid.getArtwork() : null;
    }

    // Add this getter for compatibility with TransactionService
    public Bidder getBidder() {
        return bid != null ? bid.getBidder() : null;
    }

    // Add this getter for compatibility with TransactionService
    public double getAmount() {
        return bid != null ? bid.getAmount() : 0.0;
    }

    // Add this getter for compatibility with TransactionService
    public LocalDateTime getTimestamp() {
        return getDate();
    }

    @Override
    public String toString() {
        return "Tranzactie {id=" + id + ", bid=" + bid + ", date=" + date + '}';
    }
}