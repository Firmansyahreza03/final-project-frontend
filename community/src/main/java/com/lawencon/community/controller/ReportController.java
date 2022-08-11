package com.lawencon.community.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.report.PojoLimitTimeReq;
import com.lawencon.community.pojo.report.PojoReportPaymentByCommunityRes;
import com.lawencon.community.pojo.report.PojoReportUserByCommunityRes;
import com.lawencon.community.service.ReportService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("report")
public class ReportController {
	@Autowired
	private ReportService service;
	@Autowired
	private JasperUtil jasperUtil;

	private String companyName = "Filos Community";

	private LocalDate stringToLocalDate(String dateStr) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateStr, formatter);
	}

//	Menampilkan report informasi member yang mengikuti event ataupun course pada periode tertentu 
	@GetMapping("/admin-user")
	public ResponseEntity<?> reportUserAdmin(@RequestParam String startAt, @RequestParam String endAt) throws Exception {
		PojoLimitTimeReq req = new PojoLimitTimeReq();
		req.setStartAt(stringToLocalDate(startAt));
		req.setEndAt(stringToLocalDate(endAt));

		List<PojoReportUserByCommunityRes> listData = service.userAdminReport(req);

		Map<String, Object> map = new HashMap<>();
		map.put("title", "Member Community Report " + startAt + " s/d " + endAt);
		map.put("company", companyName);

		byte[] out = jasperUtil.responseToByteArray(listData, map, "userReport");

		String fileName = "MemberCommunityReport.pdf";

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(out);
	}
	@GetMapping("/user")
	public ResponseEntity<?> reportUser(@RequestParam String startAt, @RequestParam String endAt) throws Exception {
		PojoLimitTimeReq req = new PojoLimitTimeReq();
		req.setStartAt(stringToLocalDate(startAt));
		req.setEndAt(stringToLocalDate(endAt));

		List<PojoReportUserByCommunityRes> listData = service.userReport(req);

		Map<String, Object> map = new HashMap<>();
		map.put("title", "Member Community Report " + startAt + " s/d " + endAt);
		map.put("company", companyName);

		byte[] out = jasperUtil.responseToByteArray(listData, map, "userReport");

		String fileName = "MemberCommunityReport.pdf";

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(out);
	}
	
//	Menampilkan report informasi penghasilan dari event ataupun course pada periode tertentu 
	@GetMapping("/admin-income")
	public ResponseEntity<?> reportIncome(@RequestParam String startAt, @RequestParam String endAt)
			throws Exception {
		PojoLimitTimeReq req = new PojoLimitTimeReq();
		req.setStartAt(stringToLocalDate(startAt));
		req.setEndAt(stringToLocalDate(endAt));
		List<PojoReportPaymentByCommunityRes> listData = service.incomeCommunityAdminReport(req);

		Map<String, Object> map = new HashMap<>();
		map.put("title", "Income Community Report " + startAt + " s/d " + endAt);
		map.put("company", companyName);

		byte[] out = jasperUtil.responseToByteArray(listData, map, "incomeReport");

		String fileName = "IncomeCommunityReport.pdf";

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(out);
	}
	@GetMapping("/income")
	public ResponseEntity<?> reportIncomeAdmin(@RequestParam String startAt, @RequestParam String endAt)
			throws Exception {
		PojoLimitTimeReq req = new PojoLimitTimeReq();
		req.setStartAt(stringToLocalDate(startAt));
		req.setEndAt(stringToLocalDate(endAt));
		List<PojoReportPaymentByCommunityRes> listData = service.incomeCommunityReport(req);

		Map<String, Object> map = new HashMap<>();
		map.put("title", "Income Community Report " + startAt + " s/d " + endAt);
		map.put("company", companyName);

		byte[] out = jasperUtil.responseToByteArray(listData, map, "incomeReport");

		String fileName = "IncomeCommunityReport.pdf";

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(out);
	}
}
