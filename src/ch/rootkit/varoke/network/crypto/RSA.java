package ch.rootkit.varoke.network.crypto;

import java.math.BigInteger;

import ch.rootkit.varoke.utils.RSAConfig;

public class RSA {
    private BigInteger Exponent;
    private BigInteger n;
    private BigInteger Private;
    private boolean Decryptable;
    private boolean Encryptable;
    private BigInteger Zero = new BigInteger("0");

    public RSA() {

    }

    public void init() {
        n = new BigInteger(RSAConfig.get("Crypto.RSA.N"), 16);
        Exponent = new BigInteger(RSAConfig.get("Crypto.RSA.E"), 16);
        Private = new BigInteger(RSAConfig.get("Crypto.RSA.D"), 16);
        Encryptable = (n != null && n != Zero && Exponent != Zero);
        Decryptable = (Encryptable && Private != Zero && Private != null);
    }
    
    public int getBlockSize() {
        return (n.bitLength() + 7) / 8;
    }
    
    public BigInteger doPublic(BigInteger x) {
        if (this.Encryptable) {
            return x.modPow(new BigInteger(this.Exponent + ""), this.n);
        }
        return Zero;
    }
    
    public String encrypt(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.getBlockSize()));
        if (m.equals(Zero)) {
            return null;
        }
        BigInteger c = this.doPublic(m);
        if (c.equals(Zero)) {
            return null;
        }
        String result = c.toString(16);
        if ((result.length() & 1) == 0) {
            return result;
        }
        return "0" + result;
    }
    
    public String sign(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.getBlockSize()));
        if (m.equals(Zero)) {
            return null;
        }
        BigInteger c = this.doPrivate(m);
        if (c.equals(Zero)) {
            return null;
        }
        String result = c.toString(16);
        if ((result.length() & 1) == 0) {
            return result;
        }
        return "0" + result;
    }
    
    private byte[] pkcs1pad2(byte[] data, int n) {
        byte[] bytes = new byte[n];
        int i = data.length - 1;
        while (i >= 0 && n > 11) {
            bytes[--n] = data[i--];
        }
        bytes[--n] = 0;
        while (n > 2) {
            bytes[--n] = (byte) 0xFF;
        }
        bytes[--n] = (byte) 1;
        bytes[--n] = 0;
        return bytes;
    }
    
    public BigInteger doPrivate(BigInteger x) {
        if (this.Decryptable) {
            return x.modPow(this.Private, this.n);
        }
        return Zero;
    }
    
    public String decrypt(String ctext) {
        BigInteger c = new BigInteger(ctext, 16);
        BigInteger m = this.doPrivate(c);
        if (m.equals(Zero)) {
            return null;
        }
        byte[] bytes = this.pkcs1unpad2(m, this.getBlockSize());
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }
    
    private byte[] pkcs1unpad2(BigInteger src, int n) {
        byte[] bytes = src.toByteArray();
        byte[] out;
        int i = 0;
        while (i < bytes.length && bytes[i] == 0) {
            ++i;
        }
        if (bytes.length - i != n - 1 || bytes[i] > 2) {
            return null;
        }
        ++i;
        while (bytes[i] != 0) {
            if (++i >= bytes.length) {
                return null;
            }
        }
        out = new byte[(bytes.length - i) + 1];
        int p = 0;
        while (++i < bytes.length) {
            out[p++] = (bytes[i]);
        }
        return out;
    }
    
}