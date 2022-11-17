package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.Contacts;


public interface ContactRepository extends JpaRepository<Contacts, Integer> {

	public List<Contacts> findAllByOrderByInsertDateDesc();

	public List<Contacts> findByContactCategoryOrderByInsertDateDesc(int contactCategory);

}
