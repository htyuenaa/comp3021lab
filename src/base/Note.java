package base;
import java.util.Date;
import java.util.Objects;

public class Note {
    private Date date;
    private String title;

    public Note(String title){
        this.title = title;
    }

    public String getTitle() {return this.title;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return title.equals(note.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
