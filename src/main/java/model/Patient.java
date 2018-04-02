package model;

public class Patient {
    private String ssn;
    private String name;
    private String address;
    private int consultationId;

    /**
     * Constructors
     */


    public Patient(String ssn, String name, String address) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.consultationId = 0;
    }

    public Patient() {
    }


    /**
     * Getters and setters
     */

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    /**
     * Others
     */
    public String toString() {
        return name + "," + ssn + "," + address;
    }
}
