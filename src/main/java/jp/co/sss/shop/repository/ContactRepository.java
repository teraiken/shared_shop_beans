package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.Contacts;


/**
 * @author 安倍　全件検索、カテゴリ別検索
 * @author 及川　ステータス別検索
 *
 */
public interface ContactRepository extends JpaRepository<Contacts, Integer> {
	//問い合わせ全件検索（新着順）
	public List<Contacts> findAllByOrderByInsertDateDesc();
	//問い合わせカテゴリ別検索（新着順）
	public List<Contacts> findByContactCategoryOrderByInsertDateDesc(int contactCategory);
	//問い合わせステータス別検索（新着順）
	public List<Contacts> findByStatusOrderByInsertDateDesc(int status);
}
