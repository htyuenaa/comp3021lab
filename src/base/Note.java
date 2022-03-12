package base;
import java.util.Date;
import java.util.Objects;
import java.io.Serializable;

public class Note implements Comparable<Note>, Serializable{
    private Date date;
    private String title;
    private static final long serialVersionUID = 1L;

    public Note(String title){
        this.date = new Date(System.currentTimeMillis());
        this.title = title;
    }

    public String getTitle() {return this.title;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Note){
            return title.equals(((Note) o).getTitle());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public int compareTo(Note o) {
        return -1* date.compareTo(o.date);
    }

    public String toString(){
        return date.toString()+"\t"+title;
    }

}
