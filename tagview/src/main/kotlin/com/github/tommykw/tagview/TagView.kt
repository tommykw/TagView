package com.github.tommykw.tagview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout

class TagView<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val tags = arrayListOf<TextView>()
    private var lineHeight = 0
    private val horizontalSpacing: Int
    private val verticalSpacing: Int
    private val textAppearanceId: Int
    private val textColorId: Int
    private val textFontSize: Int
    private val backgroundColorId: Int
    private val cornerRadius: Float
    private val leftDrawableId: Int
    private val leftDrawablePadding: Int
    private val sortType: Long
    private val strokeWidth: Int
    private val strokeColor: Int

    private var tagClickListener: TagClickListener<T>? = null

    init {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TagView)
        horizontalSpacing = styledAttrs.getDimension(R.styleable.TagView_horizontal_spacing, 1f).toInt()
        verticalSpacing = styledAttrs.getDimension(R.styleable.TagView_vertical_spacing, 1f).toInt()
        textAppearanceId = styledAttrs.getResourceId(R.styleable.TagView_text_style, -1)
        textColorId = styledAttrs.getColor(R.styleable.TagView_text_color, ContextCompat.getColor(context, R.color.white))
        textFontSize = styledAttrs.getDimension(R.styleable.TagView_text_font_size, 1f).toInt()
        backgroundColorId = styledAttrs.getColor(R.styleable.TagView_background_color, ContextCompat.getColor(context, R.color.white))
        cornerRadius = styledAttrs.getDimension(R.styleable.TagView_corner_radius, 60f)
        leftDrawableId = styledAttrs.getResourceId(R.styleable.TagView_left_drawable, -1)
        leftDrawablePadding = styledAttrs.getDimension(R.styleable.TagView_left_drawable_padding, 1f).toInt()
        sortType = styledAttrs.getInt(R.styleable.TagView_sort_type, -1).toLong()
        strokeWidth = styledAttrs.getDimension(R.styleable.TagView_stroke_width, 1f).toInt()
        strokeColor = styledAttrs.getColor(R.styleable.TagView_stroke_color, ContextCompat.getColor(context, R.color.white))

        styledAttrs.recycle()
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return TagLayoutParams(horizontalSpacing, verticalSpacing)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): LinearLayout.LayoutParams {
        return TagLayoutParams(horizontalSpacing, verticalSpacing)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams) = p is TagLayoutParams

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xPos = paddingLeft
        var yPos = paddingTop

        tags.forEach { child ->
            if (child.visibility != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight

                val lp = child.layoutParams as TagLayoutParams
                if (xPos + childWidth > width) {
                    xPos = paddingLeft
                    yPos += lineHeight
                }

                child.layout(xPos, yPos, xPos + childWidth, yPos + childHeight)
                xPos += childWidth + lp.horizontalSpacing
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = View.MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        var lineHeight = 0

        var xPos = paddingLeft
        var yPos = paddingTop

        val childHeightMeasureSpec: Int
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)
        } else {
            childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }

        tags.forEach { child ->
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as TagLayoutParams
                child.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST), childHeightMeasureSpec)
                val childWidth = child.measuredWidth
                lineHeight = Math.max(lineHeight, child.measuredHeight + lp.verticalSpacing)

                if (xPos + childWidth > width) {
                    xPos = paddingLeft
                    yPos += lineHeight
                }
                xPos += childWidth + lp.horizontalSpacing
            }
        }

        this.lineHeight = lineHeight
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            height = yPos + lineHeight

        } else if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            if (yPos + lineHeight < height) {
                height = yPos + lineHeight
            }
        }
        setMeasuredDimension(width, height)
    }

    fun setTags(items: List<T>, transform: DataTransform<T>) {
        val newItems = when(sortType) {
            Annotations.SORT_TYPE_ASC -> items.sortedBy { transform.transfer(it).length }
            Annotations.SORT_TYPE_DESC -> items.sortedByDescending { transform.transfer(it).length }
            else -> items
        }

        newItems.forEach { setTag(it, transform) }
        requestLayout()
    }

    private fun setTag(item: T, transform: DataTransform<T>) {
        val chips = (ContextCompat.getDrawable(context, R.drawable.chips) as GradientDrawable).also {
            it.cornerRadius = cornerRadius
            it.setColor(backgroundColorId)
            it.setStroke(strokeWidth, strokeColor)
        }

        val tag = TextView(context).apply {
            setTextColor(textColorId)
            background = chips
            text = transform.transfer(item)
            textSize = textFontSize.toFloat()
            setOnTouchListener { _, _ -> false }
            if (textAppearanceId != -1) {
                setTextAppearanceV2(context, textAppearanceId)
            }

            if (leftDrawableId != -1) {
                setCompoundDrawablesWithIntrinsicBounds(leftDrawableId, 0, 0, 0)
            }

            if (leftDrawablePadding != 1) {
                compoundDrawablePadding = leftDrawablePadding
            }

            setOnClickListener {
                tagClickListener?.onTagClick(item)
            }
        }

        tags.add(tag)
        addView(tag)
    }

    fun clear() {
        tags.clear()
        removeAllViews()
    }

    fun setClickListener(listener: TagClickListener<T>) {
        tagClickListener = listener
    }

    interface TagClickListener<T> {
        fun onTagClick(item: T)
    }
}