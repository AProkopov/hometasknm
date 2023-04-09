package com.antonprokopov.hometasknm.testsframework

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Checkable
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.SubstringMatcher


fun clickOnInParent(@IdRes parentLayout: Int, @StringRes childText: Int) {
    clickOnMatcher(childOf(ViewMatchers.withId(parentLayout), ViewMatchers.withText(childText)))
}


fun clickOnTextFirst(text: String) {
    clickOnMatcher(indexed(ViewMatchers.withText(text), 1))
}


fun clickOnMatcher(matcher: Matcher<View>) {
    //TODO Barista.performClickTypeOnMatcher(matcher, click())
    Espresso.onView(matcher).perform(ViewActions.click())
}

fun checkOnInParent(@IdRes parentLayout: Int, @StringRes childText: Int) {
    checkOnMatcher(childOf(ViewMatchers.withId(parentLayout), ViewMatchers.withText(childText)))
}

fun checkOnMatcher(matcher: Matcher<View>) {
    Espresso.onView(matcher).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
}

infix fun Matcher<View>.write(text: String) {
    Espresso.onView(this).perform(ViewActions.replaceText(text))
}

fun waitFor(timeout: Long = 10_000, func: () -> Unit) {
    val messages = mutableListOf<String>()

    val start = SystemClock.uptimeMillis()
    var iteration = -1
    while (true) {
        iteration++
        try {
            func.invoke()
            return
        } catch (t: Throwable) {
            val now = SystemClock.uptimeMillis()
            //todo manage constants
            if (now - start > timeout) {
                messages.forEach {
                    Log.e("WAIT_ESPRESSO", it) //todo print to test console
                }
                throw t
            }

            messages.add(t.message ?: "unknown")
            BaristaSleepInteractions.sleep(100)
        }
    }
}

fun pressBack(times: Int) {
    for (i in 1..times) {
        Espresso.pressBack()
    }
}

fun toggleChecked(): ViewAction {
    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return object : BaseMatcher<View>() {
                override fun matches(item: Any): Boolean {
                    return isA(Checkable::class.java).matches(item)
                }

                override fun describeMismatch(
                    item: Any,
                    mismatchDescription: Description
                ) {
                }

                override fun describeTo(description: Description) {}
            }
        }

        override fun getDescription(): String? = null

        override fun perform(uiController: UiController, view: View) {
            click().perform(uiController, view)
        }
    }
}

internal val Matcher<View>.onFirstFound get() = indexed(this, 1)
internal val Matcher<View>.onSecondFound get() = indexed(this, 2)
internal val Matcher<View>.onView get() = Espresso.onView(this)


class Contains(substring: String) : SubstringMatcher(substring) {
    override fun evalSubstringOf(s: String): Boolean =
        s.toLowerCase().contains(substring.toLowerCase())

    override fun relationship(): String = "containing (ignoring case)"
}

class Equals(substring: String) : SubstringMatcher(substring) {
    override fun evalSubstringOf(s: String) = s == substring

    override fun relationship() = "equals"
}