package jp.co.sss.shop.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 会員情報入力フォーム
 *
 * @author 及川
 */

public class ContactForm {

	@NotBlank
	@Size(min = 1, max = 30)
	private String name;

	//(message = "Invalid E-mail Format")
	
	@NotBlank
	@Email
	private String email;

	@NotNull
	private Integer contactCategory;

	@NotBlank
	private String contactForm;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getContactCategory() {
		return contactCategory;
	}

	public void setContactCategory(Integer contactCategory) {
		this.contactCategory = contactCategory;
	}

	public String getContactForm() {
		return contactForm;
	}

	public void setContactForm(String contactForm) {
		this.contactForm = contactForm;
	}
}
