package com.antonprokopov.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.antonprokopov.core.R

class EmptyStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class EmptyState {
        EMPTY,
        ERROR
    }

    private val emptyStateTextView: TextView by lazy { findViewById(R.id.empty_state_title) }
    private val emptyStateImage: ImageView by lazy { findViewById(R.id.image_empty) }
    private val retryBtn: Button by lazy { findViewById(R.id.btn_retry) }

    init {
        View.inflate(context, R.layout.core_empty_state_layout, this)
    }

    fun setState(
        state: EmptyState,
        retryCallback: () -> Unit,
        @StringRes emptyStateTextResId: Int ? = null,
        @DrawableRes emptyImageResId: Int = R.drawable.core_ic_no_photo
    ) {
        val emptyStateDrawable = resources.getDrawable(emptyImageResId, null)

        when(state) {
            EmptyState.EMPTY -> {
                emptyStateTextResId?.let { emptyStateTextView.setText(it) }
            }
            else -> {
                emptyStateTextView.setText(R.string.core_error_text)
            }
        }

        retryBtn.setOnClickListener { retryCallback.invoke() }

        emptyStateImage.setImageDrawable(emptyStateDrawable)
    }
}