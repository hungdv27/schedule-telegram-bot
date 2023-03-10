package com.example.schedule_utc_bot1.job;


import com.example.schedule_utc_bot1.model.ExcelDTO.LessonExcelDTO;
import com.example.schedule_utc_bot1.model.ExcelDTO.ScheduleExcelDTO;
import com.example.schedule_utc_bot1.model.ExcelDTO.StudentExcelDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class ReadExcelFile {

    public List<ScheduleExcelDTO> schedules = new ArrayList<>();
    public StudentExcelDTO student = new StudentExcelDTO();

    public ReadExcelFile(String pathfile) {
        readFile(pathfile);
    }


    void readFile(String pathFile) {
        try {
            FileInputStream file = new FileInputStream(pathFile);
            //Create Workbook instance holding reference to .xlsx fil111e
            HSSFWorkbook workbook = new HSSFWorkbook(file);


            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            int lastRowIndex = sheet.getLastRowNum();


            String studentName = getCellContent("C6", sheet);
            String studentCode = getCellContent("F6", sheet);
            String className = getCellContent("C7", sheet);
            String major = getCellContent("F8", sheet);
            String semester = getCellContent("A4", sheet);
            student.setStudentCode(studentCode);
            student.setStudentName(studentName);
            student.setMajor(major);
            student.setClassName(className);
            student.setStudentCode(studentCode);

//
//            Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() <= 10 || row.getRowNum() >= lastRowIndex - 7) continue;
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                String day = null;
                String subjectName = null;
                String lessonInDay = null;
                String address = null;
                Date startDate = null;
                Date endDate = null;
                String dateString = null;


                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    if (cell.getRowIndex() >= 10 && cell.getRowIndex() <= lastRowIndex - 7) {
                        switch (cell.getAddress().toString().substring(0, 1)) {
                            case "A":
                                day = getCellContent(cell.getAddress().toString(), sheet);
                                break;
                            case "D":
                                subjectName = getCellContent(cell.getAddress().toString(), sheet);
                                break;
                            case "I":
                                lessonInDay = getCellContent(cell.getAddress().toString(), sheet);
                                break;
                            case "J":
                                address = getCellContent(cell.getAddress().toString(), sheet);
                                break;
                            case "K":
                                dateString = getCellContent(cell.getAddress().toString(), sheet);
                                String[] dateList = dateString.trim().split("-");
                                startDate = formatDateYMD(dateList[0]);
                                endDate = formatDateYMD(dateList[1]);
                                break;
                        }
                    }
                }
                addToSchedule(startDate, endDate, day, lessonInDay, subjectName, address);
//                System.out.println();
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        schedules.forEach(s->{
//            System.out.println(s.toString());
//        });
        student.setSchedules(schedules);
    }

    private void addToSchedule(Date startDate, Date endDate, String day, String lessonInDay, String subjectName, String address) {
        List<Date> dateList = null;
        // console.log(start_date,end_date,day,lesson,subject_name,address);
        dateList = findDatewithDay(startDate, endDate, day);
        for (Date date : dateList) {
            int found = findIndexDate(date);
            if (found == -1) {
                List<LessonExcelDTO> lessons = new ArrayList<>();
                lessons.add(new LessonExcelDTO(lessonInDay, subjectName, address));
                schedules.add(new ScheduleExcelDTO(date, lessons));
            } else {
                schedules.get(found).addOneLesson(new LessonExcelDTO(lessonInDay, subjectName, address));
            }
        }
    }


    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private boolean checkDayOfWeek(String day, Date date) {
        //The value of dayOfWeek from 1 (Monday) to 7 (Sunday).
        var dayOfWeek_check = Integer.parseInt(day) - 1;
        DayOfWeek dayOfWeek = DayOfWeek.from(convertToLocalDateViaSqlDate(date));
        return dayOfWeek.getValue() == dayOfWeek_check;
    }

    private List<Date> findDatewithDay(Date start_date, Date end_date, String day) {
        List<Date> dateList = new ArrayList<>();
        for (var i = start_date.getTime(); i <= end_date.getTime(); i += (1000 * 60 * 60 * 24)) {
            Date date_check = new Date(i);
            if (checkDayOfWeek(day, date_check)) {
                dateList.add(date_check);
            }
        }
        return dateList;
    }

    private int findIndexDate(Date date) {
        int index = -1;
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getDate().getTime() == date.getTime()) {
                return i;
            }
        }
        return index;
    }

    public Date formatDateYMD(String dateStr) throws ParseException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            return formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCellStyle(String cellName, HSSFSheet sheet) {
        if (StringUtils.isBlank(cellName)) {
            System.out.println(":: getCellStyle>> CellName is Blank");
            return null;
        }
        try {
            CellAddress cellAddress = new CellAddress(cellName.trim());
            Row row = sheet.getRow(cellAddress.getRow());
            Cell cell = row.getCell(cellAddress.getColumn());

            return cell.getCellStyle() + "\t" + cellName.trim();
        } catch (Exception e) {
            System.out.format(":: getCellStyle >> CellName[%s] : Error get value \n", cellName);
        }
        return null;
    }

    private String getCellContent(String cellName, HSSFSheet sheet) {
        String stringReturn = null;
        if (StringUtils.isBlank(cellName)) {
            System.out.println(":: getCellContent >> CellName is Blank");
            return null;
        }
        try {
            CellAddress cellAddress = new CellAddress(cellName.trim());
            Row row = sheet.getRow(cellAddress.getRow());
            Cell cell = row.getCell(cellAddress.getColumn());
            switch (cell.getCellType()) {
                case BOOLEAN:
                    stringReturn = String.valueOf(cell.getBooleanCellValue());
                case NUMERIC:
                    stringReturn = String.valueOf(cell.getNumericCellValue());
                case STRING:
                    stringReturn = String.valueOf(cell.getRichStringCellValue());
            }

        } catch (Exception e) {
            System.out.format(":: getCellContent >> CellName[%s] : Error get value", cellName);
        }
        return stringReturn;
    }


}
