package com.example.domain;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.service.UserService;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	/**
	 * コンストラクでBeanを受け取る. SpringはValidatorインスタンス生成時にBeanを自動でInjectする.
	 * 
	 * @see <a href=
	 *      "http://dolszewski.com/spring/custom-validation-annotation-in-spring/">CUSTOM
	 *      VALIDATION ANNOTATION IN SPRING</a>
	 * 
	 * @param userService
	 */

	public UniqueValidator(UserService userService) {
		this.userService = userService;
	}

	private UserService userService;

	@Override
	public void initialize(Unique constraintAnnotation) {
	}

	@Override
	public boolean isValid(String firstName, ConstraintValidatorContext context) {
		Optional<User> user = userService.findByFistName(firstName);
		return !user.isPresent();
	}
}
