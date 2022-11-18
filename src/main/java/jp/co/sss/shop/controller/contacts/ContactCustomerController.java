package jp.co.sss.shop.controller.contacts;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.entity.Contacts;
import jp.co.sss.shop.form.ContactForm;
import jp.co.sss.shop.repository.ContactRepository;

@Controller
public class ContactCustomerController {
	@Autowired
	ContactRepository contactRepository;
	
	
	/**
	 * お問い合わせ画面の表示処理
	 * 
	 * @param model
	 * @return "/contact/customer/contact_customer_input"お問い合わせ画面へ
	 */
	@RequestMapping(path ="/contact/customer/contact_customer_input" , method = RequestMethod.GET)
	public String contactForm(Model model) {
		model.addAttribute("contactForm", new ContactForm());
		model.addAttribute("page",2);
		return "/contact/customer/contact_customer_input";
	}
	
	
	/**
	 * POSTメソッドを利用してお問い合わせ内容入力画面に戻る処理
	 * 
	 * @return "user/regist/user_regist_input" 会員情報 登録入力画面へ
	 */
	@RequestMapping(path = "/contact/customer/contact_customer_input", method = RequestMethod.POST)
	public String formContact(Model model,ContactForm contactForm) {
		model.addAttribute("page",2);
		return "/contact/customer/contact_customer_input";
	}
	
	
	/**
	 * お問い合わせ内容登録確認処理
	 * 
	 * @param form   お問い合わせ内容入力フォーム 
	 */
	@RequestMapping(path = "/contact/customer/contact_customer_check", method = RequestMethod.POST)
	public String contactCheck(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,Model model) {
		model.addAttribute("page",2);
		
		if (result.hasErrors()) {
			return "contact/customer/contact_customer_input";
		}
		
		return "contact/customer/contact_customer_check";
	}
	
	
	/**
	 * お問い合わせ内容登録確認処理
	 * 
	 * @param form   お問い合わせ内容入力フォーム 
	 */
	@RequestMapping(path = "/contact/customer/contact_customer_complete", method = RequestMethod.POST)
	public String contactComplete(@ModelAttribute ContactForm contactForm,Model model) {
		model.addAttribute("page",2);

		// お問い合わせの生成
		Contacts contact = new Contacts();

		// 入力値をお問い合わせにコピー
		BeanUtils.copyProperties(contactForm, contact);

		// お問い合わせ内容を保存
		contactRepository.save(contact);

		return "redirect:/contact/customer/contact_customer_complete";
	}
	
	
	/**
	 * お問い合わせ内容登録完了画面表示
	 *
	 * @param form お問い合わせ内容入力フォーム
	 * @return "/contact/customer/contact_customer_complete" お問い合わせ内容登録完了画面へ
	 */
	@RequestMapping(path = "/contact/customer/contact_customer_complete", method = RequestMethod.GET)
	public String contactCompleteRedirect() {
		return "/contact/customer/contact_customer_complete";
	}
}
