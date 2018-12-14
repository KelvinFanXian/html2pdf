package website.fanxian.html2PDF.generator.core;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * wkhtmltopdf.exe download:
 *  https://wkhtmltopdf.org/downloads.html
 * @author Kelvin范显
 * @createDate 2018年12月13日
 */
public class Configuration {
    static final String which_wkhtmltopdf = "D:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";

    final static String targetDir = "E:\\temp\\pdfGen";
    final static String zipDir = "E:\\temp\\zipGen";
    final static Path logPath = Paths.get(targetDir, "noScore.log");

}
