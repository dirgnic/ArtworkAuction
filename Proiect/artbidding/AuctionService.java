package artbidding;

import java.util.*;

public class AuctionService {
    private Map<Integer, User> users = new HashMap<>();
    private List<Artwork> artworks = new ArrayList<>();
    private Map<Integer, List<Bid>> bids = new HashMap<>(); //maps artwork id to list of bids
    private List<Transaction> transactions = new ArrayList<>();

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public void placeBid(Bid bid) {
        bids.computeIfAbsent(bid.getArtwork().getId(), k -> new ArrayList<>()).add(bid);
    }
/*
computeIfAbsent(...) – caută în mapă o listă de licitații pentru acel ID.
Dacă nu există, creează automat o listă nouă (new ArrayList<>) și o adaugă în mapă.
 */

    public void closeAuction(int artworkId) {
        List<Bid> artworkBids = bids.getOrDefault(artworkId, new ArrayList<>());
        /*
        getOrDefault(...):
        caută cheia artworkId în mapă,
        dacă găsește → returnează lista de oferte existentă (List<Bid>),
        dacă nu găsește → returnează un new ArrayList<>() (o listă goală temporară,
        dar nu o salvează în mapă).
         */

        if (!artworkBids.isEmpty()) {
            artworkBids.sort(null);
            Bid winningBid = artworkBids.get(0);
            Transaction transaction = new Transaction(winningBid);
            transactions.add(transaction);
            winningBid.getArtwork().setIsSold(true);
            System.out.println("Licitatie inchisa. Castigator: " + winningBid);
        } else {
            System.out.println("Nu exista investitii pentru acest ID " + artworkId);
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
        for (Artwork a : artworks) {
            if (a.getId() == artworkId) {
                artworks.remove(a);
                bids.remove(artworkId);
                return;
            }
        }
      //   bids.remove(artworkId);
    }


    public User getUserById(int id) {
        return users.get(id);
    }
    // creează o colecție nouă pentru un artist
    public void createCollectionForArtist(int artistId, String collectionName) {
        User user = users.get(artistId);
        if (user instanceof Artist) {
            ((Artist) user).createCollection(collectionName);
            System.out.println("colecția \"" + collectionName + "\" a fost creată pentru " + user.getName());
        } else {
            System.out.println("utilizatorul nu este artist.");
        }
    }

    // adaugă o operă într-o colecție existentă a artistului
    public void addArtworkToArtistCollection(int artistId, String collectionName, Artwork artwork) {
        User user = users.get(artistId);
        if (user instanceof Artist) {
            ((Artist) user).addArtworkToCollection(collectionName, artwork);
            System.out.println("opera \"" + artwork.getTitle() + "\" a fost adăugată în colecția \"" + collectionName + "\".");
        }
    }

    // afișează colecțiile artistului
    public void listCollectionsForArtist(int artistId) {
        User user = users.get(artistId);
        if (user instanceof Artist) {
            ((Artist) user).listCollections();
        } else {
            System.out.println("utilizatorul nu este artist.");
        }
    }

    // returnează o colecție după nume (poț folosi în Main sau GUI)
    public ArtCollection getCollectionByName(int artistId, String collectionName) {
        User user = users.get(artistId);
        if (user instanceof Artist) {
            for (ArtCollection col : ((Artist) user).getCollections()) {
                if (col.getName().equalsIgnoreCase(collectionName)) {
                    return col;
                }
            }
        }
        return null;
    }

}