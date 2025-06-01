package artbidding;

public class Main {
    public static void main(String[] args) {
        AuctionService service = new AuctionService();

        // se creează utilizatori (artist și licitatori)
        Artist a1 = new Artist("Leonardo", "leo@art.com");
        Artist kev = new Artist("Keke", "keke@art.com");
        Artist a2 = new Artist("Van Gogh", "vangogh@art.com");
        Bidder b1 = new Bidder("Alice", "alice@bid.com");
        Bidder b2 = new Bidder("Bob", "bob@bid.com");

        // se înregistrează utilizatorii
        service.registerUser(a1);
        service.registerUser(kev);
        service.registerUser(b1);
        service.registerUser(b2);

        kev.createCollection("oni world");

        Artwork oniGoesSoth = new Artwork("oni goes south", kev, 12000);
        service.addArtwork(oniGoesSoth);

        kev.addArtworkToCollection("oni world", oniGoesSoth);
        kev.listCollections();


        // artistul creează o colecție nouă
        a1.createCollection("renascentist");
        Artwork noapteaPeLac = new Artwork("Noaptea pe lac", a2, 2300);
        a1.addArtworkToCollection("renascentist", noapteaPeLac);

        // se adaugă o operă de artă
        Artwork art1 = new Artwork("Mona Lisa", a1, 1000);
        service.addArtwork(art1);

        Artwork art2 = new Artwork("Starry night", a1, 1000);

        // se adaugă opera în colecția artistului
        a1.addArtworkToCollection("renascentist", art1);

        // afișare colecții și conținut
        System.out.println("\ncolecții ale artistului:");
        a1.listCollections();

        // se plasează licitații pentru operă
        service.placeBid(new Bid(b1, art1, 1500));
        service.placeBid(new Bid(b2, art1, 2000));
        service.placeBid(new Bid(b2, noapteaPeLac, 3000));

        // afișare opere existente
        System.out.println("\nopere disponibile:");
        service.listArtworks();

        // închidere licitație și afișare tranzacții
        System.out.println("\ntranzacții:");
        service.closeAuction(art1.getId());
        service.listTransactions();

        // listare toți utilizatorii
        System.out.println("\nlistă utilizatori:");
        for (User u : service.listUsers()) {
            System.out.println(u.getName() + " - " + u.getEmail());
        }

        // afișare licitații pentru operă
        System.out.println("\nlicitații pentru operă:");
        for (Bid bid : service.getBidsForArtwork(art1.getId())) {
            System.out.println(bid);
        }

        // actualizare email pentru utilizator
        System.out.println("\nactualizare email:");
        service.updateUserEmail(b2.getId(), "bob_updated@bid.com");
        System.out.println("email nou pentru Bob: " + service.getUserById(b2.getId()).getEmail());

        // ștergere operă
        System.out.println("\nștergere operă:");
        service.removeArtwork(art1.getId());
        service.listArtworks();

        // ștergere utilizator
        System.out.println("\nștergere utilizator:");
        service.removeUser(b2.getId());
        for (User u : service.listUsers()) {
            System.out.println("utilizator rămas: " + u.getName());
        }

        // --- JDBC CRUD demo using singleton services ---
        try {
            UserService userService = UserService.getInstance();
            ArtworkService artworkService = ArtworkService.getInstance();
            BidService bidService = BidService.getInstance();
            TransactionService transactionService = TransactionService.getInstance();

            // CREATE user
            Artist dbArtist = new Artist("DB_Leonardo", "db_leo@art.com");
            userService.createUser(dbArtist);
            System.out.println("DB artist created with id: " + dbArtist.getId());

            // READ user
            User fetchedUser = userService.getUser(dbArtist.getId());
            System.out.println("Fetched from DB: " + fetchedUser.getName() + ", " + fetchedUser.getEmail());

            // UPDATE user
            userService.updateUserEmail(dbArtist.getId(), "db_leo_updated@art.com");
            User updatedUser = userService.getUser(dbArtist.getId());
            System.out.println("Updated email: " + updatedUser.getEmail());

            // CREATE artwork
            Artwork dbArt = new Artwork("DB Mona Lisa", (Artist) fetchedUser, 5000);
            artworkService.createArtwork(dbArt);
            System.out.println("DB artwork created with id: " + dbArt.getId());

            // UPDATE artwork
            artworkService.updateArtwork(dbArt.getId(), "DB Mona Lisa Updated", 7000);
            Artwork updatedArt = artworkService.getArtwork(dbArt.getId(), userService);
            System.out.println("Updated artwork: " + updatedArt.getTitle() + ", price: " + updatedArt.getPrice());

            // CREATE bidder
            Bidder dbBidder = new Bidder("DB_Alice", "db_alice@bid.com");
            userService.createUser(dbBidder);

            // CREATE bid
            Bid dbBid = new Bid(dbBidder, dbArt, 6000);
            bidService.createBid(dbBid);
            System.out.println("DB bid created with id: " + dbBid.getId());

            // UPDATE bid
            bidService.updateBid(dbBid.getId(), 8000);
            System.out.println("Updated bid amount to 8000");

            // CREATE transaction
            Transaction dbTransaction = new Transaction(dbBid);
            transactionService.createTransaction(dbTransaction);
            System.out.println("DB transaction created with id: " + dbTransaction.getId());

            // UPDATE transaction
            transactionService.updateTransaction(dbTransaction.getId(), 9000, java.time.LocalDateTime.now());
            System.out.println("Updated transaction amount to 9000 and timestamp to now");

            // DELETE bid
            bidService.deleteBid(dbBid.getId());
            System.out.println("DB bid deleted.");

            // DELETE transaction(s) for the artwork before deleting the artwork
            for (Transaction t : transactionService.getAllTransactions(userService, artworkService)) {
                if (t.getArtwork().getId() == dbArt.getId()) {
                    transactionService.deleteTransaction(t.getId());
                    System.out.println("DB transaction deleted for artwork id: " + dbArt.getId());
                }
            }
            // Now you can safely delete the artwork
            artworkService.deleteArtwork(dbArt.getId());
            System.out.println("DB artwork deleted.");

            // DELETE users
            userService.deleteUser(dbArtist.getId());
            userService.deleteUser(dbBidder.getId());
            System.out.println("DB users deleted.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
