package artbidding;

import java.util.ArrayList;
import java.util.List;

public class Bidder extends User {
    private List<Bid> bidHistory;

    public Bidder(int id, String name, String email) {
        super(id, name, email);
        this.bidHistory = new ArrayList<>();
    }

    @Override
    public void displayUserType() {
        System.out.println("Bidder: " + name);
    }

    public void addBid(Bid bid) {
        bidHistory.add(bid);
    }

    public List<Bid> getBidHistory() {
        return bidHistory;
    }

    public void listBidHistory() {
        System.out.println("Bid history for " + name + ":");
        for (Bid b : bidHistory) {
            System.out.println(b);
        }
    }

    public Bid getHighestBid() {
        if (bidHistory.isEmpty()) return null;

        Bid highest = bidHistory.get(0);
        for (Bid b : bidHistory) {
            if (b.getAmount() > highest.getAmount()) {
                highest = b;
            }
        }
        return highest;
    }
}
