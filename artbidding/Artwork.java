package artbidding;

public class Artwork {
    private int id;
    private String title;
    private Artist artist;
    private double startingPrice;
    private Boolean isSold;

    public Artwork(String title, Artist artist, double startingPrice) {
        // id-ul va fi setat de ArtworkService după inserare în DB
        this.title = title;
        this.artist = artist;
        this.startingPrice = startingPrice;
        this.isSold = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public double getStartingPrice() { return startingPrice; }
    public double getPrice() { return getStartingPrice(); }
    public Boolean getIsSold() { return isSold; }
    public void setIsSold(Boolean isSold) { this.isSold = isSold; }

    @Override
    public String toString() {
        return "Opera {" + "id=" + id + ", titlu='" + title + "', artist=" + artist.getName() + ", pret=" + startingPrice + ", e vanduta " + isSold + '}';
    }
}