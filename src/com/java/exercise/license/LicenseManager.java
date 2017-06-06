/**
 * 
 */
package com.java.exercise.license;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.swing.JOptionPane;

public class LicenseManager {
    private static LicenseManager instance;
    public static boolean IS_TRIAL = true;
    public static boolean IS_LICENSED =  false;
    public static License LICENSE = null;
    private static final int ENTROPY = 456456456;
    private static final String HEXES = "0123456789ABCDEF";
    
    public static final String LICENSE_FILENAME = "license";
    public static final String HASH_FILENAME = "license.sha1";
    public static final String SIGNATURE_FILENAME = "license.sig";
    
    private static final int KEY_LEN = 62;
    private static final byte[] def = new byte[]{24, 4, 124, 10, 91};
    private static final byte[][] params = new byte[][]{{24, 4, 127}, {10, 0, 56}, {1, 2, 91}, {7, 1, 100}};
    private static final Set<String> blacklist = new TreeSet<String>();
    
    private Timer t;
    private static final int DELAY = 900000;

    static {
        blacklist.add("11111111");
    }

    protected LicenseManager() {
        t = new Timer();
        t.scheduleAtFixedRate(new CheckLicenseTask(), DELAY, DELAY);
    }

    public static LicenseManager getLicenseManager() {
        if (instance == null) {
            instance = new LicenseManager();
        }

        return instance;
    }
    
