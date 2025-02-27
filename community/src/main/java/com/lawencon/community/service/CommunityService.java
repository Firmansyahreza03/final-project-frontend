package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.CommunityCategoryDao;
import com.lawencon.community.dao.CommunityDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.model.Community;
import com.lawencon.community.model.CommunityCategory;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Profile;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.community.PojoDataCommunity;
import com.lawencon.community.pojo.community.PojoFindByIdCommunityRes;
import com.lawencon.community.pojo.community.PojoInsertCommunityReq;
import com.lawencon.community.pojo.community.PojoUpdateCommunityReq;
import com.lawencon.model.SearchQuery;
import com.lawencon.security.PrincipalServiceImpl;

@Service
public class CommunityService extends BaseCoreService<Community> {
	@Autowired
	private CommunityDao communityDao;
	@Autowired
	private IndustryDao industryDao;
	@Autowired
	private CommunityCategoryDao communityCategoryDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private CodeService codeService;
	@Autowired
	private PrincipalServiceImpl principalServiceImpl;

	public LocalDateTime stringToLocalDateTime(String dateTimeStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

	private Community inputCommunityData(Community result, Boolean isActive, String title, String provider,
			String location, String startAt, String endAt, String desc, BigDecimal price, String idCategory,
			String idIndustry) throws Exception {

		CommunityCategory fkCategory = communityCategoryDao.getById(idCategory);
		Industry fkIndustry = industryDao.getById(idIndustry);

		result.setIsActive(isActive);

		result.setCommunityTitle(title);
		result.setCommunityProvider(provider);
		result.setCommunityLocation(location);
		result.setCommunityStartAt(stringToLocalDateTime(startAt));
		result.setCommunityEndAt(stringToLocalDateTime(endAt));
		result.setCommunityDesc(desc);
		result.setCommunityPrice(price);
		result.setCategory(fkCategory);
		result.setIndustry(fkIndustry);

		return result;
	}

	private PojoDataCommunity modelToRes(Community data) throws Exception {
		PojoDataCommunity result = new PojoDataCommunity();
		Industry fkIndustry = industryDao.getById(data.getIndustry().getId());
		CommunityCategory fkCategory = communityCategoryDao.getById(data.getCategory().getId());

		result.setId(data.getId());
		result.setIsActive(data.getIsActive());
		result.setVersion(data.getVersion());

		result.setTitle(data.getCommunityTitle());
		result.setProvider(data.getCommunityProvider());
		result.setLocation(data.getCommunityLocation());
		result.setStartAt(data.getCommunityStartAt());
		result.setEndAt(data.getCommunityEndAt());
		result.setDesc(data.getCommunityDesc());
		result.setCode(data.getCommunityCode());
		result.setPrice(data.getCommunityPrice());

		result.setIdCategory(fkCategory.getId());
		result.setNameCategory(fkCategory.getCategoryName());
		result.setIdIndustry(fkIndustry.getId());
		result.setNameIndustry(fkIndustry.getIndustryName());

		if (data.getFile() != null) {
			File fkFile = fileDao.getById(data.getFile().getId());
			result.setIdFile(fkFile.getId());
			result.setNameFile(fkFile.getFileName());
			result.setExtFile(fkFile.getFileExtension());
		}

		return result;
	}

	public PojoFindByIdCommunityRes findById(String id) throws Exception {
		Community data = communityDao.getById(id);

		PojoDataCommunity result = modelToRes(data);
		PojoFindByIdCommunityRes resultData = new PojoFindByIdCommunityRes();
		resultData.setData(result);

		return resultData; 
	}

	public SearchQuery<PojoDataCommunity> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Community> communityList = communityDao.searchQueryTable(query, startPage, maxPage, "communityCode",
				"communityTitle", "communityProvider", "category.categoryName", "industry.industryName");
		List<PojoDataCommunity> resultList = new ArrayList<>();

		communityList.getData().forEach(d -> {
			PojoDataCommunity data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataCommunity> result = new SearchQuery<PojoDataCommunity>();
		result.setData(resultList);
		result.setCount(communityList.getCount());
		return result;
	}

	public PojoInsertRes insert(PojoInsertCommunityReq data) throws Exception {
		try {
			PojoInsertRes insertRes = new PojoInsertRes();

			Community reqData = inputCommunityData(new Community(), true, data.getTitle(), data.getProvider(),
					data.getLocation(), data.getStartAt(), data.getEndAt(), data.getDesc(), data.getPrice(),
					data.getIdCategory(), data.getIdIndustry());

			reqData.setCommunityCode(codeService.generateRandomCodeAll().getCode());

			if (data.getNameFile() != null) {
				File fkFile = new File();
				fkFile.setFileName(data.getNameFile());
				fkFile.setFileExtension(data.getExtFile());
				fkFile.setCreatedBy(super.getAuthPrincipal());
				fileDao.save(fkFile);
				reqData.setFile(fkFile);
			}

			begin();
			Community result = super.save(reqData);
			commit();
			PojoInsertResData resData = new PojoInsertResData();
			resData.setId(result.getId());

			insertRes.setData(resData);
			insertRes.setMessage("Successfully Adding Community");

			return insertRes;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	public PojoUpdateRes update(PojoUpdateCommunityReq data) throws Exception {
		try {
			PojoUpdateRes updateRes = new PojoUpdateRes();
			Community reqData = communityDao.getById(data.getId());

			reqData = inputCommunityData(reqData, data.getIsActive(), data.getTitle(), data.getProvider(),
					data.getLocation(), data.getStartAt(), data.getEndAt(), data.getDesc(), data.getPrice(),
					data.getIdCategory(), data.getIdIndustry());

			File fkFile;
			if (reqData.getFile() == null) {
				fkFile = new File();
			} else {
				fkFile = fileDao.getById(reqData.getFile().getId());
			}

			if (data.getNameFile() != null) {
				fkFile.setFileName(data.getNameFile());
				fkFile.setFileExtension(data.getExtFile());
				if (fkFile.getId() != null) {
					fkFile.setUpdatedBy(getAuthPrincipal());
				} else {
					fkFile.setCreatedBy(getAuthPrincipal());
				}
				fileDao.save(fkFile);
				reqData.setFile(fkFile);
			}

			begin();
			Community result = communityDao.save(reqData);
			commit();
			PojoUpdateResData resData = new PojoUpdateResData();
			resData.setVersion(result.getVersion());

			updateRes.setData(resData);
			updateRes.setMessage("Successfully Editing Community");
			return updateRes;

		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	public PojoDeleteRes deleteById(String id) throws Exception {
		try {
			begin();
			boolean result = communityDao.deleteById(id);
			commit();
			PojoDeleteRes deleteRes = new PojoDeleteRes();
			if (result)
				deleteRes.setMessage("Successfully Delete Community");
			else
				deleteRes.setMessage("Failed Delete Community");
			return deleteRes;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	public SearchQuery<PojoDataCommunity> getByIdIndustryAndCategoryCode(String email, String code, Integer startPage,
			Integer maxPage) throws Exception {
		Profile profile = profileDao.getByUserMail(email);

		List<Community> communities = communityDao.getByIdIndustryAndCategoryCode(profile.getIndustry().getId(), code,
				startPage, maxPage);

		SearchQuery<Community> communityList = findAll(() -> communities);

		List<PojoDataCommunity> resultList = new ArrayList<>();

		communityList.getData().forEach(d -> {
			PojoDataCommunity data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataCommunity> result = new SearchQuery<PojoDataCommunity>();
		result.setData(resultList);
		result.setCount(communityList.getData().size());
		return result;
	}

	public SearchQuery<PojoDataCommunity> getByCategoryCode(String code, Integer startPage, Integer maxPage)
			throws Exception {

		List<Community> communities = communityDao.getByCategoryCode(code, startPage, maxPage);

		SearchQuery<Community> communityList = findAll(() -> communities);

		List<PojoDataCommunity> resultList = new ArrayList<>();

		communityList.getData().forEach(d -> {
			PojoDataCommunity data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataCommunity> result = new SearchQuery<PojoDataCommunity>();
		result.setData(resultList);
		result.setCount(communityList.getData().size());
		return result;
	}


	public SearchQuery<PojoDataCommunity> getByLogUser( Integer startPage, Integer maxPage) throws Exception {
		List<Community> communities = communityDao.findByAccPaymentAndMemberId
				(principalServiceImpl.getAuthPrincipal(), startPage, maxPage);

		SearchQuery<Community> communityList = findAll(() -> communities);

		List<PojoDataCommunity> resultList = new ArrayList<>();

		communityList.getData().forEach(d -> {
			PojoDataCommunity data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataCommunity> result = new SearchQuery<PojoDataCommunity>();
		result.setData(resultList);
		result.setCount(communityList.getData().size());
		return result;
	}
}
