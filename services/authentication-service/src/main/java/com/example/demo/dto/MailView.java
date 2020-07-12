package com.example.demo.dto;

public class MailView {
    private String mailFrom;
    private String mailTo;
    private String dodatak;


    public MailView(String mailFrom, String mailTo, String dodatak) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.dodatak = dodatak;
    }

    public MailView() {

    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getDodatak() {
        return dodatak;
    }

    public void setDodatak(String dodatak) {
        this.dodatak = dodatak;
    }
}
