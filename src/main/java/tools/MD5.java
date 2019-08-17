package tools;

import javax.swing.*;
import java.security.*;
import java.math.*;

public class MD5 {
    private MessageDigest m;
    private String result;

    public MD5() {
        m = null;
        result = "";
    }

    public String GenerateHash(String text) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");

            m.update(text.getBytes(), 0, text.length());

            result = new BigInteger(1, m.digest()).toString(16);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro ao gerar o MD5: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return result;
    }

    public String GetLastHash() {
        return this.result;
    }
}