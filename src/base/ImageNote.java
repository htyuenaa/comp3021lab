package base;
import java.io.File;

public class ImageNote extends Note{
    private File image;
    public ImageNote(String title){
        // the compiler will automatically add super() to it
        super(title);   // using the constructor of its parent class, avoid writing sth similar again
    }
}
