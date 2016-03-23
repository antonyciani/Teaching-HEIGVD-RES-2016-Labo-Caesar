package ch.heigvd.res.caesar.protocol;

/**
 *
 * @author Olivier Liechti
 * @author Ciani Antony
 * @author Peretti Christophe
 */
public class Protocol {

    public static final int DEFAULT_PORT = 4242;
    public static final int NB_LETTERS = 26;

    public static final String CMD_CONN_OK = "OK";
    public static final String CMD_SENDKEY = "HERETHEKEY";
    public static final String CMD_QUIT = "QUIT";
    public static final String CMD_SAY = "SAY";
    public static final String VERIFICATION_MESSAGE = "AVECAESAR";

    public static String caesarEncrypt(String message, int key){
        key = key % NB_LETTERS;
        String cipher = "";
        for(int i = 0; i < message.length(); i++){
            int cipherC = message.charAt(i);
            cipherC = cipherC - 'A';
            cipherC = cipherC + key;
            if(cipherC < 0){
                cipherC += NB_LETTERS;
            }
            cipherC = cipherC % NB_LETTERS;
            cipherC = cipherC + 'A';
            
            cipher += (char)cipherC;
        }
        return cipher;
    }
    
    public static String caesarDecrypt(String cipher, int key){
        return caesarEncrypt(cipher, -key);
    }
    
    public static int generateRandomKey(){
        return (int)(Math.random()*100);
    }
    
}
