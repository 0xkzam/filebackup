
package test.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.controller.Utils;

/**
 *
 * @author Kasun Amarasena
 */
public class ControllerTest {
    private String tempPath;
    private File src1,src2,dest;    

    public ControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws IOException {        
        tempPath = FileUtils.getTempDirectoryPath();          
        
        src1 = new File(tempPath + "\\TempSrc\\test1.txt");
        FileUtils.writeStringToFile(src1, "testing...file 1...");
        src2 = new File(tempPath + "\\TempSrc\\TestFolder\\test2.txt");
        FileUtils.writeStringToFile(src2, "testing...file 2...");

        dest = new File(tempPath + "\\TempDest");

        Utils.copy(new File(tempPath + "\\TempSrc"), dest);
    }
    
    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(new File(tempPath + "\\TempDest\\"));
        FileUtils.deleteDirectory(new File(tempPath + "\\TempSrc"));
    }
    
    
    /**
     * Test case 1: Typical file copy to destination folder from a source folder 
     * and content checking
     * @throws java.lang.Exception
     */
    @Test
    public void testCopy1() throws Exception {        

        Collection<File> listDest = FileUtils.listFiles(dest, null, true);
        Collection<File> listSrc = FileUtils.listFiles(new File(tempPath + "\\TempSrc"), null, true);

        assertTrue(listDest.size() == listSrc.size());

        Object[] arrayDest = listDest.toArray();
        Object[] arraySrc = listSrc.toArray();

        for (int i = 0; i < arraySrc.length; i++) {
            assertTrue("", FileUtils.contentEquals((File) arrayDest[i], (File) arraySrc[i]));
        }              
    }

    /**
     * Test case 2: add a file after copying and content checking
     * @throws Exception
     */
    @Test
    public void testCopy2() throws Exception {        
        
        File src3 = new File(tempPath + "\\TempSrc\\test3.txt");
        FileUtils.writeStringToFile(src3, "testing...file 3...");
        
        Collection<File> listDest = FileUtils.listFiles(dest, null, true);
        Collection<File> listSrc = FileUtils.listFiles(new File(tempPath + "\\TempSrc"), null, true);

        assertFalse(listDest.size() == listSrc.size());         
    }
    
    /**
     * Test case 3: Change the contents of a file after copying and content checking
     * @throws Exception
     */
    @Test
    public void testCopy3() throws Exception {        
                    
        FileUtils.writeStringToFile(src2, "xxxxxxxxxxxxxxxxxxxx");

        Collection<File> listDest = FileUtils.listFiles(dest, null, true);
        Collection<File> listSrc = FileUtils.listFiles(new File(tempPath + "\\TempSrc"), null, true);
        
        Object[] arrayDest = listDest.toArray();
        Object[] arraySrc = listSrc.toArray();

        ArrayList<Boolean> bools = new ArrayList<>();
        for (int i = 0; i < arraySrc.length; i++) {
            bools.add(FileUtils.contentEquals((File) arrayDest[i], (File) arraySrc[i]));
        }
        
        if(!bools.contains(false)){
            assertTrue(false);
        }
    }
    
    
   
}
