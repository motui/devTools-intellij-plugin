package cn.motui.devtools.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.json.XML


/**
 * Json util
 */
object JsonUtil {
    private val objectMapper: ObjectMapper = ObjectMapper()
    private var xmlMapper: XmlMapper = XmlMapper()
    private val yamlMapper: YAMLMapper = YAMLMapper()
    private val csvMapper: CsvMapper = CsvMapper()

    init {
        // 美化JSON输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun jsonTransform(json: String, language: Language) {
        when (language) {
            Language.XML -> jsonToXml(json)
            Language.CSV -> jsonToCsv(json)
            Language.YAML -> jsonToYaml(json)
            else -> throw RuntimeException("language is error")
        }
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun jsonToXml(json: String): String {
        val jsonNode = objectMapper.readTree(json)
        return xmlMapper.writer().withRootName("root").writeValueAsString(jsonNode)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun jsonToYaml(json: String): String {
        val jsonNode = objectMapper.readTree(json)
        return yamlMapper.writeValueAsString(jsonNode)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun jsonToCsv(json: String): String {
        val jsonNode = objectMapper.readTree(json)
        if (jsonNode !is ArrayNode) {
            return ""
        }
        val csvSchemaBuilder: CsvSchema.Builder = CsvSchema.builder()
        val firstObject: JsonNode = jsonNode.elements().next()
        firstObject.fieldNames().forEachRemaining { fieldName: String? ->
            csvSchemaBuilder.addColumn(
                fieldName
            )
        }
        val csvSchema: CsvSchema = csvSchemaBuilder.build().withHeader()
        return csvMapper.writerFor(JsonNode::class.java)
            .with(csvSchema)
            .writeValueAsString(jsonNode)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun xmlToJson(xml: String): String {
        val toJSONObject = XML.toJSONObject(xml)
        val json: Any = objectMapper.readValue(toJSONObject.toString(), Any::class.java)
        return objectMapper.writeValueAsString(json)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun yamlToJson(yaml: String): String {
        val jsonNode = yamlMapper.readTree(yaml)
        return objectMapper.writeValueAsString(jsonNode)
    }

    @Throws(JsonProcessingException::class, JsonMappingException::class)
    fun csvToJson(csv: String): String {
        val bootstrap = CsvSchema.emptySchema().withHeader()
        val mappingIterator: MappingIterator<Map<*, *>> =
            csvMapper.readerFor(MutableMap::class.java).with(bootstrap).readValues(csv)
        return objectMapper.writeValueAsString(mappingIterator.readAll())
    }
}

enum class Language {
    JSON,
    YAML,
    XML,
    CSV,
    PROPERTIES
}
