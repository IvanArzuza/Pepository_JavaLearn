/**
 * 
 */
package com.java.exercise.license;

import Utility.Logger;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;
import javax.crypto.*;

public class EncryptionManager {

    private static EncryptionManager instance;
    private static final String PUBLIC_KEY_FILE = "/License/public_key.der";
    private static final String PRIVATE_KEY_FILE = "/path/to/your/private_key.der";
    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    protected EncryptionManager() throws GeneralSecurityException {
    }

    public static EncryptionManager getEncryptionManager() {
        if (instance == null) {
            try {
                instance = new EncryptionManager();
                
                try {
                    privateKey = loadPrivateKey(PRIVATE_KEY_FILE);
                } 
                catch (Exception ex) {
                    //Logger.getLogger().ALog("private key failed to load - couldn't instantiate encryption manager.\n" + ex.toString());
                }
                try {
                    publicKey = loadPublicKey(PUBLIC_KEY_FILE);
                } 
                catch (Exception ex) {
                    //Logger.getLogger().ALog("public key failed to load - couldn't instantiate encryption manager.\n" + ex.toString());
                }
            }
            catch(GeneralSecurityException ex) {
                //Logger.getLogger().ALog("couldn't instantiate encryption manager.\n" + ex.toString());
            }
        }

        return instance;
    }

    /**
     * 
     * @param filename
     * @return
     * @throws Exception 
     */
    private static PublicKey loadPublicKey(String filename) throws Exception {
        DataInputStream dis = new DataInputStream(File.class.getResourceAsStream(filename));
        byte[] keyBytes = new byte[dis.available()];
        dis.readFully(keyBytes);
        dis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 
     * @param filename
     * @return
     * @throws Exception 
     */
    private static PrivateKey loadPrivateKey(String filename) throws Exception {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * 
     * @param dataToHashPath
     * @return 
     */
    public static byte[] digest(File dataToHashPath) {
        try {
            InputStream fin = new FileInputStream(dataToHashPath);
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            
            byte[] buffer = new byte[1024];
            int read;
            
            do {
                read = fin.read(buffer);
                if (read > 0) {
                    md5Digest.update(buffer, 0, read);
                }
            } while (read != -1);
            fin.close();
            
            byte[] digest = md5Digest.digest();
            if (digest == null) {
                return null;
            }
            
            return digest;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     * @param dataToVerify 
     * @param signatureFile 
     * @param hashFile 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws NoSuchPaddingException
     * @throws FileNotFoundException 
     * @throws IOException  
     */
    public static KeyStatus verify(File dataToVerify, File signatureFile, File hashFile) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            SignatureException, NoSuchPaddingException, FileNotFoundException, IOException {
        // first validate the hash of the file        
        FileInputStream hashfis = new FileInputStream(hashFile);
        byte[] hashToVerify = new byte[hashfis.available()];
        hashfis.read(hashToVerify);
        hashfis.close();
        
        byte[] licenseBytes = digest(dataToVerify);
        if(!Arrays.equals(licenseBytes, hashToVerify)) {
            Logger.getLogger().ALog("key failed to pass hash check");
            return KeyStatus.KEY_INVALID;
        }
        
        // now validate that we were the ones who shipped it
        Signature rsaSignature = Signature.getInstance("SHA1withRSA");
        rsaSignature.initVerify(publicKey);

        FileInputStream sigfis = new FileInputStream(signatureFile);
        byte[] sigToVerify = new byte[sigfis.available()];
        sigfis.read(sigToVerify);
        sigfis.close();

        FileInputStream datafis = new FileInputStream(hashFile);
        BufferedInputStream bufin = new BufferedInputStream(datafis);

        byte[] buffer = new byte[1024];
        int len;
        while (bufin.available() != 0) {
            len = bufin.read(buffer);
            rsaSignature.update(buffer, 0, len);
        };

        bufin.close();

        if (rsaSignature.verify(sigToVerify)) {
            return KeyStatus.KEY_GOOD;
        } else {
            Logger.getLogger().ALog("key failed to pass signature check");
            return KeyStatus.KEY_INVALID;
        }
    }

    /**
     * 
     * @param dataToSign 
     * @param signatureFilePath 
     * @param hashFilePath 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws FileNotFoundException
     * @throws IOException  
     */
    public static void sign(byte[] dataToSign, String signatureFilePath, String hashFilePath) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
            SignatureException, FileNotFoundException, IOException {
        // initialize the signing algorithm with our private key
        Signature rsaSignature = Signature.getInstance("SHA1withRSA");
        rsaSignature.initSign(privateKey);
        rsaSignature.update(dataToSign, 0, dataToSign.length);

        // sign it
        byte[] sig = rsaSignature.sign();

        // save the signature to disk to verify later
        FileOutputStream fos = new FileOutputStream(signatureFilePath);
        fos.write(sig);
        fos.close();
        
        fos = new FileOutputStream(hashFilePath);
        fos.write(dataToSign);
        fos.close();
    }
}