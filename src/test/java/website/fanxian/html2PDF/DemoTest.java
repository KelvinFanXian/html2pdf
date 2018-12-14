package website.fanxian.html2PDF;

import org.junit.Test;
import website.fanxian.html2PDF.generator.DemoGenerator;
import website.fanxian.html2PDF.generator.model.Demo;

/**
 * @author Kelvin范显
 * @createDate 2018年12月14日
 */
public class DemoTest {

    @Test
    public void testGenerateDemo() {
        DemoGenerator demoGenerator = new DemoGenerator();
        demoGenerator.clean();
        demoGenerator.generatePDF("pdfName", new Demo("a","b","c","d"));
    }
}
