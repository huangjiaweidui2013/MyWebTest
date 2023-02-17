package com.huang.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;

/**
 * @author localuser
 * create at 2023/1/4 14:18
 * @description 使用 org.apache.commons.text.StringSubstitutor 填充模板字符串
 */
public class StringSubstitutorTest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        String targetStr = "";
        String templateStr = "";
        String requestParams = "{\"docId\": \"1610435429345845248\", \"ownerId\": 2124, \"pathMap\": [], " +
                "\"fromFile\": false, \"sourcePath\": \"/data/upload/20230104/1610435420542001152/dwgs/1610435429345845248.dwg\"," +
                " \"showFontList\": true, \"thumbnailPath\": \"/data/temp/20230104/1610435420542001152/1610435429345845248/1610435429345845248\", " +
                "\"graphicDataPath\": \"/data/temp/20230104/1610435420542001152/1610435429345845248/layout_default.lmf\", " +
                "\"projectFontList\": [\"/data/upload/0/fonts/aehalf.shx\", \"/data/upload/0/fonts/complex.shx\", \"/data/upload/0/fonts/DIM.SHX\"," +
                " \"/data/upload/0/fonts/gdt.shx\", \"/data/upload/0/fonts/HAND1.SHX\", \"/data/upload/0/fonts/IC-complex.shx\"," +
                " \"/data/upload/0/fonts/ic-dim.shx\", \"/data/upload/0/fonts/GBCBIG.SHX\", \"/data/upload/0/fonts/ic-hand1.shx\", " +
                "\"/data/upload/0/fonts/HZTXT.SHX\", \"/data/upload/0/fonts/HZTXT1.SHX\", \"/data/upload/0/fonts/IC-isocp.shx\", " +
                "\"/data/upload/0/fonts/IC-italicc.shx\", \"/data/upload/0/fonts/ic-italict.shx\", \"/data/upload/0/fonts/IC-italic.shx\", " +
                "\"/data/upload/0/fonts/IC-Monotxt.shx\", \"/data/upload/0/fonts/ic-msimplex.shx\", \"/data/upload/0/fonts/IC-Romanc.shx\", " +
                "\"/data/upload/0/fonts/IC-Romand.shx\", \"/data/upload/0/fonts/ic-romant.shx\", \"/data/upload/0/fonts/ic-simplex.shx\"," +
                " \"/data/upload/0/fonts/ltypeshp.shx\", \"/data/upload/0/fonts/IC-txt.shx\", \"/data/upload/0/fonts/romans.shx\", " +
                "\"/data/upload/0/fonts/MSIMPLEX.SHX\", \"/data/upload/0/fonts/simplex.shx\", \"/data/upload/0/fonts/SHZTXT.SHX\", " +
                "\"/data/upload/0/fonts/SPEC_BAR.SHX\", \"/data/upload/0/fonts/sivan_m.shx\", \"/data/upload/0/fonts/SPEC_SL.SHX\", " +
                "\"/data/upload/0/fonts/SPECIAL.SHX\", \"/data/upload/0/fonts/TIMES.SHX\", \"/data/upload/0/fonts/TIMESOUT.SHX\", " +
                "\"/data/upload/0/fonts/visiohg.shx\", \"/data/upload/0/fonts/visiojp.shx\", \"/data/upload/0/fonts/visiotc.shx\"], " +
                "\"alternateFontList\": [{\"srcFont\": \"Arial\", \"tarFont\": \"SimSun\"}, {\"srcFont\": \"amgontan1.shx\", \"tarFont\": \"txt.shx\"}, " +
                "{\"srcFont\": \"ltypeshp.shx\", \"tarFont\": \"test.shx\"}, {\"srcFont\": \"SPDS.shx\", \"tarFont\": \"102.SHX\"}, " +
                "{\"srcFont\": \"WINGDNG3.TTF\", \"tarFont\": \"STLITI.TTF\"}]}";

        JsonNode node = objectMapper.readTree(requestParams);
        jsonLeaf(node);


    }

    public static void jsonLeaf(JsonNode node) {
        if (node.isValueNode()) {
            System.out.println(node.toString());
            return;
        }
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = iterator.next();
                System.out.println("key: " + entry.getKey());
                jsonLeaf(entry.getValue());
            }
        }
        if (node.isArray()) {
            Iterator<JsonNode> iterator = node.iterator();
            while (iterator.hasNext()) {
                jsonLeaf(iterator.next());
            }
        }
    }
}
