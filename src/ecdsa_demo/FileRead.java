/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecdsa_demo;

/**
 *
 * @author admin
 */
import java.io.*;

public class FileRead {

    public String FiletoString(File file) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        StringBuffer sb = new StringBuffer();

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            String s1 = "";
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));

//while (dis.available() != 0) {
//sb.append( dis.readLine() +"\n");
            while ((s1 = bf.readLine()) != null) {
                sb.append(s1 + "\n");
            }
            sb.deleteCharAt(sb.length() - 1);

            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
