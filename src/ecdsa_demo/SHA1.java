/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecdsa_demo;

/**
 *
 * @author admin
 */
public class SHA1 {

    private static int h0 = 0x67452301;
    private static int h1 = 0xefcdab89;
    private static int h2 = 0x98badcfe;
    private static int h3 = 0x10325476;
    private static int h4 = 0xc3d2e1f0;

    static int k[] = new int[80];

    static {
        for (int i1 = 0; i1 < 20; i1++) {
            k[i1] = 0x5a827999;
        }
        for (int i2 = 20; i2 < 40; i2++) {
            k[i2] = 0x6ed9eba1;
        }

        for (int i3 = 40; i3 < 60; i3++) {
            k[i3] = 0x8f1bbcdc;
        }

        for (int i4 = 60; i4 < 80; i4++) {
            k[i4] = 0xca62c1d6;
        }
    }
    // rotate left 

    static int rotate(int x, int shift) {
        return ((x << shift) | (x >>> (32 - shift)));
    }

    // Computation of Hash
    static String hash(String text) {

        int H0 = SHA1.h0;
        int H1 = SHA1.h1;
        int H2 = SHA1.h2;
        int H3 = SHA1.h3;
        int H4 = SHA1.h4;

        int K[] = new int[80];

        for (int i = 0; i < 80; i++) {
            K[i] = SHA1.k[i];
        }

        int no_of_bits = text.length() * 8;
        int excess_bits;

        int d = no_of_bits % 512;

        if (d < 448) {
            excess_bits = 448 - d;
        } else {
            excess_bits = 448 + (512 - d);
        }

        int total_no_of_bits = no_of_bits + excess_bits + 64;

        int L = total_no_of_bits / 8;

        // L = length of characters since in ASCII each character is 
        // represented by 8 bits.
        int character_array[] = new int[L];
        for (int i = 0; i < text.length(); i++) {
            character_array[i] = (int) text.charAt(i);
            character_array[text.length()] = 0x0080;
        }

        int Y[] = new int[L / 2];
        int Z[] = new int[L / 2];
        int message_array[] = new int[L / 2];

        for (int j = 0; j < L / 2; j++) {
            Y[j] = (character_array[2 * j] << 8);
            Z[j] = character_array[2 * j + 1];
            message_array[j] = Y[j] | Z[j];
        }
        message_array[L / 2 - 1] = no_of_bits;

        int text_array[] = new int[L / 4];
        int Y2[] = new int[L / 4];
        int Z2[] = new int[L / 4];

        for (int j1 = 0; j1 < L / 4; j1++) {
            Y2[j1] = (message_array[2 * j1] << 16);
            Z2[j1] = message_array[2 * j1 + 1];
            text_array[j1] = Y2[j1] | Z2[j1];
        }

        int arr_size = text_array.length;
        int W[] = new int[80];
        int t;
        for (int m = 0; m < arr_size; m = m + 16) {

            for (t = 0; t < 16; t++) {
                W[t] = text_array[m + t];
            }

            for (t = 16; t < 80; t++) {
                W[t] = rotate(W[t - 3] ^ W[t - 8] ^ W[t - 14] ^ W[t - 16], 1);
            }

            int A = H0;
            int B = H1;
            int C = H2;
            int D = H3;
            int E = H4;
            int T;
            int F;

            for (t = 0; t < 20; t++) {
                F = (B & C) | ((~B) & D);
                T = rotate(A, 5) + F + E + W[t] + K[t];
                E = D;
                D = C;
                C = rotate(B, 30);
                B = A;
                A = T;
            }
            for (t = 20; t < 40; t++) {
                F = B ^ C ^ D;
                T = rotate(A, 5) + F + E + W[t] + K[t];
                E = D;
                D = C;
                C = rotate(B, 30);
                B = A;
                A = T;
            }
            for (t = 40; t < 60; t++) {
                F = (B & C) | (B & D) | (C & D);
                T = rotate(A, 5) + F + E + W[t] + K[t];
                E = D;
                D = C;
                C = rotate(B, 30);
                B = A;
                A = T;
            }
            for (t = 60; t < 80; t++) {
                F = B ^ C ^ D;
                T = rotate(A, 5) + F + E + W[t] + K[t];
                E = D;
                D = C;
                C = rotate(B, 30);
                B = A;
                A = T;
            }

            H0 = H0 + A;
            H1 = H1 + B;
            H2 = H2 + C;
            H3 = H3 + D;
            H4 = H4 + E;
        }

        // convert result to binary string:
        String SH0 = Integer.toHexString(H0);
        String SH1 = Integer.toHexString(H1);
        String SH2 = Integer.toHexString(H2);
        String SH3 = Integer.toHexString(H3);
        String SH4 = Integer.toHexString(H4);

        StringBuffer hashvalue = new StringBuffer(SH0);

        String result = new String(hashvalue.append(SH1).append(SH2).append(SH3).append(SH4));

        return result;

    }
}
