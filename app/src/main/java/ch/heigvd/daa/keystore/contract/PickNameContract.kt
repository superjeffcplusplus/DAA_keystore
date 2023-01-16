/**
 *
 * @author Damien Maier, Vincent Peer, Jean-François Pasche
 */

package ch.heigvd.daa.keystore.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ch.heigvd.daa.keystore.EditName

/**
 * Asks the user to edit his name.
 * Input : the user name to edit
 * Output : the name chosen by the user, or null if the user does not provide a name.
 */
class PickNameContract : ActivityResultContract<String?, String?>() {
    // Run the EditName activity and give the current name to it
    override fun createIntent(context: Context, input: String?) =
        Intent(context, EditName::class.java).apply {
            putExtra(EditName.ASK_FOR_NAME_RESULT_KEY, input)
        }

    // Receive the response from the EditName activity and collect the name
    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.getStringExtra(EditName.ASK_FOR_NAME_RESULT_KEY)
    }
}