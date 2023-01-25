package ch.heigvd.daa.keystore

import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class EncryptorSymChaCha20(private val keyAlias: String) {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val secretKey: SecretKey
    init {
        val existingKey = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        secretKey = existingKey?.secretKey ?: generateKey()
    }

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM, "AndroidOpenSSL")
        // Set key length to 256 bits
        keyGenerator.init(256)
        val key =  keyGenerator.generateKey()
        // Manually saves the key in keystore
        // Throws java.lang.IllegalArgumentException: Unsupported secret key algorithm: ChaCha20
//        keyStore.setEntry(
//            keyAlias,
//            KeyStore.SecretKeyEntry(key),
//            KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).build()
//        )
        return key
    }

    fun encrypt(toEncrypt: ByteArray): EncryptedFileBox {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryption = cipher.doFinal(toEncrypt)
        return EncryptedFileBox(iv, encryption)
    }

    fun decrypt(toDecrypt: EncryptedFileBox): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        // Tag is 128 bits length (default), GCMParameterSpec works also with ChaCha20
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, toDecrypt.iv))
        return cipher.doFinal(toDecrypt.cipherText)
    }

    companion object {
        private const val ALGORITHM = "ChaCha20"
        private const val BLOCK_MODE = "Poly1305"
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

}

