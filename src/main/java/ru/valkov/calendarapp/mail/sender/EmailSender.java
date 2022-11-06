package ru.valkov.calendarapp.mail.sender;

public interface EmailSender {
    void sendEmail(String to, String email);
}
