package org.mc2.util.miscellaneous;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 */
public class ImageHandler {

    public final static BufferedImage NULLIMAGE = null;
    public final static BufferedImage BLANKIMAGE =  new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    
    public ImageHandler(){

   }
   public static Boolean isFileAnImage(String pathname) throws IOException
   {
        java.io.File file = new java.io.File(pathname);
        return isFileAnImage(file);
 
    }
    public static Boolean isFileAnImage(File file) throws IOException
    {
        BufferedImage img = null;
        img = ImageIO.read(file);

        if (img!=null) return true;
        return false;
 
    } 
    public  static BufferedImage getImagefromFile (java.io.File file) throws IOException{

         if (file== null){return NULLIMAGE;}
         if (!file.exists()) {return NULLIMAGE;}
         if (!file.canRead()) {return NULLIMAGE;}

         BufferedImage img = ImageIO.read(file);
         return img;


     }
     public  static BufferedImage getImagefromFile (String pathname) throws IOException{
    
        if (pathname == null) {return NULLIMAGE;}

        File file  = new File(pathname);

        BufferedImage img = getImagefromFile(file);

        return img;
  
    }
     public  static BufferedImage getImagefromInputStream (InputStream in) throws IOException{
        
            BufferedImage img;
            img = ImageIO.read(in);
            return img;

    }
     public static BufferedImage resize(BufferedImage img,
                                int width,
                                int height){
        
        if (img==null){return NULLIMAGE;}
        
        if (img.getHeight() != width ||
               img.getWidth() !=  height)
        {
              img = resizeImageWithHint(img,width,height);
        }
        
        return img;
        
    }
     public static BufferedImage resizeMantainProps(BufferedImage img,
                                int width,
                                int height){
        
        if (img==null){return NULLIMAGE;}
        
        if (img.getHeight() != width ||
               img.getWidth() !=  height)
        {
              img = resizeImageProp(img,width,height);
        }
        
        return img;
        
    }
    private static BufferedImage resizeImageProp (BufferedImage originalImage,
                                                int width,
                                                int height){
        
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        

        float wRatio = (float)width/(float)w;
        float hRatio = (float)height/(float)h;
        
        float ratio = (wRatio>hRatio) ? hRatio : wRatio;
        
        int newW = (int) Math.rint( w*ratio );
        int newH = (int) Math.rint( h*ratio );

        return resizeImageWithHint(originalImage, newW, newH);
    }
    private static BufferedImage resizeImageWithHint(
                                                BufferedImage originalImage,
                                                int width,
                                                int height){

        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();
	g.setComposite(AlphaComposite.Src);

	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);

	return resizedImage;
    }
    public static void writeImageToFile(BufferedImage image,
                                           java.io.File file,
                                           String format) throws IOException{
        
        ImageIO.write(image, format, file);
    }
}
