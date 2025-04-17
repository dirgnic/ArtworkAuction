package artbidding;

import java.time.LocalDateTime;

public class Transaction {
    private static int idCounter = 1;
    private final int id;
    private Bid bid;
    private LocalDateTime date;

    public Transaction(Bid bid) {
        this.id = idCounter++;
        this.bid = bid;
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Tranzactie {id=" + id + ", bid=" + bid + ", date=" + date + '}';
    }
}