package id.co.edtslib.slidingbutton

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.card.MaterialCardView
import id.co.edtslib.slidingbutton.arrow.ArrowDelegate
import id.co.edtslib.slidingbutton.arrow.ArrowView

class SlidingButton: FrameLayout {
    private var cvDefault: MaterialCardView? = null
    private var cvSwipe: CardView? = null
    private var vArrow: ArrowView? = null
    private var tvButtonDefault: TextView? = null
    private var tvButtonSwipe: TextView? = null

    var delegate: SlidingButtonDelegate? = null
    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val view = inflate(context, R.layout.v_sliding_button, this)

        cvDefault = view.findViewById(R.id.cvDefault)
        cvSwipe = view.findViewById(R.id.cvSwipe)
        tvButtonDefault = view.findViewById(R.id.tvButtonDefault)

        tvButtonSwipe = view.findViewById(R.id.tvButtonSwipe)
        tvButtonSwipe?.isActivated = false

        vArrow = view.findViewById(R.id.vArrow)

        var activated = true

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingButton,
                0, 0
            )

            val height = a.getDimension(R.styleable.SlidingButton_height, 0F)
            if (height > 0) {
                view.layoutParams.height = height.toInt()
            }

            val cornerRadius = a.getDimension(R.styleable.SlidingButton_cornerRadius, 0F)
            if (cornerRadius > 0) {
                cvDefault?.radius = cornerRadius
                cvSwipe?.radius = cornerRadius
            }

            val text = a.getString(R.styleable.SlidingButton_text)
            if (text != null) {
                tvButtonDefault?.text = text
                tvButtonSwipe?.text = text
            }

            val textColor = a.getResourceId(R.styleable.SlidingButton_textColor, 0)
            if (textColor > 0) {

                tvButtonDefault?.setTextColor(ContextCompat.getColorStateList(context, textColor))
                tvButtonSwipe?.setTextColor(ContextCompat.getColorStateList(context, textColor))
            }

            val textStyle = a.getResourceId(R.styleable.SlidingButton_textStyle, 0)
            if (textStyle > 0) {

                TextViewCompat.setTextAppearance(tvButtonDefault!!, textStyle)
                TextViewCompat.setTextAppearance(tvButtonSwipe!!, textStyle)
            }

            val slideColor = a.getColor(R.styleable.SlidingButton_slideColor, 0)
            if (slideColor != 0) {
                cvDefault?.strokeColor = slideColor
                cvSwipe?.setCardBackgroundColor(slideColor)
            }

            val arrowColor = a.getResourceId(R.styleable.SlidingButton_arrowColor, 0)
            if (arrowColor > 0) {

                vArrow?.setColor(ContextCompat.getColorStateList(context, arrowColor))
            }

            activated = a.getBoolean(R.styleable.SlidingButton_activated, true)
            if (arrowColor > 0) {

                vArrow?.setColor(ContextCompat.getColorStateList(context, arrowColor))
            }

            a.recycle()
        }

        vArrow?.delegate = object : ArrowDelegate {
            override fun drag(x: Float) {
                val layoutParams = cvSwipe?.layoutParams
                layoutParams?.width = vArrow!!.width + x.toInt() +
                        2*resources.getDimensionPixelSize(R.dimen.dimen_6dp)

                cvSwipe?.layoutParams = layoutParams
            }

            override fun endDrag() {
                val layoutParams = cvSwipe?.layoutParams
                layoutParams?.width = 0

                cvSwipe?.layoutParams = layoutParams
            }

            override fun click() {
                delegate?.onCompleted()
            }
        }

        isActivated = activated
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val width = right - left

            vArrow!!.maxWidth = width.toFloat()-vArrow!!.width.toFloat()-2*resources.getDimensionPixelSize(
                R.dimen.dimen_6dp
            )

            tvButtonDefault?.translationX = (width.toFloat() - tvButtonDefault!!.width)/2.0f
            tvButtonSwipe?.translationX = tvButtonDefault!!.translationX

        }
    }

    // enabled/disabled slide button view
    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)

        if (activated) {
            if (true == vArrow?.locked) {
                vArrow?.reset()
            }
        }
        else {
            vArrow?.reset()
        }

        cvDefault?.strokeWidth = if (activated)
            context.resources.getDimensionPixelSize(R.dimen.dimen_1dp) else 0

        vArrow?.isActivated = ! activated
        vArrow?.isSelected = ! activated
        vArrow?.locked = ! activated
        tvButtonSwipe?.isActivated = false
    }

    // reset slide button to initial satet
    fun reset() {
        if (isActivated) {
            vArrow?.reset()
        }
    }
}