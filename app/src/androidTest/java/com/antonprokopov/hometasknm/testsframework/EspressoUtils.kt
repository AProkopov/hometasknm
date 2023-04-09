package com.antonprokopov.hometasknm.testsframework

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.hamcrest.Matcher
import org.hamcrest.core.SubstringMatcher

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
            if (now - start > timeout) {
                messages.forEach {
                }
                throw t
            }

            messages.add(t.message ?: "unknown")
            BaristaSleepInteractions.sleep(100)
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