package artbidding;

public class Artwork {
    private static int idCounter = 1;
    private final int id;
    private String title;
    private Artist artist;
    private double startingPrice;
    private Boolean isSold;

    public Artwork(String title, Artist artist, double startingPrice) {
        this.id = idCounter++;
        this.title = title;
        this.artist = artist;
        this.startingPrice = startingPrice;
        this.isSold = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public double getStartingPrice() { return startingPrice; }
    public Boolean getIssold() { return isSold; }
    public void setIsSold(Boolean issold) { this.isSold = issold; }

    @Override
    public String toString() {
        return "Opera {" + "id=" + id + ", titlu='" + title + "', artist=" + artist.getName() + ", pret=" + startingPrice + ", e vanduta " + isSold + '}';
    }
}