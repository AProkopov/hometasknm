package com.antonprokopov.hometasknm.testsframework

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.interaction.BaristaClickInteractions
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

private typealias Action = (timeout: Long) -> Unit

object I


object History {
    val last = AtomicReference<Action>()
    val beforeLast = AtomicReference<Action>()
    fun add(action: Action) {
        beforeLast.set(last.getAndSet(action))
        Log.i("TEST_DSL#", "\n ############## test ############## \n set beforeLast  \n ##################################")
    }

    fun clear() {
        last.set(null)
        beforeLast.set(null)
        Log.i("TEST_DSL#", "\n ############## test ############## \n clear beforeLast  \n ##################################")
    }
}

fun I.perform(action: Action): I {
    var timeout = 100L
    repeatIfFail(4) {
        timeout *= 2
        repeatIfFail(1) {
            action.invoke(timeout)
            Log.i("TEST_DSL#", "\n ############## test ############## \n repeatIfFail 1 \"$timeout\"  \n ##################################")
            System.out.println("\n ")
        } ?: repeatIfFail(2) {
            action.invoke(timeout)
            Log.i("TEST_DSL#", "\n ############## test ############## \n repeatIfFail 2 \"$timeout\" \n ##################################")
            System.out.println("\n ")
        } ?: repeatIfFail(3) {
            History.last.get()?.invoke(timeout)
            action.invoke(timeout)
            Log.i("TEST_DSL#", "\n ############## test ############## \n repeatIfFail 3 \"$timeout\" \n ##################################")
            System.out.println("\n ")
        }
    } ?: action.invoke(timeout)
    History.add(action)
    Log.i("TEST_DSL#", "\n ############## test ############## \n repeatIfFail 4 \"$timeout\" \n ##################################")
    return this
}

fun repeatIfFail(times: Int = 3, func: () -> Unit?): Unit? {
    repeat(times) {
        try {
            func()?.let { done -> return done }
            Log.i("TEST_DSL#", "\n ############## test ############## \n repeat \"$times\" - succeed! \n ##################################")
        } catch (t: Throwable) {
            Log.i("TEST_DSL#", "\n ############## test ############## \n repeat \"$times\" - failed! \n ##################################")
        }
    }
    return null
}

infix fun I.clickInSpinner(position: Int) = I.perform {
    Espresso.onData(Matchers.anything())
        .inAdapterView(
            Matchers.allOf(
                ViewMatchers.isAssignableFrom(AdapterView::class.java),
                ViewMatchers.isDisplayed()
            )
        )
        .atPosition(position)
        .perform(ViewActions.click())
}

infix fun I.scrollTo(text: String) = I.perform {
    try {
        BaristaScrollInteractions.scrollTo(text)
        Log.i(
            "TEST_DSL#",
            "\n ############## test ############## \n scrollTo \"$text\" - succeed! \n ##################################"
        )
    } catch (e: Exception) {
        Log.i(
            "TEST_DSL#",
            "\n ############## test ############## \n scrollTo \"$text\" - failed! \n ##################################"
        )
        throw e
    }
}

fun I.swipeDown() = I.perform {
    Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
        GeneralSwipeAction(
            Swipe.SLOW,
            GeneralLocation.CENTER,
            GeneralLocation.TOP_CENTER,
            Press.FINGER
        )
    )
}

infix fun I.scrollTo(id: Int) = I.perform {
    BaristaScrollInteractions.scrollTo(id)
}

infix fun I.safetyScrollToText(text: String) = I.perform {
    BaristaScrollInteractions.safelyScrollTo(text)
}

infix fun I.safetyScrollToId(id: Int) = I.perform {
    BaristaScrollInteractions.safelyScrollTo(id)
}

