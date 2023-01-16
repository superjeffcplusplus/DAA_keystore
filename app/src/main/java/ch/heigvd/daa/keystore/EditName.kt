/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

/**
 * Asks the user to edit his name.
 *
 * An initial name value can be provided via an intent with the `ASK_FOR_NAME_RESULT_KEY` key
 *
 * If the user enters a name, the result intent has status code `RESULT_OK`
 * and the new name is provided with the `ASK_FOR_NAME_RESULT_KEY` key
 */
class EditName : AppCompatActivity() {

    /**
     * Displays
     * - A text field to edit the name
     * - A validation button that ends the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        val textField = findViewById<EditText>(R.id.edit_name)

        // Set the username in the text field
        textField.setText(intent?.getStringExtra(ASK_FOR_NAME_RESULT_KEY))

        // When the button is pressed, we put the content of the text field in the intent
        // and we finish the activity
        findViewById<Button>(R.id.save_button).setOnClickListener {
            val data = Intent()

            data.putExtra(ASK_FOR_NAME_RESULT_KEY, textField.text.toString())
            setResult(RESULT_OK, data)

            finish()
        }
    }

    // Key for the name in the intent
    companion object {
        const val ASK_FOR_NAME_RESULT_KEY = "NAME_KEY"
    }
}