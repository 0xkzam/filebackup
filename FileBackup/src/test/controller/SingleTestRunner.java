/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

/**
 *
 * @author Kasun Amarasena
 */
public class SingleTestRunner {

    public SingleTestRunner() {
    }

    @Test
    public void runSingleTest() {
        Request request = Request.method(test.controller.ControllerTest.class, "testFileExists1");
        Result result = new JUnitCore().run(request);
        assertTrue(result.wasSuccessful());
       
    }
}
