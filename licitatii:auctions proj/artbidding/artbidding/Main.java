package artbidding;

public class Main {
    public static void main(String[] args) {
        AuctionService service = new AuctionService();

        Artist a1 = new Artist(1, "Leonardo", "leo@art.com");
        Bidder b1 = new Bidder(2, "Alice", "alice@bid.com");
        Bidder b2 = new Bidder(3, "Bob", "bob@bid.com");

        service.registerUser(a1);
        service.registerUser(b1);
        service.registerUser(b2);

        Artwork art1 = new Artwork("Mona Lisa", a1, 1000);
        service.addArtwork(art1);

        service.placeBid(new Bid(b1, art1, 1500));
        service.placeBid(new Bid(b2, art1, 2000));

        service.listArtworks();
        service.closeAuction(art1.getId());
        service.listTransactions();

        // Extra actions:
        System.out.println("\nList all users:");
        for (User u : service.listUsers()) {
            System.out.println(u.getName() + " - " + u.getEmail());
        }

        System.out.println("\nGet all bids for artwork:");
        for (Bid bid : service.getBidsForArtwork(art1.getId())) {
            System.out.println(bid);
        }

        System.out.println("\nUpdate user email:");
        service.updateUserEmail(b2.getId(), "bob_updated@bid.com");
        System.out.println("New email for Bob: " + service.getUserById(b2.getId()).getEmail());

        System.out.println("\nRemove artwork:");
        service.removeArtwork(art1.getId());
        service.listArtworks();

        System.out.println("\nRemove user:");
        service.removeUser(b2.getId());
        for (User u : service.listUsers()) {
            System.out.println("Remaining user: " + u.getName());
        }

    }
}