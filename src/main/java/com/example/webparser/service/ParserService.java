package com.example.webparser.service;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;

public class ParserService {

    public static long id = 1;

    public static void parse() throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get("https://dubai.dubizzle.com/en/property-for-sale/residential/apartment/");
        File file = new File("C:\\Users\\User\\Desktop\\data2022-04-29_14_28.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);


        Document document = Jsoup.parse(webDriver.getPageSource());
        Elements elements = document.getElementsByClass("list-item-link");
        int i = 1;
        for (Element e :
                elements) {

            webDriver.get("https://dubai.dubizzle.com" + e.attr("href"));
            Thread.sleep(5000);
            document = Jsoup.parse(webDriver.getPageSource());

            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell;
            Elements elements1;

            // Number Link
//            webDriver.findElement(By.cssSelector("button[class='Button__Root-sc-1wygika-0 GtiDG Leads__LeadsButton-aimca1-1 fOiDhy']")).click();
//            Thread.sleep(5000);
//            elements1 = document.getElementsByClass("DisplayedPhoneNumber__Root-j2oej4-0 kPTOkb");
            elements1 = document.getElementsByAttributeValue("data-ui", "RERA Permit Number-value");
            cell = row.createCell(91, CellType.STRING);
            cell.setCellValue(elements1.text());


            // url
            cell = row.createCell(90, CellType.STRING);
            cell.setCellValue("https://dubai.dubizzle.com" + e.attr("href"));

            // Price
            elements1 = document.getElementsByClass("Text__Root-sc-1q498l3-0 Text___StyledRoot-sc-1q498l3-1 iSMDlX PropertyDPVContainer___StyledText5-xl2wz4-28 hjpqAU");
            System.out.println(elements1.text());
            String[] s = elements1.text().split(" ");
            cell = row.createCell(16, CellType.STRING);
            String x = s[1].replace(",", "");
            cell.setCellValue(x);
            cell = row.createCell(89, CellType.STRING);
            cell.setCellValue(s[0]);

            // Listed by
            elements1 = document.getElementsByAttributeValue("data-ui", "Listed By-value");
            System.out.println(elements1.text());
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(elements1.text());


            // Posted on
            elements1 = document.getElementsByAttributeValue("data-ui", "Posted On-value");
            System.out.println(elements1.text());
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(elements1.text());

            // Country City Address
            elements1 = document.getElementsByClass("PropertyDPVContainer__BreadcrumbsLink-xl2wz4-4 euLwEm Link__Root-sc-15fgovp-0 kKPupw");
            System.out.println(elements1.text());
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("UAE");
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Dubai");
            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(elements1.text());


            // Building name
            elements1 = document.getElementsByAttributeValue("data-ui", "Building-value");
            System.out.println(elements1.text());
            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(elements1.text());

            // Type
            elements1 = document.getElementsByAttributeValue("data-ui", "Apartment For-value");
            System.out.println(elements1.text());
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(elements1.text());


            // Number Of Rooms
            elements1 = document.getElementsByAttributeValue("data-testid", "listing-key-fact-bedrooms");
            System.out.println(elements1.text());
            cell = row.createCell(25, CellType.STRING);
            cell.setCellValue(elements1.text());
            // beds
            cell = row.createCell(82, CellType.STRING);
            cell.setCellValue(elements1.text());


            // sq ft
            elements1 = document.getElementsByAttributeValue("data-testid", "listing-key-fact-size");
            System.out.println(elements1.text());
            String x1 = elements1.text().replace("SqFt", "");
            cell = row.createCell(39, CellType.STRING);
            cell.setCellValue(x1.replace(",", ""));
            // Total area
            cell = row.createCell(30, CellType.STRING);
            cell.setCellValue(x1.replace(",", ""));

            // Number Of Bathroom
            elements1 = document.getElementsByAttributeValue("data-testid", "listing-key-fact-bathrooms");
            System.out.println(elements1.text());
            cell = row.createCell(36, CellType.STRING);
            cell.setCellValue(elements1.text());


            // Description
            elements1 = document.getElementsByClass("sc-AxjAm ShowMore__Root-p5zknf-0 ShowMore___StyledRoot-p5zknf-1 cOrysj");
            System.out.println(elements1.text());
            cell = row.createCell(88, CellType.STRING);
            cell.setCellValue(elements1.text());

            // Security
            elements1 = document.getElementsByAttributeValue("data-ui", "Security");
            if ((elements1.text() == null) || elements1.text().equals("")) {
                cell = row.createCell(55, CellType.STRING);
                cell.setCellValue(1);
                System.out.println(1);
            } else {
                System.out.println(0);
                cell = row.createCell(55, CellType.STRING);
                cell.setCellValue(0);
            }

            // Image Link
            elements1 = document.getElementsByClass("image-gallery-thumbnail-image");
            for (Element e1 :
                    elements1) {
                System.out.println(e1.attr("src"));
                cell = row.createCell(81, CellType.STRING);
                cell.setCellValue(e1.attr("src"));

            }

            // Id
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(id);

            
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            i++;
            id++;


            System.out.println("      ");

        }


        webDriver.close();
    }
}
