package website.fanxian.html2PDF.generator;

import com.alibaba.fastjson.JSONObject;
import website.fanxian.html2PDF.generator.core.Generator;
import website.fanxian.html2PDF.generator.model.Demo;

import java.util.List;

/**
 * @author Kelvin范显
 * @createDate 2018年12月14日
 */
public class DemoGenerator extends Generator<Demo> {


    @Override
    public List<Demo> recordsJSONObject2Model(List<JSONObject> rows) {
        return null;
    }

    @Override
    public String getSourceHtmlUrl(Demo demo) {
        return host.concat("/demo.html?"+demo.toQueryStrig());
    }
}
