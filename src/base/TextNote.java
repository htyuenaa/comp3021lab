package base;

import java.io.*;

import java.util.Scanner;


public class TextNote extends Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;

    public TextNote(String title){
        super(title);
    }

    public TextNote(String title, String content){
        super(title);
        this.content = content;
    }
    /**
     * load a TextNote from File f
     *
     * the tile of the TextNote is the name of the file
     * the content of the TextNote is the content of the file
     *
     * @param f
     */
    public TextNote(File f){
        super(f.getName());
        this.content = getTextFromFile(f.getAbsolutePath());
    }

    /**
     * get the content of a file
     *
     * @param absolutePath of the file
     * @return the content of the file
     */
    private String getTextFromFile(String absolutePath){
        String result = "";
        try{
            Scanner input = new Scanner(new File(absolutePath));
            while(input.hasNext()){
                result += input.next() + " ";
            }
            input.close();
        }
        catch (FileNotFoundException fileNotFoundException){
            result = "fileNotFound";
        }
        // return the text read from the file
        return result;
    }

    public String getContent() {
        return content;
    }

    /**
     * export text note to file
     *
     *
     * @param pathFolder path of the folder where to export the note
     * the file has to be named as the title of the note with extension ".txt"
     *
     * if the tile contains white spaces " " they has to be replaced with underscores "_"
     *
     *
     */
    public void exportTextToFile(String pathFolder) {
        // TODO
        // replace all " " by "_" in the title
        String title = getTitle().replaceAll(" ", "_");
        File file = new File( "C:/Users/yuenh/Desktop/comp3021/comp3021lab"+pathFolder + File.separator +title+ ".txt");
        // TODO
        try{
            file.createNewFile();
            PrintWriter output = new PrintWriter(file);
            output.print(this.content);
            output.close();
        }
        catch (FileNotFoundException e){
            System.out.println("FileNotFound");
        }
        catch (IOException exception){
            System.out.println("IOExecption");
        }
    }
}
