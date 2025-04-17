package artbidding;

public class Bidder extends User {
    public Bidder(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayUserType() {
        System.out.println("Bidder: " + name);
    }
}