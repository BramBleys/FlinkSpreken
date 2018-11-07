package be.thomasmore.flinkspreken;

public class Paar {
    private long id;
    private String naam;


    public Paar() {

    }

    public Paar(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getnaam() {
        return naam;
    }

    public void setnaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam.substring(0,1).toUpperCase() + naam.substring(1).toLowerCase();
    }
}
