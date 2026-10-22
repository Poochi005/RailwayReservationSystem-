package railway.model;

import java.io.Serializable;
import java.time.LocalDate;

/** A ticket booking identified by a PNR number. */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { CONFIRMED, CANCELLED }

    private final String pnr;
    private final int trainNumber;
    private final String passengerName;
    private final int age;
    private final String gender;
    private final int seats;
    private final LocalDate travelDate;
    private final double amount;
    private Status status = Status.CONFIRMED;

    public Booking(String pnr, int trainNumber, String passengerName, int age,
                   String gender, int seats, LocalDate travelDate, double amount) {
        this.pnr = pnr;
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.seats = seats;
        this.travelDate = travelDate;
        this.amount = amount;
    }

    public String getPnr() { return pnr; }
    public int getTrainNumber() { return trainNumber; }
    public String getPassengerName() { return passengerName; }
    public int getSeats() { return seats; }
    public double getAmount() { return amount; }
    public Status getStatus() { return status; }
    public void cancel() { status = Status.CANCELLED; }

    @Override
    public String toString() {
        return String.format("PNR:%s | Train:%d | %s (%d, %s) | Seats:%d | Date:%s | Rs.%.2f | %s",
                pnr, trainNumber, passengerName, age, gender, seats, travelDate, amount, status);
    }
}
