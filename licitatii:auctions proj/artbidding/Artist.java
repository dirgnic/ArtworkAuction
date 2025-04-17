package artbidding;

import java.util.ArrayList;
import java.util.List;

public class Artist extends User {
    private List<Artwork> artworks;

    public Artist(int id, String name, String email) {
        super(id, name, email);
        this.artworks = new ArrayList<>();
    }

    @Override
    public void displayUserType() {
        System.out.println("Artist: " + name);
    }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void listOwnArtworks() {
        System.out.println("Artworks by " + name + ":");
        for (Artwork a : artworks) {
            System.out.println(a);
        }
    }

    public boolean ownsArtwork(Artwork artwork) {
        return artworks.contains(artwork);
    }
}
