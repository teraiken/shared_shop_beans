package jp.co.sss.shop.controller.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.entity.Contacts;
import jp.co.sss.shop.repository.ContactRepository;

@Controller
public class ContactAdminController {

	@Autowired
	ContactRepository contactRepository;

	/**
	 * 問い合わせ一覧表示
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/contact/admin/list")
	public String contactAdminList(Model model) {
		//データ登録順で検索
		List<Contacts> contactList = contactRepository.findAllByOrderByInsertDateDesc();
		//リストをViewに渡す
		model.addAttribute("contacts", contactList);
		//カテゴリ別検索を表示させるための値をViewに渡す
		model.addAttribute("page",1);
		return "contacts/contact_admin_list";
	}

	/**
	 * カテゴリ別検索
	 * 
	 * @param contactCategory
	 * @param model
	 * @return
	 */
	@RequestMapping("/contact/admin/list/category")
	public String contactAdminListfindByCategory(int contactCategory, Model model) {
		//カテゴリ別にデータ登録順で検索
		List<Contacts> contactList = contactRepository.findByContactCategoryOrderByInsertDateDesc(contactCategory);
		//リストをViewに渡す
		model.addAttribute("contacts", contactList);
		//カテゴリ別検索を表示させるための値をViewに渡す
		model.addAttribute("page",1);
		return "contacts/contact_admin_list";
	}

}
