package artbidding;

import java.sql.*;
import java.util.*;

public class BidService {
    private static BidService instance;
    private Connection conn = DatabaseConnection.getInstance().getConnection();

    private BidService() {}

    public static BidService getInstance() {
        if (instance == null) {
            instance = new BidService();
        }
        return instance;
    }

    public void createBid(Bid bid) throws SQLException {
        String sql = "INSERT INTO bids (bidder_id, artwork_id, amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bid.getBidder().getId());
            stmt.setInt(2, bid.getArtwork().getId());
            stmt.setDouble(3, bid.getAmount());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) bid.setId(rs.getInt(1));
        }
        AuditService.getInstance().logAction("createBid");
    }

    public List<Bid> getBidsForArtwork(int artworkId, UserService userService, ArtworkService artworkService) throws SQLException {
        List<Bid> bids = new ArrayList<>();
        String sql = "SELECT * FROM bids WHERE artwork_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, artworkId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bidder bidder = (Bidder) userService.getUser(rs.getInt("bidder_id"));
                Artwork artwork = artworkService.getArtwork(rs.getInt("artwork_id"), userService);
                Bid bid = new Bid(bidder, artwork, rs.getDouble("amount"));
                bid.setId(rs.getInt("id"));
                bids.add(bid);
            }
        }
        AuditService.getInstance().logAction("getBidsForArtwork");
        return bids;
    }

    public void deleteBid(int id) throws SQLException {
        String sql = "DELETE FROM bids WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("deleteBid");
    }

    public void updateBid(int id, double newAmount) throws SQLException {
        String sql = "UPDATE bids SET amount = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("updateBid");
    }
}