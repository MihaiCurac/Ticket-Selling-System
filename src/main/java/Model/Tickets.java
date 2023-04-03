package Model;

public class Tickets {

    private int tid;
    private int sid;
    private int nrSeats;
    public Tickets(int tid, int sid, int nrSeats) {
        this.tid = tid;
        this.sid = sid;
        this.nrSeats = nrSeats;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }
}
