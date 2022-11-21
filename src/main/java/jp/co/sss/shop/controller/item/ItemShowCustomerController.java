package jp.co.sss.shop.controller.item;


import java.util.ArrayList;
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
 * @author 安倍　商品一覧、売れ筋順表示、画面詳細
 * @author 鷹松　新着順表示、カテゴリ別検索
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
	 * @param sortType ソートタイプ番号
	 * @param model Viewとの値受渡し
	 * @param categoryId カテゴリID
	 * @return "item/list/item_list"商品一覧表示画面
	 */
	@RequestMapping(path = "/item/list/{sortType}")
	public String itemList(@PathVariable Integer sortType, Model model, Integer categoryId) {
		//変数定義、初期化
		List<Item> itemList = null;

		if (sortType == 1) {
			// 商品情報を全件検索(新着順)
			itemList = itemRepository.findByDeleteFlagOrderByInsertDateDescIdAsc(Constant.NOT_DELETED);
		} else {
			// 商品情報を全件検索(売れ筋順)
			itemList = itemRepository.findAllOrderById();
		}

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);
		// sortTypeをViewへ渡す
		model.addAttribute("sortType", sortType);

		return "item/list/item_list";
	}

	/**
	 * カテゴリ別検索
	 *
	 * @param sortType	ソートタイプ番号
	 * @param categoryId カテゴリID
	 * @param model Viewとの値受渡し
	 * @return "item/list/item_list"商品一覧画面
	 */
	@RequestMapping("/item/list/category/{sortType}")
	public String sortByCategory(@PathVariable int sortType, int categoryId, Model model) {
		//変数定義、初期化
		List<Item> itemList = null;

		if (sortType == 1) {
			// 商品情報をカテゴリ別検索(新着順)
			itemList = itemRepository.findByCategoryIdOrderByInsertDate(categoryId);
		} else {
			// 商品情報をカテゴリ別検索(売れ筋順)
			itemList = itemRepository.findByCategoryIdOrderById(categoryId);
		}
		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);
		// 商品情報をViewへ渡す
		model.addAttribute("items", itemBeanList);
		// sortTypeをViewへ渡す
		model.addAttribute("sortType", sortType);

		return "item/list/item_list";

	}

	/**
	 * 商品詳細画面
	 *
	 * @param id 選択された商品ID
	 * @param model Viewとの値受渡し
	 * @return "item/detail/item_detail"商品詳細画面
	 */
	@RequestMapping(path = "/item/detail/{id}")
	public String itemDetail(@PathVariable int id, Model model) {
		// 商品IDで検索しJavaBeansにコピー
		ItemBean item = BeanCopy.copyEntityToBean(itemRepository.getById(id));
		// 商品情報を全件検索(売れ筋順)
		List<Item> itemsList = itemRepository.findByCategoryIdOrderById(item.getCategoryId());
		//全件検索の結果から商品ID検索の結果と同じものを取り除く
		List<Item> itemList = new ArrayList<Item>();
		for(Item value: itemsList) {
			if(value.getId() != item.getId()) {
				itemList.add(value);
			}
		}
		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);


		// 商品情報をViewへ渡す
		model.addAttribute("item", item);
		model.addAttribute("items", itemBeanList);


		return "item/detail/item_detail";
	}

}
