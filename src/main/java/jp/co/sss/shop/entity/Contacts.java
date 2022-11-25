package jp.co.sss.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * お問い合わせ情報エンティティクラス
 * 
 * @author 安倍
 * @author 及川
 * @author 岡部
 *
 */
@Entity
@Table(name = "contacts")
public class Contacts {
	/**
	 *　商品ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contact_gen")
	@SequenceGenerator(name = "seq_contact_gen", sequenceName = "seq_contacts", allocationSize = 1)
	private Integer id;

	/**
	 * 商品名
	 */
	@Column
	private String name;

	/**
	 * メールアドレス
	 */
	@Column
	private String email;

	/**
	 * カテゴリ
	 */
	@Column
	private Integer contactCategory;

	/**
	 * 問い合わせ内容
	 */
	@Column
	private String contactForm;

	/**
	 * ステータス
	 */
	@Column (insertable = false)
	private Integer status;

	/**
	 * 登録日
	 */
	@Column (insertable = false)
	private Date insertDate;

	/**
	 * コンストラクタ
	 */
	public Contacts() {

	}

	/**
	 * コンストラクタ
	 *
	 * @param id　商品ID
	 * @param name　商品名
	 * @param email　メールアドレス
	 * @param contactCategory　カテゴリ
	 * @param contactForm　問い合わせ内容
	 * @param status　ステータス
	 * @param insertDate　登録日
	 */
	public Contacts(Integer id, String name, String email, Integer contactCategory, String contactForm, Integer status,
			Date insertDate) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactCategory = contactCategory;
		this.contactForm = contactForm;
		this.status = status;
		this.insertDate = insertDate;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email セットする email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return contactCategory
	 */
	public Integer getContactCategory() {
		return contactCategory;
	}

	/**
	 * @param contactCategory セットする contactCategory
	 */
	public void setContactCategory(Integer contactCategory) {
		this.contactCategory = contactCategory;
	}

	/**
	 * @return contact_form
	 */
	public String getContactForm() {
		return contactForm;
	}

	/**
	 * @param contact_form セットする contact_form
	 */
	public void setContactForm(String contact_form) {
		this.contactForm = contact_form;
	}

	/**
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status セットする status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return insertDate
	 */
	public Date getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate セットする insertDate
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

}
