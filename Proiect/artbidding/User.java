package artbidding;

public abstract class User {
    protected static int idCounter=1;
    protected final int id;
    protected String name;
    protected String email;

    public User(String name, String email) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract void displayUserType();
}