package uk.co.alt236.bluetoothconnectionlog.ui.onboarding.pagefactory

import android.content.Context
import io.github.dreierf.materialintroscreen.SlideFragment
import io.github.dreierf.materialintroscreen.SlideFragmentBuilder
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.permissions.AndroidPermissionChecker
import java.util.*

class PageFactory(private val context: Context) {

    fun getPages(): List<SlideFragment> {
        val retVal = ArrayList<SlideFragment>()

        addAndroidPermissionsPage(retVal)

        if (retVal.isNotEmpty()) {
            addExplanationPage(retVal)
        }

        return retVal
    }


    private fun addExplanationPage(list: MutableList<SlideFragment>) {
        val title = context.getString(R.string.app_name)

        val description = context.getString(R.string.message_onboarding_explanation)

        val fragment = SlideFragmentBuilder()
            .backgroundColor(R.color.colorPrimary)
            .buttonsColor(R.color.colorAccent)
            .title(title)
            .description(description)
            .image(R.drawable.ic_onboarding_app_logo)
            .build()

        // Explanation goes first
        list.add(0, fragment)
    }

    private fun addAndroidPermissionsPage(list: MutableList<SlideFragment>) {
        val logic = AndroidPermissionChecker(context)

        val title = context.getString(R.string.slide_android_title)
        val description = context.getString(R.string.slide_android_permissions)

        val fragment = SlideFragmentBuilder()
            .backgroundColor(R.color.colorPrimary)
            .buttonsColor(R.color.colorAccent)
            .title(title)
            .description(description)
            .image(R.drawable.ic_onboarding_location)
            .possiblePermissions(logic.getRequiredPermissions())
            .build()

        list.add(fragment)

    }
}
