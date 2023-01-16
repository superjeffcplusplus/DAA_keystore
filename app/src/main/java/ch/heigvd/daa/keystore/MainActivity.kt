/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.daa.keystore.contract.PickNameContract

private const val SAVED_STATE_NAME_KEY = "NAME"

/**
 * Displays a personalized welcome message and allows the user to edit his name.
 */
class MainActivity : AppCompatActivity() {

    private var name: String? = null

    // Collect the username through the contract with the EditName class
    private val manageUsername = registerForActivityResult(PickNameContract()) {
        if (it != null) {
            name = it
            updateWelcomeText()
        }

    }

    /**
     * Displays a layout with
     * - A welcome text based on the user name
     * - A button that allows the user to edit his name
     * If a previous activity state was saved, the user name is restored from it.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = savedInstanceState?.getString(SAVED_STATE_NAME_KEY)

        setContentView(R.layout.activity_main)

        updateWelcomeText()

        // When the button is pressed, we retrieve the user name using the contract linked to manageUsername
        findViewById<Button>(R.id.edit_button).setOnClickListener {
            manageUsername.launch(name)
        }
    }

    /**
     * Updates the welcome text displayed according to the user name.
     * If the name is null, a generic welcome text is displayed.
     * Otherwise, a personalized welcome text is displayed.
     */
    private fun updateWelcomeText() {
        findViewById<TextView>(R.id.welcome).text =
            if (name == null) getString(R.string.ask_for_name)
            else getString(R.string.welcome_user, name)
    }

    /**
     * Saves the user name
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_STATE_NAME_KEY, name)
    }

}
