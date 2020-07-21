package com.postcode.io;

import com.postcode.io.initializers.PostcodeLookup;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Deepak
 */
class PostCodeDetailsTest {

    @Test
    void testPostCodeGenerate() throws MalformedURLException {
        PostCodeDetails postCodeDetails = PostCodeDetails.generate(PostcodeLookup.postcode("bs347np")
                .build());
        assertThat(postCodeDetails).isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("postcode", "BS34 7NP")
                .hasFieldOrPropertyWithValue("quality", 1)
                .hasFieldOrPropertyWithValue("eastings", 360605)
                .hasFieldOrPropertyWithValue("northings", 178655)
                .hasFieldOrPropertyWithValue("country", "England")
                .hasFieldOrPropertyWithValue("nhs_ha", "South West")
                .hasFieldOrPropertyWithValue("longitude", -2.568992)
                .hasFieldOrPropertyWithValue("latitude", 51.505445)
                .hasFieldOrPropertyWithValue("parliamentary_constituency", "Filton and Bradley Stoke")
                .hasFieldOrPropertyWithValue("european_electoral_region", "South West")
                .hasFieldOrPropertyWithValue("primary_care_trust", "South Gloucestershire")
                .hasFieldOrPropertyWithValue("region", "South West")
                .hasFieldOrPropertyWithValue("lsoa", "South Gloucestershire 018D")
                .hasFieldOrPropertyWithValue("msoa", "South Gloucestershire 018")
                .hasFieldOrPropertyWithValue("incode", "7NP")
                .hasFieldOrPropertyWithValue("outcode", "BS34")
                .hasFieldOrPropertyWithValue("admin_district", "South Gloucestershire")
                .hasFieldOrPropertyWithValue("parish", "Filton")
                .hasFieldOrPropertyWithValue("admin_county", false)
                .hasFieldOrPropertyWithValue("admin_ward", "Filton")
                .hasFieldOrPropertyWithValue("ccg", "NHS Bristol, North Somerset and South Gloucestershire")
                .hasFieldOrPropertyWithValue("nuts", "Bath and North East Somerset, North Somerset and South Gloucestershire")
                .hasFieldOrPropertyWithValue("codes.admin_district", "E06000025")
                .hasFieldOrPropertyWithValue("codes.admin_county", "E99999999")
                .hasFieldOrPropertyWithValue("codes.admin_ward", "E05012113")
                .hasFieldOrPropertyWithValue("codes.parish", "E04001052")
                .hasFieldOrPropertyWithValue("codes.ccg", "E38000222")
                .hasFieldOrPropertyWithValue("codes.nuts", "UKK12");
    }

}
