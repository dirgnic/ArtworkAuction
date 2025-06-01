package artbidding;

import java.sql.*;
import java.util.*;

public class UserService {
    private static UserService instance; // 1. static instance
    private Connection conn = DatabaseConnection.getInstance().getConnection();

    private UserService() {}             // 2. private constructor

    public static UserService getInstance() { // 3. global access point
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user instanceof Artist ? "artist" : "bidder");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) user.setId(rs.getInt(1));
        }
        AuditService.getInstance().logAction("createUser");
    }

    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                User user;
                if ("artist".equals(type)) {
                    user = new Artist(rs.getString("name"), rs.getString("email"));
                } else {
                    user = new Bidder(rs.getString("name"), rs.getString("email"));
                }
                user.setId(rs.getInt("id"));
                return user;
            }
        }
        AuditService.getInstance().logAction("getUser");
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                User user;
                if ("artist".equals(type)) {
                    user = new Artist(rs.getString("name"), rs.getString("email"));
                } else {
                    user = new Bidder(rs.getString("name"), rs.getString("email"));
                }
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        }
        AuditService.getInstance().logAction("getAllUsers");
        return users;
    }

    public void updateUserEmail(int id, String newEmail) throws SQLException {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("updateUserEmail");
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        AuditService.getInstance().logAction("deleteUser");
    }
}