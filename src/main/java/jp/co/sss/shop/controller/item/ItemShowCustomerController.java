package jp.co.sss.shop.controller.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * 商品管理 一覧表示機能(一般会員用)のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class ItemShowCustomerController {
	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;

	/**
	 * トップ画面 表示処理
	 *
	 * @param model Viewとの値受渡し
	 * @return "/" トップ画面へ
	 */
	@RequestMapping(path = "/")
	public String index(Model model) {
		// 商品情報を全件検索(新着順)
		List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDescIdAsc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);

		return "index";
	}

	/**
	 * 新着順表示
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/item/list/1")
	public String newDateDesc(Model model) {
		List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDescIdAsc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);
		return "item/list/item_list";
	}

	@RequestMapping(path = "/item/list/2")
	public String hotSalling(Model model) {

		List<Item> itemList = itemRepository.findByHotSelling();

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		// model.addAttribute("items", itemBeanList);
		model.addAttribute("items", itemBeanList);
		model.addAttribute("sortType", 1);
		System.out.println("aaa");
		return "item/list/item_list";

	}

	/**
	 * 商品詳細画面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/item/detail/{id}")
	public String itemDetail(@PathVariable int id, Model model) {
		ItemBean item = BeanCopy.copyEntityToBean(itemRepository.getById(id));

		model.addAttribute("item", item);
		return "item/detail/item_detail";
	}

}
