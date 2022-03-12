package base;
import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class ImageNote extends Note implements Serializable {
    private File image;
    private static final long serialVersionUID = 1L;
    public ImageNote(String title){
        // the compiler will automatically add super() to it
        super(title);   // using the constructor of its parent class, avoid writing sth similar again
    }
}
