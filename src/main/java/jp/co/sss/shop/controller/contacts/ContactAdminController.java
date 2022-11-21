package jp.co.sss.shop.controller.contacts;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.entity.Contacts;
import jp.co.sss.shop.form.ContactForm;
import jp.co.sss.shop.repository.ContactRepository;

/**
 * お問い合わせ一覧表示（運用側）コントローラークラス
 * 
 * @author 安倍 問い合わせ一覧、問い合わせカテゴリ別
 * @author 及川 ステータス別検索
 * @author 岡部 詳細画面、ステータス更新
 *
 */
@Controller
public class ContactAdminController {

	@Autowired
	ContactRepository contactRepository;

	/**
	 * 問い合わせ一覧表示
	 * 
	 * @param model Viewとの値受渡し
	 * @return "contact/admin/contact_admin_list"一覧表示画面
	 */
	@RequestMapping("/contact/admin/contact_admin_list")
	public String contactAdminList(Model model) {
		// データ登録順で検索
		List<Contacts> contactList = contactRepository.findAllByOrderByInsertDateDesc();
		// リストをViewに渡す
		model.addAttribute("contacts", contactList);
		// カテゴリ別検索を表示させるための値をViewに渡す
		model.addAttribute("page", 1);
		return "contact/admin/contact_admin_list";
	}

	/**
	 * カテゴリ別検索
	 * 
	 * @param contactCategory カテゴリID
	 * @param model           Viewとの値受渡し
	 * @return "contact/admin/contact_admin_list"一覧表示画面
	 */
	@RequestMapping("/contact/admin/category")
	public String contactAdminListFindByCategory(int contactCategory, Model model) {
		// カテゴリ別にデータ登録順で検索
		List<Contacts> contactList = contactRepository.findByContactCategoryOrderByInsertDateDesc(contactCategory);
		// リストをViewに渡す
		model.addAttribute("contacts", contactList);
		// カテゴリ別検索を表示させるための値をViewに渡す
		model.addAttribute("page", 1);
		return "contact/admin/contact_admin_list";
	}

	/**
	 * ステータス別検索
	 * 
	 * @param status ステータスID
	 * @param model  Viewとの値受渡し
	 * @return "contact/admin/contact_admin_list"一覧表示画面
	 */
	@RequestMapping("/contact/admin/status")
	public String contactAdminListFindByStatus(int status, Model model) {
		// ステータス別にデータ登録順で検索
		List<Contacts> contactList = contactRepository.findByStatusOrderByInsertDateDesc(status);
		// リストをViewに渡す
		model.addAttribute("contacts", contactList);
		// ステータス別検索を表示させるための値をViewに渡す
		model.addAttribute("page", 1);

		return "contact/admin/contact_admin_list";
	}

	
	/**
	 * 問い合わせ情報詳細表示処理
	 *
	 * 
	 * @param id 問い合わせID
	 * @param model Viewとの値受渡し
	 * @param contctForm お問い合わせ情報
	 * @return "contact/admin/contact_admin_detail" 問い合わせ情報 詳細画面へ
	 */
	@RequestMapping(path = "/contact/admin/contact_admin_detail/{id}")
	public String contactAdminDetail(@PathVariable int id, Model model, @ModelAttribute ContactForm contactForm) {

		// IDに該当する問い合わせ情報を取得
		Contacts contactsDetail = contactRepository.getById(id);
		
		BeanUtils.copyProperties(contactsDetail, contactForm);


		// 問い合わせ情報をViewへ渡す
		model.addAttribute("contactForm", contactForm);

		return "contact/admin/contact_admin_detail";
	}
	
	/**
	 * ステータス更新処理
	 * 
	 * @param id お問い合わせID
	 * @param model Viewとの値受渡し
	 * @param contctForm お問い合わせ情報
	 * 
	 * @return redirect:/contact/admin/contact_admin_detail/{id} 問い合わせ情報 詳細画面へ
	 * 
	 */
	
	@RequestMapping(path = "/contact/admin/contact_admin_detail/{id}", method = RequestMethod.POST)
	public String updateStatus(@PathVariable int id, Model model, @ModelAttribute ContactForm contactForm) {		
		//変更する問い合わせ情報を取得
		Contacts contact =  contactRepository.getById(id);
		contact.setStatus(contactForm.getStatus());
		//問い合わせ情報を保存
		contactRepository.save(contact);
		return "redirect:/contact/admin/contact_admin_detail/{id}";
		
		
	}
	
	

}


