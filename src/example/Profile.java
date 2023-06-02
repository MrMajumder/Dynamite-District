
package example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Profile  {
    
   private File file;
   private String im ;
   private int imagenum ;
   private String name;
   private int level;

    public Profile() {
        
        im = "image/sprite.png" ;
        imagenum = 1;
        name="Noobie";
        level=1;
        createf();
    }
    
    public Profile (String name, int imagenum, int level) {
        this.name = name;
        this.imagenum = imagenum;
        this.level = level;
        this.getImage(imagenum);
        createf();
    }
   
   public void createf() {
   
     file = new File("File"+Example.PlayerCount+".txt");
    
    if(!file.exists())  
    {
        try{
        file.createNewFile();
        PrintWriter pw = new PrintWriter(file);
        pw.println(name);
        pw.println(level);
        pw.close();
        
        }
        catch(Exception e){
            
        }
    }
    Example.PlayerCount++;
   }

    public int getImagenum() {
        return imagenum;
    }

    public void setImagenum(int imagenum) {
        this.imagenum = imagenum;
    }

   
     public String getName(){
        
       try {
         Scanner reader= new Scanner(file);
         name = reader.nextLine();
       } catch (IOException e) {}
       return name;
    }

     public void setName(String n) {
         try {
            name = n;
            PrintWriter pw = new PrintWriter(file);
            pw.println(name);
            pw.close();
         } catch (IOException e) {}
    }

    public  int getLevel() {
        return level;
    }

    public  void setLevel(int level) {
        this.level = level;
    }

     public  String getImage(int imagenum)  {
        if(imagenum == 1)
           im = "image/sprite.png";
        if(imagenum == 2)
           im = "image/sprite1.png";
        if(imagenum == 3)
           im = "image/minus.jpg";
        
        
        return im;
    }
     public  String getImage()  {
        if(imagenum == 1)
           im = "image/sprite.png";
        if(imagenum == 2)
           im = "image/sprite1.png";
        if(imagenum == 3)
           im = "image/minus.jpg";
        
        
        return im;
    }

    
     public void valueplus()
    {
        imagenum++;
        if(imagenum>2)
            imagenum = 1;
    }
     public void valueminus()
    {
        imagenum--;
        if(imagenum<1)
            imagenum = 2;
    }
    
 
     
     
     
}
