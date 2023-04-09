package com.antonprokopov.hometasknm.testsframework

import android.view.View
import androidx.appcompat.widget.WithHint
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object TextInputLayoutMatchers {

    fun withTextInputLayoutHint(stringMatcher: Matcher<String>): Matcher<View> {
        return object : BaseMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.let {
                    it.appendText("with TextInputLayout hint: ")
                    stringMatcher.describeTo(description)
                }
            }

            override fun matches(item: Any?): Boolean {
                return item is WithHint && stringMatcher.matches(item.hint?.toString())
            }
        }
    }
}