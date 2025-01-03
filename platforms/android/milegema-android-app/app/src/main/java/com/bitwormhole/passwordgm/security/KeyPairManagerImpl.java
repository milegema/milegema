package com.bitwormhole.passwordgm.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.data.ids.UserAlias;
import com.bitwormhole.passwordgm.utils.Errors;
import com.bitwormhole.passwordgm.utils.HashUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class KeyPairManagerImpl implements KeyPairManager {


    @Override
    public KeyPairHolder get(KeyAlias alias) {
        return new MyKeyHolder(alias);
    }

    @Override
    public KeyPairHolder get(UserAlias alias) {
        KeyAlias ka = KeySelector.alias(alias);
        return this.get(ka);
    }

    @Override
    public KeyPairHolder getRoot() {
        return this.get(new KeyAlias("root"));
    }

    @Override
    public KeyAlias[] listAliases() {
        List<KeyAlias> dst = new ArrayList<>();
        try {
            KeyStore store = getKeyStore();
            Enumeration<String> src = store.aliases();
            while (src.hasMoreElements()) {
                String al = src.nextElement();
                dst.add(new KeyAlias(al));
            }
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                 IOException e) {
            Errors.handle(null, e);
        }
        return dst.toArray(new KeyAlias[0]);
    }


    private static class MyKeyHolder implements KeyPairHolder {

        private final KeyAlias mAlias;
        private KeyPair mKeyPair;
        private KeyFingerprint mFinger;

        public MyKeyHolder(KeyAlias alias) {
            this.mAlias = new KeyAlias(alias);
        }

        @Override
        public KeyAlias alias() {
            return this.mAlias;
        }

        @Override
        public KeyFingerprint fingerprint() {
            KeyFingerprint fp = mFinger;
            if (fp == null) {
                fp = loadFingerprint(this);
                mFinger = fp;
            }
            return fp;
        }

        @Override
        public boolean create() {
            long now = System.currentTimeMillis();
            Date t1 = new Date(now - 1000);
            Date t2 = new Date(now + (1000L * 99L * 365 * 24 * 3600));
            int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
            int keySize = 1024 * 4;
            try {
                String alias = this.mAlias.getValue();
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(alias, purposes)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                        .setCertificateNotBefore(t1).setCertificateNotAfter(t2)
                        .setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC, KeyProperties.BLOCK_MODE_CTR)
                        .setKeySize(keySize)
                        .build());
                KeyPair kp = kpg.generateKeyPair();
                if (kp == null) {
                    throw new RuntimeException("result of generateKeyPair() is null");
                }
                return true;
            } catch (NoSuchProviderException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean delete() {
            try {
                String alias = this.mAlias.getValue();
                KeyStore store = getKeyStore();
                store.deleteEntry(alias);
                return true;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                // throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public boolean exists() {
            try {
                String alias = this.mAlias.getValue();
                KeyStore store = getKeyStore();
                return store.containsAlias(alias);
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                //  throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public KeyPair fetch() {
            KeyPair kp = this.mKeyPair;
            if (kp != null) {
                return kp;
            }
            try {
                String alias = this.mAlias.getValue();
                KeyStore store = getKeyStore();
                KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) store.getEntry(alias, null);
                PrivateKey pri = entry.getPrivateKey();
                PublicKey pub = entry.getCertificate().getPublicKey();
                kp = new KeyPair(pub, pri);
                this.mKeyPair = kp;
                return kp;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     UnrecoverableEntryException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static KeyFingerprint loadFingerprint(MyKeyHolder h) {
        PublicKey pub = h.fetch().getPublic();
        byte[] bin = pub.getEncoded();
        byte[] sum = HashUtils.sum(bin, HashUtils.SHA256);
        return new KeyFingerprint(sum);
    }

    private static KeyStore getKeyStore() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore store = KeyStore.getInstance("AndroidKeyStore");
        store.load(null);
        return store;
    }
}