//infix fun I.clickSimple(text: String) = I.perform { timeout ->
//    waitFor(timeout) {
//        val matcher: Matcher<View> = Matchers.anyOf(
//            withText(Contains(text)),
//            withHint(Contains(text)),
//            TextInputLayoutMatchers.withTextInputLayoutHint(Contains(text))
//        )
//        try {
//            matcher.onFirstFound.onView.perform(ViewActions.click())
//            Log.i("TEST_DSL#", "\n ############## test ############## \n clickSimple \"$text\" - succeed! \n ##################################")
//        } catch (e: Exception) {
//            try {
//                Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
//                    GeneralSwipeAction(
//                        Swipe.SLOW,
//                        GeneralLocation.CENTER,
//                        GeneralLocation.TOP_CENTER,
//                        Press.FINGER
//                    )
//                )
//                matcher.onFirstFound.onView.perform(ViewActions.click())
//                Log.i("TEST_DSL#", "\n ############## test ############## \n clickSimple Swipe \"$text\" - succeed! \n ##################################")
//            } catch (e: Exception) {
//                Log.i("TEST_DSL#", "\n ############## test ############## \n clickSimple \"$text\" - failed! \n ##################################")
//                throw e
//            }
//        }
//    }
//}

infix fun I.clickContentDesc(text: String) = I.perform { timeout ->
    waitFor(timeout) {
        val matcher: Matcher<View> = Matchers.anyOf(
            ViewMatchers.withContentDescription(text)
        )
        try {
            matcher.onFirstFound.onView.perform(ViewActions.click())
            Log.i("TEST_DSL#", "\n ############## test ############## \n clickContentDesc \"$text\" - succeed! \n ##################################")
        } catch (e: Exception) {
            try {
                Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
                    GeneralSwipeAction(
                        Swipe.SLOW,
                        GeneralLocation.CENTER,
                        GeneralLocation.TOP_CENTER,
                        Press.FINGER
                    )
                )
                matcher.onFirstFound.onView.perform(ViewActions.click())
                Log.i("TEST_DSL#", "\n ############## test ############## \n clickContentDesc Swipe \"$text\" - succeed! \n ################################")
            } catch (e: Exception) {
                Log.i("TEST_DSL#", "\n ############## test ############## \n clickContentDesc \"$text\" - failed! \n ##################################")
                throw e
            }
        }
    }
}

//infix fun I.click(text: String) = I.perform { timeout ->
//    waitFor(timeout) {
//        val equalsMatcher: Matcher<View> = Matchers.allOf(
//            Matchers.anyOf(
//                ViewMatchers.withText(text),
//                ViewMatchers.withHint(text),
//                TextInputLayoutMatchers.withTextInputLayoutHint(Equals(text))
//            ), ViewMatchers.isDisplayed()
//        )
//        val matcher: Matcher<View> = Matchers.allOf(
//            Matchers.anyOf(
//                withText(Contains(text)),
//                withHint(Contains(text)),
//                TextInputLayoutMatchers.withTextInputLayoutHint(Contains(text))
//            ), ViewMatchers.isDisplayed()
//        )
//
//        try {
//            equalsMatcher.onFirstFound.onView.perform(ViewActions.click())
//            Log.i("TEST_DSL#", "\n ############## test ############## \n click equalsMatcher \"$text\" - succeed! \n ##################################")
//        } catch (t: Throwable) {
//            try {
//                matcher.onFirstFound.onView.perform(ViewActions.click())
//                Log.i("TEST_DSL#", "\n ############## test ############## \n click containsMatcher \"$text\" - succeed! \n ##################################")
//            } catch (t: Throwable) {
//                try {
//                    matcher.onSecondFound.onView.perform(ViewActions.click())
//                    Log.i("TEST_DSL#", "\n ############## test ############## \n click onSecondFound \"$text\" - succeed! \n ##################################")
//                } catch (t: Throwable) {
//                    try {
//                        Hard.performClick(text)
//                        Log.i("TEST_DSL#", "\n ############## test ############## \n click Hard \"$text\" - succeed! \n ##################################")
//                    } catch (t: Throwable) {
//                        try {
//                            Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
//                                GeneralSwipeAction(
//                                    Swipe.SLOW,
//                                    GeneralLocation.CENTER,
//                                    GeneralLocation.TOP_CENTER,
//                                    Press.FINGER
//                                )
//                            )
//                            matcher.onFirstFound.onView.perform(ViewActions.click())
//                            Log.i("TEST_DSL#", "\n ############## test ############## \n click Swipe \"$text\" - succeed! \n ##################################")
//                        } catch (t: Throwable) {
//                            Log.i("TEST_DSL#", "\n ############## test ############## \n click \"$text\" - failed! \n ##################################")
//                            I.onError()
//                            BaristaScrollInteractions.safelyScrollTo(text) //todo чтобы здесь работал contains
//                            throw t
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//}

