package com.journaldev.spring.controller;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.User;

import net.sf.jxls.transformer.XLSTransformer;

@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(@Validated User user, HttpServletResponse response, Model model) throws Exception {
		System.out.println("User Page Requested");
		model.addAttribute("userName", user.getUserName());
		Map<String, Object> beans =  new HashMap<String, Object>();
		//beans bao gồm lst ngày cùng tháng năm, tên nhân viên của Thảo quản lý, lặp qua danh sách tên do người dùng submit để tạo sheet cho từng người
		export(response,beans);
		return "user";
	}
	public void export(HttpServletResponse response, Map<String, Object> beans) throws Exception {
		// Tao file
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream tempFile = classLoader.getResourceAsStream("/templates/Temp_Thao.xlsx");
		Workbook book = new XLSTransformer().transformXLS(tempFile, beans);
		tempFile.close();
		// Download file
		response.setContentType("application/vnd.ms-excel");
		//response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + common.util.EndcodeUtil.urlEncodeUtf8(templateName) + ".xlsx");
		ServletOutputStream out = response.getOutputStream();
		book.write(out);
		out.close();
	}
}
