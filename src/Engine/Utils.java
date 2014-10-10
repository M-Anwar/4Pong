/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

/**
 * Basic Utilities that are useful for IO functionalities.
 * @author muhammed.anwar
 */
public class Utils 
{
    /******UTILS TO BE IMPLEMENTED********/
    
    /**
     * Return a BufferedReader object that represents a text file being read. 
     * The file is located in the CLASSPATH of the project
     * @param path - the local path of the resource
     * @return - BufferedReader object which has the file stream
     */
    public static BufferedReader getTextStream(String path)
    {
        return null; //TODO ADD FUNCTIONALITY
    }
    
    /**
     * Write to a string to a local text file
     * @param path - the path of the local file to write to
     * @param txt - the text to write to the file
     */
    public static void writeToFile(String path, String txt)
    {
        //TODO ADD FUNCTIONALITY - Jason
    }   
        
    /******UTILS TO BE IMPLEMENTED********/
    
    /**
     * Load an image resource from the classpath of the project
     * @param s - the local path of the resource
     * @return - a generic image representing the loaded resource.
     */
    public static Image loadJava2DImage(String s) {
        Java2DImage image = null;
        try {
            image = new Java2DImage(ImageIO.read(ClassLoader.class.getResourceAsStream(s)));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    /**
     * Serialize an object to a file
     * @param filename - the file to save the serialized object
     * @param object - the object to serialize
     * @throws Exception - Any exception thrown by the operation
     */
    public static void writeToFile(String filename, Object object) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
 
        try {
            fos = new FileOutputStream(new File(filename));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    
    /**
     * Reads a serialized object from a file
     * @param filename - the file to read the serialized object from
     * @return - An object from the read file ( needs to be parsed into another
     * object type if necessary)
     * @throws Exception - Any exception thrown by the operation
     */
    public static Object readFromFile(String filename) throws Exception {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
 
        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return object;
    }
}
