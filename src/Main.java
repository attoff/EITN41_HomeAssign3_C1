import sun.plugin2.message.Message;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {

        int k = 16;

        String[] vYes = new String[(int) Math.pow(2, k + 1)];
        String[] vNo = new String[(int) Math.pow(2, k + 1)];

        MessageDigest md;

        for (int i = 0; i < vYes.length; i++) {

            String bin16 = Integer.toBinaryString(0x10000 | i).substring(1);

            try {
                md = MessageDigest.getInstance("SHA-256");

                md.update(("0" + bin16).getBytes("UTF-8"));
                String tmp = String.format("%064x", new java.math.BigInteger(1, md.digest()));
                vYes[i] = tmp.substring(0, 1);

                md.update(("1" + bin16).getBytes("UTF-8"));
                tmp = String.format("%064x", new java.math.BigInteger(1, md.digest()));
                vNo[i] = tmp.substring(0, 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int p = 0;
        int q = 0;
        int bind = 0;
        int conc = 0;
        for (int i = 0; i < vYes.length; i++) {
            p = 0;
            q = 0;
            for (int j = 0; j < vNo.length; j++) {
                if (vYes[i].compareTo(vNo[j]) == 0 && p == 0) {
                    bind++;
                    p = 1;
                } else if (vNo[i].compareTo(vYes[j]) == 0 && q == 0) {
                    conc++;
                    q = 1;
                }
            }
        }
        float bindVal = (bind + conc) * 100 / (2 * vYes.length);
        float conVal = (2 * vYes.length - (bind + conc)) * 100 / (2 * vYes.length);
        System.out.println(bind + " found collisions out of " + vYes.length + " possible. This gives us a:");
        System.out.println(bindVal + " % binding probability");
        System.out.println(conc);
        System.out.println(2 * vYes.length - (bind + conc) + " found values without collision");
        System.out.println(conVal + " % concealig probability");
    }
}
