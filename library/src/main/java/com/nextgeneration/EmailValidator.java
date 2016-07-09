package com.nextgeneration;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import com.nextgeneration.R;

/**
 * Email Validator for EditText
 */
public class EmailValidator extends Validator {

    private String errorMessage;

    public EmailValidator(TextView textView) {
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
        if (string.length() > 0 && !Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
            if (errorMessage != null) {
                return errorMessage;
            }
            return context.getString(R.string.av_email_address_is_invalid);
        }
        return null;
    }
}
