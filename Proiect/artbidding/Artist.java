package artbidding;

import java.util.ArrayList;
import java.util.List;

public class Artist extends User {
    private List<ArtCollection> collections;

    public Artist(String name, String email) {
        super(name, email);
        this.collections = new ArrayList<>();
    }

    @Override
    public void displayUserType() {
        System.out.println("artist: " + name);
    }

    public void createCollection(String collectionName) {
        for (ArtCollection collection : collections) {
            if (collection.getName().equalsIgnoreCase(collectionName)) {
                return;
            }
        }
        // dacă colecția nu există, o adaugam
        ArtCollection newCollection = new ArtCollection(collectionName);
        collections.add(new ArtCollection(collectionName));
    }

    public void addArtworkToCollection(String collectionName, Artwork artwork) {
        for (ArtCollection collection : collections) {
            if (collection.getName().equalsIgnoreCase(collectionName)) {
                collection.addArtwork(artwork);
                return;
            }
        }
        // dacă colecția nu există, o creăm și adăugăm opera
        ArtCollection newCollection = new ArtCollection(collectionName);
        newCollection.addArtwork(artwork);
        collections.add(newCollection);
    }

    public List<ArtCollection> getCollections() {
        return collections;
    }

    public void listCollections() {
        for (ArtCollection col : collections) {
            col.displayCollection();
        }
    }
}

