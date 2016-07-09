package com.nextgeneration;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.widget.TextView;

import com.nextgeneration.R;

/**
 * Phone Number Validator for EditText
 */
public class PhoneNumberValidator extends Validator {

    private String errorMessage;

    public PhoneNumberValidator(TextView textView) {
        super(textView);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@Nullable String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String onValidate(String string, Context context) {
        if (string.length() > 1 && !Patterns.PHONE.matcher(string).matches()) {
            if (errorMessage != null) {
                return errorMessage;
            }
            return context.getString(R.string.av_phone_number_is_invalid);
        }
        return null;
    }
}
