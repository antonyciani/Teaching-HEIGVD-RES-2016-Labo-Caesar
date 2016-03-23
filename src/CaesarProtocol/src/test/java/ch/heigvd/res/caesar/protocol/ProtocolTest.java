/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.caesar.protocol;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antony
 */
public class ProtocolTest {
    
    @Test
    public void testEncryptDecrypt(){
        String message = "AVECAESARVENIVIDIVICI";
        String cipher = Protocol.caesarEncrypt(message, 12);
        System.out.println(cipher);
        assertEquals(message, Protocol.caesarDecrypt(cipher, 12));
        
        message = "CAMARCHEOUBIEN";
        cipher = Protocol.caesarEncrypt(message, 128);
        System.out.println(cipher);
        assertEquals(message, Protocol.caesarDecrypt(cipher, 128));
        
    }
    
}
