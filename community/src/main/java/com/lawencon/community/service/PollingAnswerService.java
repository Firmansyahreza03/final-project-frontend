package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PollingAnswerDao;
import com.lawencon.community.dao.PollingHdrDao;
import com.lawencon.community.dao.PollingOptionDao;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.pollinganswer.PojoAnswerPollling;
import com.lawencon.community.pojo.pollinganswer.PojoFindByIdPollingAnswerRes;
import com.lawencon.community.pojo.pollinganswer.PojoInsertPollingAnswerReq;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerData;
import com.lawencon.model.SearchQuery;

@Service
public class PollingAnswerService extends BaseCoreService<PollingAnswer> {

	@Autowired
	private PollingAnswerDao pollingAnswerDao;
	@Autowired
	private PollingOptionDao pollingOptionDao;
	@Autowired
	private PollingHdrDao hdrDao;
	
	public class InvalidVoteException extends PersistenceException {
		
		private static final long serialVersionUID = -6110580823865514804L;

		public InvalidVoteException() {
			super();
		}
		
		public InvalidVoteException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public InvalidVoteException(String message) {
			super(message);
		}
		
		public InvalidVoteException(Throwable cause) {
			super(cause);
		}
	}
	
	private PollingAnswer inputPollingData(PollingAnswer result, String pollingOptionId, Boolean isActive) {
		PollingOption option = pollingOptionDao.getById(pollingOptionId);
		result.setOption(option);
		result.setIsActive(isActive);

		return result;
	}

	private PojoPollingAnswerData modelToRes(PollingAnswer data) {
		PojoPollingAnswerData result = new PojoPollingAnswerData();

		result.setId(data.getId());
		result.setIsActive(data.getIsActive());
		result.setPollingOptionId(data.getOption().getId());
		result.setPollingOptionName(data.getOption().getOptionName());
		result.setVersion(data.getVersion());

		return result;
	}

	public PojoFindByIdPollingAnswerRes findById(String id) throws Exception {
		PollingAnswer data = pollingAnswerDao.getById(id);

		PojoPollingAnswerData result = modelToRes(data);
		PojoFindByIdPollingAnswerRes res = new PojoFindByIdPollingAnswerRes();
		res.setData(result);

		return res;
	}

	public SearchQuery<PojoPollingAnswerData> getAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<PollingAnswer> answerList = pollingAnswerDao.findAll(query, startPage, maxPage);
		List<PojoPollingAnswerData> results = new ArrayList<>();

		answerList.getData().forEach(d -> {
			PojoPollingAnswerData data;
			try {
				data = modelToRes(d);
				results.add(data);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		SearchQuery<PojoPollingAnswerData> result = new SearchQuery<>();
		result.setData(results);
		result.setCount(answerList.getCount());
		return result;
	}

	public PojoAnswerPollling insert(PojoInsertPollingAnswerReq data) throws Exception {
		try {
			hdrDao.checkExpiredAtFromOptionId(data.getOptionId());
			
			begin();
			PojoAnswerPollling insertRes = new PojoAnswerPollling();

			PollingAnswer reqData = inputPollingData(new PollingAnswer(), data.getOptionId(), data.getIsActive());

			PollingAnswer result = save(reqData);

			PojoInsertResData resData = new PojoInsertResData();
			resData.setId(result.getId());

			insertRes.setIsVoted(true);

			commit();
			return insertRes;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new InvalidVoteException("Voting is over");
		}
	}

	public PojoDeleteRes deleteById(String id) throws Exception {
		try {
			begin();
			boolean result = pollingAnswerDao.deleteById(id);
			PojoDeleteRes deleteRes = new PojoDeleteRes();
			if (result)
				deleteRes.setMessage("Successfully Delete Answer");
			else
				deleteRes.setMessage("Failed Delete Answer");
			commit();
			return deleteRes;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}
	
	public Integer countPollingAnswer(String id) throws Exception{
		Integer result = 0;
		try {
			result = pollingAnswerDao.countPollingAnswer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
