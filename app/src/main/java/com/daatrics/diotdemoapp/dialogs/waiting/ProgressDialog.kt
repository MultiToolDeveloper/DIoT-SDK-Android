package com.daatrics.diotdemoapp.dialogs.waiting

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.daatrics.diotdemoapp.R

class ProgressDialog : DialogFragment() {
    var dialogContainer: ConstraintLayout? = null
    var background: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_FRAME,
            R.style.Theme_DIoTDemoApp
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val root: View = inflater.inflate(R.layout.dialog_progress, container, false)
        dialogContainer = root.findViewById(R.id.dialogContainer)
        background = root.findViewById(R.id.dialogBackground)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateAppear()
    }

    private fun animateAppear() {
        dialogContainer?.alpha = 0f
        dialogContainer?.animate()?.alpha(1f)?.duration = 200
        background?.animate()?.alpha(1f)?.duration = 200
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        animateDisappear()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    private fun animateDisappear() {
        dialogContainer?.animate()?.alpha(0f)?.setDuration(200)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    dialog?.dismiss()
                }
            })
        background?.animate()?.alpha(0f)?.duration = 200
    }

    override fun onResume() {
        super.onResume()
    }
}