    /**
     * 
     * @param lic 
     */
    private void writeLicenseFile(License lic, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path + LICENSE_FILENAME)));
            oos.writeObject(lic);
            oos.close();
        }
        catch(Exception ex) { }
    }
    
    public static KeyStatus readLicenseFile(String licensePath, String signaturePath, String hashPath) {
        try {
            // read in file and validate the LICENSE based on the signature
            // this will remove changes of faking a LICENSE file
            // the LICENSE file has to be signed with our key
            File licenseFile = new File(licensePath);
            File signatureFile = new File(signaturePath);
            File hashFile = new File(hashPath);
            
            KeyStatus status = EncryptionManager.getEncryptionManager().verify(licenseFile, signatureFile, hashFile);
            
            if(!status.equals(KeyStatus.KEY_GOOD)) {
                return KeyStatus.KEY_INVALID;
            }
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(licenseFile));
            LICENSE = (License)ois.readObject();
            
            String lic = LICENSE.getLicenseNumber();
            if(LICENSE.getLicenseType().equals(LicenseType.TRIAL)) {
                IS_TRIAL = true;
                
                Calendar c = Calendar.getInstance();
                if(c.getTime().after(LICENSE.getExpiration())) {
                    return KeyStatus.KEY_EXPIRED;
                }
                
                Date expiration = LicenseManager.getLicenseManager().LICENSE.getExpiration();

                long val = expiration.getTime() - c.getTime().getTime();
                val /= (1000 * 60 * 60 * 24);
                JOptionPane.showMessageDialog(null, "This is a trial version of the software.  You have " + val + " days remaining in your trial.", "Trial Version", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(LICENSE.getLicenseType().equals(LicenseType.SINGLE_VERSION)) {
                IS_TRIAL = false;
                status = checkKey(lic);
                IS_LICENSED = true;
            }
            else if(LICENSE.getLicenseType().equals(LicenseType.LIFETIME)) {
                IS_TRIAL = false;
                status = checkKey(lic);
                IS_LICENSED = true;
            }
            
            if(!status.equals(KeyStatus.KEY_GOOD)) {
                return status;
            }

            return KeyStatus.KEY_GOOD;
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
            return KeyStatus.KEY_INVALID;
        }
    }

    /**
     * 
     * @param name
     * @param email
     * @param authCode 
     * @param licenseType 
     * @param expiration 
     * @param version
     * @param path  
     */
    public void createLicense(String name, String email, String authCode, LicenseType licenseType, Date expiration, String version, String path) {
        byte[] entropy = null;
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            entropy = digest.digest(getByteArrayFromHexString(authCode));
        }
        catch(NoSuchAlgorithmException ex) { /* this will never happen */ }

        License lic = new License(name, email, LicenseManager.makeKey(ENTROPY, entropy), expiration, licenseType, version);
        writeLicenseFile(lic, path);
    }

    /**
     * 
     * @return 
     */
    private static byte[] getHardwareEntropy() {
        byte[] mac;
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null) {
                mac = ni.getHardwareAddress();
                if (mac == null) {
                    mac = def;   
                }
            } else {
                mac = def;
            }
        } 
        catch (Exception ex) { 
            mac = def;
        }
        
        byte[] entropyEncoded = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            entropyEncoded = digest.digest(mac);
        }
        catch(NoSuchAlgorithmException ex) { /* this will never happen */ }
        
        return entropyEncoded;
    }

    /**
     * 
     * @param seed
     * @param a
     * @param b
     * @param c
     * @return 
     */
    private static byte getKeyByte(final int seed, final byte a, final byte b, final byte c) {
        final int a1 = a % 25;
        final int b1 = b % 3;
        if (a1 % 2 == 0) {
            return (byte) (((seed >> a1) & 0x000000FF) ^ ((seed >> b1) | c));
        } else {
            return (byte) (((seed >> a1) & 0x000000FF) ^ ((seed >> b1) & c));
        }
    }

    /**
     * 
     * @param s
     * @return 
     */
    private static String getChecksum(final String s) {
        int left = 0x0056;
        int right = 0x00AF;
        for (byte b : s.getBytes()) {
            right += b;
            if (right > 0x00FF) {
                right -= 0x00FF;
            }
            left += right;
            if (left > 0x00FF) {
                left -= 0x00FF;
            }
        }
        int sum = (left << 8) + right;
        return intToHex(sum, 4);
    }

    /**
     * 
     * @param seed
     * @param entropy 
     * @return 
     */
    public static String makeKey(final int seed, byte[] entropy) {
        // fill keyBytes with values derived from seed.
        // the parameters used here must be exactly the same
        // as the ones used in the checkKey function.
        final byte[] keyBytes = new byte[25];
        keyBytes[0] = getKeyByte(seed, params[0][0], params[0][1], params[0][2]);
        keyBytes[1] = getKeyByte(seed, params[1][0], params[1][1], params[1][2]);
        keyBytes[2] = getKeyByte(seed, params[2][0], params[2][1], params[2][2]);
        keyBytes[3] = getKeyByte(seed, params[3][0], params[3][1], params[3][2]);
        for(int i = 4, j = 0; (j + 2) < entropy.length; i++, j += 3) {
            keyBytes[i] = getKeyByte(seed, entropy[j], entropy[j + 1], entropy[j + 2]);
        }      

        // the key string begins with a hexadecimal string of the seed
        final StringBuilder result = new StringBuilder(intToHex(seed, 8));

        // then is followed by hexadecimal strings of each byte in the key
        for (byte b : keyBytes) {
            result.append(intToHex(b, 2));
        }

        // add checksum to key string
        String key = result.toString();
        key += getChecksum(key);

        return key;
    }

    /**
     * 
     * @param key
     * @return 
     */
    private static boolean validateKeyChecksum(final String key) {
        if (key.length() != KEY_LEN) {
            return false;
        }

        // last four characters are the checksum
        final String checksum = key.substring(KEY_LEN - 4);
        return checksum.equals(getChecksum(key.substring(0, KEY_LEN - 4)));
    }

    /**
     * 
     * @param key
     * @return 
     */
    public static KeyStatus checkKey(final String key) {
        if (!validateKeyChecksum(key)) {
            return KeyStatus.KEY_INVALID; // bad checksum or wrong number of
            // characters
        }
        
        // test against blacklist
        for (String bl : blacklist) {
            if (key.startsWith(bl)) {
                return KeyStatus.KEY_BLACKLISTED;
            }
        }

        // at this point, the key is either valid or forged,
        // because a forged key can have a valid checksum.
        // we now test the "bytes" of the key to determine if it is
        // actually valid.

        // when building your release application, use conditional defines
        // or comment out most of the byte checks! this is the heart
        // of the partial key verification system. by not compiling in
        // each check, there is no way for someone to build a keygen that
        // will produce valid keys. if an invalid keygen is released, you can
        // simply change which byte checks are compiled in, and any serial
        // number built with the fake keygen no longer works.

        // note that the parameters used for getKeyByte calls MUST
        // MATCH the values that makeKey uses to make the key in the
        // first place!

        // extract the seed from the supplied key string
        final int seed;
        try {
            seed = Integer.valueOf(key.substring(0, 8), 16);
        } catch (NumberFormatException e) {
            return KeyStatus.KEY_PHONY;
        }

        // test key 0
        final String kb0 = key.substring(8, 10);
        final byte b0 = getKeyByte(seed, params[0][0], params[0][1], params[0][2]);
        if (!kb0.equals(intToHex(b0, 2))) {
            return KeyStatus.KEY_PHONY;
        }

        // test key1
        final String kb1 = key.substring(10, 12);
        final byte b1 = getKeyByte(seed, params[1][0], params[1][1], params[1][2]);
        if (!kb1.equals(intToHex(b1, 2))) {
            return KeyStatus.KEY_PHONY;
        }

        // test key2
        final String kb2 = key.substring(12, 14);
        final byte b2 = getKeyByte(seed, params[2][0], params[2][1], params[2][2]);
        if (!kb2.equals(intToHex(b2, 2))) {
            return KeyStatus.KEY_PHONY;
        }

        // test key3
        final String kb3 = key.substring(14, 16);
        final byte b3 = getKeyByte(seed, params[3][0], params[3][1], params[3][2]);
        if (!kb3.equals(intToHex(b3, 2))) {
            return KeyStatus.KEY_PHONY;
        }
        
        // test the hardware entropy
        byte[] encodedEntropy = getHardwareEntropy();
        for(int i = 16, j = 0; (j + 2) < encodedEntropy.length; i += 2, j += 3) {
            String kb = key.substring(i, i + 2);
            byte b = getKeyByte(seed, encodedEntropy[j], encodedEntropy[j + 1], encodedEntropy[j + 2]);
            if(!kb.equals(intToHex(b, 2))) {
                return KeyStatus.KEY_INVALID;
            }
        }

        // if we get this far, then it means the key is either good, or was made
        // with a keygen derived from "this" release.
        return KeyStatus.KEY_GOOD;
    }

    /**
     * 
     * @param n
     * @param chars
     * @return 
     */
    protected static String intToHex(final Number n, final int chars) {
        return String.format("%0" + chars + "x", n);
    }
    
    /**
     * 
     * @param raw
     * @return 
     */
    public static String getHexStringFromBytes(byte[] raw) {
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }

        return hex.toString();
    }
  
    /**
     * 
     * @param s
     * @return 
     */
    public static byte[] getByteArrayFromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
  
    private class CheckLicenseTask extends TimerTask {
        public CheckLicenseTask() { }

        @Override
        public void run() {
            System.out.println("checking license");
        }
    }
}
