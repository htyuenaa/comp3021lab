package base;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// lab4 imports


public class NoteBook implements Serializable{
    private ArrayList<Folder> folders;
    private static final long serialVersionUID = 1L;

    public NoteBook(){
        folders = new ArrayList<>();
    }

    public NoteBook(String file){
        // TODO
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try{
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            NoteBook n = (NoteBook) in.readObject();
            this.folders = n.getFolders();
            in.close();
            fis.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean insertNote(String folderName, Note note){
        Folder f = null;
        for (Folder f1: folders){
            if (f1.getName().equals(folderName)){
                f = f1;
                break;
            }
        }
        if (f == null){
            f = new Folder(folderName);
            folders.add(f);
        }

        for (Note n: f.getNotes()){
                if (n.equals(note)){
                    System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed.");
                    return false;
                }
        }
        f.addNote(note);
        return true;
    }

    public boolean createTextNote(String folderName, String title){
        TextNote note = new TextNote(title);
        return insertNote(folderName, note);
    }

    public boolean createTextNote(String folderName, String title, String content){
        TextNote note = new TextNote(title, content);
        return insertNote(folderName, note);
    }

    public boolean createImageNote(String folderName, String title){
        ImageNote note = new ImageNote(title);
        return insertNote(folderName, note);
    }

    public ArrayList<Folder> getFolders() {return folders;}

    public void sortFolders(){
        for (Folder f :folders){
            f.sortNotes();
        }
        Collections.sort(folders);
    }

    public List<Note> searchNotes(String keywords){
        List<Note> noteList = new ArrayList<>();
        for (Folder f: folders){
            noteList.addAll(f.searchNotes(keywords));
        }
        return  noteList;
    }

    /**
     * method to save the NoteBook instance to file
     *
     * @param file, the path of the file where to save the object serialization
     * @return true if save on file is successful, false otherwise
     */
    public boolean save(String file){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try{
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
}
