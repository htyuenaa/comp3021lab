package base;
import java.io.File;

public class ImageNote extends Note{
    private File image;
    public ImageNote(String title){
        super(title);   // using the constructor of its parent class, avoid writing sth similar
    }
}
