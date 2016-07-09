package com.nextgeneration.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.nextgeneration.ConfirmPasswordValidator;
import com.nextgeneration.EmailValidator;
import com.nextgeneration.PasswordValidator;
import com.nextgeneration.PhoneNumberValidator;
import com.nextgeneration.Validator;

public class SampleActivity  extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

	private EmailValidator emailValidator;
	private PhoneNumberValidator phoneNumberValidator;
	private ConfirmPasswordValidator confirmPasswordValidator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		EditText email = (EditText) findViewById(R.id.email);
		emailValidator = new Validator.Builder<>(new EmailValidator(email))
				.setRequired(true)
				.setStopTypingValidate(true)
				.setTextInputLayoutValidate(true)
		.build();
		((CheckBox) findViewById(R.id.emailLoseFocusValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.emailStopTypingValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.emailShowError)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.emailTextLayoutValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.emailRequiredValidate)).setOnCheckedChangeListener(this);

		EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		phoneNumberValidator = new PhoneNumberValidator(phoneNumber);
		((CheckBox) findViewById(R.id.phoneNumberLoseFocusValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.phoneNumberStopTypingValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.phoneNumberShowError)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.phoneNumberTextLayoutValidate)).setOnCheckedChangeListener(this);
		((CheckBox) findViewById(R.id.phoneNumberRequiredValidate)).setOnCheckedChangeListener(this);

		EditText password = (EditText) findViewById(R.id.password);
		EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		PasswordValidator passwordValidator = new PasswordValidator(password);
		passwordValidator.setMinLength(3);
		passwordValidator.setHasUpperChar("Insert Upper Char Error here");
		passwordValidator.setHasLowerChar("Insert Lower Char Error here");
		passwordValidator.setStopTypingValidate(true);
		passwordValidator.setTextInputLayoutValidate(true);
		PasswordValidator confirmPasswordValidator = passwordValidator.clone(confirmPassword);
		this.confirmPasswordValidator = new ConfirmPasswordValidator(passwordValidator, confirmPasswordValidator);
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		switch (compoundButton.getId()) {
			case R.id.emailLoseFocusValidate:
				emailValidator.setLoseFocusValidate(b);
				break;
			case R.id.emailStopTypingValidate:
				emailValidator.setStopTypingValidate(b);
				break;
			case R.id.emailShowError:
				emailValidator.setShowError(b);
				break;
			case R.id.emailTextLayoutValidate:
				emailValidator.setTextInputLayoutValidate(b);
				break;
			case R.id.emailRequiredValidate:
				emailValidator.setRequired(b);
				break;
			case R.id.phoneNumberLoseFocusValidate:
				phoneNumberValidator.setLoseFocusValidate(b);
				break;
			case R.id.phoneNumberStopTypingValidate:
				phoneNumberValidator.setStopTypingValidate(b);
				break;
			case R.id.phoneNumberShowError:
				phoneNumberValidator.setShowError(b);
				break;
			case R.id.phoneNumberTextLayoutValidate:
				phoneNumberValidator.setTextInputLayoutValidate(b);
				break;
			case R.id.phoneNumberRequiredValidate:
				phoneNumberValidator.setRequired(b);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
}
