package com.antonprokopov.hometasknm.testsframework

import android.R
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.util.concurrent.atomic.AtomicInteger

object I

infix fun I.see(text: String): I {
    waitFor {
        val equalsMatcher: Matcher<View> = Matchers.allOf(
            Matchers.anyOf(
                withText(text),
                withHint(text),
                TextInputLayoutMatchers.withTextInputLayoutHint(Equals(text))
            ),
            ViewMatchers.isDisplayed()
        )
        val matcher: Matcher<View> = Matchers.anyOf(
            withText(Contains(text)),
            withHint(Contains(text)),
            TextInputLayoutMatchers.withTextInputLayoutHint(Contains(text))
        )
        try {
            equalsMatcher.onFirstFound.onView.check(ViewAssertions.matches(Matchers.anything()))
        } catch (e: Exception) {
            try {
                try {
                    matcher.onFirstFound.onView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                } catch (e: Exception) {
                    Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
                        GeneralSwipeAction(
                            Swipe.SLOW,
                            GeneralLocation.CENTER,
                            GeneralLocation.TOP_CENTER,
                            Press.FINGER
                        )
                    )
                    matcher.onFirstFound.onView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                }
            } catch (t: Throwable) {
                try {
                    matcher.onSecondFound.onView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                } catch (t: Throwable) {
                    onError()
                    throw t
                }
            }
        }
    }
    return this
}

infix fun I.dontSee(text: String): I {
    waitFor {
        try {
            try {
                BaristaVisibilityAssertions.assertNotDisplayed(text)
            } catch (t: Throwable) {
                Espresso.onView(withText(text)).check(ViewAssertions.doesNotExist())
            }
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
    return this
}

private object Sequence3of4 {
    val state = AtomicInteger()
    fun nextBoolean(): Boolean = state.incrementAndGet() % 4 != 3
}

private fun onError() {
    BaristaKeyboardInteractions.closeKeyboard()
    Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
        if (Sequence3of4.nextBoolean())
            GeneralSwipeAction(
                Swipe.SLOW,
                GeneralLocation.CENTER,
                GeneralLocation.TOP_CENTER,
                Press.FINGER
            )
        else
            ViewActions.swipeDown()
    )
}