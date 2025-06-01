package artbidding;

import java.util.ArrayList;
import java.util.List;

public class ArtCollection {
    private String name;
    private List<Artwork> artworks;

    public ArtCollection(String name) {
        this.name = name;
        this.artworks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public void removeArtwork(Artwork artwork) {
        artworks.remove(artwork);
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void displayCollection() {
        System.out.println("colecție: " + name);
        for (Artwork art : artworks) {
            System.out.println(art);
        }
    }
}
