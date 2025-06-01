package artbidding;

import java.sql.*;
import java.util.*;

public class TransactionService {
    private static TransactionService instance;
    private Connection conn = DatabaseConnection.getInstance().getConnection();

    private TransactionService() {}

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public void createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (artwork_id, bidder_id, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, transaction.getArtwork().getId());
            stmt.setInt(2, transaction.getBidder().getId());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getTimestamp()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) transaction.setId(rs.getInt(1));
        }
        AuditService.getInstance().logAction("createTransaction");
    }

    public List<Transaction> getAllTransactions(UserService userService, ArtworkService artworkService) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Artwork artwork = artworkService.getArtwork(rs.getInt("artwork_id"), userService);
                Bidder bidder = (Bidder) userService.getUser(rs.getInt("bidder_id"));
                double amount = rs.getDouble("amount");
                java.time.LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                // Create a Bid object to wrap in Transaction (for consistency)
                Bid bid = new Bid(bidder, artwork, amount);
                bid.setId(-1); // or leave unset if not needed
                bid.setTime(timestamp); // set the bid time to match transaction timestamp
                Transaction transaction = new Transaction(bid);
                transaction.setId(rs.getInt("id"));
                transaction.setDate(timestamp);
                transactions.add(transaction);
            }
        }
        AuditService.getInstance().logAction("getAllTransactions");
        return transactions;
    }

    public void deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("deleteTransaction");
    }

    public void updateTransaction(int id, double newAmount, java.time.LocalDateTime newTimestamp) throws SQLException {
        String sql = "UPDATE transactions SET amount = ?, timestamp = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setTimestamp(2, Timestamp.valueOf(newTimestamp));
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("updateTransaction");
    }
}