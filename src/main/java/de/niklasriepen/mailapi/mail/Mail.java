package de.niklasriepen.mailapi.mail;

public class Mail {

    private String senderAddress;
    private String recipientAddress;

    private String subject;

    private String content;

    public Mail(String senderAddress, String recipientAddress) {
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
