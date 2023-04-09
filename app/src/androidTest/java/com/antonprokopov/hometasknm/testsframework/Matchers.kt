package com.antonprokopov.hometasknm.testsframework

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.atomic.AtomicInteger

fun <T> first(matcher: Matcher<T>): Matcher<T> {
    return object : BaseMatcher<T>() {
        internal var isFirst = true

        override fun matches(item: Any): Boolean {
            if (isFirst && matcher.matches(item)) {
                isFirst = false
                return true
            }

            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("should return first matching item")
        }
    }
}

fun clickWithIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        var curIndex = 0

        override fun describeTo(description: Description?) {
            description?.appendText("with index: ")
            description?.appendValue(index)
            matcher.describeTo(description)
        }

        override fun matchesSafely(item: View?): Boolean {
            return matcher.matches(item) && curIndex++ == index
        }
    }
}

fun childOf(
    parentMatcher: Matcher<View>,
    childMatcher: Matcher<View>,
    maxDeep: Int = 10
): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("child of parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            if (!childMatcher.matches(view)) return false

            var v = view
            for (i in 1..maxDeep) {
                val parent = v.parent as? ViewGroup ?: return false
                if (parentMatcher.matches(parent))
                    return true
                v = parent
            }
            return false
        }
    }
}

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

fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(drawableResId)
        }

        override fun matchesSafely(target: View?): Boolean {
            if (target !is ImageView) {
                return false
            }
            if (drawableResId < 0) {
                return target.drawable == null
            }
            val expectedDrawable = target.context.getDrawable(drawableResId)
                ?: return false

            val bitmap = (target.drawable as BitmapDrawable).bitmap
            val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
            return bitmap.sameAs(otherBitmap)
        }
    }
}


class DrawableNameMatcher private constructor(@DrawableRes private val expectedDrawableRes: Int) :
    TypeSafeMatcher<View>(View::class.java) {

    companion object {
        @JvmStatic
        fun withDrawable(@DrawableRes resourceId: Int) = DrawableNameMatcher(resourceId)
    }

    private var expectedBitmap: Bitmap? = null


    override fun matchesSafely(target: View): Boolean {
        if (expectedBitmap == null) {
            val drawable = AppCompatResources.getDrawable(target.context, expectedDrawableRes)
            drawable?.let {
                expectedBitmap = drawableToBitmap(drawable)
            } ?: return false
        }
        if (target !is ImageView) {
            return false
        }
        val imageView = target
        if (imageView.drawable == null) {
            return false
        }

        val viewBitmap = drawableToBitmap(imageView.drawable)
        return compareBitmaps(viewBitmap, expectedBitmap)
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedDrawableRes)
    }
}

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(root: Root): Boolean {
        val type = root.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.TYPE_TOAST || type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            if (windowToken == appToken) {
                return true
            }
        }
        return false
    }
}

fun atRvPosition(
    position: Int,
    itemMatcher: Matcher<View>
): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}
