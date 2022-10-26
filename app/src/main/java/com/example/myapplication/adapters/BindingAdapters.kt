package com.example.myapplication.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplication.R
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.model.CountryLocale
import com.example.myapplication.utils.formatDate

@BindingAdapter("setLoanBadgeImage")
fun setLoanBadgeImage(view: ImageView, loanLevel: String?) {
    if (!loanLevel.isNullOrEmpty()) {
        when (loanLevel) {
            "silver" -> view.setImageResource(R.drawable.img_silver_badge_large)
            "gold" -> view.setImageResource(R.drawable.img_gold_badge_large)
            "bronze" -> view.setImageResource(R.drawable.img_bronze_badge_large)
            else -> view.setImageResource(R.drawable.img_blue_badge_large)
        }
    } else {
        view.setImageResource(R.drawable.img_blue_badge_large)
    }
}

@BindingAdapter("setLoanStoryImage")
fun setLoanStoryImage(view: ImageView, userLocale: CountryLocale?) {
    if (userLocale?.localeCode != null) {
        when (userLocale.localeCode) {
            "ke" -> view.setImageResource(R.drawable.img_story_card_ke)
            "mx" -> view.setImageResource(R.drawable.img_story_card_mx)
            "ph" -> view.setImageResource(R.drawable.img_story_card_ph)
            else -> view.setImageResource(R.drawable.img_story_card_ke)
        }
    } else {
        view.setImageResource(R.drawable.img_story_card_ke)
    }
}

@BindingAdapter("loanStatusTextToFormat", "userLoanItem")
fun setLoanStatusText(textView: TextView, loanStatusTextToFormat: String, userLoan: UserLoan) {
    if(userLoan.loan != null && !userLoan.loan?.status.isNullOrEmpty()) {

        var currency = "Kshs"
        if (!userLoan.userLoanLocale?.currency.isNullOrEmpty()) {
            currency = userLoan.userLoanLocale?.currency!!
        }

        var loanLimit = "0"
        if (userLoan.userLoanLocale?.loanLimit != null) loanLimit = ""+userLoan.userLoanLocale?.loanLimit

        textView.text = String.format(loanStatusTextToFormat, currency, loanLimit)
    }
}

@BindingAdapter("stringToFormat", "stringToFormatText", "stringToFormatText2")
fun setFormattedDueText(textView: TextView, stringToFormat: String, stringToFormatText: String, stringToFormatText2: String) {
    textView.text = String.format(stringToFormat, stringToFormatText.trim(), stringToFormatText2.trim())
}

@BindingAdapter("setFormattedDueDateText")
fun setFormattedDueDateText(textView: TextView, loanDateDue: Long?) {
    try {
        if(loanDateDue != null) {
            textView.text = formatDate(loanDateDue)
        } else{
            textView.text = R.string.due_loan_date_tag.toString()
        }

    } catch (e: Exception) {
        textView.text = R.string.due_loan_date_tag.toString()
    }
}