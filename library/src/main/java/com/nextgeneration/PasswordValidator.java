package com.nextgeneration;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Password Validator for EditText
 */
public class PasswordValidator extends Validator {

    private int minLength;
    private String minLengthError;
    private Pattern hasUpperChar;
    private String upperCharError;
    private Pattern hasLowerChar;
    private String lowerCharError;
    private Pattern hasDigit;
    private String digitError;
    private Pattern hasSpecialChar;
    private String specialCharError;

    public PasswordValidator(TextView textView) {
        super(textView);
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMinLength(int minLength, @Nullable String minLengthError) {
        this.minLength = minLength;
        this.minLengthError = minLengthError;
    }

    public void setHasUpperChar(@Nullable String upperCharError) {
        if (upperCharError != null) {
            this.hasUpperChar = Pattern.compile(".*[A-Z].*");
            this.upperCharError = upperCharError;
        } else {
            this.hasUpperChar = null;
            this.upperCharError = null;
        }
    }

    public void setHasLowerChar(@Nullable String lowerCharError) {
        if (lowerCharError != null) {
            this.hasLowerChar = Pattern.compile(".*[a-z].*");
            this.lowerCharError = lowerCharError;
        } else {
            this.hasLowerChar = null;
            this.lowerCharError = null;
        }
    }

    public void setHasDigit(@Nullable String digitError) {
        if (digitError != null) {
            this.hasDigit = Pattern.compile(".*\\d.*");
            this.digitError = digitError;
        } else {
            this.hasDigit = null;
            this.digitError = null;
        }
    }

    public void setHasSpecialChar(@Nullable String specialCharError) {
        if (specialCharError != null) {
            this.hasSpecialChar = Pattern.compile(".*[~!].*");
            this.specialCharError = specialCharError;
        } else {
            this.hasSpecialChar = null;
            this.specialCharError = null;
        }
    }

    public PasswordValidator clone(TextView textView) {
        PasswordValidator passwordValidator = new Validator.Builder<>(new PasswordValidator(textView))
                .setLoseFocusValidate(this.isLoseFocusValidate())
                .setStopTypingValidate(this.isStopTypingValidate())
                .setShowError(this.isShowError())
                .setRequired(this.isRequired())
                .setTextInputLayoutValidate(this.isTextInputLayoutValidate())
                .setValidateListener(this.getValidateListener())
                .build();
        passwordValidator.minLength = this.minLength;
        passwordValidator.minLengthError = this.minLengthError;
        passwordValidator.hasUpperChar = this.hasUpperChar;
        passwordValidator.upperCharError = this.upperCharError;
        passwordValidator.hasLowerChar = this.hasLowerChar;
        passwordValidator.lowerCharError = this.lowerCharError;
        passwordValidator.hasDigit = this.hasDigit;
        passwordValidator.digitError = this.digitError;
        passwordValidator.hasSpecialChar = this.hasSpecialChar;
        passwordValidator.specialCharError = this.specialCharError;
        return passwordValidator;
    }
    @Override
    public String onValidate(String string, Context context) {
        if (!isRequired() && string.length() < minLength) {
            if (minLengthError != null) {
                return minLengthError;
            }
            return context.getString(R.string.av_password_must_contain_at_least_d_characters, minLength);
        }
        if (hasUpperChar != null && !hasUpperChar.matcher(string).matches()) {
            if (upperCharError != null) {
                return upperCharError;
            }
            return context.getString(R.string.av_password_must_contain_at_least_one_upper_case_letter);
        }
        if (hasLowerChar != null && !hasLowerChar.matcher(string).matches()) {
            if (lowerCharError != null) {
                return lowerCharError;
            }
            return context.getString(R.string.av_password_must_contain_at_least_one_lower_case_letter);
        }
        if (hasDigit != null && !hasDigit.matcher(string).matches()) {
            if (digitError != null) {
                return digitError;
            }
            return context.getString(R.string.av_password_must_contain_at_least_one_digit);
        }
        if (hasSpecialChar != null && !hasSpecialChar.matcher(string).matches()) {
            if (specialCharError != null) {
                return specialCharError;
            }
            return context.getString(R.string.av_password_must_contain_at_least_one_special_character);
        }
        return null;
    }
}
