package dto;

import java.sql.Date;

public class IssuedBookView {
    public String readerName;
    public String bookTitle;
    public Date issueDate;
    public Date dueDate;
    public String status;

    public IssuedBookView(String readerName, String bookTitle,
                          Date issueDate, Date dueDate, String status) {
        this.readerName = readerName;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
    }
}

