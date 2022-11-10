package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.util.JPQLConstant;

/**
 * ordersテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findTop1ByOrderByInsertDateDesc();

	// 会員IDに該当する注文情報を注文日付順で検索
	List<Order> findByUserIdOrderByInsertDateDescIdAsc(int userId);

	// 注文日付順で注文情報すべてを検索
	@Query(JPQLConstant.FIND_ALL_ORDERS_ORDER_BY_INSERT_DATE)
	List<Order> findAllOrderByInsertDateDesc();
}
