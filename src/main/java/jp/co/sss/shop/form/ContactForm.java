package jp.co.sss.shop.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * お問い合わせ内容入力クラス
 * 
 * お問い合わせ一覧、お問い合わせ詳細表示クラス
 *
 * @author 及川 お問い合わせ内容入力フォーム
 * @author 岡部 お問い合わせ一覧、お問い合わせ詳細表示フォーム
 */

public class ContactForm {
	
	
	/**
	 * 問い合わせID
	 */
	private Integer id;

	/**
	 * 氏名
	 */
	@NotBlank
	@Size(min = 1, max = 30)
	private String name;
	
	/**
	 * メールアドレス
	 */
	@NotBlank
	@Email
	private String email;

	/**
	 * お問い合わせカテゴリ
	 */
	@NotNull
	private Integer contactCategory;

	/**
	 * お問い合わせ内容
	 */
	@NotBlank
	@Size(min = 1, max = 400)
	private String contactForm;
	
	/**
	 * 問い合わせステータス
	 */
	private Integer status;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	

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


	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
}