# FHIR-Record-to-MS-Word

This program is for the hackathon organized by UCL on the 2nd of March 2020. This program fills in data from FHIR records into a Microsoft Word document. Included is an example template document "Example.docx" to help familiarize usage of this program.

Instructions

1. Go to this repository https://github.com/goshdrive/FHIRworks_2020 and follow instructions to set up
2. Navigate to out/artifacts/hackathon_jar/ and open hackathon.jar
3. Open the word document you want the program to edit, an example document is included with the name "Example.docx"
4. Fill in the Patient's ID in the text field eg. 8f789d0b-3145-4cf2-8504-13159edaa747
5. Click on Run

## List of Macros accepted by the program:

Note: Ensure that your macros are written all at once, don't copy and paste parts of the macro, because sometimes the text can be separated by MS Word and as a result the macro will not get replaced

Version ID: <#VERSIONID#>

Last Updated: <#LASTUPDATED#>

Patient ID/Medical Record Number: <#PATIENTID#>

Prefix Name: <#PREFIXNAME#>

Given Name: <#GIVENNAME#>

Family Name: <#FAMILYNAME#>

Gender: <#GENDER#>

Date of Birth: <#BIRTHDATE#>

Marital Status <#MARITALSTATUS#>

Social Security Number: <#SOCIALSECURITYNUMBER#>

Driver License: <#DRIVERLICENSE#>

Phone Type: <#PHONETYPE#>

Phone Number: <#PHONE#>

Street Address: <#STREET#>

City: <#CITY#>

State: <#STATE#>

Postal Code: <#POSTALCODE#>

Country: <#COUNTRY#>

