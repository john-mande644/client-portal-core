package com.owd.jobs.jobobjects.pgp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.*;

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;





public class PGPUtils {
private final static Logger log =  LogManager.getLogger();


    public static void init()
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args)  throws Exception {

        String input = "-----BEGIN PGP MESSAGE-----\n" +
                "Version: GnuPG v1.2.0 (MingW32)\n" +
                "\n" +
                "hQEMA/DGVvEJBLJ8AQf8CbOksomYNS4VXsbB2iPhRKJaf/LB5lauYa0EdcqdMfHz\n" +
                "JJGHfiFFWrXtstW/c3QvUp1iB88mOYfjpsnoIEo5KoTyXqdBtoe8XI6YAT5QokNP\n" +
                "PD4ZCj6gx/DxWfofBwD3b1Xxe3oFBTDhaZ5uU15ywu6/9h1p4tS8xeIKj1afmvPi\n" +
                "Unp0+Nx0LiIMQyZ/eFFVyit9psj156Ezj9U3+9lQ4ZFEZ2ET4j/eAbp9O/JNZsoy\n" +
                "c+Su6lfRVpvokQgZC5Vsjx7YdCVJSBUNfiqh0okVJ3wvtdMtpt9F6knHEs/WPDQ5\n" +
                "I8kGHneJcXN/gezLjtyXYcnQLT78HdhiA2YWvBkJq8nqVGVq86N5k0jyht+tePrK\n" +
                "6NhmtXj/4FCq7cG7mcCtx4zvKXvjCMgy8+jc17+3VTmDc3FBghLFKRiovbsskcVq\n" +
                "vBfm+P2bd8dqpmLdiou2FyIWTJiyy/RfB/A7Nbmco8hRFpLE8mi88lNTTG8jmJx8\n" +
                "8cA5QIDWqMX42SizobwnRb9HTbOyEm/+t8NtlBjEW1qNRMOOlAt1N12mflV9ZRCw\n" +
                "fkjFQRkIHCJcJa7ukvUmwdUaaETS6LM6v7Y0t1GsjvD0/WEHS9cGABixjI0iiSx4\n" +
                "GrwtabKPQOSmfIRimlNaThIbKJXDSO9olWvU5tsKP9Q1k6E5YxaLDZXaOn9Xr6Cr\n" +
                "SmsI09b1SVshW3CDrlOBvMnmg/Ft5xGGLSSZa6Cz6PkeAs5zXBzreUe3UJqdfFad\n" +
                "Lq7vIeI2MY6gIE5XhtnfM4X6x1o2ahhlvRRwuoogviMp4Tjzgbm+hJcssVkeauJ4\n" +
                "gATH2lQaHFoEdoTAaFxYbgXWzlEKJUeXyXdYkehseW5Bt1GIDIFTHEwbl7HC5DMl\n" +
                "8EqkQDD+SCOc6odUB/PdSAUZ+gqu/tfdu9IzjwTUMN5HP+EQ4NT9ITy6kxcDojPh\n" +
                "7iI4LIFRdgHSnZXNJpj/v6f6RZTvnih6hvt9yc6Aw/pVWlLh6+N0aWaoBiDzUQgb\n" +
                "uGeP0zLggUuhyGCTci13+lpZXxoyH8DsRVRGWhUuv2aD+zN+7gAKLTqYiES9W9/+\n" +
                "IcOVN85hbqP8ioTxeSju6XAsulHMYwKjsAjNuYyUbhbG5CH9PEkPx4beO8CEubzp\n" +
                "RbM1PB8FLnetDvVv2ZBnv37fCnMLWXb8saLIzEp/nwAYHkg1ljcI5OyMl7b/d0EO\n" +
                "insCMNPE/osH2P+aTzNzLK4YtgbpeE56vthbGlAX8aHJ5mfGrboaUIEl5NlJwZC7\n" +
                "mrYd5MHVn6BuVyOEV2PVlL2vnTHbLUtLicrr/5/HGN8ftkUfSRdHqR4ZYPwoN4fi\n" +
                "j1u9YQC4rTNkQhcSFAnZk6GHqr4WqZPewEfAyGbq9pQpA+2GSoaj9IRnUhmqtlIX\n" +
                "+hsmFy+NH6UcdhYfj0ieXfWpqsDvLz45IE0QmRx5SRJuyq9MdTFqS6s34nPsrjs1\n" +
                "0SKiZ2tSAO60ADMDKidBAjpwZKLNmsJvbxzAvIKjl0Jx4P0350ywJNruyauzkoOu\n" +
                "P2WRNLW2FNumL3uQzwmZMHHrrEMrVXyW3VgHnn0JtdQpYLpvQq1Lt4jJXaJd9IRu\n" +
                "M3bqxw3qm9f6m5QCQPnnuYE2raNBKmwGcL9WoHDjXy3r2JGXMeDVfVGgSreMb/Ht\n" +
                "MjmC3TzfozCCWFf/Tb6uBaB1F+Sb6H8PCojMA1kpL6klxof6XPNaTkO84g38agws\n" +
                "MumFCIWmQzL7W+zI3BsygOBrlmNsE92Wf/rUnCcAfjRmSTpsZC7sYNrMDbOMzxan\n" +
                "p6bVeUgqFmtnP2xjLqVEMdIW+AgUOrF3KdmSVlE1t4lAnrmRh7wcNS538eVoFkTb\n" +
                "eQXnek1XUsz9nJu9vzyE204VbWZzK+9VXrWxM0SZt2EADgQdUiKVMSICfnePdOUx\n" +
                "d6FAqJfAetaDyq7PhK9IGNf3d6qs+rxtIRU1wepmieXwASFsCb281t+FA4cDkjcB\n" +
                "VrFfBcrlsVna4tyRjNo8Z59mG+7J7xzKSuR6eK2sFB9pJCGbSDn6Tuqj5UibJJAA\n" +
                "HW0vCwQrnNno4qANuaDVcoz4uwtCLF+tpZJ9l/HTggq9q3UeQVXHu4HeK00sht2P\n" +
                "hsUf/KJ9/GlfqwPWq0qgqHPtaiaCpX4cgGOGcVwY6Wubk8WkyboIVjfH3Xo/Gk+m\n" +
                "LDNMO/BQwjUED0BmdiXdYK0fbyqpzoXQFMNEFwbZHcFGEPJDLCQP8zljA2VTbw9h\n" +
                "jRKYpBZxZAClv+ZPqLatY4l1tp6klTvp78UY+Df/7mxSjTE3KTm/hIGxrYDnJMuL\n" +
                "CfCDtt+0E6N/Ik6lxaPEZ4+RRZXWgnsigGyicz+hu+p3RXBdqMozZJf+GMJAaG3P\n" +
                "8RZoU9v1mBkkKPqqgDCknnSVXvXuM5Lu1CuVi7LFXWGpta2ar35PafgWGFFviNYj\n" +
                "VvNFuMCYaSOVwdSM1KWngz0jm96kf1vYS9L1Uvm/2yZekvomR2lhCjbFJaD2CEE0\n" +
                "lkOKW7RajYpsCFFetYFOVJ3F7Ogv6Jmvl+rrv9xkGvm4mbAIE0g6sfIMG4Ao0Qkx\n" +
                "PWXI+ug8cWFRr9B9z68kUhgXw2jTILQnVlZzNMEBuQi/XYU2TY6+liaaPnLpt8PZ\n" +
                "W3xFoMdRMUfL/hGb5ZPKuA==\n" +
                "=p+Rq\n" +
                "-----END PGP MESSAGE-----\n";



       // decryptFile(new ByteArrayInputStream( input.getBytes()),System.out,new ByteArrayInputStream( WalmartPgp.owdPrivateKey.getBytes()),"badhorse57601".toCharArray());
        byte[] decrypted = decrypt(input.getBytes(), new ByteArrayInputStream( WalmartPgp.owdPrivateKey.getBytes()), "badhorse57601".toCharArray());
        log.debug("\r\nUNCRYPT\r\n");

        log.debug(new String(decrypted));

        byte[] crypted = encrypt( "Hello World!".getBytes(), readPublicKey(new ByteArrayInputStream(WalmartPgp.owdPublicKey.getBytes())),"filename.fil",  true, true);

        log.debug("\r\nCRYPT\r\n");
        log.debug(new String(crypted));

      //  decryptFile(new ByteArrayInputStream( new String(crypted).getBytes()),System.out,new ByteArrayInputStream( WalmartPgp.owdPrivateKey.getBytes()),"badhorse57601".toCharArray());
        decrypted = decrypt(new String(crypted).getBytes(), new ByteArrayInputStream( WalmartPgp.owdPrivateKey.getBytes()), "badhorse57601".toCharArray());
        log.debug("\r\nUNCRYPT\r\n");

        log.debug(new String(decrypted));

    }

