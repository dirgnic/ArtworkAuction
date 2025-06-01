package artbidding;

import java.time.LocalDateTime;

public class Bid implements Comparable<Bid> {
    private int id;
    private Bidder bidder;
    private Artwork artwork;
    private double amount;
    private LocalDateTime time;

    public Bid(Bidder bidder, Artwork artwork, double amount) {
        // id-ul va fi setat de BidService după inserare în DB
        this.bidder = bidder;
        this.artwork = artwork;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Bidder getBidder() { return bidder; }
    public Artwork getArtwork() { return artwork; }
    public double getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }

    // Add setter for time to allow setting bid time from DB
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int compareTo(Bid o) {
        return Double.compare(o.amount, this.amount);
    }

    @Override
    public String toString() {
        return "Bid{id=" + id + ", bidder=" + bidder.getName() + ", artwork=" + artwork.getTitle() + ", amount=" + amount + ", time=" + time + '}';
    }
}