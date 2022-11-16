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
		// 商品情報を全件検索(売れ筋順)
		List<Item> itemList = itemRepository.findAllOrderById();

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);

		return "index";
	}

	/**
	 * 新着順、売れ筋順表示
	 *
	 * @param sortType
	 * @param model
	 * @param categoryId
	 * @return 商品一覧表示画面
	 */
	@RequestMapping(path = "/item/list/{sortType}")
	public String itemList(@PathVariable Integer sortType, Model model, Integer categoryId) {

		List<Item> itemList = null;

		if (sortType == 1) {
			// 商品情報を全件検索(新着順)
			itemList = itemRepository.findByDeleteFlagOrderByInsertDateDescIdAsc(Constant.NOT_DELETED);
			model.addAttribute("sortType", 1);
		} else {
			// 商品情報を全件検索(売れ筋順)
			itemList = itemRepository.findAllOrderById();
			model.addAttribute("sortType", 2);
		}

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);

		return "item/list/item_list";
	}


	/**
	 * 商品詳細画面
	 *
	 * @param id
	 * @param model
	 * @return 商品詳細画面
	 */
	@RequestMapping(path = "/item/detail/{id}")
	public String itemDetail(@PathVariable int id, Model model) {
		// 商品IDで検索しJavaBeansにコピー
		ItemBean item = BeanCopy.copyEntityToBean(itemRepository.getById(id));
		// 商品情報をViewへ渡す
		model.addAttribute("item", item);
		return "item/detail/item_detail";
	}

	@RequestMapping(path = "/item/list/category/{sortType}")
	public String itemListCategory1 (@PathVariable Integer sortType, Integer categoryId, Model model){

		List<Item> itemList = null;

		if(sortType == 1) {
			itemList = itemRepository.findByCategoryIdOrderByInsertDate(categoryId);

		}else {
			itemList = itemRepository.findByCategoryIdOrderById(categoryId);
		}

		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		model.addAttribute("items", itemBeanList);
		model.addAttribute("sortType",sortType);

		return "item/list/item_list";
	}


}
