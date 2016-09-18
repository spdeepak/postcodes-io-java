[![Build Status](https://travis-ci.org/spdeepak/postcodes-io-java.svg?branch=master)](https://travis-ci.org/spdeepak/postcodes-io-java)
[![Build status](https://ci.appveyor.com/api/projects/status/ugthr96uix5pmim7?svg=true)](https://ci.appveyor.com/project/spdeepak/postcodes-io-java)
[![Coverage Status](https://coveralls.io/repos/github/spdeepak/postcodes-io-java/badge.svg?branch=master)](https://coveralls.io/github/spdeepak/postcodes-io-java?branch=master)

# PostCodes-io-java
UK Postcode Java API serving up Open Data http://postcodes.io

<b>1. [Lookup a Post Code] (https://github.com/spdeepak/postcodes-io-java#1-lookup-a-postcode)</b><br/>
<b>2. [Bulk lookup Post Codes](https://github.com/spdeepak/postcodes-io-java#2-bulk-lookup-postcodes)</b><br/>
<b>3. [Get nearest postcodes for a given longitude & latitude] (https://github.com/spdeepak/postcodes-io-java#3-get-nearest-postcodes-for-a-given-longitude--latitude)</b><br/>
<b>4. [Bulk Reverse Geocoding] (https://github.com/spdeepak/postcodes-io-java#4-bulk-reverse-geocoding)</b><br/>

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
	
	PostcodeLookup.reverseGeocodings(reverseList).asJson()