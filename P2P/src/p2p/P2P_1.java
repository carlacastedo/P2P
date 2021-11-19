/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author anton
 */
public class P2P_1 {

    private final SecureRandom generadorAleatorio;

    private final MessageDigest algoritmoSHA512;

    public P2P_1() {
        generadorAleatorio = new SecureRandom();
        MessageDigest algoritmo;
        try {
            algoritmo = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            algoritmo = null;
        }
        this.algoritmoSHA512 = algoritmo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        P2P_1 prueba = new P2P_1();

        byte[] hash = prueba.hashContrasena("dafddala");
        System.out.println("hash: " + hash);
    }

    private byte[] hashContrasena(String contraseña) {
        byte[] sal = new byte[15];
        generadorAleatorio.nextBytes(sal);

        System.out.println("sal: " + sal.toString());
        byte[] hash = aplicarAlgoritmoHash(contraseña, sal);

        return hash;
    }

    private byte[] aplicarAlgoritmoHash(String contraseña, byte[] sal) {

        byte[] hash;

        algoritmoSHA512.update(contraseña.getBytes(StandardCharsets.UTF_8));
        algoritmoSHA512.update(sal);

        hash = algoritmoSHA512.digest();

        algoritmoSHA512.reset();

        return hash;
    }

}
