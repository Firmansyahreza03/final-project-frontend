package com.lawencon.community.dao;

import java.sql.Timestamp;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;

@Repository
public class ProfileDao extends AbstractJpaDao<Profile> {
	

	private Profile inputData(Object obj) throws Exception{
		Profile results = new Profile();
		Object[] objArr = (Object[]) obj;
		results.setId(objArr[0].toString());
		results.setFullName(objArr[1].toString());
		results.setCompanyName(objArr[2].toString());
		results.setPositionName(objArr[3].toString());
		
		Industry fkIndustry = new Industry();
		fkIndustry.setId(objArr[4].toString());
		results.setIndustry(fkIndustry);
		
		User fkUser = new User();
		fkUser.setId(objArr[5].toString());
		results.setUser(fkUser); 

		if(objArr[6] != null & objArr[7] !=null) {
			results.setCreatedBy(objArr[6].toString());
			results.setCreatedAt(((Timestamp) objArr[7]).toLocalDateTime());			
		}
		
		if(objArr[8] != null)
			results.setUpdatedBy(objArr[8].toString());
		if(objArr[9] != null)
			results.setUpdatedAt(((Timestamp) objArr[9]).toLocalDateTime());
		
		results.setIsActive(Boolean.valueOf(objArr[10].toString()));
		results.setVersion(Integer.valueOf(objArr[11].toString()));
		
		return results;
	}

	public Profile getByUserMail(String mail) throws Exception {
		StringBuilder sql = new StringBuilder()
		.append("SELECT p.* FROM comm_user u ")
		.append(" INNER JOIN comm_profile p ON u.id = p.user_id ")
		.append(" WHERE u.user_email = :mail ");
		
		Profile res = null;
		try {			
			Object rs = createNativeQuery(sql.toString())
					.setParameter("mail", mail)
					.getSingleResult();
			
			if(rs != null) {
				res = inputData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public Profile getByUserId(String id){
		StringBuilder sql = new StringBuilder()
				.append("SELECT p.* FROM comm_user u ")
				.append(" INNER JOIN comm_profile p ON u.id = p.user_id ")
				.append(" WHERE u.id = :id ");
		
		Profile res = null;
		try {			
			Object rs = createNativeQuery(sql.toString())
					.setParameter("id", id)
					.getSingleResult();
			if(rs != null) res = inputData(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}