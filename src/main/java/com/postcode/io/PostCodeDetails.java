package com.postcode.io;

import com.postcode.io.initializers.PostcodeLookup;
import com.postcode.io.json.JsonFetcher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link PostCodeDetails} Explanation<br/> <br/> For example: <b>BS34 7NP</b><br/>
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

    private static final String F_CODES = "codes";
    private static final String F_RESULT = "result";
    private static final String F_POSTCODE = "postcode";
    private static final String F_QUALITY = "quality";
    private static final String F_EASTINGS = "eastings";
    private static final String F_NORTHINGS = "northings";
    private static final String F_COUNTRY = "country";
    private static final String F_NHS_HA = "nhs_ha";
    private static final String F_LONGITUDE = "longitude";
    private static final String F_LATITUDE = "latitude";
    private static final String F_PARLIAMENTARY_CONSTITUENCY = "parliamentary_constituency";
    private static final String F_EUROPEAN_ELECTORAL_REGION = "european_electoral_region";
    private static final String F_PRIMARY_CARE_TRUST = "primary_care_trust";
    private static final String F_REGION = "region";
    private static final String F_LSOA = "lsoa";
    private static final String F_MSOA = "msoa";
    private static final String F_INCODE = "incode";
    private static final String F_OUTCODE = "outcode";
    private static final String F_ADMIN_DISTRICT = "admin_district";
    private static final String F_PARISH = "parish";
    private static final String F_ADMIN_COUNTY = "admin_county";
    private static final String F_ADMIN_WARD = "admin_ward";
    private static final String F_CCG = "ccg";
    private static final String F_NUTS = "nuts";

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
            generateCodes(json.getJSONObject(F_RESULT));
        } catch (MalformedURLException e) {
            log.info("Error with URL");
            throw new MalformedURLException("Error with URL");
        }
        return pc;
    }

    private static void generateResult(JSONObject json) {
        if (json.has(F_RESULT)) {
            json = json.getJSONObject(F_RESULT);
            pc = new PostCodeDetails();
            if (isJSONPresentAndNotNull(json, F_POSTCODE)) {
                pc.postcode = json.getString(F_POSTCODE);
            }
            if (isJSONPresentAndNotNull(json, F_QUALITY)) {
                pc.quality = json.getInt(F_QUALITY);
            }
            if (isJSONPresentAndNotNull(json, F_EASTINGS)) {
                pc.eastings = json.getInt(F_EASTINGS);
            }
            if (isJSONPresentAndNotNull(json, F_NORTHINGS)) {
                pc.northings = json.getInt(F_NORTHINGS);
            }
            if (isJSONPresentAndNotNull(json, F_COUNTRY)) {
                pc.country = json.getString(F_COUNTRY);
            }
            if (isJSONPresentAndNotNull(json, F_NHS_HA)) {
                pc.nhs_ha = json.getString(F_NHS_HA);
            }
            if (isJSONPresentAndNotNull(json, F_LONGITUDE)) {
                pc.longitude = json.getDouble(F_LONGITUDE);
            }
            if (isJSONPresentAndNotNull(json, F_LATITUDE)) {
                pc.latitude = json.getDouble(F_LATITUDE);
            }
            if (isJSONPresentAndNotNull(json, F_PARLIAMENTARY_CONSTITUENCY)) {
                pc.parliamentary_constituency = json.getString(F_PARLIAMENTARY_CONSTITUENCY);
            }
            if (isJSONPresentAndNotNull(json, F_EUROPEAN_ELECTORAL_REGION)) {
                pc.european_electoral_region = json.getString(F_EUROPEAN_ELECTORAL_REGION);
            }
            if (isJSONPresentAndNotNull(json, F_PRIMARY_CARE_TRUST)) {
                pc.primary_care_trust = json.getString(F_PRIMARY_CARE_TRUST);
            }
            if (isJSONPresentAndNotNull(json, F_REGION)) {
                pc.region = json.getString(F_REGION);
            }
            if (isJSONPresentAndNotNull(json, F_LSOA)) {
                pc.lsoa = json.getString(F_LSOA);
            }
            if (isJSONPresentAndNotNull(json, F_MSOA)) {
                pc.msoa = json.getString(F_MSOA);
            }
            if (isJSONPresentAndNotNull(json, F_INCODE)) {
                pc.incode = json.getString(F_INCODE);
            }
            if (isJSONPresentAndNotNull(json, F_OUTCODE)) {
                pc.outcode = json.getString(F_OUTCODE);
            }
            if (isJSONPresentAndNotNull(json, F_ADMIN_DISTRICT)) {
                pc.admin_district = json.getString(F_ADMIN_DISTRICT);
            }
            if (isJSONPresentAndNotNull(json, F_PARISH)) {
                pc.parish = json.getString(F_PARISH);
            }
            if (isJSONPresentAndNotNull(json, F_ADMIN_COUNTY)) {
                pc.admin_county = json.getBoolean(F_ADMIN_COUNTY);
            }
            if (isJSONPresentAndNotNull(json, F_ADMIN_WARD)) {
                pc.admin_ward = json.getString(F_ADMIN_WARD);
            }
            if (isJSONPresentAndNotNull(json, F_CCG)) {
                pc.ccg = json.getString(F_CCG);
            }
            if (isJSONPresentAndNotNull(json, F_NUTS)) {
                pc.nuts = json.getString(F_NUTS);
            }
        } else {
            throw new IllegalArgumentException("Postcode not found");
        }
    }

    private static void generateCodes(JSONObject json) {
        if (json.has(F_CODES) && !json.get(F_CODES)
                .equals(JSONObject.NULL)) {
            JSONObject jcodes = json.getJSONObject(F_CODES);
            Codes cod = pc.new Codes();
            if (jcodes.has(F_ADMIN_DISTRICT) && !jcodes.get(F_ADMIN_DISTRICT)
                    .equals(JSONObject.NULL)) {
                cod.admin_district = jcodes.getString(F_ADMIN_DISTRICT);
            }
            if (jcodes.has(F_ADMIN_COUNTY) && !jcodes.get(F_ADMIN_COUNTY)
                    .equals(JSONObject.NULL)) {
                cod.admin_county = jcodes.getString(F_ADMIN_COUNTY);
            }
            if (jcodes.has(F_ADMIN_WARD) && !jcodes.get(F_ADMIN_WARD)
                    .equals(JSONObject.NULL)) {
                cod.admin_ward = jcodes.getString(F_ADMIN_WARD);
            }
            if (jcodes.has(F_PARISH) && !jcodes.get(F_PARISH)
                    .equals(JSONObject.NULL)) {
                cod.parish = jcodes.getString(F_PARISH);
            }
            if (jcodes.has(F_CCG) && !jcodes.get(F_CCG)
                    .equals(JSONObject.NULL)) {
                cod.ccg = jcodes.getString(F_CCG);
            }
            if (jcodes.has(F_NUTS) && !jcodes.get(F_NUTS)
                    .equals(JSONObject.NULL)) {
                cod.nuts = jcodes.getString(F_NUTS);
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
