package Model;

public class Performances {

    private int pid;
    private String name;
    private String genre;
    private int sid;

    public Performances(int pid, String name, String genre, int sid) {
        this.pid = pid;
        this.name = name;
        this.genre = genre;
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
