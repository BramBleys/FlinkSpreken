package be.thomasmore.flinkspreken;

import java.io.Serializable;

public class Score implements Serializable{
    private long id;
    private long score;
    private long accountId;
    private long paarId;

    public Score() {
    }

    public Score(long id, long score, long accountId, long paarId) {
        this.id = id;
        this.score = score;
        this.accountId = accountId;
        this.paarId = paarId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
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
}
