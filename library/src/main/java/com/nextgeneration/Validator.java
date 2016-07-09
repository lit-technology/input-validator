package com.nextgeneration;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.nextgeneration.R;

/**
 * Abstract class that represents what functionality a Validator should have.
 */
public abstract class Validator implements View.OnFocusChangeListener, TextWatcher {

    private final TextView textView;
    private ValidateListener validateListener;
    private boolean stopTypingValidate;
    private boolean loseFocusValidate = true;
    private boolean showError = true;
    private boolean required;
    private String requiredErrorText;
    private boolean textInputLayoutValidate;
    private TextInputLayout textInputLayout;

    public Validator(TextView textView) {
        this.textView = textView;
    }

    public ValidateListener getValidateListener() {
        return validateListener;
    }

    /**
     * Set interface callback for validation success and errors.
     *
     * @param validateListener
     */
    public void setValidateListener(@Nullable ValidateListener validateListener) {
        this.validateListener = validateListener;
    }

    public boolean isStopTypingValidate() {
        return stopTypingValidate;
    }

    /**
     * Set whether to validate when the user stops typing.
     *
     * @param stopTypingValidate
     */
    public void setStopTypingValidate(boolean stopTypingValidate) {
        this.stopTypingValidate = stopTypingValidate;
        if (stopTypingValidate) {
            this.textView.addTextChangedListener(this);
        } else {
            this.textView.removeTextChangedListener(this);
        }
    }

    public boolean isLoseFocusValidate() {
        return loseFocusValidate;
    }

    /**
     * Set whether to validate when the user loses focus from the field.
     *
     * @param loseFocusValidate
     */
    public void setLoseFocusValidate(boolean loseFocusValidate) {
        this.loseFocusValidate = loseFocusValidate;
        if (loseFocusValidate) {
            this.textView.setOnFocusChangeListener(this);
        } else {
            this.textView.setOnFocusChangeListener(null);
        }
    }

    public boolean isShowError() {
        return showError;
    }

    /**
     * Set whether to display error messages. Use this with ValidateListener to manually handle Errors.
     *
     * @param showError
     */
    public void setShowError(boolean showError) {
        this.showError = showError;
        if (!showError) {
            if (textInputLayoutValidate) {
                textInputLayout.setError(null);
            } else {
                textView.setError(null);
            }
        }
    }

    public boolean isRequired() {
        return required;
    }

    /**
     * Set whether the field is required. Uses the default "Required field" error message.
     *
     * @param required
     */
    public void setRequired(boolean required) {
        setRequired(required, textView.getContext().getString(R.string.av_required_field));
    }

    /**
     * Set whether the field is required.
     *
     * @param required
     * @param requiredErrorText
     */
    public void setRequired(boolean required, @Nullable String requiredErrorText) {
        this.required = required;
        this.requiredErrorText = requiredErrorText;
    }

    public boolean isTextInputLayoutValidate() {
        return textInputLayoutValidate;
    }

    /**
     * Set whether to display error messages inside the TextInputLayout. Throws an IllegalArgumentException if the parent of the EditText is not a TextInputLayout.
     *
     * @param textInputLayoutValidate
     */
    public void setTextInputLayoutValidate(boolean textInputLayoutValidate) {
        this.textInputLayoutValidate = textInputLayoutValidate;
        if (textInputLayoutValidate) {
            ViewParent view = textView.getParent();
            while (view != null && !(view instanceof TextInputLayout)) {
                view = view.getParent();
            }
            if (view == null || !(view instanceof TextInputLayout)) {
                throw new IllegalArgumentException("Error finding TextInputLayout");
            }
            textInputLayout = (TextInputLayout) view;
            if (!TextUtils.isEmpty(textView.getError())) {
                textInputLayout.setError(textView.getError());
                textView.setError(null);
            }
        } else {
            if (textInputLayout != null && !TextUtils.isEmpty(textInputLayout.getError())) {
                textView.setError(textInputLayout.getError());
                textInputLayout.setError(null);
            }
            textInputLayout = null;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            validate();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            validate();
        } else if (required) {
            validate();
        } else {
            setError(null);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public final boolean validate() {
        return validate(getText());
    }

    public final boolean validate(String text) {
        if (required && TextUtils.isEmpty(text)) {
            if (showError) {
                setError(requiredErrorText);
            }
            if (validateListener != null) {
                validateListener.onError(requiredErrorText);
            }
            return false;
        }

        String error = onValidate(text, textView.getContext());
        if (showError) {
            setError(error);
        }
        if (error != null) {
            if (validateListener != null) {
                validateListener.onError(error);
            }
            return false;
        } else {
            if (validateListener != null) {
                validateListener.onSuccess();
            }
            return true;
        }
    }

    public String getText() {
        return textView.getText().toString();
    }

    public Context getContext() {
        return textView.getContext();
    }

    public void setError(@Nullable String error) {
        if (textInputLayoutValidate) {
            textInputLayout.setError(error);
        } else {
            textView.setError(error);
        }
    }

    public interface ValidateListener {
        void onSuccess();

        void onError(String errorString);
    }

    /**
     * Put validation logic in here. Return null if there is no Errors.
     *
     * @param string Text to validate.
     * @return String representing an Error message, null if none.
     */
    public abstract String onValidate(String string, Context context);

    public static class Builder<T extends Validator> {
        private T validator;

        public Builder(T validator) {
            this.validator = validator;
        }

        /**
         * Set interface callback for validation success and errors.
         *
         * @param validateListener
         */
        public Builder<T> setValidateListener(ValidateListener validateListener) {
            this.validator.setValidateListener(validateListener);
            return this;
        }

        /**
         * Set whether to validate when the user stops typing.
         *
         * @param stopTypingValidate
         */
        public Builder<T> setStopTypingValidate(boolean stopTypingValidate) {
            this.validator.setStopTypingValidate(stopTypingValidate);
            return this;
        }

        /**
         * Set whether to validate when the user loses focus from the field.
         *
         * @param loseFocusValidate
         */
        public Builder<T> setLoseFocusValidate(boolean loseFocusValidate) {
            this.validator.setLoseFocusValidate(loseFocusValidate);
            return this;
        }

        /**
         * Set whether to display error messages. Use this with ValidateListener to manually handle Errors.
         *
         * @param showError
         */
        public Builder<T> setShowError(boolean showError) {
            this.validator.setShowError(showError);
            return this;
        }

        /**
         * Set whether the field is required. Uses the default "Required field" error message.
         *
         * @param required
         */
        public Builder<T> setRequired(boolean required) {
            this.validator.setRequired(required);
            return this;
        }

        /**
         * Set whether the field is required.
         *
         * @param required
         * @param requiredErrorText
         */
        public Builder<T> setRequired(boolean required, @Nullable String requiredErrorText) {
            this.validator.setRequired(required, requiredErrorText);
            return this;
        }

        /**
         * Set whether to display error messages inside the TextInputLayout. Throws an IllegalArgumentException if the parent of the EditText is not a TextInputLayout.
         *
         * @param textInputLayoutValidate
         */
        public Builder<T> setTextInputLayoutValidate(boolean textInputLayoutValidate) {
            this.validator.setTextInputLayoutValidate(textInputLayoutValidate);
            return this;
        }

        public T build() {
            return validator;
        }
    }
}
