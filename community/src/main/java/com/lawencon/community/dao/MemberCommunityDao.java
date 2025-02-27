package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Community;
import com.lawencon.community.model.MemberCommunity;
import com.lawencon.community.model.PaymentTransaction;
import com.lawencon.community.model.User;

@Repository
public class MemberCommunityDao extends AbstractJpaDao<MemberCommunity> {

	private MemberCommunity inputData(Object obj) throws Exception {
		MemberCommunity results = new MemberCommunity();
		Object[] objArr = (Object[]) obj;
		results.setId(objArr[0].toString());

		User fkUser = new User();
		fkUser.setId(objArr[1].toString());
		results.setUser(fkUser);

		Community fkCommunity = new Community();
		fkCommunity.setId(objArr[2].toString());
		results.setCommunity(fkCommunity);

		PaymentTransaction fkPayment = new PaymentTransaction();
		fkPayment.setId(objArr[3].toString());
		results.setPayment(fkPayment);

		if (objArr[4] != null & objArr[5] != null) {
			results.setCreatedBy(objArr[4].toString());
			results.setCreatedAt(((Timestamp) objArr[5]).toLocalDateTime());
		}

		if (objArr[6] != null)
			results.setUpdatedBy(objArr[6].toString());
		if (objArr[7] != null)
			results.setUpdatedAt(((Timestamp) objArr[7]).toLocalDateTime());

		results.setIsActive(Boolean.valueOf(objArr[8].toString()));
		results.setVersion(Integer.valueOf(objArr[9].toString()));

		return results;
	}

	public List<MemberCommunity> findByPeriode(LocalDate startAt, LocalDate endAt) {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT mc.* FROM comm_member_community AS mc ")
				.append(" INNER JOIN comm_user AS u ON u.id = mc.user_id ")
				.append(" INNER JOIN comm_community AS c ON c.id = mc.community_id ")
				.append(" INNER JOIN comm_payment_transaction AS pt ON pt.id = mc.payment_id ")
				.append(" WHERE c.community_start_at >= DATE(:startAt) ")
				.append(" AND c.community_end_at <= DATE(:endAt) ")
				.append(" ORDER BY c.community_title DESC ");

		List<MemberCommunity> res = new ArrayList<>();

		List<?> rs = createNativeQuery(sql.toString())
				.setParameter("startAt", startAt)
				.setParameter("endAt", endAt)
				.getResultList();

		rs.forEach(obj -> {
			try {
				MemberCommunity data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}

	public List<MemberCommunity> findByPeriodeAndAccPayment(LocalDate startAt, LocalDate endAt) {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT mc.* FROM comm_member_community AS mc ")
				.append(" INNER JOIN comm_community AS c ON c.id = mc.community_id ")
				.append(" INNER JOIN comm_payment_transaction AS pt ON pt.id = mc.payment_id ")
				.append(" WHERE c.community_start_at >= DATE(:startAt) ")
				.append(" AND c.community_end_at <= DATE(:endAt) ")
				.append(" AND pt.is_acc = TRUE ")
				.append(" ORDER BY c.community_title DESC ");

		List<MemberCommunity> res = new ArrayList<>();

		List<?> rs = createNativeQuery(sql.toString())
				.setParameter("startAt", startAt)
				.setParameter("endAt", endAt)
				.getResultList();

		rs.forEach(obj -> {
			try {
				MemberCommunity data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}
	

	public List<MemberCommunity> findByPeriodeAndIdUser(String idUser, LocalDate startAt, LocalDate endAt) {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT mc.* FROM comm_member_community AS mc ")
				.append(" INNER JOIN comm_user AS u ON u.id = mc.user_id ")
				.append(" INNER JOIN comm_community AS c ON c.id = mc.community_id ")
				.append(" WHERE c.community_start_at >= DATE(:startAt) ")
				.append(" AND c.community_end_at <= DATE(:endAt) ")
				.append(" AND u.id = :idUser ")
				.append(" ORDER BY c.community_title DESC ");

		List<MemberCommunity> res = new ArrayList<>();

		List<?> rs = createNativeQuery(sql.toString())
				.setParameter("startAt", startAt)
				.setParameter("endAt", endAt)
				.setParameter("idUser", idUser)
				.getResultList();

		rs.forEach(obj -> {
			try {
				MemberCommunity data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}


	public List<MemberCommunity> findByPeriodeAndAccPaymentAndIdMaker(String idUser, LocalDate startAt, LocalDate endAt) {
		StringBuilder sql = new StringBuilder()
				.append(" SELECT mc.* FROM comm_member_community AS mc ")
				.append(" INNER JOIN comm_community AS c ON c.id = mc.community_id ")
				.append(" INNER JOIN comm_payment_transaction AS pt ON pt.id = mc.payment_id ")
				.append(" WHERE c.community_start_at >= DATE(:startAt) ")
				.append(" AND c.community_end_at <= DATE(:endAt) ")
				.append(" AND pt.is_acc = TRUE ")
				.append(" AND c.created_by = :idUser ")
				.append(" ORDER BY c.community_title DESC ");

		List<MemberCommunity> res = new ArrayList<>();

		List<?> rs = createNativeQuery(sql.toString())
				.setParameter("startAt", startAt)
				.setParameter("endAt", endAt)
				.setParameter("idUser", idUser)
				.getResultList();

		rs.forEach(obj -> {
			try {
				MemberCommunity data = inputData(obj);
				res.add(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return res;
	}
	
	public Boolean findIsActiveByUserIdAndCommunityId(String userId, String communityId) throws Exception{
		StringBuilder sql = new StringBuilder()
				.append(" SELECT c.is_active FROM comm_member_community mc ")
				.append(" RIGHT JOIN comm_community c ON c.id = mc.community_id ")
				.append(" WHERE (mc.user_id = :userId AND mc.community_id = :communityId) OR (c.created_by = :userId AND c.id = :communityId) ");
		
		Boolean result = null;
		try {
			Object res = createNativeQuery(sql.toString())
					.setParameter("userId", userId)
					.setParameter("communityId", communityId)
					.getSingleResult();
			
			if(res != null) {
				result = Boolean.valueOf(res.toString());
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}
}