package id.co.edtslib.slidingbutton.arrow

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.google.android.material.card.MaterialCardView
import id.co.edtslib.slidingbutton.R

class ArrowView: FrameLayout {
    var delegate: ArrowDelegate? = null
    private var lastX = 0.0f
    private var flagMoving = false
    internal var maxWidth: Float = 1000.0f
    internal var locked = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.v_arrow, this)
    }

    @Suppress("RedundantOverride")
    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            // Calling performClick on every ACTION_DOWN so OnClickListener is triggered properly.
            performClick()
        }

        if (event != null && isEnabled && ! locked) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    flagMoving = true
                    lastX = event.x
                    isSelected = true
                    isActivated = false
                    delegate?.drag(0f)

                    requestDisallowInterceptTouchEvent(true)

                }
                MotionEvent.ACTION_MOVE -> {
                    if (flagMoving) {
                        val diffX = (event.x - lastX) / 2.0f
                        var x = translationX + diffX
                        if (x < 0) {
                            x = 0f
                        } else
                            if (x >= maxWidth) {
                                x = maxWidth
                            }

                        delegate?.drag(x)
                        translationX = x
                    }
                }
                MotionEvent.ACTION_UP -> {
                    requestDisallowInterceptTouchEvent(false)
                    if (translationX < maxWidth) {
                        animate().translationX(0.0f)
                            .setListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator?) {
                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    isSelected = false
                                    delegate?.endDrag()
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                    isSelected = false
                                    delegate?.endDrag()
                                }

                                override fun onAnimationRepeat(animation: Animator?) {
                                }


                            })
                    }
                    else {
                        locked = true
                        delegate?.click()
                    }

                    flagMoving = false
                }
            }

            return true
        }

        return super.onTouchEvent(event)
    }

    fun setColor(color: ColorStateList?) {
        val materialCardView = findViewById<MaterialCardView>(R.id.cardView)
        materialCardView.setCardBackgroundColor(color)

    }

    fun reset() {
        locked = false
        translationX = 0f
        isSelected = false
        delegate?.endDrag()
    }
}