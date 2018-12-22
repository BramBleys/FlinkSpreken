package be.thomasmore.flinkspreken;

import java.io.Serializable;

public class Score implements Serializable {
    private long id;
    private String score;
    private long accountId;
    private String spel;
    private String datum;
    private long paarId;

    public Score() {
    }

    public Score(long id, String score, long accountId, String spel, String datum, long paarId) {
        this.id = id;
        this.score = score;
        this.accountId = accountId;
        this.spel = spel;
        this.datum = datum;
        this.paarId = paarId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getPaarId() {
        return paarId;
    }

    public void setPaarId(long paarId) {
        this.paarId = paarId;
    }

    public String getSpel() {
        return spel;
    }

    public void setSpel(String spel) {
        this.spel = spel;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
