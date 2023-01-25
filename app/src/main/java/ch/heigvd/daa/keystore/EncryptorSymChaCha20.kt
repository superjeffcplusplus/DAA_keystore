package ch.heigvd.daa.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.spec.AlgorithmParameterSpec
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
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setEncryptionPaddings(PADDING)
            .build()
        keyGenerator.init(256)
        return keyGenerator.generateKey()
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

//data class EncryptedFileBox(val iv: ByteArray, val cipherText: ByteArray) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as EncryptedFileBox
//
//        if (!iv.contentEquals(other.iv)) return false
//        if (!cipherText.contentEquals(other.cipherText)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = iv.contentHashCode()
//        result = 31 * result + cipherText.contentHashCode()
//        return result
//    }
//}
