package com.postcode.io;

import com.postcode.io.initializers.PostcodeLookup;
import com.postcode.io.json.JsonFetcher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link PostCodeDetails} Explaination<br/> <br/> For example: <b>BS34 7NP</b><br/>
 * <b>BS</b>: Area<br/>
 * <b>34</b>: District<br/>
 * <b>7</b>: Sector<br/>
 * <b>NP</b>: Unit<br/>
 *
 * @author Deepak
 */
@Data
@Slf4j
public class PostCodeDetails {

    private static PostCodeDetails pc;
    /**
     * <b>Postcode.</b> All current (‘live’) postcodes within the United Kingdom, the Channel Islands and the Isle of Man, received monthly from Royal Mail. 2, 3 or
     * 4-character outward code, single space and 3-character inward code.
     */
    private String postcode;
    /**
     * <b>Positional Quality.</b> Shows the status of the assigned grid reference.<br/> 1 = within the building of the matched address closest to the postcode mean <br/> 2 =
     * as for status value 1, except by visual inspection of Landline maps (Scotland only) <br/> 3 = approximate to within 50m <br/> 4 = postcode unit mean (mean of matched
     * addresses with the same postcode, but not snapped to a building)<br/> 5 = imputed by ONS, by reference to surrounding postcode grid references <br/> 6 = postcode sector
     * mean, (mainly | PO Boxes) <br/> 8 = postcode terminated prior to Gridlink® initiative, last known ONS postcode grid reference1 <br/> 9 = no grid reference available
     */
    private Integer quality;
    /**
     * <b>Eastings.</b> The Ordnance Survey postcode grid reference Easting to 1 metre resolution; blank for postcodes in the Channel Islands and the Isle of Man. Grid
     * references for postcodes in Northern Ireland relate to the Irish Grid system.
     */
    private Integer eastings;
    /**
     * <b>Northings.</b> The Ordnance Survey postcode grid reference Northing to 1 metre resolution; blank for postcodes in the Channel Islands and the Isle of Man. Grid
     * references for postcodes in Northern Ireland relate to the Irish Grid system.
     */
    private Integer northings;
    /**
     * <b>Country.</b> The code for the appropriate country (i.e. one of the four constituent countries of the United Kingdom or the Channel Islands or the Isle of Man) to
     * which each postcode is assigned.
     */
    private String country;
    /**
     * <b>Strategic Health Authority.</b> The health area code for the postcode.
     */
    private String nhs_ha;
    /**
     * <b>Longitude.</b> The WGS84 longitude given the Postcode's national grid reference
     */
    private Double longitude;
    /**
     * <b>Latitude.</b> The WGS84 latitude given the Postcode's national grid reference
     */
    private Double latitude;
    /**
     * <b>Westminster Parliamentary Constituency.</b> The Westminster Parliamentary Constituency code for each postcode.
     */
    private String parliamentary_constituency;
    /**
     * <b>European Electoral Region (EER).</b> The European Electoral Region code for each postcode.
     */
    private String european_electoral_region;
    /**
     * <b>Primary Care Trust (PCT).</b> The code for the Primary Care areas in England, LHBs in Wales, CHPs in Scotland, LCG in Northern Ireland and PHD in the Isle of Man;
     * there are no equivalent areas in the Channel Islands. Care Trust/ Care Trust Plus (CT)/ local health board (LHB)/ community health partnership (CHP)/ local
     * commissioning group (LCG)/ primary healthcare directorate (PHD).
     */
    private String primary_care_trust;
    /**
     * <b>Region (formerly GOR).</b> The Region code for each postcode. The nine GORs were abolished on 1 April 2011 and are now known as ‘Regions’. They were the primary
     * statistical subdivisions of England and also the areas in which the Government Offices for the Regions fulfilled their role. Each GOR covered a number of local
     * authorities.
     */
    private String region;
    /**
     * <b>2011Census lower layer super output area (LSOA).</b> The 2011 Census lower layer SOA code for England and Wales, SOA code for Northern Ireland and data zone code
     * for Scotland.
     */
    private String lsoa;
    /**
     * <b>2011 Census middle layer super output area (MSOA).</b> The 2011 Census middle layer SOA (MSOA) code for England and Wales and intermediate zone for Scotland.
     */
    private String msoa;
    /**
     * <b>Unit Code in Postcode
     */
    private String incode;
    /**
     * <b>District Code in Postcode
     */
    private String outcode;
    /**
     * <b>District.</b>The current district/unitary authority to which the postcode has been assigned.
     */
    private String admin_district;
    /**
     * <b>Parish (England)/ community (Wales).</b>The smallest type of administrative area in England is the parish (also known as 'civil parish'); the equivalent units in
     * Wales are communities.
     */
    private String parish;
    /**
     * <b>County.</b> The current county to which the postcode has been assigned.
     */
    private boolean admin_county;
    /**
     * <b>Ward.</b> The current administrative/electoral area to which the postcode has been assigned.
     */
    private String admin_ward;
    /**
     * <b>Clinical Commissioning Group.</b> Clinical commissioning groups (CCGs) are NHS organisations set up by the Health and Social Care Act 2012 to organise the delivery
     * of NHS services in England.
     */
    private String ccg;
    /**
     * <b>Nomenclature of Units for Territorial Statistics (NUTS) / Local Administrative Units (LAU) areas.</b> The LAU2 code for each postcode. NUTS is a hierarchical
     * classification of spatial units that provides a breakdown of the European Union’s territory for producing regional statistics which are comparable across the Union. The
     * NUTS area classification in the United Kingdom comprises current national administrative and electoral areas, except in Scotland where some NUTS areas comprise whole
     * and/or part Local Enterprise Regions. NUTS levels 1-3 are frozen for a minimum of three years and NUTS levels 4 and 5 are now local Administrative Units (LAU) levels 1
     * and 2 respectively.
     */
    private String nuts;
    /**
     * <b>Returns an ID or Code associated with the postcode.</b> Typically these are a 9 character code known as an ONS Code or GSS Code. This is currently only available
     * for districts, parishes, counties, CCGs, NUTS and wards.
     */
    private Codes codes;

