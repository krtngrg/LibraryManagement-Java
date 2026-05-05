package dto;

import java.sql.Timestamp;

public class Reservation {
    private int resId;
    private int userId;
    private int bookId;
    private String readerName;
    private String bookTitle;
    private Timestamp reservationDate;
    private Timestamp notifiedAt;
    private String status;

    public Reservation() {
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Timestamp getNotifiedAt() {
        return notifiedAt;
    }

    public void setNotifiedAt(Timestamp notifiedAt) {
        this.notifiedAt = notifiedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
