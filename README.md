[![Build Status](https://travis-ci.org/spdeepak/postcodes-io-java.svg?branch=master)](https://travis-ci.org/spdeepak/postcodes-io-java)
[![Build status](https://ci.appveyor.com/api/projects/status/ugthr96uix5pmim7?svg=true)](https://ci.appveyor.com/project/spdeepak/postcodes-io-java)
[![Coverage Status](https://coveralls.io/repos/github/spdeepak/postcodes-io-java/badge.svg?branch=master)](https://coveralls.io/github/spdeepak/postcodes-io-java?branch=master)

# PostCodes-io-java
UK Postcode Java API serving up Open Data from [Post Code](http://postcodes.io)

<h2>To Get JSON data</h2>
<b>1. [Lookup a Post Code] (https://github.com/spdeepak/postcodes-io-java#1-lookup-a-postcode)</b><br/>
<b>2. [Bulk lookup Post Codes](https://github.com/spdeepak/postcodes-io-java#2-bulk-lookup-postcodes)</b><br/>
<b>3. [Get nearest Post Codes for a given longitude & latitude] (https://github.com/spdeepak/postcodes-io-java#3-get-nearest-postcodes-for-a-given-longitude--latitude)</b><br/>
<b>4. [Bulk Reverse Geocoding] (https://github.com/spdeepak/postcodes-io-java#4-bulk-reverse-geocoding)</b><br/>
<b>5. [Get a random Post Code] (https://github.com/spdeepak/postcodes-io-java#5-get-a-random-postcode)</b><br/>
<b>6. [Validate a Post Code] (https://github.com/spdeepak/postcodes-io-java#6-validate-a-postcode)</b><br/>
<b>7. [Nearest Post Codes for Post Code] (https://github.com/spdeepak/postcodes-io-java#7-nearest-postcodes-for-postcode)</b><br/>
<b>8. [Autocomplete a postcode partial] (https://github.com/spdeepak/postcodes-io-java#8-autocomplete-a-postcode-partial)</b><br/>
<b>9. [Lookup Outward Code] (https://github.com/spdeepak/postcodes-io-java#9-lookup-outward-code)</b><br/>
<b>10. [Nearest outward code for outward code] (https://github.com/spdeepak/postcodes-io-java#10-nearest-outward-code-for-outward-code)</b><br/>
<b>11. [Get nearest outward codes for a given longitude & latitude] (https://github.com/spdeepak/postcodes-io-java#11-get-nearest-outward-codes-for-a-given-longitude--latitude)</b><br/>

###1. Lookup a postcode
	PostcodeLookup.postcode("BS347NP").asJson();
###2. Bulk lookup postcodes
	PostcodeLookup.postcodes(new String[] { "OX49 5NU", "M32 0JG", "NE30 1DP" }).asJson()
###3. Get nearest postcodes for a given longitude & latitude
	PostcodeLookup.reverseGeocoding(0.629834723775309, 51.7923246977375).limit(100).radius(2000)
                        .wideSearch(true).asJson()
###4. Bulk Reverse Geocoding
	List<Reverse> reverseList = new ArrayList<>();
	
	ReverseGeocoding reverseGeocoding = new ReverseGeocoding();
	//Create first Reverse
	Reverse reverse = reverseGeocoding.new Reverse();
	reverse.setLongitude(0.629834723775309);
	reverse.setLatitude(51.7923246977375);
	reverse.setLimit(0);
	reverse.setRadius(0);
	reverse.setWideSearch(false);
	//Add Reverse to reverseList
	reverseList.add(reverse);
	//Create second Reverse
	reverse = reverseGeocoding.new Reverse(-2.49690382054704, 53.5351312861402, 5, 1000, false);
	//Add Reverse to reverseList
	reverseList.add(reverse);
	
	PostcodeLookup.reverseGeocodings(reverseList).asJson();
	
###5. Get a random postcode
	PostcodeLookup.randomPostcode().asJson();
	
###6. Validate a postcode
	PostcodeLookup.isValid("ST42EU");
	
###7. Nearest postcodes for postcode
	PostcodeLookup.nearestPostcode("ST4 2EU").asJson();
	
Limits number of postcodes matches to return based on <i><b><u>limit</b></u></i>. Defaults to 10. Needs to be less than 100.
	
	PostcodeLookup.nearestPostcode("ST4 2EU").limit(20).asJson();
	
Limits number of postcodes matches to return based on <i><b><u>radius</b></u></i>. Defaults to 100m. Needs to be less than 2,000m.

	PostcodeLookup.nearestPostcode("ST42EU").radius(100).asJson();

###8. Autocomplete a postcode partial
	PostcodeLookup.autocomplete("ST4").asJson();

Limits number of postcodes matches to return based on <i><b><u>limit</b></u></i>. Defaults to 10. Needs to be less than 100.
	
	PostcodeLookup.autocomplete("ST4").limit(20).asJson();

###9. Lookup Outward Code
	PostcodeLookup.lookupOutwardCode("ST4").asJson();

###10. Nearest outward code for outward code
	PostcodeLookup.nearestOutwardCode("ST4").asJson();

Limits number of postcodes matches to return based on <i><b><u>limit</b></u></i>. Defaults to 10. Needs to be less than 100.

	PostcodeLookup.nearestOutwardCode("ST4").limit(5).asJson();

Limits number of postcodes matches to return based on <i><b><u>radius</b></u></i>. Defaults to 5,000m. Needs to be less than 25,000m.

	PostcodeLookup.nearestOutwardCode("ST4").radius(20000).asJson();

###11. Get nearest outward codes for a given longitude & latitude
	PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272).asJson();

Limits number of postcodes matches to return based on <i><b><u>limit</b></u></i>. Defaults to 10. Needs to be less than 100.

	PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272).limit(20).asJson();
	
Limits number of postcodes matches to return based on <i><b><u>radius</b></u></i>. Defaults to 5,000m. Needs to be less than 25,000m.

	PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272).radius(10000).asJson();