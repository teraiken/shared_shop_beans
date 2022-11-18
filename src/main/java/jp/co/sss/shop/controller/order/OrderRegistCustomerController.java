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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String inputAddress(@ModelAttribute OrderForm orderForm, boolean backflg, Model model) {
		
		if (!backflg) {
			UserBean userBean = (UserBean)session.getAttribute("user");
			User user = userRepository.getById(userBean.getId());
			orderForm.setPostalCode(user.getPostalCode());
			orderForm.setAddress(user.getAddress());
			orderForm.setName(user.getName());
			orderForm.setPhoneNumber(user.getPhoneNumber());
			model.addAttribute("orderForm", orderForm);
		}
		return "order/regist/order_address_input";
	}
	
	@RequestMapping(path = "/address/input")
	public String inputAddressRedirect() {
		
		return "order/regist/order_address_input";
	}
	
	@RequestMapping(path = "/payment/input", method = RequestMethod.POST)
	public String inputPayment(@Valid @ModelAttribute OrderForm orderForm, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderForm", result);
			redirectAttributes.addFlashAttribute("orderForm", orderForm);
			return "redirect:/address/input";
		}
		return "order/regist/order_payment_input";
	}
	
	@RequestMapping(path = "/order/check", method = RequestMethod.POST)
	public String checkOrder(@ModelAttribute OrderForm orderForm, Model model) {
		
		List<BasketBean> basketBeanList = (ArrayList<BasketBean>)session.getAttribute("basketBeans");
		List<BasketBean> toRemove = new ArrayList<BasketBean>();
		List<String> stockZeros = new ArrayList<String>();
		List<String> stockShortages = new ArrayList<String>();
		for (BasketBean basketBean : basketBeanList) {
			Item item = itemRepository.getById(basketBean.getId());
			if (item.getStock() == 0) {
				toRemove.add(basketBean);
				stockZeros.add(item.getName());
			} else if (item.getStock() < basketBean.getOrderNum()) {
				basketBean.setOrderNum(item.getStock());
				stockShortages.add(item.getName());
			}
		}
		basketBeanList.removeAll(toRemove);
		session.setAttribute("basketBeans", basketBeanList);
		model.addAttribute("stockZeros", stockZeros);
		model.addAttribute("stockShortages", stockShortages);
		List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();
		Integer total = 0;
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
		model.addAttribute("orderItemBeans", orderItemBeanList);
		model.addAttribute("total", total);
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
			Item item = itemRepository.getById(basketBean.getId());
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(basketBean.getOrderNum());
			orderItem.setOrder(order);
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