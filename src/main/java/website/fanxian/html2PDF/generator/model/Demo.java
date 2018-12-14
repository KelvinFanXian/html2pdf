package website.fanxian.html2PDF.generator.model;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author Kelvin范显
 * @createDate 2018年12月14日
 */
@Data
@AllArgsConstructor
public class Demo {
    private String name,skill,from,at;

    public static LinkedHashMap<String, String> map2Excel(){
        LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
        map.put("name", "姓名");
        map.put("skill", "技能");
        map.put("from", "时间");
        map.put("at", "地址");
        return map;
    }

    public String toQueryStrig(){
        return "name="+this.name+
                "&skill="+this.skill+
                "&from="+this.from+
                "&at="+this.at;
    }
}
