package ch.rootkit.varoke.network.crypto;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
	private int BITLENGTH = 30;

	private BigInteger Prime;
	private BigInteger Generator;

	private BigInteger PrivateKey;
	private BigInteger PublicKey;

	private BigInteger PublicClientKey;

	private BigInteger SharedKey;

    public DiffieHellman() {
        this.initialize();
    }

    public DiffieHellman(int b) {
        this.BITLENGTH = b;

        this.initialize();
    }

    public DiffieHellman(BigInteger prime, BigInteger generator) {
        this.Prime = prime;
        this.Generator = generator;

        this.PrivateKey = new BigInteger(generateRandomHexString(BITLENGTH), 16);

        if (this.Generator.intValue() > this.Prime.intValue()) {
            BigInteger temp = this.Prime;
            this.Prime = this.Generator;
            this.Generator = temp;
        }

        this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime);
    }
    public BigInteger getPrime(){
    	return Prime;
    }
    
    public BigInteger getGenerator(){
    	return Generator;
    }
    
    public BigInteger getPublicKey(){
    	return PublicKey;
    }
    
    public BigInteger getSharedKey(){
    	return SharedKey;
    }
    
    public static String generateRandomHexString(int len) {
        int rand = 0;
        String result = "";

        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            rand = 1 + (int) (rnd.nextDouble() * 254); // 1 - 255
            result += Integer.toString(rand, 16);
        }
        return result;
    }

    private void initialize() {
        this.PublicKey = BigInteger.valueOf(0);
        Random random = new Random();
        while (this.PublicKey.intValue() == 0) {
            this.Prime = BigInteger.probablePrime(BITLENGTH, random);
            this.Generator = BigInteger.probablePrime(BITLENGTH, random);

            this.PrivateKey = new BigInteger(generateRandomHexString(BITLENGTH), 16);

            if (this.PrivateKey.intValue() < 1) {
                continue;
            }

            if (this.Generator.intValue() > this.Prime.intValue()) {
                BigInteger temp = this.Prime;
                this.Prime = this.Generator;
                this.Generator = temp;
            }

            this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime);
        }
    }

    public void generateSharedKey(String ckey) {
        this.PublicClientKey = new BigInteger(ckey);
        this.SharedKey = this.PublicClientKey.modPow(this.PrivateKey, this.Prime);
    }
}