    private PostCodeDetails() {
    }

    /**
     * Generate {@link PostCodeDetails} from the given {@link PostcodeLookup}
     *
     * @param postcodeLookup
     * @return {@link PostCodeDetails}
     * @throws MalformedURLException
     */
    public static PostCodeDetails generate(PostcodeLookup postcodeLookup) throws MalformedURLException {
        JSONObject json;
        try {
            json = JsonFetcher.urlToJson(new URL(postcodeLookup.getUrl()));
            generateResult(json);
            generateCodes(json.getJSONObject("result"));
        } catch (MalformedURLException e) {
            log.info("Error with URL");
            throw new MalformedURLException("Error with URL");
        }
        return pc;
    }

    private static void generateResult(JSONObject json) {
        if (json.has("result")) {
            json = json.getJSONObject("result");
            pc = new PostCodeDetails();
            if (isJSONPresentAndNotNull(json, "postcode")) {
                pc.postcode = json.getString("postcode");
            }
            if (isJSONPresentAndNotNull(json, "quality")) {
                pc.quality = json.getInt("quality");
            }
            if (isJSONPresentAndNotNull(json, "eastings")) {
                pc.eastings = json.getInt("eastings");
            }
            if (isJSONPresentAndNotNull(json, "northings")) {
                pc.northings = json.getInt("northings");
            }
            if (isJSONPresentAndNotNull(json, "country")) {
                pc.country = json.getString("country");
            }
            if (isJSONPresentAndNotNull(json, "nhs_ha")) {
                pc.nhs_ha = json.getString("nhs_ha");
            }
            if (isJSONPresentAndNotNull(json, "longitude")) {
                pc.longitude = json.getDouble("longitude");
            }
            if (isJSONPresentAndNotNull(json, "latitude")) {
                pc.latitude = json.getDouble("latitude");
            }
            if (isJSONPresentAndNotNull(json, "parliamentary_constituency")) {
                pc.parliamentary_constituency = json.getString("parliamentary_constituency");
            }
            if (isJSONPresentAndNotNull(json, "european_electoral_region")) {
                pc.european_electoral_region = json.getString("european_electoral_region");
            }
            if (isJSONPresentAndNotNull(json, "primary_care_trust")) {
                pc.primary_care_trust = json.getString("primary_care_trust");
            }
            if (isJSONPresentAndNotNull(json, "region")) {
                pc.region = json.getString("region");
            }
            if (isJSONPresentAndNotNull(json, "lsoa")) {
                pc.lsoa = json.getString("lsoa");
            }
            if (isJSONPresentAndNotNull(json, "msoa")) {
                pc.msoa = json.getString("msoa");
            }
            if (isJSONPresentAndNotNull(json, "incode")) {
                pc.incode = json.getString("incode");
            }
            if (isJSONPresentAndNotNull(json, "outcode")) {
                pc.outcode = json.getString("outcode");
            }
            if (isJSONPresentAndNotNull(json, "admin_district")) {
                pc.admin_district = json.getString("admin_district");
            }
            if (isJSONPresentAndNotNull(json, "parish")) {
                pc.parish = json.getString("parish");
            }
            if (isJSONPresentAndNotNull(json, "admin_county")) {
                pc.admin_county = json.getBoolean("admin_county");
            }
            if (isJSONPresentAndNotNull(json, "admin_ward")) {
                pc.admin_ward = json.getString("admin_ward");
            }
            if (isJSONPresentAndNotNull(json, "ccg")) {
                pc.ccg = json.getString("ccg");
            }
            if (isJSONPresentAndNotNull(json, "nuts")) {
                pc.nuts = json.getString("nuts");
            }
        } else {
            throw new IllegalArgumentException("Postcode not found");
        }
    }

