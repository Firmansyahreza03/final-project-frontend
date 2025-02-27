package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.article.PojoDataArticle;
import com.lawencon.community.pojo.article.PojoFindByIdArticleRes;
import com.lawencon.community.pojo.article.PojoInsertArticleReq;
import com.lawencon.community.pojo.article.PojoUpdateArticleReq;
import com.lawencon.model.SearchQuery;

@Service
public class ArticleService extends BaseCoreService<Article> {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private IndustryDao industryDao;

	private Article inputArticleData(Article result, Boolean isActive, String title, String content, String idIndustry) throws Exception {
		Industry fkIndustry = industryDao.getById(idIndustry);

		result.setIsActive(isActive);

		result.setArticleTitle(title);
		result.setArticleContent(content);
		result.setIndustry(fkIndustry);

		return result;
	}

	private PojoDataArticle modelToRes(Article data) throws Exception {
		PojoDataArticle result = new PojoDataArticle();
		Industry fkIndustry = industryDao.getById(data.getIndustry().getId());

		result.setId(data.getId());
		result.setIsActive(data.getIsActive());
		result.setVersion(data.getVersion());
		result.setCreatedAt(data.getCreatedAt());

		result.setTitle(data.getArticleTitle());
		result.setContent(data.getArticleContent());
		
		result.setIdIndustry(fkIndustry.getId());
		result.setNameIndustry(fkIndustry.getIndustryName());

		return result;
	}

	public PojoFindByIdArticleRes findById(String id) throws Exception {
		Article data = articleDao.getById(id);

		PojoDataArticle result = modelToRes(data);
		PojoFindByIdArticleRes resultData = new PojoFindByIdArticleRes();
		resultData.setData(result);

		return resultData;
	}

	public SearchQuery<PojoDataArticle> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Article> getAllArticle = articleDao.searchQueryTable(query, startPage, maxPage, 
				"articleTitle", "articleContent", "industry.industryName");

		List<PojoDataArticle> resultList = new ArrayList<>();

		getAllArticle.getData().forEach(d -> {
			PojoDataArticle data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataArticle> result = new SearchQuery<PojoDataArticle>();
		result.setData(resultList);
		result.setCount(getAllArticle.getCount());
		return result;
	}

	public PojoInsertRes insert(PojoInsertArticleReq data) throws Exception {
		try {
			PojoInsertRes insertRes = new PojoInsertRes();

			Article reqData = inputArticleData(new Article(), true, data.getTitle(), data.getContent(), data.getIdIndustry());
			
			begin();
			Article result = save(reqData);
			commit();
			
			PojoInsertResData resData = new PojoInsertResData();
			resData.setId(result.getId());

			insertRes.setData(resData);
			insertRes.setMessage("Successfully Adding Article");

			return insertRes;
		} catch (Exception e) {
			rollback();
			throw new Exception(e);
		}
	}

	public PojoUpdateRes update(PojoUpdateArticleReq data) throws Exception {
		try {
			PojoUpdateRes updateRes = new PojoUpdateRes();
			Article reqData = articleDao.getById(data.getId());

			reqData = inputArticleData(reqData, data.getIsActive(), data.getTitle(), data.getContent(), data.getIdIndustry());

			begin();
			Article result = save(reqData);
			commit();
			
			PojoUpdateResData resData = new PojoUpdateResData();
			resData.setVersion(result.getVersion());

			updateRes.setData(resData);
			updateRes.setMessage("Successfully Editing Article");
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
			boolean result = articleDao.deleteById(id);
			commit();
			PojoDeleteRes deleteRes = new PojoDeleteRes();
			if (result)
				deleteRes.setMessage("Successfully Delete Article");
			else
				deleteRes.setMessage("Failed Delete Article");
			return deleteRes;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	public SearchQuery<PojoDataArticle> getAllByIdIndustry(String idx, Integer startPage, Integer maxPage)
			throws Exception {
		List<Article> tmp = articleDao.getByIdIndustry(idx, startPage, maxPage);

		SearchQuery<Article> articleList = findAll(() -> tmp);

		List<PojoDataArticle> resultList = new ArrayList<>();

		articleList.getData().forEach(d -> {
			PojoDataArticle data;
			try {
				data = modelToRes(d);
				resultList.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		SearchQuery<PojoDataArticle> result = new SearchQuery<PojoDataArticle>();
		result.setData(resultList);
		result.setCount(articleList.getData().size());
		return result;
	}
}
