package railway.model;

import java.io.Serializable;

/** A train with a fixed route and limited seats (Day 7 - Classes & Encapsulation). */
public class Train implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int number;
    private final String name;
    private final String source;
    private final String destination;
    private final String departure;
    private final int totalSeats;
    private int availableSeats;
    private final double farePerSeat;

    public Train(int number, String name, String source, String destination,
                 String departure, int totalSeats, double farePerSeat) {
        this.number = number;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.departure = departure;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.farePerSeat = farePerSeat;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDeparture() { return departure; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getFarePerSeat() { return farePerSeat; }

    public void reserveSeats(int n) { availableSeats -= n; }
    public void releaseSeats(int n) { availableSeats += n; }

    @Override
    public String toString() {
        return String.format("%-6d %-22s %-12s -> %-12s %-6s Seats:%-4d Fare:Rs.%.2f",
                number, name, source, destination, departure, availableSeats, farePerSeat);
    }
}
