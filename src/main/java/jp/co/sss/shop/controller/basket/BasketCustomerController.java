package jp.co.sss.shop.controller.basket;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;

@Controller
public class BasketCustomerController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ItemRepository itemRepository;
	
	@RequestMapping(path = "/basket/add", method = RequestMethod.POST)
	public String addItem(Integer id, Model model) {
		
		List<BasketBean> basketBeanList = (ArrayList<BasketBean>)session.getAttribute("basketBeans");
		if (basketBeanList == null) {
			basketBeanList = new ArrayList<BasketBean>();
		}
		Item item = itemRepository.getById(id);
		boolean flag = true;
		for (BasketBean basketBean : basketBeanList) {
			if (item.getId() == basketBean.getId()) {
				flag = false;
				if (item.getStock() > basketBean.getOrderNum()) {
					basketBean.setOrderNum(basketBean.getOrderNum() + 1);
				} else {
					model.addAttribute("errorItem", '※' + item.getName());
				}
				break;
			}
		}
		if (flag && item.getStock() >= 1) {
			BasketBean basketBean = new BasketBean(item.getId(), item.getName(), item.getStock());
			basketBeanList.add(basketBean);
		}
		session.setAttribute("basketBeans", basketBeanList);
		return "basket/shopping_basket";
	}
	
	@RequestMapping(path = "/basket/delete", method = RequestMethod.POST)
	public String deleteItem(Integer id) {
		
		List<BasketBean> basketBeanList = (ArrayList<BasketBean>)session.getAttribute("basketBeans");
		List<BasketBean> toRemove = new ArrayList<BasketBean>();
		Item item = itemRepository.getById(id);
		for (BasketBean basketBean : basketBeanList) {
			if (item.getId() == basketBean.getId()) {
				basketBean.setOrderNum(basketBean.getOrderNum() - 1);
				if (basketBean.getOrderNum() == 0) {
					toRemove.add(basketBean);
				}
				break;
			}
		}
		basketBeanList.removeAll(toRemove);
		session.setAttribute("basketBeans", basketBeanList);
		return "basket/shopping_basket";
	}
	
	@RequestMapping(path = "/basket/allDelete", method = RequestMethod.POST)
	public String deleteAll() {
		
		session.removeAttribute("basketBeans");
		return "basket/shopping_basket";
	}
	
	@RequestMapping(path = "/basket/list")
	public String basketListGet() {
		
		return "basket/shopping_basket";
	}
}
