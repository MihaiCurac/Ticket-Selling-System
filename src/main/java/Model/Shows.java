package Model;

public class Shows {
    private int sid;
    private String title;
    private String date_time;
    private int max_tickets;
    private double price;

    public Shows(int sid, String title, String date_time, int max_tickets, double price) {
        this.sid = sid;
        this.title = title;
        this.date_time = date_time;
        this.max_tickets = max_tickets;
        this.price = price;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getMax_tickets() {
        return max_tickets;
    }

    public void setMax_tickets(int max_tickets) {
        this.max_tickets = max_tickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
