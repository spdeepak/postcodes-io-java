package com.postcode.io;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.postcode.io.initializers.PostcodeLookup;
import com.postcode.io.json.JsonFetcher;

/**
 * {@link PostCode} Explaination<br/>
 * <br/>
 * For example: <b>BS34 7NP</b><br/>
 * <b>BS</b>: Area<br/>
 * <b>34</b>: District<br/>
 * <b>7</b>: Sector<br/>
 * <b>NP</b>: Unit<br/>
 * 
 * @author Deepak
 *
 */
public class PostCode {

    private String postcode;

    private Integer quality;

    private Integer eastings;

    private Integer northings;

    private String country;

    private String nhs_ha;

    private Double longitude;

    private Double latitude;

    private String parliamentary_constituency;

    private String european_electoral_region;

    private String primary_care_trust;

    private String region;

    private String lsoa;

    private String msoa;

    private String incode;

    private String outcode;

    private String admin_district;

    private String parish;

    private boolean admin_county;

    private String admin_ward;

    private String ccg;

    private String nuts;

    private Codes codes;

    private static PostCode pc;

    private PostCode() {
    }

    /**
     * @author Deepak
     *
     */
    public class Codes {

        private String admin_district;

        private String admin_county;

        private String admin_ward;

        private String parish;

        private String ccg;

        private String nuts;

        private Codes() {
        }

        private Codes(String admin_district, String admin_county, String admin_ward, String parish, String ccg,
                String nuts) {
            this.admin_district = admin_district;
            this.admin_county = admin_county;
            this.admin_ward = admin_ward;
            this.parish = parish;
            this.ccg = ccg;
            this.nuts = nuts;
        }

        public String getAdmin_district() {
            return admin_district;
        }

        /**
         * <b>County.</b> The current county to which the postcode has been assigned.
         * 
         * @return
         */
        public String getAdmin_county() {
            return admin_county;
        }

        /**
         * <b>Ward.</b> The current administrative/electoral area to which the postcode has been
         * assigned.
         * 
         * @return
         */
        public String getAdmin_ward() {
            return admin_ward;
        }

        /**
         * <b>Parish (England)/ community (Wales).</b>The smallest type of administrative area in
         * England is the parish (also known as 'civil parish'); the equivalent units in Wales are
         * communities.
         * 
         * @return
         */
        public String getParish() {
            return parish;
        }

        /**
         * <b>Clinical Commissioning Group.</b> Clinical commissioning groups (CCGs) are NHS
         * organisations set up by the Health and Social Care Act 2012 to organise the delivery of
         * NHS services in England.
         * 
         * @return
         */
        public String getCcg() {
            return ccg;
        }

        /**
         * <b>Nomenclature of Units for Territorial Statistics (NUTS) / Local Administrative Units
         * (LAU) areas.</b> The LAU2 code for each postcode. NUTS is a hierarchical classification
         * of spatial units that provides a breakdown of the European Union’s territory for
         * producing regional statistics which are comparable across the Union. The NUTS area
         * classification in the United Kingdom comprises current national administrative and
         * electoral areas, except in Scotland where some NUTS areas comprise whole and/or part
         * Local Enterprise Regions. NUTS levels 1-3 are frozen for a minimum of three years and
         * NUTS levels 4 and 5 are now local Administrative Units (LAU) levels 1 and 2 respectively.
         * 
         * @return
         */
        public String getNuts() {
            return nuts;
        }
    }

    /**
     * <b>Postcode.</b> All current (‘live’) postcodes within the United Kingdom, the Channel
     * Islands and the Isle of Man, received monthly from Royal Mail. 2, 3 or 4-character outward
     * code, single space and 3-character inward code.
     * 
     * @return
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * <b>Positional Quality.</b> Shows the status of the assigned grid reference.<br/>
     * 1 = within the building of the matched address closest to the postcode mean <br/>
     * 2 = as for status value 1, except by visual inspection of Landline maps (Scotland only) <br/>
     * 3 = approximate to within 50m <br/>
     * 4 = postcode unit mean (mean of matched addresses with the same postcode, but not snapped to
     * a building)<br/>
     * 5 = imputed by ONS, by reference to surrounding postcode grid references <br/>
     * 6 = postcode sector mean, (mainly | PO Boxes) <br/>
     * 8 = postcode terminated prior to Gridlink® initiative, last known ONS postcode grid
     * reference1 <br/>
     * 9 = no grid reference available
     * 
     * @return
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * <b>Eastings.</b> The Ordnance Survey postcode grid reference Easting to 1 metre resolution;
     * blank for postcodes in the Channel Islands and the Isle of Man. Grid references for postcodes
     * in Northern Ireland relate to the Irish Grid system.
     * 
     * @return
     */
    public Integer getEastings() {
        return eastings;
    }

    /**
     * <b>Northings.</b> The Ordnance Survey postcode grid reference Northing to 1 metre resolution;
     * blank for postcodes in the Channel Islands and the Isle of Man. Grid references for postcodes
     * in Northern Ireland relate to the Irish Grid system.
     * 
     * @return
     */
    public Integer getNorthings() {
        return northings;
    }

