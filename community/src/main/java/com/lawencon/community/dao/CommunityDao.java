package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Community;
import com.lawencon.community.model.CommunityCategory;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;

@Repository
public class CommunityDao extends AbstractJpaDao<Community> {

	private Community inputData(Object obj) throws Exception {
		Community results = new Community();
		Object[] objArr = (Object[]) obj;
		results.setId(objArr[0].toString());

		results.setCommunityTitle(objArr[1].toString());
		results.setCommunityProvider(objArr[2].toString());
		results.setCommunityLocation(objArr[3].toString());
		results.setCommunityStartAt(((Timestamp) objArr[4]).toLocalDateTime());
		results.setCommunityEndAt(((Timestamp) objArr[5]).toLocalDateTime());
		results.setCommunityDesc(objArr[6].toString());
		results.setCommunityCode(objArr[7].toString());
		results.setCommunityPrice(new BigDecimal(objArr[8].toString()));
		if (objArr[9] != null) {
			File fkFile = new File();
			fkFile.setId(objArr[9].toString());
			results.setFile(fkFile);
		}
		CommunityCategory fkCategory = new CommunityCategory();
		fkCategory.setId(objArr[10].toString());
		results.setCategory(fkCategory);

		Industry fkIndustry = new Industry();
		fkIndustry.setId(objArr[11].toString());
		results.setIndustry(fkIndustry);

		if (objArr[12] != null)
			results.setCreatedBy(objArr[12].toString());
		if (objArr[13] != null)
			results.setCreatedAt(((Timestamp) objArr[13]).toLocalDateTime());

		if (objArr[14] != null)
			results.setUpdatedBy(objArr[14].toString());
		if (objArr[15] != null)
			results.setUpdatedAt(((Timestamp) objArr[15]).toLocalDateTime());

		results.setIsActive(Boolean.valueOf(objArr[16].toString()));
		results.setVersion(Integer.valueOf(objArr[17].toString()));

		return results;
	}

	public Community getByName(String name) throws Exception {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT c.* FROM comm_community c ")
				.append(" WHERE c.community_title = :name ");

		Community res = null;
		try {
			Object result = createNativeQuery(sql.toString())
					.setParameter("name", name)
					.getSingleResult();

			if (result != null) {
				Object[] objArr = (Object[]) result;
				res = inputData(objArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Community> getByCategoryCode(String code, Integer startPage, Integer maxPage) throws Exception {
		StringBuilder sql = new StringBuilder()
				.append("SELECT c.* FROM comm_community c ")
				.append(" INNER JOIN comm_community_category cc ON cc.id = c.category_id ")
				.append(" WHERE cc.category_code = :code ")
				.append(" ORDER BY c.created_at DESC ");

		List<Community> res = new ArrayList<>();

		Query q = createNativeQuery(sql.toString())
				.setParameter("code", code);

		if (startPage != null && maxPage != null) {
			q.setFirstResult(startPage)
			.setMaxResults(maxPage);
		}

		List<?> rs = q.getResultList();

		rs.forEach(obj -> {
			try {
				Community data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return res;
	}

	public List<Community> getByIdIndustryAndCategoryCode(String id, String code, Integer startPage, Integer maxPage)
			throws Exception {
		StringBuilder sql = new StringBuilder()
				.append("SELECT c.* FROM comm_community c ")
				.append(" INNER JOIN comm_community_category cc ON cc.id = c.category_id ")
				.append(" WHERE c.industry_id = :id AND cc.category_code = :code ")
				.append(" ORDER BY c.created_at DESC ");

		List<Community> res = new ArrayList<>();

		Query q = createNativeQuery(sql.toString())
				.setParameter("id", id)
				.setParameter("code", code);

		if (startPage != null && maxPage != null) {
			q.setFirstResult(startPage)
			.setMaxResults(maxPage);
		}

		List<?> rs = q.getResultList();

		rs.forEach(obj -> {
			try {
				Community data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return res;
	}

	public Community getByNameAndDate(String name, String startAt, String endAt) throws Exception {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT c.* FROM comm_community c ")
				.append(" WHERE c.community_title = :name ")
				.append(" AND c.community_start_at <= :startAt ")
				.append(" AND c.community_end_at >= :endAt ");

		Community res = null;
		try {
			Object result = createNativeQuery(sql.toString())
					.setParameter("name", name)
					.setParameter("startAt", startAt)
					.setParameter("endAt", endAt)
					.getSingleResult();

			if (result != null) {
				Object[] objArr = (Object[]) result;
				res = inputData(objArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Community> findByAccPaymentAndMemberId(String idUser, Integer startPage, Integer maxPage) {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT c.* FROM comm_member_community AS mc ")
				.append(" INNER JOIN comm_user AS u ON u.id = mc.user_id ")
				.append(" INNER JOIN comm_community AS c ON c.id = mc.community_id ")
				.append(" INNER JOIN comm_payment_transaction AS pt ON pt.id = mc.payment_id ")
				.append(" WHERE u.id = :idUser AND pt.is_acc = TRUE ")
				.append(" ORDER BY c.created_at DESC ");

		List<Community> res = new ArrayList<>();

		Query q = createNativeQuery(sql.toString())
				.setParameter("idUser", idUser);

		if (startPage != null && maxPage != null) {
			q.setFirstResult(startPage).setMaxResults(maxPage);
		}

		List<?> rs = q.getResultList();

		rs.forEach(obj -> {
			try {
				Community data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}
}
