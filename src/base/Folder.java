package base;
import java.util.ArrayList;
import java.util.Objects;

public class Folder {
    private ArrayList<Note> notes;
    private String name;

    public Folder(String name){
        this.name = name;
        notes = new ArrayList<>();
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public String getName() {return this.name;}

    public ArrayList<Note> getNotes() {return notes;}

    @Override
    public boolean equals(Object o) {
        if(o instanceof Folder)
            return this.name.equals((String) o);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        int nText = 0;
        int nImage = 0;

        for (Note n: notes){
            if (n instanceof TextNote)
                ++nText;
            if (n instanceof ImageNote)
                ++nImage;
        }

        return name + ":" + nText + ":" + nImage;
    }

}
