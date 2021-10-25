package website.fanxian.html2PDF.generator.core;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.client.RestTemplate;
import pl.touk.throwing.ThrowingConsumer;
import website.fanxian.html2PDF.uitl.ExcelUtils;
import website.fanxian.html2PDF.uitl.FileToZip;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kelvin范显
 * @createDate 2018年12月13日
 */
public abstract class Generator<T> {
    public static final String host = "http://127.0.0.1:8080";

    final Html2PDF html2PDF = new Html2PDF();
    protected final RestTemplate restTemplate;

    public Generator() {
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
    }

    /// 解析数据源

    public List<JSONObject> parseExcelThenGetRecords(String excelPath, String... fieldNames) throws IOException {
        return ExcelUtils.importWorkBook(Files.newInputStream(Paths.get(excelPath)),
                Arrays.asList(fieldNames),
                null,JSONObject.class)
                .parallelStream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public abstract List<T> recordsJSONObject2Model(List<JSONObject> rows);

    public abstract String getSourceHtmlUrl(T obj);

    public void generatePDF(String pdfName, T obj){
        html2PDF.writePdfToDir(
                pdfName,
                getSourceHtmlUrl(obj),
                Configuration.targetDir
        );
    }

    /**
     * 打印汇总信息到excel
     * @param excelName
     * @param obj
     */
    public void genSummaryExcel(String excelName, LinkedHashMap<String,String> map, List<T> obj){
        try(OutputStream excelOut = Files.newOutputStream(Paths.get(Configuration.targetDir, excelName.concat(".xlsx")))) {
            ByteArrayResource excelResource =
                    ExcelUtils.exportExcel(excelName, map, obj);
            excelOut.write(excelResource.getByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("---成功完成Excel打印---");
    }

    public void packageToZip(String zipName){
        FileToZip.fileToZip(Configuration.targetDir, Configuration.zipDir, zipName);
    }

    public void clean(){
        try {
            Path targetDir = Paths.get(Configuration.targetDir);
            Path zipDir = Paths.get(Configuration.zipDir);
            //clear directory
            Files.walk(targetDir, FileVisitOption.FOLLOW_LINKS)
                    .filter(t -> !t.toString().equalsIgnoreCase(targetDir.toString()))
                    .forEach(ThrowingConsumer.unchecked(Files::delete));
            Files.walk(zipDir, FileVisitOption.FOLLOW_LINKS)
                    .filter(t -> !t.toString().equalsIgnoreCase(zipDir.toString()))
                    .forEach(ThrowingConsumer.unchecked(Files::delete));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void log(String msg) {
        byte[] log = msg.concat("\r\n").getBytes();
        Path logPath = Configuration.logPath;
        try {
            Files.write(logPath, log, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
