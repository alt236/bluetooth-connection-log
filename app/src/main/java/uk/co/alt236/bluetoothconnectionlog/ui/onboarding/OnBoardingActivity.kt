/*
 * Copyright 2016 Alexandros Schillings
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.alt236.bluetoothconnectionlog.ui.onboarding

import android.os.Bundle
import android.util.Log
import io.github.dreierf.materialintroscreen.MaterialIntroActivity
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.ui.onboarding.pagefactory.PageFactory

class OnBoardingActivity : MaterialIntroActivity() {

    lateinit var onboardingStatusStore: OnboardingStatusStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pages = PageFactory(this).getPages()
        Log.d(TAG, "Number of pages: " + pages.size)
        onboardingStatusStore = OnboardingStatusStore(this)

        if (pages.isEmpty()) {
            finish()
        } else {
            for (page in pages) {
                addSlide(page)
            }
        }
    }

    override fun onFinish() {
        super.onFinish()
        onboardingStatusStore.setOnBoardedVersion(BuildConfig.VERSION_CODE.toLong())
    }

    private companion object {
        private val TAG = OnBoardingActivity::class.java.simpleName
    }
}
