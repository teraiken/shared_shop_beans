package jp.co.sss.shop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.sss.shop.validator.LoginValidator;

/**
 * ログインチェックの独自アノテーション定義
 *
 * @author System Shared
 */

/**
 * アノテーション付与対象
 */
@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })

/**
 * アノテーション情報の維持範囲
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 入力チェック対象：LoginValidator
 */
@Constraint(validatedBy = { LoginValidator.class })
public @interface LoginCheck {
	String message() default "{login.missing.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldEmail() default "email";

	String fieldPassword() default "password";
}
