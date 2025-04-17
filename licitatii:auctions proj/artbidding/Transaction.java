package artbidding;

import java.time.LocalDateTime;

public class Transaction {
    private static int idCounter = 1;
    private final int id;
    private Bid bid;
    private LocalDateTime date;

    public Transaction(Bid bid) {
        this.id = idCounter++;
        this.bid = bid;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Bid getBid() {
        return bid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return bid.getAmount();
    }

    public Bidder getBidder() {
        return bid.getBidder();
    }

    public Artist getArtist() {
        return bid.getArtwork().getArtist();
    }

    public String getArtworkTitle() {
        return bid.getArtwork().getTitle();
    }

    public boolean isAboveThreshold(double threshold) {
        return bid.getAmount() > threshold;
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", artwork=" + getArtworkTitle() + ", buyer=" + getBidder().getName() +
                ", seller=" + getArtist().getName() + ", amount=" + getAmount() + ", date=" + date + '}';
    }
}
