package artbidding;

import java.util.*;

public class AuctionService {
    private Map<Integer, User> users = new HashMap<>();
    private List<Artwork> artworks = new ArrayList<>();
    private Map<Integer, List<Bid>> bids = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public void placeBid(Bid bid) {
        bids.computeIfAbsent(bid.getArtwork().getId(), k -> new ArrayList<>()).add(bid);
    }

    public void closeAuction(int artworkId) {
        List<Bid> artworkBids = bids.getOrDefault(artworkId, new ArrayList<>());
        if (!artworkBids.isEmpty()) {
            artworkBids.sort(null);
            Bid winningBid = artworkBids.get(0);
            Transaction transaction = new Transaction(winningBid);
            transactions.add(transaction);
            System.out.println("Auction closed. Winning bid: " + winningBid);
        } else {
            System.out.println("No bids placed for artwork ID " + artworkId);
        }
    }

    public void listArtworks() {
        for (Artwork a : artworks) {
            System.out.println(a);
        }
    }

    public void listTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    public List<Bid> getBidsForArtwork(int artworkId) {
        return bids.getOrDefault(artworkId, new ArrayList<>());
    }

    public void updateUserEmail(int userId, String newEmail) {
        User user = users.get(userId);
        if (user != null) {
            user.email = newEmail;
        }
    }

    public void removeUser(int userId) {
        users.remove(userId);
    }

    public void removeArtwork(int artworkId) {
        artworks.removeIf(art -> art.getId() == artworkId);
        bids.remove(artworkId);
    }

    public User getUserById(int id) {
        return users.get(id);
    }

}