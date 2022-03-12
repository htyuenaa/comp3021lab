package base;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Folder implements Comparable<Folder>, Serializable {
    private ArrayList<Note> notes;
    private String name;
    private static final long serialVersionUID = 1L;

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

    @Override
    public int compareTo(Folder o) {
        return name.compareTo(o.getName());
    }

    public void sortNotes(){
        Collections.sort(notes);
    }
    private boolean contains(String content, String[] tokens, int index){
        if(index >= tokens.length)
            return true;
        if(tokens[index+1].equals("or")){   // if the next token is "or" operator
            return (content.contains(tokens[index]) || content.contains(tokens[index+2]) )&& contains(content, tokens, index+3);
        }
        return content.contains(tokens[index]) && contains(content, tokens, index+1);
    }
    public List<Note> searchNotes(String keywords){
        List<Note> noteList = new ArrayList<>();
        keywords = keywords.toLowerCase(Locale.ROOT);
        String[] tokens = keywords.split(" ");

        for (Note n: notes){
            boolean condition = contains(n.getTitle().toLowerCase(Locale.ROOT), tokens, 0);
            if(n instanceof TextNote && condition == false){
                condition = contains(((TextNote) n).getContent().toLowerCase(Locale.ROOT), tokens, 0);
            }

            if (condition){
                noteList.add(n);
            }
        }
        return noteList;
    }
}
