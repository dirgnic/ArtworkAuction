package artbidding;

import java.sql.*;
import java.util.*;

public class ArtworkService {
    private static ArtworkService instance; 
    // avem un membru static de tip ArtworkService 
    // care va fi instanta unica a acestei clase -> singleton
    
    private Connection conn = DatabaseConnection.getInstance().getConnection();

    private ArtworkService() {}
    // constructorul este privat pentru a preveni 
    // instantierea directa -> singleton

    //  metoda publica, statica, acesta returneaza
    // instanta unica a clasei
    // de tip ArtworkService -> singleton

    public static ArtworkService getInstance() {
        if (instance == null) {
            instance = new ArtworkService();
        }
        return instance;
    }
   // metode statice ->
   // In the static method, the method use compile-time or early
   // binding -> le putem apela fara a crea o instanta a clasei

    public void createArtwork(Artwork artwork) throws SQLException {
        String sql = "INSERT INTO artworks (title, artist_id, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, artwork.getTitle());
            stmt.setInt(2, artwork.getArtist().getId());
            stmt.setDouble(3, artwork.getPrice());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) artwork.setId(rs.getInt(1));
        }
        AuditService.getInstance().logAction("createArtwork");
    }

    public Artwork getArtwork(int id, UserService userService) throws SQLException {
        String sql = "SELECT * FROM artworks WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int artistId = rs.getInt("artist_id");
                Artist artist = (Artist) userService.getUser(artistId);
                Artwork artwork = new Artwork(rs.getString("title"), artist, rs.getDouble("price"));
                artwork.setId(rs.getInt("id"));
                return artwork;
            }
        }
        AuditService.getInstance().logAction("getArtwork");
        return null;
    }

    public List<Artwork> getAllArtworks(UserService userService) throws SQLException {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM artworks";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int artistId = rs.getInt("artist_id");
                Artist artist = (Artist) userService.getUser(artistId);
                Artwork artwork = new Artwork(rs.getString("title"), artist, rs.getDouble("price"));
                artwork.setId(rs.getInt("id"));
                artworks.add(artwork);
            }
        }
        AuditService.getInstance().logAction("getAllArtworks");
        return artworks;
    }

    public void deleteArtwork(int id) throws SQLException {
        String sql = "DELETE FROM artworks WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("deleteArtwork");
    }

    public void updateArtwork(int id, String newTitle, double newPrice) throws SQLException {
        String sql = "UPDATE artworks SET title = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newTitle);
            stmt.setDouble(2, newPrice);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("updateArtwork");
    }
}