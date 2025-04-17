package artbidding;

public class Artwork {
    private static int idCounter = 1;
    private final int id;
    private String title;
    private Artist artist;
    private double startingPrice;

    public Artwork(String title, Artist artist, double startingPrice) {
        this.id = idCounter++;
        this.title = title;
        this.artist = artist;
        this.startingPrice = startingPrice;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public double getStartingPrice() { return startingPrice; }

    @Override
    public String toString() {
        return "Artwork{" + "id=" + id + ", title='" + title + "', artist=" + artist.getName() + ", price=" + startingPrice + '}';
    }
}