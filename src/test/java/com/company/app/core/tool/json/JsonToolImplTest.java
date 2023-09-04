package com.company.app.core.tool.json;

import com.company.app.core.temp.tool.impl.DataExtractorToolImpl;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.core.temp.tool.json.JsonToolImpl;
import com.company.app.core.temp.tool.json.MapperSettings;
import com.company.app.core.tool.json.data.Response;
import com.company.app.core.tool.json.testEntity.ProductDescription;
import com.company.app.core.tool.json.testEntity.ProductProperty;
import com.company.app.core.tool.json.testEntity.TestLot;
import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

class JsonToolImplTest {

    private static final String FILE_NAME = "src/test/resources/core/lot_test.json";

    private JsonTool<TestLot> jsonTool;

    @BeforeEach
    public void init() {
        jsonTool = new JsonToolImpl<>();
    }

    private void cleanFile() throws IOException {
        FileUtils.write(new File(FILE_NAME), "", Charset.defaultCharset());
    }

    @SneakyThrows
    @Test
    void saveAndLoadTest() {
        cleanFile();
        List<TestLot> before = createLots();

        jsonTool.toJson(before, new File(FILE_NAME));
        List<TestLot> after = jsonTool.toJavaAsList(new File(FILE_NAME), TestLot.class);

        Assertions.assertEquals(2, after.size());
        Assertions.assertEquals(before.get(0).getPrice(), after.get(0).getPrice());
        Assertions.assertIterableEquals(before, after);
    }

    private List<TestLot> createLots() {
        return ImmutableList.<TestLot>builder()
                .add(TestLot.builder().id(1L).name("43409221").price("1500").discount("0.19").build())
                .add(TestLot.builder().id(2L).name("15694225").price("5500").discount("0.17").build())
                .build();
    }

    @SneakyThrows
    @Test
    void nestedObjectsTest() {
        cleanFile();
        List<TestLot> before = createLotsWithNestedFields();

        jsonTool.toJson(before, new File(FILE_NAME));
        List<TestLot> after = jsonTool.toJavaAsList(new File(FILE_NAME), TestLot.class);

        MatcherAssert.assertThat(before.size(), IsEqual.equalTo(after.size()));
        Assertions.assertEquals(before.size(), after.size());
        Assertions.assertEquals(before.get(0).getPrice(), after.get(0).getPrice());
        Assertions.assertIterableEquals(before, after);
    }

    private List<TestLot> createLotsWithNestedFields() {
        return ImmutableList.<TestLot>builder()
                .add(TestLot.builder()
                        .id(2L)
                        .name("2")
                        .price("2")
                        .discount("0.17")
                        .productDescription(ProductDescription.builder().description("2").composition("2").build())
                        .productPropertiesList(ImmutableList.<ProductProperty>builder()
                                .add(ProductProperty.builder().property("2").build())
                                .add(ProductProperty.builder().property("2").build())
                                .add(ProductProperty.builder().property("2").build())
                                .build())
                        .build())
                .add(TestLot.builder()
                        .id(3L)
                        .name("3")
                        .price("3")
                        .discount("0.17")
                        .productDescription(ProductDescription.builder().description("3").composition("3").build())
                        .productPropertiesList(ImmutableList.<ProductProperty>builder()
                                .add(ProductProperty.builder().property("3").build())
                                .add(ProductProperty.builder().property("3").build())
                                .add(ProductProperty.builder().property("3").build())
                                .build())
                        .build())
                .build();
    }

    @SneakyThrows
    @Test
    void temp_name_test() {
        DataExtractorToolImpl dataExtractorTool = new DataExtractorToolImpl();
        JsonToolImpl<Response> tool = new JsonToolImpl<>();
        String fileAsString = dataExtractorTool.getFileAsString("core/tool/wb_response_example.json");

        Response response = tool.toJavaAsObject(fileAsString, Response.class, MapperSettings.builder()
                .failOnUnknownProperties(false)
                .build());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData().getProducts());
        Assertions.assertEquals(2, response.getData().getProducts().size());
    }

}