infix fun I.longClick(text: String) = I.perform { timeout ->
    System.err.println("longClick $text")
    waitFor(timeout) {
        val equalsMatcher: Matcher<View> = Matchers.allOf(
            Matchers.anyOf(
                ViewMatchers.withText(text),
                ViewMatchers.withHint(text),
                TextInputLayoutMatchers.withTextInputLayoutHint(Equals(text))
            ),
            ViewMatchers.isDisplayed()
        )
        equalsMatcher.onFirstFound.onView.perform(ViewActions.longClick())
    }
}

infix fun I.clickFirst(text: String) = I.perform {
    click(text, 0)
}

infix fun I.clickSecond(text: String) = I.perform {
    click(text, 1)
}

infix fun I.clickThird(text: String) = I.perform {
    click(text, 2)
}

class RvInteraction(val interaction: ViewInteraction)

class TvInteraction(val interaction: ViewInteraction)

infix fun I.findRecyclerView(withId: Int): RvInteraction = RvInteraction(
    Espresso.onView(
        ViewMatchers.withId(withId)
    )
)

infix fun I.findTextView(withId: Int): TvInteraction = TvInteraction(
    Espresso.onView(
        ViewMatchers.withId(
            withId
        )
    )
)

infix fun TvInteraction.see(text: String) {
    waitFor {
        interaction.check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}

data class ViewInRoot(val id: Int, val rootId: Int)

fun withIdInRoot(id: Int, rootId: Int) = ViewInRoot(id, rootId)




infix fun I.findRecyclerView(viewInRoot: ViewInRoot): RvInteraction = RvInteraction(
    Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(viewInRoot.id), ViewMatchers.isDescendantOfA(
                ViewMatchers.withId(viewInRoot.rootId)
            )
        )
    )
)

infix fun RvInteraction.click(text: String) {
    interaction.perform(
        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            ViewMatchers.hasDescendant(ViewMatchers.withText(text)), ViewActions.click()
        )
    )
}

//infix fun RvInteraction.clickInItem(id: Int) {
//    interaction.perform(
//        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//            ViewMatchers.hasDescendant(ViewMatchers.withId(id)), clickInsideRvItem(id)
//        )
//    )
//}


infix fun RvInteraction.clickAt(position: Int) {
    interaction.perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position,
            ViewActions.click()
        )
    )
}

data class TextPosition(val position: Int, val text: String)

fun atPositionWithText(position: Int, text: String) = TextPosition(position, text)

infix fun RvInteraction.see(textPosition: TextPosition) {
    waitFor {
        interaction.check(
            ViewAssertions.matches(
                atRvPosition(
                    textPosition.position,
                    ViewMatchers.hasDescendant(ViewMatchers.withText(textPosition.text))
                )
            )
        )
    }
}

infix fun RvInteraction.dontSee(textPosition: TextPosition) {
    interaction.check(
        ViewAssertions.matches(
            Matchers.not(
                atRvPosition(
                    textPosition.position,
                    ViewMatchers.hasDescendant(ViewMatchers.withText(textPosition.text))
                )
            )
        )
    )
}

