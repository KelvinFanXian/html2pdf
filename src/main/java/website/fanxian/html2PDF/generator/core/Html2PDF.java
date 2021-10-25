package website.fanxian.html2PDF.generator.core;

import org.apache.http.util.Asserts;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static website.fanxian.html2PDF.generator.core.Configuration.which_wkhtmltopdf;


/**
 * @author Kelvin范显
 * @createDate 2018年03月27日
 */
public class Html2PDF {
    private String msg_pdfGenFailed = "PDF文件生成失败";

    public void writePdfToDir(String pdfFileName, String sourceHtmlUrl, String targetDir) {
        Asserts.notEmpty(pdfFileName, "pdfFileName不能为空");
        Asserts.notEmpty(sourceHtmlUrl, "sourceHtmlUrl不能为空");

        List<String> pdfCommand = Arrays.asList(
                which_wkhtmltopdf,
                "--disable-smart-shrinking",
                sourceHtmlUrl,
                "-"
        );

        ProcessBuilder pb = new ProcessBuilder(pdfCommand);
        Process pdfProcess;

        try {
            pdfProcess = pb.start();
            try(InputStream in = pdfProcess.getInputStream()) {
                OutputStream out = Files.newOutputStream(Paths.get(targetDir, pdfFileName+".pdf"));
                IOUtils.copy(in, out);
                waitForProcessBeforeContinueCurrentThread(pdfProcess);
                requireSuccessfulExitStatus(pdfProcess);
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }finally {
                pdfProcess.destroy();
            }
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void waitForProcessBeforeContinueCurrentThread(Process process) {
        try {
            process.waitFor(2, TimeUnit.SECONDS);
        }catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void requireSuccessfulExitStatus(Process process) {
        if (process.exitValue() != 0) {
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            try {
                String s;
                while ((s=bufferedReader.readLine())!=null) {
                    sb.append(s+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(sb.toString());
        }
    }
}
