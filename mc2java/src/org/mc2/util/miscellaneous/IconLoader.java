/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.util.miscellaneous;
import org.mc2.util.exceptions.MC2Exception;
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 *
 * @author marco
 */
public class IconLoader {
    
    
    public IconLoader(){
 
    }
    /** Returns an ImageIcon, or throw MC2Exception if the path was invalid. */
    protected ImageIcon createImageIcon(Class cl, 
                                                    String path,
                                                    String description) throws MC2Exception {
   
        if (cl == null) throw new MC2Exception ("Couldn't find class:" + cl.getCanonicalName());
        
        java.net.URL imgURL = cl.getResource(path);
        
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } 
        else {
            System.err.println("Couldn't find file: " + path);
            throw new MC2Exception ("Couldn't find file: " + path);
        }
    }
    protected ImageIcon createImageIcon(Image image, String description){
        return new ImageIcon(image, description);
    }
    
}
