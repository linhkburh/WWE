package com.journaldev.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
		List<String> lstStaff = new ArrayList<>();
		lstStaff.add("Hậu Phạm");
		lstStaff.add("Nhàn");
		lstStaff.add("Hà Mint(NA)");
		lstStaff.add("Vinh Khoa");
		lstStaff.add("Lương Mai");
		lstStaff.add("Nguyễn Trang");
		lstStaff.add("Thúy Lê");
		lstStaff.add("Vũ Hương");
		lstStaff.add("Thăng Nhật");
		lstStaff.add("Ngát Mint");
		lstStaff.add("Phương Thúy");
		lstStaff.add("Nguyện");
		lstStaff.add("Trang Bi");
		lstStaff.add("Nhung Sakura");
		lstStaff.add("Nguyễn Thủy");
		model.addAttribute("lstStaff", lstStaff);
		return "home";
	}

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, Object> beans =  new HashMap<String, Object>();
		String dateData = request.getParameter("dateData");
		String[] staffData = request.getParameterValues("staffData");
		List<String> allDayOfM = getAllDatesInMonth(dateData);
		LocalDate date = LocalDate.parse(dateData);
		String time = "T"+String.valueOf(date.getMonthValue())+"/"+String.valueOf(date.getYear());
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream tempFile = classLoader.getResourceAsStream("/templates/Temp_Thao.xlsx");
		beans.put("dayOfMonth", allDayOfM);
		beans.put("time", time);
		Workbook book = new XLSTransformer().transformXLS(tempFile, beans);
		processExcel(book,staffData);
		tempFile.close();
		// Download file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" +time+ ".xlsx");
		ServletOutputStream out = response.getOutputStream();
		book.write(out);
		out.close();
	}

	private List<String> getAllDatesInMonth(String dateString) {
		List<String> allDatesInMonth = new ArrayList<>();
		LocalDate date = LocalDate.parse(dateString);
		LocalDate firstDayOfMonth = date.withDayOfMonth(1);
		LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());
		for (LocalDate d = firstDayOfMonth; !d.isAfter(lastDayOfMonth); d = d.plusDays(1)) {
			allDatesInMonth.add(String.valueOf(d.getDayOfMonth()) + "/" + String.valueOf(d.getMonthValue()));
		}
		return allDatesInMonth;
	}

	private void processExcel(Workbook book, String[] staffData) {
		Sheet sheetTemp = book.getSheet("SheetTemp");
		for (int i = 0; i < staffData.length; i++) {
				if(!staffData[i].equals("")) {
				Sheet clonedSheet = book.cloneSheet(book.getSheetIndex(sheetTemp));
				CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 1, 8);
				clonedSheet.addMergedRegion(mergedRegion);
				clonedSheet.getRow(0).getCell(1).setCellValue(staffData[i]);
				book.setSheetName(book.getSheetIndex(clonedSheet), (i+1)+"."+staffData[i]);
			}
		}
		book.removeSheetAt(book.getSheetIndex(sheetTemp));
	}
}
