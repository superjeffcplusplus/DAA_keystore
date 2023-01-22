/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import ch.heigvd.daa.keystore.EncryptionUtils
import org.w3c.dom.Text
import java.io.FileInputStream


/**
 * Displays a personalized welcome message and allows the user to edit his name.
 */
class MainActivity : AppCompatActivity() {
    private val encryptionUtils = EncryptionUtils()
    private var messageToEncrypt = "message to encrypt"
    private var messageToDecrypt: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val message = findViewById<TextView>(R.id.msg_to_encrypt)
        message.text = messageToEncrypt

        findViewById<Button>(R.id.encrypt_button).setOnClickListener {
            val bytes = messageToEncrypt.encodeToByteArray()
            val file = File(filesDir, "secret.txt")
            if(!file.exists()) {
                file.createNewFile()
            }
            val fos = FileOutputStream(file)

            messageToDecrypt = encryptionUtils.encrypt(bytes, fos).decodeToString()
            findViewById<TextView>(R.id.encrypted_msg).text = messageToEncrypt
        }

        findViewById<Button>(R.id.decrypt_button).setOnClickListener {
            val file = File(filesDir, "secret.txt")
            messageToEncrypt = encryptionUtils.decrypt(
                inputStream = FileInputStream(file)
            ).decodeToString()

            findViewById<TextView>(R.id.decrypted_msg).text = messageToEncrypt

        }

    }
}
