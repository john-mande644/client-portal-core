package com.owd.jobs.jobobjects.pgp;

import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.jcajce.JcaPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.io.Streams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

public class PgpUtil {
    public static void init()
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] createEncryptedData(
            PGPPublicKey encryptionKey,
            byte[] data)
            throws PGPException, IOException
    {
        PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(
                new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256)
                        .setWithIntegrityPacket(true)
                        .setSecureRandom(new SecureRandom()).setProvider("BC"));
        encGen.addMethod(
                new JcePublicKeyKeyEncryptionMethodGenerator(encryptionKey)
                        .setProvider("BC"));
        ByteArrayOutputStream encOut = new ByteArrayOutputStream();
        // create an indefinite length encrypted stream
        OutputStream cOut = encGen.open(encOut, new byte[4096]);
        // write out the literal data
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
        OutputStream pOut = lData.open(
                cOut, PGPLiteralData.BINARY,
                PGPLiteralData.CONSOLE, data.length, new Date());
        pOut.write(data);
        pOut.close();
        // finish the encryption
        cOut.close();
        return encOut.toByteArray();
    }

    public static byte[] extractPlainTextData(
            PGPPrivateKey privateKey,
            byte[] pgpEncryptedData)
            throws PGPException, IOException
    {
        PGPObjectFactory pgpFact = new JcaPGPObjectFactory(pgpEncryptedData);
        PGPEncryptedDataList enc;

        Object o = pgpFact.nextObject();
        //
        // the first object might be a PGP marker packet.
        //
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpFact.nextObject();
        }
        // find the matching public key encrypted data packet.
        PGPPublicKeyEncryptedData encData = null;

        for (int i = 0; i < enc.size(); i++) {
            PGPPublicKeyEncryptedData pkEnc
                    = (PGPPublicKeyEncryptedData)enc.get(i);
            if (pkEnc.getKeyID() == privateKey.getKeyID())
            {
                encData = pkEnc;
                break;
            }
        }

        /*for (PGPEncryptedData pgpEnc: encList)
        {
            PGPPublicKeyEncryptedData pkEnc
                    = (PGPPublicKeyEncryptedData)pgpEnc;
            if (pkEnc.getKeyID() == privateKey.getKeyID())
            {
                encData = pkEnc;
                break;
            }
        }*/
        if (encData == null)
        {
            throw new IllegalStateException("matching encrypted data not found");
        }
        // build decryptor factory
        PublicKeyDataDecryptorFactory dataDecryptorFactory =
                new JcePublicKeyDataDecryptorFactoryBuilder()
                        .setProvider("BC")
                        .build(privateKey);
        InputStream clear = encData.getDataStream(dataDecryptorFactory);
        byte[] literalData = Streams.readAll(clear);
        clear.close();
        // check data decrypts okay
        if (encData.verify())
        {
            // parse out literal data
            PGPObjectFactory litFact = new JcaPGPObjectFactory(literalData);
            PGPLiteralData litData = (PGPLiteralData)litFact.nextObject();
            byte[] data = Streams.readAll(litData.getInputStream());
            return data;
        }
        throw new IllegalStateException("modification check failed");
    }

    public static PGPPublicKey readPublicKey(String keyText) throws IOException, PGPException {
        InputStream in=new ByteArrayInputStream(keyText.getBytes(StandardCharsets.US_ASCII));
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);

        JcaPGPPublicKeyRingCollection pgpPub = new JcaPGPPublicKeyRingCollection(in);
        in.close();

        PGPPublicKey key = null;
        Iterator<PGPPublicKeyRing> rIt = pgpPub.getKeyRings();
        while (key == null && rIt.hasNext())
        {
            PGPPublicKeyRing kRing = rIt.next();
            Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
            while (key == null && kIt.hasNext())
            {
                PGPPublicKey k = kIt.next();

                if (k.isEncryptionKey())
                {
                    key = k;
                }
            }
        }
        return key;
    }

    public static PGPSecretKey readSecretKey(InputStream input) throws IOException, PGPException
    {
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(input), new JcaKeyFingerprintCalculator());

        //
        // we just loop through the collection till we find a key suitable for encryption, in the real
        // world you would probably want to be a bit smarter about this.
        //

        Iterator keyRingIter = pgpSec.getKeyRings();
        while (keyRingIter.hasNext())
        {
            PGPSecretKeyRing keyRing = (PGPSecretKeyRing)keyRingIter.next();

            Iterator keyIter = keyRing.getSecretKeys();
            while (keyIter.hasNext())
            {
                PGPSecretKey key = (PGPSecretKey)keyIter.next();

                if (key.isSigningKey())
                {
                    return key;
                }
            }
        }

        throw new IllegalArgumentException("Can't find signing key in key ring.");
    }



}