    private static void generateCodes(JSONObject json) {
        if (json.has("codes") && !json.get("codes")
                .equals(JSONObject.NULL)) {
            JSONObject jcodes = json.getJSONObject("codes");
            Codes cod = pc.new Codes();
            if (jcodes.has("admin_district") && !jcodes.get("admin_district")
                    .equals(JSONObject.NULL)) {
                cod.admin_district = jcodes.getString("admin_district");
            }
            if (jcodes.has("admin_county") && !jcodes.get("admin_county")
                    .equals(JSONObject.NULL)) {
                cod.admin_county = jcodes.getString("admin_county");
            }
            if (jcodes.has("admin_ward") && !jcodes.get("admin_ward")
                    .equals(JSONObject.NULL)) {
                cod.admin_ward = jcodes.getString("admin_ward");
            }
            if (jcodes.has("parish") && !jcodes.get("parish")
                    .equals(JSONObject.NULL)) {
                cod.parish = jcodes.getString("parish");
            }
            if (jcodes.has("ccg") && !jcodes.get("ccg")
                    .equals(JSONObject.NULL)) {
                cod.ccg = jcodes.getString("ccg");
            }
            if (jcodes.has("nuts") && !jcodes.get("nuts")
                    .equals(JSONObject.NULL)) {
                cod.nuts = jcodes.getString("nuts");
            }
            pc.codes = cod;
        }
    }

    private static boolean isJSONPresentAndNotNull(JSONObject json, String jsonString) {
        log.info("Checking whether {} has {}", json, jsonString);
        return json.has(jsonString) && !json.get(jsonString)
                .equals(JSONObject.NULL);
    }

    /**
     * @author Deepak
     */
    @Data
    public class Codes {

        /**
         * <b>District.</b>The current district/unitary authority to which the postcode has been assigned.
         */
        private String admin_district;
        /**
         * <b>County.</b> The current county to which the postcode has been assigned.
         */
        private String admin_county;
        /**
         * <b>Ward.</b> The current administrative/electoral area to which the postcode has been assigned.
         */
        private String admin_ward;
        /**
         * <b>Parish (England)/ community (Wales).</b>The smallest type of administrative area in England is the parish (also known as 'civil parish'); the equivalent units
         * in Wales are communities.
         */
        private String parish;
        /**
         * <b>Clinical Commissioning Group.</b> Clinical commissioning groups (CCGs) are NHS organisations set up by the Health and Social Care Act 2012 to organise the
         * delivery of NHS services in England.
         */
        private String ccg;
        /**
         * <b>Nomenclature of Units for Territorial Statistics (NUTS) / Local Administrative Units (LAU) areas.</b> The LAU2 code for each postcode. NUTS is a hierarchical
         * classification of spatial units that provides a breakdown of the European Union’s territory for producing regional statistics which are comparable across the Union.
         * The NUTS area classification in the United Kingdom comprises current national administrative and electoral areas, except in Scotland where some NUTS areas comprise
         * whole and/or part Local Enterprise Regions. NUTS levels 1-3 are frozen for a minimum of three years and NUTS levels 4 and 5 are now local Administrative Units (LAU)
         * levels 1 and 2 respectively.
         */
        private String nuts;

        private Codes() {
        }

    }
}
