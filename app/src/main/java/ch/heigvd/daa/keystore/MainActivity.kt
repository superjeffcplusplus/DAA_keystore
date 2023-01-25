/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import java.util.Base64


/**
 * Displays a personalized welcome message and allows the user to edit his name.
 */
class MainActivity : AppCompatActivity() {
    private val encryptor = EncryptorSym("secret")
    private lateinit var encryptedMsg: EncryptedFileBox
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textToEncrypt = findViewById<EditText>(R.id.msg_to_encrypt)
        val cipherTextView = findViewById<TextView>(R.id.encrypted_msg)
        val cipherTextIvView = findViewById<TextView>(R.id.encrypted_msg_iv)

        findViewById<Button>(R.id.encrypt_button).setOnClickListener {
            encryptedMsg = encryptor.encrypt(textToEncrypt.text.toString().encodeToByteArray())
            cipherTextView.text = Base64.getEncoder().encodeToString(encryptedMsg.cipherText)
            cipherTextIvView.text = Base64.getEncoder().encodeToString(encryptedMsg.iv)
        }

        findViewById<Button>(R.id.decrypt_button).setOnClickListener {
            val cipherText: String = cipherTextView.text.toString()
            val iv = cipherTextIvView.text.toString()
            if (iv.isNotEmpty() && cipherText.isNotEmpty()) {
                val cipherTextDec = Base64.getDecoder().decode(cipherText)
                val ivDec = Base64.getDecoder().decode(iv)
                try {
                    val decryptedMsg = encryptor.decrypt(
                        EncryptedFileBox(cipherText = cipherTextDec, iv = ivDec)
                    )
                    findViewById<TextView>(R.id.decrypted_msg).text = decryptedMsg.decodeToString()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this, e.message, LENGTH_LONG).show()
                }
            }
        }
    }
}
