package artbidding;

import java.time.LocalDateTime;

public class Bid implements Comparable<Bid> {
    private static int idCounter = 1;
    private final int id;
    private Bidder bidder;
    private Artwork artwork;
    private double amount;
    private LocalDateTime time;

    public Bid(Bidder bidder, Artwork artwork, double amount) {
        this.id = idCounter++;
        this.bidder = bidder;
        this.artwork = artwork;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Bidder getBidder() { return bidder; }
    public Artwork getArtwork() { return artwork; }
    public double getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }

    @Override
    public int compareTo(Bid o) {
        return Double.compare(o.amount, this.amount);
    }

    @Override
    public String toString() {
        return "Bid{id=" + id + ", bidder=" + bidder.getName() + ", artwork=" + artwork.getTitle() + ", amount=" + amount + ", time=" + time + '}';
    }
}