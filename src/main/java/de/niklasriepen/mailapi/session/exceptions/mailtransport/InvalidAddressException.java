package de.niklasriepen.mailapi.session.exceptions.mailtransport;

public class InvalidAddressException extends Exception {

    private String address;
    private String cause;

    public InvalidAddressException(String address, String cause) {
        super();
        this.address = address;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return "'" + address + "' is an invalid " + cause + " address.";
    }
}
