/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/**
 * Displays a personalized welcome message and allows the user to edit his name.
 */
class MainActivity : AppCompatActivity() {
    private val keystore = EncryptorSym("secret")
    private lateinit var encryptedMsg : EncryptedFileBox
    private lateinit var decryptedMsg : ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textToEncrypt = findViewById<EditText>(R.id.msg_to_encrypt)

        findViewById<Button>(R.id.encrypt_button).setOnClickListener {
            encryptedMsg = keystore.encrypt(textToEncrypt.text.toString().encodeToByteArray())
            findViewById<TextView>(R.id.encrypted_msg).text =
                "Encrypted message : ${encryptedMsg.cipherText}"
        }

        findViewById<Button>(R.id.decrypt_button).setOnClickListener {
            decryptedMsg = keystore.decrypt(
                encryptedMsg
            )
            findViewById<TextView>(R.id.decrypted_msg).text =
                "Decrypted message : " + decryptedMsg.decodeToString()
        }
    }
}
