package util;

import dao.TransactionDao;
import dto.IssuedBookView;
import mailservice.EmailOTPService;
import java.util.List;

public class OverdueNotifierTask implements Runnable {
    private final TransactionDao transactionDao = new TransactionDao();
    private final dao.ReservationDao reservationDao = new dao.ReservationDao();

    @Override
    public void run() {
        try {
            System.out.println("Running Library Maintenance Tasks...");
            List<IssuedBookView> overdueBooks = transactionDao.getOverdueTransactions();
            System.out.println("Found " + overdueBooks.size() + " overdue books.");
            for (IssuedBookView book : overdueBooks) {
                String email = book.getEmail();
                if (email != null && !email.isEmpty()) {
                    EmailOTPService.sendOverdueNotice(email, book.getBookTitle(), book.getDueDate().toString());
                }
            }

            reservationDao.expireReservations();

            System.out.println("Library Maintenance Tasks completed.");
        } catch (Exception e) {
            System.err.println("Error in Maintenance Task: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
