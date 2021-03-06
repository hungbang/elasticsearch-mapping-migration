package org.lateralthoughts.elasticsearch.mapping.serializer;

import org.junit.Test;
import org.lateralthoughts.elasticsearch.mapping.domain.ElasticSearchMapping;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticSearchMappingSerializerTest {

    private final ElasticSearchMappingSerializer serializer = new ElasticSearchMappingSerializer();

    @Test
    public void should_serialize_mapping_put_query() {
        ElasticSearchMapping mapping = new ElasticSearchMapping("", "myIndex", "myType");
        mapping.addField("monField", "string");

        String body = serializer.dump(mapping);

        assertThat(body).isEqualTo("{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\"}}}}");
    }

    @Test
    public void should_serialize_mapping_with_analyzer() {
        ElasticSearchMapping mapping = new ElasticSearchMapping("", "myIndex", "myType");
        mapping.addField("monField", "string", ElasticSearchMapping.NOT_ANALYZED);

        String body = serializer.dump(mapping);

        assertThat(body).isEqualTo("{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\"}}}}");
    }

    @Test
    public void should_serialize_mapping_with_store() {
        ElasticSearchMapping mapping = new ElasticSearchMapping("", "myIndex", "myType");
        mapping.addField("monField", "string", true);

        String body = serializer.dump(mapping);

        assertThat(body).isEqualTo("{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"store\":true}}}}");
    }

    @Test
    public void should_serialize_mapping_with_omit_norms_and_index_options() {
        ElasticSearchMapping mapping = new ElasticSearchMapping("", "myIndex", "myType");
        mapping.addField("monField", "string", ElasticSearchMapping.NOT_ANALYZED, null, true, "docs");

        String body = serializer.dump(mapping);

        assertThat(body).isEqualTo("{\"myType\":{\"properties\":{\"monField\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"omit_norms\":true,\"index_options\":\"docs\"}}}}");
    }
}
