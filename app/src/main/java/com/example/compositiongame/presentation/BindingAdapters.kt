package com.example.compositiongame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.compositiongame.R


@BindingAdapter("emoji")
fun bindEmoji(imageView: ImageView, winner : Boolean){
    if(winner) imageView.setImageResource(R.drawable.ic_smile)
    else imageView.setImageResource(R.drawable.ic_sad)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count :Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_amount_of_right_answers),
        count.toString()
    )
}

@BindingAdapter("userAnswers")
fun bindUserAnswers(textView: TextView, answers: Int){
    textView.text = String.format(
        textView.context.getString(R.string.your_score),
        answers.toString()
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percentage: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percent_of_right_answers),
        percentage.toString()
    )
}

@BindingAdapter("userPercentage")
fun bindUserPercentage(textView: TextView, percentage: Int){
    textView.text = String.format(
        textView.context.getString(R.string.right_answers_percentage),
        percentage.toString()
    )
}

@BindingAdapter("rightAnswersProgress")
fun bindRightAnswersProgress(progressBar: ProgressBar, progress: Int){
    progressBar.setProgress(progress, true)
}


@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean){
    textView.setTextColor(getColor(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean){
    progressBar.progressTintList = ColorStateList.valueOf(getColor(progressBar.context, enough))
}

private fun getColor(context: Context, isGreen : Boolean) : Int{
    return if(isGreen) {
        ContextCompat.getColor(context, android.R.color.holo_green_light)
    } else {
        ContextCompat.getColor(context, android.R.color.holo_red_light)
    }
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int){
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener{
    fun onOptionClick(option: Int)
}