/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.util.miscellaneous;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.mc2.util.exceptions.ObjectPersistException;

/**
 *
 * @author marco
 */
public class PersistObjectInFile {
    
    private File file=null;
    private Object obj=null;
    
    public PersistObjectInFile(String path) throws ObjectPersistException{
        
        this.obj=null;
        this.file = new File(path);
        init();
 
    }
    public PersistObjectInFile(File file) throws ObjectPersistException{
        
        this.obj=null;
        this.file=file;
        init();

    }
    public PersistObjectInFile(Object obj, String path) throws ObjectPersistException{
        
        this.file = new File(path);
        this.obj=obj;
        init();

    }
    public PersistObjectInFile(Object obj, File file) throws ObjectPersistException{
        
        this.file=file;
        this.obj=obj;
        init();

    }
    private void init() throws ObjectPersistException{
    
        if (this.obj == null && this.file != null) ReadObjectFromFile();
        else if (this.obj != null && this.file != null) SaveObjectToFile();
        else throw new ObjectPersistException("invalid parameters - obj: "+obj.toString()+" - file: "+file.getPath());
    }
    private void SaveObjectToFile() throws ObjectPersistException{
       
        if ((this.obj==null) && !(this.file== null) && (this.file.exists())) {
            DeleteFile();
            return;
        } 
        else if (this.obj==null) return;

        try {
                // Write to disk with FileOutputStream
                FileOutputStream f_out;
                f_out = new FileOutputStream(this.file);

                // Write object with ObjectOutputStream
                ObjectOutputStream obj_out;
                obj_out = new ObjectOutputStream (f_out);

                // Write object out to disk
                obj_out.writeObject (this.obj);

            } catch (FileNotFoundException ex) {
                throw new ObjectPersistException(ex.getMessage());
            } catch (IOException ex) {
                throw new ObjectPersistException(ex.getMessage());
            }
    }
    private void  ReadObjectFromFile() throws ObjectPersistException {
        
        if (this.file.exists()) {
            
            try {
                
                // Read from disk using FileInputStream
                FileInputStream f_in;
                f_in = new FileInputStream(this.file);


                // Read object using ObjectInputStream
                ObjectInputStream obj_in;
                obj_in = new ObjectInputStream (f_in);

                // Read an object
                obj = obj_in.readObject();

                } catch (FileNotFoundException ex) {
                    throw new ObjectPersistException(ex.getMessage());
                }catch (IOException ex) {
                    throw new ObjectPersistException(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    throw new ObjectPersistException(ex.getMessage());
                } 
        }
    }

    private void DeleteFile() throws ObjectPersistException {

        if (!this.file.exists())throw new ObjectPersistException("file does not exists.");
        if (!this.file.canRead())throw new ObjectPersistException("can't read file");  
        if (!this.file.canWrite()) throw new ObjectPersistException("can't delete file");
       
        try {

            if (!this.file.delete())throw new ObjectPersistException("can't delete file");

        } catch (Exception ex) {
                throw new ObjectPersistException("can't delete file");
        }
    }
    public Object getObject(){
        return obj;
    }
    public void setObject(Object obj) throws ObjectPersistException{
        this.obj =obj;
        SaveObjectToFile();
    }
}
