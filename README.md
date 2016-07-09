# Input Validator

[![Release](https://jitpack.io/v/philip-bui/input-validator.svg)](https://jitpack.io/#philip-bui/input-validator)
[![Downloads](https://jitpack.io/v/philip-bui/input-validator/month.svg)](https://jitpack.io/#philip-bui/input-validator)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/philip-bui/input-validator/blob/master/LICENSE)

Design pattern to create validators. By wrapping TextInputs with Validators, we bind validation checks to events required, only customizing error messages and validation required.

* Material TextInputLayout support.
* OnFocusChange and onTextChanged events.
* Error messages customizable.
* Error handling callback.
* Provided Email Validator.
* Provided Phone Number Validator.
* Provided Password Validator (confirmation, upper, lower, digit, special, minimum characters).

## [Demo](https://appetize.io/app/zwj3xzcy2zbtt8c78gq8yzum7g?device=nexus5&scale=75&orientation=portrait&osVersion=6.0)

## Requirements

- Android SDK 8.0+

## Installation

```gradle
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
dependencies {
    compile 'com.github.philip-bui:input-validator:1.0.0'
}
```

## Usage

```java
  EditText email = (EditText) findViewById(R.id.email);
  EmailValidator emailValidator = new EmailValidator(email);
  emailValidator.setTextInputLayoutValidate(true);
  emailValidator.setRequired(true, "Required email address"); // Error message on empty.
  emailValidator.setLoseFocusValidate(true); // onFocusChange event validation binded.
  if (emailValidator.validate()) {
    ... // Successful validation.
  } else {
    ... // Unsucessful validation. Error messages / callbacks have been displayed.
  }
```

## Customize

Customization is easy. To create new Validation logic, just extend Validator.

```java
public class PhoneNumberValidator extends Validator {
    public PhoneNumberValidator(EditText editText) {
        super(editText);
    }

    @Override
    public String onValidate(String string, Context context) {
        if (!Patterns.PHONE.matcher(string).matches()) {
            return context.getString(R.string.av_phone_number_is_invalid);
        }
        return null;
    }
}
```

Alternatively multiple Validators can be composed onto one ([see ConfirmPasswordValidator](https://github.com/philip-bui/input-validator/blob/master/library/src/main/java/com/nextgeneration/ConfirmPasswordValidator.java)), representing a checklist.

## Customization Attributes

`` validateListener `` *Default: null*

Interface when a success or error validation happens. 

`` stopTypingValidate `` *Default: false*

Whether to validate when the user stops typing. 

`` loseFocusValidate `` *Default: true*

Whether to validate when the user changes focus FROM the field.

`` showError `` *Default: true*

Whether to show error messages. Useful to set false if you want to manually handle error messages.

`` textInputValidate `` *Default: false*

Whether to show the error message on the parent TextInputLayout. If the parent of the EditText is not a TextInputLayout, it throws an Exception when set to true.

`` required `` *Default: false, "Required field"*

Whether to return an error when empty. This is called before validate(), and also comes with a custom Error message.

# License

Input Validator is available under the MIT license. [See LICENSE](https://github.com/philip-bui/input-validator/blob/master/LICENSE) for details.

