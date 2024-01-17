package com.freediving.memberservice.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : @EnumValid가 적용된 객체 검증
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public class EnumValidator implements ConstraintValidator<EnumValid, Enum> {
	private EnumValid annotation;

	@Override
	public void initialize(EnumValid constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Enum value, ConstraintValidatorContext context) {
		boolean result = false;
		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (value == enumValue) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}