//Read more: http://www.aviransplace.com/2004/10/12/using-rsa-encryption-with-java/#ixzz2GYW2AadB

    @SuppressWarnings("unchecked")
    public static PGPPublicKey readPublicKey(InputStream in)
            throws IOException, PGPException
    {

        PGPPublicKeyRingCollection keyRingCollection = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(in));

        //
        // we just loop through the collection till we find a key suitable for encryption, in the real
        // world you would probably want to be a bit smarter about this.
        //
        PGPPublicKey publicKey = null;

        //
        // iterate through the key rings.
        //
        Iterator<PGPPublicKeyRing> rIt = keyRingCollection.getKeyRings();

        while (publicKey == null && rIt.hasNext()) {
            PGPPublicKeyRing kRing = rIt.next();
            Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
            while (publicKey == null && kIt.hasNext()) {
                PGPPublicKey key = kIt.next();
                if (key.isEncryptionKey()) {
                    publicKey = key;
                }
            }
        }

        if (publicKey == null) {
            throw new IllegalArgumentException("Can't find public key in the key ring.");
        }

        return publicKey;
    }




   //*
    //* Encrypts a file for transfer over standard FTP or via email
    //* @param out Output Stream containing the file
    //* @param fileName String representation of the file we want to create
    //* @param encKey PGPPublicKey to encrypt the file.
    //* @param armor boolean value decides whether we need a new instance of an ArmoredOutputStream
    //* @param withIntegrityCheck boolean value for setting the Integrity Packet
    //* @throws IOException
    //* @throws NoSuchProviderException
    //


    public static byte[] encrypt(byte[] clearData, PGPPublicKey encKey,
                                 String fileName,boolean withIntegrityCheck, boolean armor)
            throws IOException, PGPException, NoSuchProviderException {
        if (fileName == null) {
            fileName = PGPLiteralData.CONSOLE;
        }

        ByteArrayOutputStream encOut = new ByteArrayOutputStream();

        OutputStream out = encOut;
        if (armor) {
            out = new ArmoredOutputStream(out);
        }

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(
                PGPCompressedDataGenerator.ZIP);
        OutputStream cos = comData.open(bOut); // open it with the final
        // destination
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();

        // we want to generate compressed data. This might be a user option
        // later,
        // in which case we would pass in bOut.
        OutputStream pOut = lData.open(cos, // the compressed output stream
                PGPLiteralData.BINARY, fileName, // "filename" to store
                clearData.length, // length of clear data
                new Date() // current time
        );
        pOut.write(clearData);

        lData.close();
        comData.close();

        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256).setWithIntegrityPacket(withIntegrityCheck).setSecureRandom(new SecureRandom()));


        BcPublicKeyKeyEncryptionMethodGenerator encKeyGen = new BcPublicKeyKeyEncryptionMethodGenerator(encKey);
        cPk.addMethod(encKeyGen);
        byte[] bytes = bOut.toByteArray();

        OutputStream cOut = cPk.open(out, bytes.length);

        cOut.write(bytes); // obtain the actual bytes from the compressed stream

        cOut.close();

        out.close();

        return encOut.toByteArray();
    }

    public static byte[] decrypt(byte[] encrypted, InputStream keyIn, char[] password)
            throws IOException, PGPException, NoSuchProviderException {
        InputStream in = new ByteArrayInputStream(encrypted);

        in = PGPUtil.getDecoderStream(in);

        PGPObjectFactory pgpF = new PGPObjectFactory(in);
        PGPEncryptedDataList enc = null;
        Object o = pgpF.nextObject();

        //
        // the first object might be a PGP marker packet.
        //
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }



        //
        // find the secret key
        //
        Iterator it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(keyIn));

        while (sKey == null && it.hasNext()) {
            pbe = (PGPPublicKeyEncryptedData) it.next();

            sKey = findSecretKey(pgpSec, pbe.getKeyID(), password);
        }

        if (sKey == null) {
            throw new IllegalArgumentException(
                    "secret key for message not found.");
        }

        InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));



        PGPObjectFactory pgpFact = new PGPObjectFactory(clear);

        PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();

        pgpFact = new PGPObjectFactory(cData.getDataStream());

        PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();

        InputStream unc = ld.getInputStream();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;

        while ((ch = unc.read()) >= 0) {
            out.write(ch);

        }

        byte[] returnBytes = out.toByteArray();
        out.close();
        return returnBytes;
    }

    private static PGPPrivateKey findSecretKey(
            PGPSecretKeyRingCollection pgpSec, long keyID, char[] pass)
            throws PGPException, NoSuchProviderException {
        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null) {
            return null;
        }

        PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(pass);
        return pgpSecKey.extractPrivateKey(decryptor);

    }
}
