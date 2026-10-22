package railway.service;

import railway.exception.ReservationException;
import railway.model.Booking;
import railway.model.Train;
import railway.util.DataStore;

import java.time.LocalDate;
import java.util.*;

/** Core business logic: search, book, cancel (Collections, Generics, Synchronization). */
public class ReservationService {
    private static final int MAX_SEATS_PER_BOOKING = 6;

    private final String dataDir;
    private final Map<Integer, Train> trains = new LinkedHashMap<>();
    private final Map<String, Booking> bookings = new LinkedHashMap<>();
    private int pnrCounter = 1000;

    public ReservationService(String dataDir) {
        this.dataDir = dataDir;
        Map<Integer, Train> t = DataStore.load(dataDir + "/trains.dat");
        Map<String, Booking> b = DataStore.load(dataDir + "/bookings.dat");
        Integer c = DataStore.load(dataDir + "/counter.dat");
        if (t == null) seedTrains(); else trains.putAll(t);
        if (b != null) bookings.putAll(b);
        if (c != null) pnrCounter = c;
    }

    private void seedTrains() {
        add(new Train(12601, "Chennai Mail", "Chennai", "Bengaluru", "06:00", 100, 450));
        add(new Train(12602, "Bengaluru Express", "Bengaluru", "Chennai", "15:30", 100, 450));
        add(new Train(12631, "Vaigai Express", "Chennai", "Madurai", "07:15", 120, 380));
        add(new Train(12632, "Pandian Express", "Madurai", "Chennai", "21:00", 120, 380));
        add(new Train(16723, "Anantapuri Express", "Chennai", "Trivandrum", "19:45", 150, 620));
        add(new Train(12007, "Shatabdi Express", "Chennai", "Mysuru", "06:00", 80, 1050));
        add(new Train(22637, "West Coast Express", "Chennai", "Mangaluru", "13:10", 140, 540));
        save();
    }

    private void add(Train t) { trains.put(t.getNumber(), t); }

    public Collection<Train> allTrains() { return trains.values(); }

    public List<Train> search(String source, String destination) {
        List<Train> result = new ArrayList<>();
        for (Train t : trains.values()) {
            if (t.getSource().equalsIgnoreCase(source.trim())
                    && t.getDestination().equalsIgnoreCase(destination.trim())) {
                result.add(t);
            }
        }
        return result;
    }

    public synchronized Booking book(int trainNo, String name, int age, String gender,
                                     int seats, LocalDate date) throws ReservationException {
        Train train = trains.get(trainNo);
        if (train == null) throw new ReservationException("Train " + trainNo + " not found.");
        if (name == null || name.isBlank()) throw new ReservationException("Passenger name is required.");
        if (age < 1 || age > 120) throw new ReservationException("Invalid age.");
        if (seats < 1 || seats > MAX_SEATS_PER_BOOKING)
            throw new ReservationException("You can book 1 to " + MAX_SEATS_PER_BOOKING + " seats at a time.");
        if (date.isBefore(LocalDate.now())) throw new ReservationException("Travel date cannot be in the past.");
        if (train.getAvailableSeats() < seats)
            throw new ReservationException("Only " + train.getAvailableSeats() + " seat(s) available.");

        double amount = seats * train.getFarePerSeat();
        String pnr = "PNR" + (++pnrCounter);
        Booking b = new Booking(pnr, trainNo, name.trim(), age, gender, seats, date, amount);
        train.reserveSeats(seats);
        bookings.put(pnr, b);
        save();
        return b;
    }

    public synchronized double cancel(String pnr) throws ReservationException {
        Booking b = bookings.get(pnr.trim().toUpperCase());
        if (b == null) throw new ReservationException("No booking found for " + pnr);
        if (b.getStatus() == Booking.Status.CANCELLED)
            throw new ReservationException("Booking " + pnr + " is already cancelled.");
        b.cancel();
        trains.get(b.getTrainNumber()).releaseSeats(b.getSeats());
        save();
        return b.getAmount() * 0.8; // 20% cancellation charge
    }

    public Booking find(String pnr) throws ReservationException {
        Booking b = bookings.get(pnr.trim().toUpperCase());
        if (b == null) throw new ReservationException("No booking found for " + pnr);
        return b;
    }

    public Collection<Booking> allBookings() { return bookings.values(); }

    private void save() {
        new java.io.File(dataDir).mkdirs();
        DataStore.save(new LinkedHashMap<>(trains), dataDir + "/trains.dat");
        DataStore.save(new LinkedHashMap<>(bookings), dataDir + "/bookings.dat");
        DataStore.save(pnrCounter, dataDir + "/counter.dat");
    }
}
