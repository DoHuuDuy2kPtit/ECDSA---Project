/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecdsa_demo;

/**
 *
 * @author admin
 */
import java.math.*;

public class Elliptic_Curve {

    private BigInteger a, b, prime_p;

    Elliptic_Curve(BigInteger value_a, BigInteger value_b, BigInteger value_p) {
        a = value_a;
        b = value_b;
        prime_p = value_p;
    }

    BigInteger get_a() {
        return this.a;
    }

    BigInteger get_b() {
        return this.b;
    }

    BigInteger get_p() {
        return this.prime_p;
    }

    //Elliptic Curve Point Addition method
    ECPoint point_add(ECPoint P, ECPoint Q) {
        ECPoint R = new ECPoint(BigInteger.ZERO, BigInteger.ZERO);
        BigInteger lambda, x, y, temp;

        if (((P.point_at_infinity) && (Q.point_at_infinity)) || (P.get_x().equals(Q.get_x()) && P.get_y().equals(Q.get_y().negate()))) {
            R.point_at_infinity = true;

        } else {
            // if two points are equal
            if ((P.get_x().equals(Q.get_x())) && (P.get_y().equals(Q.get_y()))) {

                temp = new BigInteger("3");
                lambda = ((temp.multiply(P.get_x().pow(2))).add(this.a)).multiply((P.get_y().add(P.get_y())).modInverse(this.prime_p));
                x = (lambda.multiply(lambda)).subtract(P.get_x().add(Q.get_x()));
                y = (lambda.multiply(P.get_x().subtract(x))).subtract(P.get_y());

                x = x.mod(this.prime_p);
                y = y.mod(this.prime_p);

                R = new ECPoint(x, y);

            } else {
                // if two points are distinct
                if (P.point_at_infinity) {
                    x = Q.get_x();
                    y = Q.get_y();
                    R = new ECPoint(x, y);

                } else if (Q.point_at_infinity) {
                    x = P.get_x();
                    y = P.get_y();
                    R = new ECPoint(x, y);

                } else {
                    temp = (Q.get_x().subtract(P.get_x())).modInverse(this.prime_p);
                    lambda = (Q.get_y().subtract(P.get_y())).multiply(temp);

                    x = (lambda.multiply(lambda)).subtract(P.get_x().add(Q.get_x()));
                    y = (lambda.multiply(P.get_x().subtract(x))).subtract(P.get_y());

                    x = x.mod(this.prime_p);
                    y = y.mod(this.prime_p);

                    R = new ECPoint(x, y);

                }
            }
        }
        return R;
    }

    //Elliptic Curve Point Multiplication method
    ECPoint point_multiplication(BigInteger d, ECPoint P, Elliptic_Curve E1) {

        String d_binary = d.toString(2);
        ECPoint Q = new ECPoint(BigInteger.ZERO, BigInteger.ZERO);
        Q.point_at_infinity = true;

        /*if(d_binary.substring(0,1).equals("1")){
				Q = P;
			}
         */
        for (int i = 0; i < d_binary.length(); i++) {
            Q = E1.point_add(Q, Q);

            if (d_binary.substring(i, i + 1).equals("1")) {
                Q = E1.point_add(Q, P);
            }
        }
        return Q;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Point checking

    void point_check(ECPoint P) {
        BigInteger k1 = (P.get_y().pow(2)).mod(this.prime_p);
        BigInteger k2 = ((P.get_x().pow(3)).add((this.a).multiply(P.get_x())).add(this.b)).mod(this.prime_p);
        if (k1.compareTo(k2) == 0) {
            System.out.println("The point is within the curve");
        } else {
            System.out.println("The point is not within the curve");
        }
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
