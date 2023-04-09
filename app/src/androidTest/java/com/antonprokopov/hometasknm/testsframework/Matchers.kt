package com.antonprokopov.hometasknm.testsframework

import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.atomic.AtomicInteger

fun indexed(matcher: Matcher<View>, indexFrom1: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        var count = AtomicInteger(indexFrom1)
        override fun describeTo(description: Description) {
            description.appendText("index $indexFrom1 of ")
            matcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            if (!matcher.matches(view)) return false

            if (count.decrementAndGet() == 0)
                return true
            return false
        }
    }
}
