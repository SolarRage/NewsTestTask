package com.myarulin.newstesttask.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.myarulin.newstesttask.R


class VerticalSpaceItemDecoration(context: Context) :
    ItemDecoration() {

    private val verticalSpaceHeight = context.resources.getDimensionPixelSize(R.dimen.offset)
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
    }
}