    /**
     * <b>Country.</b> The code for the appropriate country (i.e. one of the four constituent
     * countries of the United Kingdom or the Channel Islands or the Isle of Man) to which each
     * postcode is assigned.
     * 
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * <b>Strategic Health Authority.</b> The health area code for the postcode.
     * 
     * @return
     */
    public String getNhs_ha() {
        return nhs_ha;
    }

    /**
     * 
     * <b>Longitude.</b> The WGS84 longitude given the Postcode's national grid reference
     * 
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * <b>Latitude.</b> The WGS84 latitude given the Postcode's national grid reference
     * 
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * <b>Westminster Parliamentary Constituency.</b> The Westminster Parliamentary Constituency
     * code for each postcode.
     * 
     * @return
     */
    public String getParliamentary_constituency() {
        return parliamentary_constituency;
    }

    /**
     * <b>European Electoral Region (EER).</b> The European Electoral Region code for each postcode.
     * 
     * @return
     */
    public String getEuropean_electoral_region() {
        return european_electoral_region;
    }

    /**
     * <b>Primary Care Trust (PCT).</b> The code for the Primary Care areas in England, LHBs in
     * Wales, CHPs in Scotland, LCG in Northern Ireland and PHD in the Isle of Man; there are no
     * equivalent areas in the Channel Islands. Care Trust/ Care Trust Plus (CT)/ local health board
     * (LHB)/ community health partnership (CHP)/ local commissioning group (LCG)/ primary
     * healthcare directorate (PHD).
     * 
     * @return
     */
    public String getPrimary_care_trust() {
        return primary_care_trust;
    }

    /**
     * <b>Region (formerly GOR).</b> The Region code for each postcode. The nine GORs were abolished
     * on 1 April 2011 and are now known as ‘Regions’. They were the primary statistical
     * subdivisions of England and also the areas in which the Government Offices for the Regions
     * fulfilled their role. Each GOR covered a number of local authorities.
     * 
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * <b>2011Census lower layer super output area (LSOA).</b> The 2011 Census lower layer SOA code
     * for England and Wales, SOA code for Northern Ireland and data zone code for Scotland.
     * 
     * @return
     */
    public String getLsoa() {
        return lsoa;
    }

    /**
     * <b>2011 Census middle layer super output area (MSOA).</b> The 2011 Census middle layer SOA
     * (MSOA) code for England and Wales and intermediate zone for Scotland.
     * 
     * @return
     */
    public String getMsoa() {
        return msoa;
    }

    /**
     * <b>Unit Code in Postcode
     * 
     * @return
     */
    public String getIncode() {
        return incode;
    }

    /**
     * <b>District Code in Postcode
     * 
     * @return
     */
    public String getOutcode() {
        return outcode;
    }

    /**
     * <b>District.</b>The current district/unitary authority to which the postcode has been
     * assigned.
     * 
     * @return
     */
    public String getAdmin_district() {
        return admin_district;
    }

    /**
     * <b>Parish (England)/ community (Wales).</b>The smallest type of administrative area in
     * England is the parish (also known as 'civil parish'); the equivalent units in Wales are
     * communities.
     * 
     * @return
     */
    public String getParish() {
        return parish;
    }

    /**
     * <b>County.</b> The current county to which the postcode has been assigned.
     * 
     * @return
     */
    public boolean getAdmin_county() {
        return admin_county;
    }

    /**
     * <b>Ward.</b> The current administrative/electoral area to which the postcode has been
     * assigned.
     * 
     * @return
     */
    public String getAdmin_ward() {
        return admin_ward;
    }

    /**
     * <b>Clinical Commissioning Group.</b> Clinical commissioning groups (CCGs) are NHS
     * organisations set up by the Health and Social Care Act 2012 to organise the delivery of NHS
     * services in England.
     * 
     * @return
     */
    public String getCcg() {
        return ccg;
    }

    /**
     * <b>Nomenclature of Units for Territorial Statistics (NUTS) / Local Administrative Units (LAU)
     * areas.</b> The LAU2 code for each postcode. NUTS is a hierarchical classification of spatial
     * units that provides a breakdown of the European Union’s territory for producing regional
     * statistics which are comparable across the Union. The NUTS area classification in the United
     * Kingdom comprises current national administrative and electoral areas, except in Scotland
     * where some NUTS areas comprise whole and/or part Local Enterprise Regions. NUTS levels 1-3
     * are frozen for a minimum of three years and NUTS levels 4 and 5 are now local Administrative
     * Units (LAU) levels 1 and 2 respectively.
     * 
     * @return
     */
    public String getNuts() {
        return nuts;
    }

    /**
     * <b>Returns an ID or Code associated with the postcode.</b> Typically these are a 9 character
     * code known as an ONS Code or GSS Code. This is currently only available for districts,
     * parishes, counties, CCGs, NUTS and wards.
     * 
     * @return
     */
    public Codes getCodes() {
        return codes;
    }

