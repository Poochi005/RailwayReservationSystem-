package railway.exception;

/** Custom checked exception for all reservation-related errors (Day 13). */
public class ReservationException extends Exception {
    public ReservationException(String message) {
        super(message);
    }
}
