package com.owd.jobs.jobobjects.pgp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.*;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.util.io.Streams;

import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by stewartbuskirk1 on 9/16/14.
 */
public class PgpTest {
private final static Logger log =  LogManager.getLogger();
    public static void main(String args[]) {
        Security.insertProviderAt(new BouncyCastleProvider(), 0);
        byte inBytes[] = "The quick brown fox jumps over the lazy dog.".getBytes();

        try {
            SecureRandom rand = new SecureRandom();

            RSAKeyPairGenerator kpg = new RSAKeyPairGenerator();
            kpg.init(new RSAKeyGenerationParameters(BigInteger.valueOf(0x10001), rand, 1024, 90));

            BcPGPKeyPair sender = new BcPGPKeyPair(PGPPublicKey.RSA_GENERAL, kpg.generateKeyPair(), new Date());
            BcPGPKeyPair recip = new BcPGPKeyPair(PGPPublicKey.RSA_GENERAL, kpg.generateKeyPair(), new Date());

            PGPPublicKey walmartKey= readPublicKey(new ByteArrayInputStream( WalmartPgp.owdPublicKey.getBytes()));

            ByteArrayOutputStream sendMessage = new ByteArrayOutputStream();
            ByteArrayOutputStream recvMessage = new ByteArrayOutputStream();
          //  signEncryptMessage(new ByteArrayInputStream(inBytes), sendMessage, readPublicKey(new ByteArrayInputStream( WalmartPgp.owdPublicKey.getBytes())), getSecretKey(WalmartPgp.owdPrivateKey.getBytes()), rand);

            log.debug(sendMessage.toString());

            decryptVerifyMessage(new ByteArrayInputStream(sendMessage.toByteArray()), recvMessage, recip.getPrivateKey(), sender.getPublicKey());

            log.debug(recvMessage.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptVerifyMessage(InputStream in, OutputStream fOut, PGPPrivateKey secretKey, PGPPublicKey publicKey) throws IOException, SignatureException, PGPException {
        in = PGPUtil.getDecoderStream(in);

        PGPObjectFactory pgpF = new PGPObjectFactory(in);
        PGPEncryptedDataList enc;

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
        Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
       /* while (sKey == null && it.hasNext()) {
            pbe = it.next();
            PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(INSTANCE._secretKeyPass.toCharArray());
            PGPSecretKey psKey = INSTANCE._secretKeyRingCollection.getSecretKey(pbe.getKeyID());
            if (psKey != null) {
                sKey = psKey.extractPrivateKey(decryptor);
            }
        }
        if (sKey == null) {
            throw new IllegalArgumentException("Unable to find secret key to decrypt the message");
        }*/
        pbe = it.next();
        sKey=secretKey;

        InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));

        PGPObjectFactory plainFact = new PGPObjectFactory(clear);

        Object message;

        PGPOnePassSignatureList onePassSignatureList = null;
        PGPSignatureList signatureList = null;
        PGPCompressedData compressedData;

        message = plainFact.nextObject();
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();

        while (message != null) {
         //   __l.trace(message.toString());
            if (message instanceof PGPCompressedData) {
                compressedData = (PGPCompressedData) message;
                plainFact = new PGPObjectFactory(compressedData.getDataStream());
                message = plainFact.nextObject();
            }

            if (message instanceof PGPLiteralData) {
                // have to read it and keep it somewhere.
                Streams.pipeAll(((PGPLiteralData) message).getInputStream(), actualOutput);
            } else if (message instanceof PGPOnePassSignatureList) {
                onePassSignatureList = (PGPOnePassSignatureList) message;
            } else if (message instanceof PGPSignatureList) {
                signatureList = (PGPSignatureList) message;
            } else {
                throw new PGPException("message unknown message type.");
            }
            message = plainFact.nextObject();
        }
        actualOutput.close();
       // PGPPublicKey publicKey = null;
        byte[] output = actualOutput.toByteArray();
        if (onePassSignatureList == null || signatureList == null) {
            throw new PGPException("Poor PGP. Signatures not found.");
        } else {

            for (int i = 0; i < onePassSignatureList.size(); i++) {
                PGPOnePassSignature ops = onePassSignatureList.get(0);
              //  __l.trace("verifier : " + ops.getKeyID());
        /*        PGPPublicKeyRingCollection pgpRing = new PGPPublicKeyRingCollection(
                        PGPUtil.getDecoderStream(publicKey));
                publicKey = pgpRing.getPublicKey(ops.getKeyID());*/
                if (publicKey != null) {
                    ops.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), publicKey);
                    ops.update(output);
                    PGPSignature signature = signatureList.get(i);
                    if (ops.verify(signature)) {
                        Iterator<?> userIds = publicKey.getUserIDs();
                        while (userIds.hasNext()) {
                            String userId = (String) userIds.next();
                           // __l.trace(String.format("Signed by {%s}", userId));
                        }
                       // __l.trace("Signature verified");
                    } else {
                        throw new SignatureException("Signature verification failed");
                    }
                }
            }

        }

