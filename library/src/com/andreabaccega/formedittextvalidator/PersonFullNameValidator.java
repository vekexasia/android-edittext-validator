package com.andreabaccega.formedittextvalidator;

public class PersonFullNameValidator extends RegexpValidator {
	public PersonFullNameValidator(String message) {
		// will allow people with hyphens in his name or surname. Supports also unicode
		super(message, "[\\p{L}- ]+");
	}
}
