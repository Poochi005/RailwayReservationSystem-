package railway;

import railway.exception.ReservationException;
import railway.model.Booking;
import railway.model.Train;
import railway.service.ReservationService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Console menu for the Railway Reservation System. */
public class Main {
    public static void main(String[] args) {
        ReservationService service = new ReservationService("data");
        Scanner in = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("   RAILWAY RESERVATION SYSTEM");
        System.out.println("=====================================");

        while (true) {
            System.out.println("\n1. View all trains");
            System.out.println("2. Search trains");
            System.out.println("3. Book ticket");
            System.out.println("4. Cancel ticket");
            System.out.println("5. Check PNR status");
            System.out.println("6. View all bookings");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> service.allTrains().forEach(System.out::println);
                    case "2" -> {
                        System.out.print("From: "); String s = in.nextLine();
                        System.out.print("To: "); String d = in.nextLine();
                        List<Train> r = service.search(s, d);
                        if (r.isEmpty()) System.out.println("No trains found.");
                        else r.forEach(System.out::println);
                    }
                    case "3" -> {
                        System.out.print("Train number: "); int no = Integer.parseInt(in.nextLine().trim());
                        System.out.print("Passenger name: "); String name = in.nextLine();
                        System.out.print("Age: "); int age = Integer.parseInt(in.nextLine().trim());
                        System.out.print("Gender (M/F/O): "); String g = in.nextLine().trim();
                        System.out.print("Seats: "); int seats = Integer.parseInt(in.nextLine().trim());
                        System.out.print("Travel date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(in.nextLine().trim());
                        Booking b = service.book(no, name, age, g, seats, date);
                        System.out.println("Booking confirmed!\n" + b);
                    }
                    case "4" -> {
                        System.out.print("PNR: ");
                        double refund = service.cancel(in.nextLine());
                        System.out.printf("Cancelled. Refund: Rs.%.2f (20%% cancellation charge)%n", refund);
                    }
                    case "5" -> {
                        System.out.print("PNR: ");
                        System.out.println(service.find(in.nextLine()));
                    }
                    case "6" -> {
                        if (service.allBookings().isEmpty()) System.out.println("No bookings yet.");
                        else service.allBookings().forEach(System.out::println);
                    }
                    case "0" -> { System.out.println("Thank you!"); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (ReservationException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            } catch (DateTimeParseException e) {
                System.out.println("Error: use date format YYYY-MM-DD.");
            }
        }
    }
}
