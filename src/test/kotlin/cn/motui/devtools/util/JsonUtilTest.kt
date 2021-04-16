package cn.motui.devtools.util

import org.junit.Test

import org.junit.Assert.*

class JsonUtilTest {
    // language=JSON
    private val json =
        "{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"DevTools\",\"url\":\"www.github.com\"},{\"id\":\"2\",\"name\":\"DevTools\",\"url\":\"c.github.com\"},{\"id\":\"3\",\"name\":\"DevTools\",\"url\":\"www.github.com\"}]}}"

    // language=XML
    private val xml =
        "<root><sites><site><id>1</id><name>DevTools</name><url>www.github.com</url></site><site><id>2</id><name>DevTools</name><url>c.github.com</url></site><site><id>3</id><name>DevTools</name><url>www.github.com</url></site></sites></root>"

    // language=YAML
    private val yaml = "---\n" +
            "sites:\n" +
            "  site:\n" +
            "  - id: \"1\"\n" +
            "    name: \"DevTools\"\n" +
            "    url: \"www.github.com\"\n" +
            "  - id: \"2\"\n" +
            "    name: \"DevTools\"\n" +
            "    url: \"c.github.com\"\n" +
            "  - id: \"3\"\n" +
            "    name: \"DevTools\"\n" +
            "    url: \"www.github.com\""

    // language=CSV
    private val csv = "id,name,url\n" +
            "1,DevTools,www.github.com\n" +
            "2,DevTools,c.github.com\n" +
            "3,DevTools,www.github.com"

    @Test
    fun jsonToXml() {
        val jsonToXml = JsonUtil.jsonToXml(json)
        assertEquals(xml, jsonToXml)
    }

    @Test
    fun jsonToYaml() {
        val jsonToYaml = JsonUtil.jsonToYaml(json)
        assertEquals(yaml, jsonToYaml)
    }

    @Test
    fun jsonToCsv() {
        val jsonToCsv = JsonUtil.jsonToCsv("[{\"id\":\"1\",\"name\":\"DevTools\",\"url\":\"www.github.com\"},{\"id\":\"2\",\"name\":\"DevTools\",\"url\":\"c.github.com\"},{\"id\":\"3\",\"name\":\"DevTools\",\"url\":\"www.github.com\"}]")
        assertEquals(
            "id,name,url\n" +
                    "1,DevTools,www.github.com\n" +
                    "2,DevTools,c.github.com\n" +
                    "3,DevTools,www.github.com\n", jsonToCsv
        )
    }

    @Test
    fun xmlToJson() {
        val xmlToJson = JsonUtil.xmlToJson(xml)
        assertEquals("{\"root\":{\"sites\":{\"site\":[{\"name\":\"DevTools\",\"id\":1,\"url\":\"www.github.com\"},{\"name\":\"DevTools\",\"id\":2,\"url\":\"c.github.com\"},{\"name\":\"DevTools\",\"id\":3,\"url\":\"www.github.com\"}]}}}", xmlToJson)
    }

    @Test
    fun yamlToJson() {
        val yamlToJson = JsonUtil.yamlToJson(yaml)
        assertEquals(json, yamlToJson)
    }

    @Test
    fun csvToJson() {
        val csvToJson = JsonUtil.csvToJson(csv)
        assertEquals("[{\"id\":\"1\",\"name\":\"DevTools\",\"url\":\"www.github.com\"},{\"id\":\"2\",\"name\":\"DevTools\",\"url\":\"c.github.com\"},{\"id\":\"3\",\"name\":\"DevTools\",\"url\":\"www.github.com\"}]", csvToJson)
    }
}