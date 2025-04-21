package com.example.compositiongame.presentation

import android.widget.ImageView
import android.widget.TextView
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