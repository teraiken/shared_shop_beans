package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.util.JPQLConstant;

/**
 * itemsテーブル用リポジトリ
 *
 * @author System Shared
 * @author 安倍　売れ筋順
 * @author 鷹松　カテゴリ別新着順、カテゴリ別売れ筋順
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {


	/**  商品情報を新着順で検索*/
	public List<Item> findByDeleteFlagOrderByInsertDateDescIdAsc(int deleteFlag);

	/** 商品情報を売れ筋順で検索*/
	@Query(JPQLConstant.FIND_ORDER_BY_ORDER_COUNT)
	public List<Item> findAllOrderById();

	/** カテゴリ別新着順検索 **/
	@Query(JPQLConstant.FIND_BY_CATEGORY_ID_ORDER_BY_INSERT_DATE)
	public List<Item>findByCategoryIdOrderByInsertDate(@Param("categoryId") int categoryId);

	/** カテゴリ別売れ筋順検索 */
	@Query(JPQLConstant.FIND_BY_CATEGORY_ID_ORDER_BY_ORDER_COUNT)
	public List<Item>findByCategoryIdOrderById(@Param("categoryId") int categoryId);
}

