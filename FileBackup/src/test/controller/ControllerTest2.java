

package test.controller;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for Controller.fileExists() method
 * 
 * @author Kasun Amarasena
 */
public class ControllerTest2 {
 
    @Test
    public void testFileExists1(){
        assertFalse(com.controller.Utils.fileExists(new File("dddddddddddddddddd")));        
    }    
    
    @Test
    public void testFileExists2(){
         File file = FileUtils.getTempDirectory();
         assertTrue(com.controller.Utils.fileExists(file));      
    }
    
    @Test
    public void testFileExists3(){
         File file = FileUtils.getTempDirectory();
         String path = " "+file.toString();
         assertTrue(com.controller.Utils.fileExists(new File(path)));      
    }
    
    @Test
    public void testFileExists4(){
         File file = FileUtils.getTempDirectory();
         String path = file.toString()+" ";
         assertTrue(com.controller.Utils.fileExists(new File(path)));      
    }
    
   
}
