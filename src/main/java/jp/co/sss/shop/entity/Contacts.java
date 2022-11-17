package jp.co.sss.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contacts {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contact_gen")
	@SequenceGenerator(name = "seq_contact_gen", sequenceName = "seq_contacts", allocationSize = 1)
	private Integer id;

	@Column
	private String name;

	@Column
	private String email;

	@Column 
	private Integer contactCategory;

	@Column
	private String contactForm;

	@Column (insertable = false)
	private Integer status;

	@Column (insertable = false)
	private Date insertDate;

	public Contacts() {

	}

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