        if (pbe.isIntegrityProtected() && !pbe.verify()) {
            throw new PGPException("Data is integrity protected but integrity is lost.");
        } else if (publicKey == null) {
            throw new SignatureException("Signature not found");
        } else {
            fOut.write(output);
            fOut.flush();
            fOut.close();
        }
    }

    public static void signEncryptMessage(InputStream in, OutputStream out, PGPPublicKey publicKey, PGPPrivateKey secretKey, SecureRandom rand) throws Exception {
        out = new ArmoredOutputStream(out);

        PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256).setWithIntegrityPacket(true).setSecureRandom(rand));
        encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(publicKey));

        OutputStream compressedOut = new PGPCompressedDataGenerator(PGPCompressedData.ZIP).open(encryptedDataGenerator.open(out, new byte[4096]), new byte[4096]);

        PGPSignatureGenerator signatureGenerator = new PGPSignatureGenerator(new BcPGPContentSignerBuilder(publicKey.getAlgorithm(), HashAlgorithmTags.SHA512));
        signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, secretKey);
        signatureGenerator.generateOnePassVersion(true).encode(compressedOut);

        OutputStream finalOut = new PGPLiteralDataGenerator().open(compressedOut, PGPLiteralData.BINARY, "", new Date(), new byte[4096]);

        byte[] buf = new byte[4096];
        int len;
        while ((len = in.read(buf)) > 0) {
            finalOut.write(buf, 0, len);
            signatureGenerator.update(buf, 0, len);
        }

        finalOut.close();
        in.close();
        signatureGenerator.generate().encode(compressedOut);
        compressedOut.close();
        encryptedDataGenerator.close();
        out.close();
    }

    public static PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException {
        in = PGPUtil.getDecoderStream(in);
        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in);
        PGPPublicKey key = null;
        Iterator rIt = pgpPub.getKeyRings();
        while (key == null && rIt.hasNext()) {
            PGPPublicKeyRing kRing = (PGPPublicKeyRing) rIt.next();
            Iterator kIt = kRing.getPublicKeys();
            while (key == null && kIt.hasNext()) {
                PGPPublicKey k = (PGPPublicKey) kIt.next();
                if (k.isEncryptionKey()) {
                    key = k;
                }
            }
        }
        if (key == null) {
            throw new IllegalArgumentException("Can't find encryption key in key ring.");
        }
        return key;
    }



    public static PGPPrivateKey getSecretKey(PGPPublicKeyEncryptedData pbe, InputStream keyIn,char[] password) throws IOException, PGPException, IllegalArgumentException
    {

        PGPPrivateKey sKey = null;
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(keyIn));


           // sKey = findSecretKey(pgpSec, pbe.getKeyID(), password);

        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(pbe.getKeyID());

        if (pgpSecKey == null) {
            return null;
        }

        sKey = pgpSecKey.extractPrivateKey(new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(password));

        if (sKey == null) {
            throw new IllegalArgumentException(
                    "secret key for message not found.");
        }


         return sKey;

    }

}
