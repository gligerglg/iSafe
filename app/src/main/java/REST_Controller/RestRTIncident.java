package REST_Controller;

import java.util.Date;

public class RestRTIncident {
    private double latitude;
    private double longitude;

    private Date date;
    private String accidentDesc;
    private String accidentType;

    public RestRTIncident(double latitude, double longitude, Date date, String accidentDesc, String accidentType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.accidentDesc = accidentDesc;
        this.accidentType = accidentType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccidentDesc() {
        return accidentDesc;
    }

    public void setAccidentDesc(String accidentDesc) {
        this.accidentDesc = accidentDesc;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }
}