    /**
     * @param postcodeLookup
     * @return
     */
    public static PostCode generate(PostcodeLookup postcodeLookup) {
        JSONObject json;
        try {
            json = JsonFetcher.urlToJson(new URL(postcodeLookup.getUrl()));
            if (json.has("result")) {
                json = json.getJSONObject("result");
                pc = new PostCode();
                if (json.has("postcode") && !json.get("postcode").equals(JSONObject.NULL)) {
                    pc.postcode = json.getString("postcode");
                }
                if (json.has("quality") && !json.get("quality").equals(JSONObject.NULL)) {
                    pc.quality = json.getInt("quality");
                }
                if (json.has("eastings") && !json.get("eastings").equals(JSONObject.NULL)) {
                    pc.eastings = json.getInt("eastings");
                }
                if (json.has("northings") && !json.get("northings").equals(JSONObject.NULL)) {
                    pc.northings = json.getInt("northings");
                }
                if (json.has("country") && !json.get("country").equals(JSONObject.NULL)) {
                    pc.country = json.getString("country");
                }
                if (json.has("nhs_ha") && !json.get("nhs_ha").equals(JSONObject.NULL)) {
                    pc.nhs_ha = json.getString("nhs_ha");
                }
                if (json.has("longitude") && !json.get("longitude").equals(JSONObject.NULL)) {
                    pc.longitude = json.getDouble("longitude");
                }
                if (json.has("latitude") && !json.get("latitude").equals(JSONObject.NULL)) {
                    pc.latitude = json.getDouble("latitude");
                }
                if (json.has("parliamentary_constituency")
                        && !json.get("parliamentary_constituency").equals(JSONObject.NULL)) {
                    pc.parliamentary_constituency = json.getString("parliamentary_constituency");
                }
                if (json.has("european_electoral_region")
                        && !json.get("european_electoral_region").equals(JSONObject.NULL)) {
                    pc.european_electoral_region = json.getString("european_electoral_region");
                }
                if (json.has("primary_care_trust") && !json.get("primary_care_trust").equals(JSONObject.NULL)) {
                    pc.primary_care_trust = json.getString("primary_care_trust");
                }
                if (json.has("region") && !json.get("region").equals(JSONObject.NULL)) {
                    pc.region = json.getString("region");
                }
                if (json.has("lsoa") && !json.get("lsoa").equals(JSONObject.NULL)) {
                    pc.lsoa = json.getString("lsoa");
                }
                if (json.has("msoa") && !json.get("msoa").equals(JSONObject.NULL)) {
                    pc.msoa = json.getString("msoa");
                }
                if (json.has("incode") && !json.get("incode").equals(JSONObject.NULL)) {
                    pc.incode = json.getString("incode");
                }
                if (json.has("outcode") && !json.get("outcode").equals(JSONObject.NULL)) {
                    pc.outcode = json.getString("outcode");
                }
                if (json.has("admin_district") && !json.get("admin_district").equals(JSONObject.NULL)) {
                    pc.admin_district = json.getString("admin_district");
                }
                if (json.has("parish") && !json.get("parish").equals(JSONObject.NULL)) {
                    pc.parish = json.getString("parish");
                }
                if (json.has("admin_county") && !json.get("admin_county").equals(JSONObject.NULL)) {
                    pc.admin_county = json.getBoolean("admin_county");
                }
                if (json.has("admin_ward") && !json.get("admin_ward").equals(JSONObject.NULL)) {
                    pc.admin_ward = json.getString("admin_ward");
                }
                if (json.has("ccg") && !json.get("ccg").equals(JSONObject.NULL)) {
                    pc.ccg = json.getString("ccg");
                }
                if (json.has("nuts") && !json.get("nuts").equals(JSONObject.NULL)) {
                    pc.nuts = json.getString("nuts");
                }
                generateCodes(json);
            } else {
                throw new IllegalArgumentException("Postcode not found");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return pc;
    }

    private static void generateCodes(JSONObject json) {
        if (json.has("codes") && !json.get("codes").equals(JSONObject.NULL)) {
            JSONObject jcodes = json.getJSONObject("codes");
            Codes cod = pc.new Codes();
            if (jcodes.has("admin_district") && !jcodes.get("admin_district").equals(JSONObject.NULL)) {
                cod.admin_district = jcodes.getString("admin_district");
            }
            if (jcodes.has("admin_county") && !jcodes.get("admin_county").equals(JSONObject.NULL)) {
                cod.admin_county = jcodes.getString("admin_county");
            }
            if (jcodes.has("admin_ward") && !jcodes.get("admin_ward").equals(JSONObject.NULL)) {
                cod.admin_ward = jcodes.getString("admin_ward");
            }
            if (jcodes.has("parish") && !jcodes.get("parish").equals(JSONObject.NULL)) {
                cod.parish = jcodes.getString("parish");
            }
            if (jcodes.has("ccg") && !jcodes.get("ccg").equals(JSONObject.NULL)) {
                cod.ccg = jcodes.getString("ccg");
            }
            if (jcodes.has("nuts") && !jcodes.get("nuts").equals(JSONObject.NULL)) {
                cod.nuts = jcodes.getString("nuts");
            }
            pc.codes = cod;
        }
    }
}
