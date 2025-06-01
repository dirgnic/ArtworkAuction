package artbidding;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Database connection failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
