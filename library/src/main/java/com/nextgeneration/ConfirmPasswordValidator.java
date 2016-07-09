package com.nextgeneration;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Class that contains two PasswordValidators and validates both Texts equals
 */
public class ConfirmPasswordValidator implements Validator.ValidateListener {

    private final PasswordValidator passwordValidator;
    private final PasswordValidator confirmPasswordValidator;
    private String mismatchPasswordError;

    public ConfirmPasswordValidator(PasswordValidator passwordValidator, PasswordValidator confirmPasswordValidator) {
        this.passwordValidator = passwordValidator;
        this.confirmPasswordValidator = confirmPasswordValidator;
        this.passwordValidator.setValidateListener(this);
        this.confirmPasswordValidator.setValidateListener(this);
    }

    public boolean validate() {
        if (passwordValidator.validate() && confirmPasswordValidator.validate()) {
            if (!passwordValidator.getText().equals(confirmPasswordValidator.getText())) {
                setErrorMismatch();
                return false;
            }
            return true;
        }
        return false;
    }

    public void setMismatchPasswordError(@Nullable String mismatchPasswordError) {
        this.mismatchPasswordError = mismatchPasswordError;
    }

    public void setErrorMismatch() {
        if (!TextUtils.isEmpty(mismatchPasswordError)) {
            setError(mismatchPasswordError);
        } else {
            setError(passwordValidator.getContext().getString(R.string.av_passwords_do_not_match));
        }
    }

    public void setError(@Nullable String error) {
        passwordValidator.setError(error);
        confirmPasswordValidator.setError(error);
    }

    @Override
    public void onSuccess() {
        String password = passwordValidator.getText();
        String confirmPassword = confirmPasswordValidator.getText();
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
            if (!password.equals(confirmPassword)) {
                setErrorMismatch();
            } else {
                setError(null);
            }
        }
    }

    @Override
    public void onError(String errorString) {

    }
}
