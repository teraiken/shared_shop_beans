package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class OrderRegistCustomerController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@RequestMapping(path = "/address/input", method = RequestMethod.POST)
	public String inputAssress(@ModelAttribute OrderForm orderForm, boolean backflg, Model model) {
		if (!backflg) {
			UserBean userBean = (UserBean)session.getAttribute("user");
			User user = userRepository.getById(userBean.getId());
			userBean.setPostalCode(user.getPostalCode());
			userBean.setAddress(user.getAddress());
			userBean.setPhoneNumber(user.getPhoneNumber());
			model.addAttribute("orderForm", userBean);
		}
		return "order/regist/order_address_input";
	}
	
	@RequestMapping(path = "/payment/input", method = RequestMethod.POST)
	public String inputPayment(@Valid @ModelAttribute OrderForm orderForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "order/regist/order_address_input";
		}
		return "order/regist/order_payment_input";
	}
	
	@RequestMapping(path = "/order/check", method = RequestMethod.POST)
	public String inputPayment(@ModelAttribute OrderForm orderForm, Model model) {
		List<BasketBean> basketBeanList = (ArrayList<BasketBean>)session.getAttribute("basketBeans");
		List<BasketBean> toRemove = new ArrayList<BasketBean>();
		List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();
		Integer total = 0;
		List<String> stockShortages = new ArrayList<String>();
		List<String> stockZeros = new ArrayList<String>();
		for (BasketBean basketBean : basketBeanList) {
			Item item = itemRepository.getById(basketBean.getId());
			if (item.getStock() == 0) {
				toRemove.add(basketBean);
				stockZeros.add(item.getName());
			} else if (item.getStock() < basketBean.getOrderNum()) {
				basketBean.setOrderNum(item.getStock());
				stockShortages.add(item.getName());
			} else {
				basketBean.setOrderNum(basketBean.getOrderNum());
			}
		}
		basketBeanList.removeAll(toRemove);
		session.setAttribute("basketBeans", basketBeanList);
		model.addAttribute("stockShortages", stockShortages);
		model.addAttribute("stockZeros", stockZeros);
		for (BasketBean basketBean : basketBeanList) {
			Item item = itemRepository.getById(basketBean.getId());
			OrderItemBean orderItemBean = new OrderItemBean();
			orderItemBean.setId(basketBean.getId());
			orderItemBean.setName(basketBean.getName());
			orderItemBean.setPrice(item.getPrice());
			orderItemBean.setImage(item.getImage());
			orderItemBean.setOrderNum(basketBean.getOrderNum());
			Integer subtotal = item.getPrice() * basketBean.getOrderNum();
			orderItemBean.setSubtotal(subtotal);
			orderItemBeanList.add(orderItemBean);
			total += subtotal;
		}
		model.addAttribute("total", total);
		model.addAttribute("orderItemBeans", orderItemBeanList);
		return "order/regist/order_check";
	}
	
	@RequestMapping(path = "/order/complete", method = RequestMethod.POST)
	public String completeOrder(OrderForm orderForm) {
		Order order = new Order();
		BeanUtils.copyProperties(orderForm, order);
		UserBean userBean = (UserBean)session.getAttribute("user");
		User user = userRepository.getById(userBean.getId());
		order.setUser(user);
		orderRepository.save(order);
		List<BasketBean> basketBeanList = (ArrayList<BasketBean>)session.getAttribute("basketBeans");
		for (BasketBean basketBean : basketBeanList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(basketBean.getOrderNum());
			orderItem.setOrder(order);
			Item item = itemRepository.getById(basketBean.getId());
			orderItem.setItem(item);
			orderItem.setPrice(item.getPrice());
			orderItemRepository.save(orderItem);
			item.setStock(item.getStock() - orderItem.getQuantity());
			itemRepository.save(item);
		}
		session.removeAttribute("basketBeans");
		return "order/regist/order_complete";
	}
}