import sun.plugin2.message.Message;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vikto on 2016-12-02.
 */
public class Main {

    public static void main(String[] args) {

        int k = 16;
        int X = 160;

        String[] vYes = new String[(int) Math.pow(2, k + 1)];
        String[] vNo = new String[(int) Math.pow(2, k + 1)];

        MessageDigest md;


        for (int i = 0; i < vYes.length; i++) {

            String bin16 = Integer.toBinaryString(0x10000 | i).substring(1);

            try {
                md = MessageDigest.getInstance("SHA-256");

                md.update(("0" + bin16).getBytes("UTF-8"));
                vYes[i] = String.format("%064x", new java.math.BigInteger(1, md.digest()));


                md.update(("1" + bin16).getBytes("UTF-8"));
                vNo[i] = String.format("%064x", new java.math.BigInteger(1, md.digest()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int prob = 0;
        int g = 0;

        for (int i = 0; i < vYes.length; i++) {
            innerloop:
            for (int j = 0; j < vNo.length; j++) {
                if (vYes[i].compareTo(vNo[j]) == 0) {
                    prob++;
                    break innerloop;
                }
            }
            g++;
        }
        System.out.println(g);
        System.out.println(prob);
        System.out.println(prob*100 / vYes.length +"% probability");

    }
}