private fun I.click(text: String, position: Int) {
    waitFor {
        try {
            Espresso.onView(
                clickWithIndex(
                    ViewMatchers.withText(text), position
                )
            ).perform(ViewActions.click())
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
}

infix fun I.click(@DrawableRes drawableId: Int) = I.perform {
    System.err.println("click drawableId")
    waitFor(it) {
        val matcher: Matcher<View> = DrawableNameMatcher.withDrawable(drawableId)
        try {
            matcher.onFirstFound.onView.perform(ViewActions.click())
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
}

//infix fun I.click(button: DialogButton) = I.perform {
//    System.err.println("click DialogButton")
//    waitFor(it) {
//        try {
//            when (button) {
//                DialogButton.POSITIVE -> BaristaClickInteractions.clickOn(R.id.positive_button)
//                DialogButton.NEGATIVE -> BaristaClickInteractions.clickOn(R.id.negative_button)
//            }
//        } catch (t: Throwable) {
//            onError()
//            throw t
//        }
//
//    }
//}

//infix fun I.clickDialog(button: DialogButton) = I.perform {
//    System.err.println("click DialogButton")
//    waitFor(it) {
//        try {
//            when (button) {
//                DialogButton.POSITIVE -> BaristaDialogInteractions.clickDialogPositiveButton()
//                DialogButton.NEGATIVE -> BaristaDialogInteractions.clickDialogNegativeButton()
//            }
//        } catch (t: Throwable) {
//            onError()
//            throw t
//        }
//    }
//}

infix fun I.clickById(@IdRes viewId: Int) = I.perform {
    waitFor(it) {
        val matcher: Matcher<View> = ViewMatchers.withId(viewId)
        try {
            matcher.onFirstFound.onView.perform(ViewActions.click())
            Log.i("TEST_DSL#", "\n ############## test ############## \n clickById \"$viewId\" - succeed! \n ##################################")
        } catch (t: Throwable) {
            Log.i("TEST_DSL#", "\n ############## test ############## \n clickById \"$viewId\" - failed! \n ##################################")
            onError()
            throw t
        }
    }
}

infix fun I.clickVisibleById(@IdRes viewId: Int) = I.perform {
    waitFor(it) {
        val matcher: Matcher<View> =
            Matchers.allOf(ViewMatchers.withId(viewId), ViewMatchers.isDisplayed())
        try {
            matcher.onFirstFound.onView.perform(ViewActions.click())
            Log.i("TEST_DSL#", "\n ############## test ############## \n clickVisibleById \"$viewId\" - succeed! \n ##################################")
        } catch (t: Throwable) {
            Log.i("TEST_DSL#", "\n ############## test ############## \n clickVisibleById \"$viewId\" - failed! \n ##################################")
            onError()
            throw t
        }
    }
}

infix fun I.toggleCheckedById(@IdRes viewId: Int) = I.perform {
    System.err.println("toggleCheckedById $viewId")
    waitFor(it) {
        val matcher: Matcher<View> = ViewMatchers.withId(viewId)
        try {
            matcher.onFirstFound.onView.perform(toggleChecked())
        } catch (t: Throwable) {
            try {
                Espresso.onView(ViewMatchers.withId(R.id.content)).perform(
                    GeneralSwipeAction(
                        Swipe.SLOW,
                        GeneralLocation.CENTER,
                        GeneralLocation.TOP_CENTER,
                        Press.FINGER
                    )
                )
                matcher.onFirstFound.onView.perform(toggleChecked())
            } catch (t: Throwable) {
                throw t
            }
        }
    }
}

infix fun I.see(text: String): I {
    waitFor {
        val equalsMatcher: Matcher<View> = Matchers.allOf(
            Matchers.anyOf(
                ViewMatchers.withText(text),
                ViewMatchers.withHint(text),
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
            Log.i("TEST_DSL#", "\n ############## test ############## \n see equalsMatcher \"$text\" - succeed! \n ##################################")
        } catch (e: Exception) {
            try {
                try {
                    matcher.onFirstFound.onView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    Log.i("TEST_DSL#", "\n ############## test ############## \n see containsMatcher \"$text\" - succeed! \n ##################################")
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
                    Log.i("TEST_DSL#", "\n ############## test ############## \n see Swipe \"$text\" - succeed! \n ##################################")
                }
            } catch (t: Throwable) {
                try {
                    matcher.onSecondFound.onView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    Log.i("TEST_DSL#", "\n ############## test ############## \n see onSecondFound \"$text\" - succeed! \n ##################################")
                } catch (t: Throwable) {
                    Log.i("TEST_DSL#", "\n ############## test ############## \n see \"$text\" - failed! \n ##################################")
                    onError()
                    throw t
                }
            }
        }
    }
    return this
}

infix fun I.seeFocused(text: String): I {
    waitFor {
        val equalsMatcher: Matcher<View> = Matchers.allOf(
            Matchers.anyOf(
                ViewMatchers.withText(text),
                ViewMatchers.withHint(text),
                TextInputLayoutMatchers.withTextInputLayoutHint(Equals(text))
            ),
            ViewMatchers.isDisplayed(),
            ViewMatchers.hasFocus()
        )

        equalsMatcher.onView.check(ViewAssertions.matches(Matchers.anything()))
    }
    return this
}

infix fun I.see(@DrawableRes drawableId: Int): I {
    waitFor {
        try {
            val matcher: Matcher<View> = DrawableNameMatcher.withDrawable(drawableId)
            Espresso.onView(matcher).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
    return this
}

infix fun I.seeToast(text: String) {
    waitFor {
        Espresso.onView(withText(Contains(text)))
            .inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

infix fun I.waitMillis(time: Long) {
    BaristaSleepInteractions.sleep(time)

}

infix fun I.dontSee(text: String): I {
    waitFor {
        try {
            try {
                BaristaVisibilityAssertions.assertNotDisplayed(text)
            } catch (t: Throwable) {
                Espresso.onView(ViewMatchers.withText(text)).check(ViewAssertions.doesNotExist())
            }
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
    return this
}

infix fun I.dontSeeById(@IdRes viewId: Int): I {
    System.err.println("checking that view with id $viewId is not displayed")
    waitFor {
        try {
            val matcher: Matcher<View> = ViewMatchers.withId(viewId)
            Espresso.onView(matcher).check(ViewAssertions.doesNotExist())
        } catch (t: Throwable) {
            throw t
        }
    }
    return this
}

infix fun I.dontSee(@DrawableRes drawableId: Int): I {
    waitFor {
        try {
            val matcher: Matcher<View> = DrawableNameMatcher.withDrawable(drawableId)
            try {
                Espresso.onView(matcher)
                    .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())))
            } catch (t: Throwable) {
                Espresso.onView(matcher).check(ViewAssertions.doesNotExist())
            }
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
    return this
}

//infix fun I.type(text: String): I {
//    waitFor {
//        try {
//            Matchers.allOf(
//                ViewMatchers.isAssignableFrom(EditText::class.java),
//                ViewMatchers.hasFocus()
//            ).onFirstFound.onView.perform(
//                ViewActions.actionWithAssertions(ReplaceTextActionCustom(text))
//            )
//            BaristaKeyboardInteractions.closeKeyboard()
//            Log.i("TEST_DSL#", "\n ############## test ############## \n type \"$text\" - succeed! \n ##################################")
//        } catch (t: Throwable) {
//            throw t
//            Log.i("TEST_DSL#", "\n ############## test ############## \n type \"$text\" - failed! \n ##################################")
//        }
//    }
//    return this
//}

infix fun I.click(type: PressAction) = I.perform {
    System.err.println("click PressAction")
    waitFor(it) {
        try {
            when (type) {
                KEYBOARD_IME -> I.onPressIme()
                BACK -> BaristaClickInteractions.clickBack()
            }

        } catch (t: Throwable) {
            I.onError()
            throw t
        }
    }
}

//fun I.clickOnRecyclerViewItem(rvId: Int, itemPosition: Int) {
//    waitFor {
//        Espresso.onView(ViewMatchers.withId(rvId)).perform(RecyclerViewActions.actionOnItemAtPosition<ListOfferViewHolder>(
//            itemPosition,
//            ViewActions.click()
//        ))
//    }
//}

private object Sequence3of4 {
    val state = AtomicInteger()
    fun nextBoolean(): Boolean = state.incrementAndGet() % 4 != 3
}


private fun I.onError() {
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
    Log.i("TEST_DSL#", "\n ############## test ############## \n onError \"$Sequence3of4\" - succeed! \n ##################################")
}

private fun I.onPressIme() {
    Matchers.allOf(
        ViewMatchers.supportsInputMethods(),
        ViewMatchers.hasFocus()
    ).onFirstFound.onView.perform(ViewActions.pressImeActionButton())
}


enum class DialogButton {
    POSITIVE, NEGATIVE
}

sealed class PressAction
object KEYBOARD_IME : PressAction()
object BACK : PressAction()


infix fun I.click(matcher: Matcher<View>) = I.perform {
    System.err.println("click Matcher")
    waitFor(it) {
        try {
            matcher.onView.perform(ViewActions.click())
        } catch (t: Throwable) {
            onError()
            throw t
        }
    }
}


class ReplaceTextActionCustom(private val stringToBeSet: String) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf<View>(
            ViewMatchers.hasFocus(),
            ViewMatchers.isAssignableFrom(EditText::class.java)
        )
    }

    override fun perform(uiController: UiController, view: View) {
        (view as EditText).setText(stringToBeSet)
    }

    override fun getDescription(): String = "replace text"

}

//object SuccessfulResponse {
//    var dispatcher: SimpleUrlDispatcher? = null
//}
//
//object FailureResponse {
//    var dispatcher: SimpleUrlDispatcher? = null
//}

//class MockNetworkBuilder(
//    private val dispatcher: SimpleUrlDispatcher,
//    private val mockResponseBuilder: MockResponseMatcher.Builder,
//    private val successful: Boolean
//) {
//
//    private var responseBody: String? = null
//    private var responseCode: Int? = null
//
//    fun buildAndSet() {
//        if (successful)
//            dispatcher.addMatcher(mockResponseBuilder.createSuccessful(responseBody, responseCode))
//        else
//            dispatcher.addMatcher(mockResponseBuilder.createFailure(responseBody, responseCode))
//    }
//
//    infix fun withBody(responseBody: String): MockNetworkBuilder {
//        this.responseBody = responseBody
//        buildAndSet()
//        return this
//    }
//
//    infix fun withCode(responseCode: Int): MockNetworkBuilder {
//        this.responseCode = responseCode
//        buildAndSet()
//        return this
//    }
//}

//infix fun SuccessfulResponse.wherePathEqualsTo(path: String): MockNetworkBuilder {
//    val currentDispatcher = dispatcher
//    requireNotNull(currentDispatcher) { "dispatcher is not set for object SuccessfulResponse" }
//    return MockNetworkBuilder(
//        currentDispatcher,
//        MockResponseMatcher.wherePathEqualsTo(path, null),
//        true
//    ).apply { buildAndSet() }
//}

//infix fun SuccessfulResponse.wherePathContains(path: String): MockNetworkBuilder {
//    val currentDispatcher = dispatcher
//    requireNotNull(currentDispatcher) { "dispatcher is not set for object SuccessfulResponse" }
//    return MockNetworkBuilder(
//        currentDispatcher,
//        MockResponseMatcher.wherePathContains(path, null),
//        true
//    ).apply { buildAndSet() }
//}
//
//infix fun FailureResponse.wherePathEqualsTo(path: String): MockNetworkBuilder {
//    val currentDispatcher = dispatcher
//    requireNotNull(currentDispatcher) { "dispatcher is not set for object FailureResponse" }
//    return MockNetworkBuilder(
//        currentDispatcher,
//        MockResponseMatcher.wherePathEqualsTo(path, null),
//        false
//    ).apply { buildAndSet() }
//}
//
//infix fun FailureResponse.wherePathContains(path: String): MockNetworkBuilder {
//    val currentDispatcher = dispatcher
//    requireNotNull(currentDispatcher) { "dispatcher is not set for object FailureResponse" }
//    return MockNetworkBuilder(
//        currentDispatcher,
//        MockResponseMatcher.wherePathContains(path, null),
//        false
//    ).apply { buildAndSet() }
//}
//
//infix fun I.swipeRightById(@IdRes viewId: Int) = I.perform {
//    waitFor(it) {
//        val matcher: Matcher<View> = ViewMatchers.withId(viewId)
//        try {
//            matcher.onFirstFound.onView.perform(ViewActions.swipeRight())
//        } catch (t: Throwable) {
//            onError()
//            throw t
//        }
//    }
//}
//
//infix fun I.swipeLeftById(@IdRes viewId: Int) = I.perform {
//    waitFor(it) {
//        val matcher: Matcher<View> = ViewMatchers.withId(viewId)
//        try {
//            matcher.onFirstFound.onView.perform(ViewActions.swipeLeft())
//        } catch (t: Throwable) {
//            onError()
//            throw t
//        }
//    }
//}