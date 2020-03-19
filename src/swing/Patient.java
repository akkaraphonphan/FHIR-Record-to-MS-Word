package swing;

import org.json.JSONArray;
import org.json.JSONObject;

public class Patient {
    String givenName;
    String familyName;
    String prefixName;
    String patientID;
    String phone;
    String phoneType;
    String gender;
    String birthDate;
    String street;
    String city;
    String state;
    String postalCode;
    String country;
    String maritalStatus;
    String versionID;
    String lastUpdated;
    String socialSecurityNumber;
    String driverLicense;

    public Patient(JSONObject data) {
        String resourceType = (String) data.get("resourceType");
        if (!resourceType.equals("Patient")) {
            System.out.println("Not a patient");
        }
        patientID = (String) data.get("id");
        gender = (String) data.get("gender");
        birthDate = (String) data.get("birthDate");

        JSONObject meta = data.getJSONObject("meta");
        if (meta.length() > 0) {
            versionID = (String) meta.get("versionId");
            lastUpdated = (String) meta.get("lastUpdated");
        }

        JSONArray identifier = data.getJSONArray("identifier");
        if (identifier.length() > 0) {
            JSONObject identifierSSN = identifier.getJSONObject(2);
            JSONObject identifierDriver = identifier.getJSONObject(3);
            socialSecurityNumber = (String) identifierSSN.get("value");
            driverLicense = (String) identifierDriver.get("value");
        }

        JSONArray names = data.getJSONArray("name");
        if (names.length() > 0) {
            JSONObject firstNameData = names.getJSONObject(0);
            JSONArray firstNames = firstNameData.getJSONArray("given");
            if (firstNames.length() > 0) {
                givenName = (String) firstNames.get(0);
            }
            familyName = (String) firstNameData.get("family");
            JSONArray prefixNames = firstNameData.getJSONArray("prefix");
            if (prefixNames.length() > 0) {
                prefixName = (String) prefixNames.get(0);
            }
        }

        JSONArray phoneInfo = data.getJSONArray("telecom");
        if (phoneInfo.length() > 0) {
            JSONObject phoneData = phoneInfo.getJSONObject(0);
            phone = (String) phoneData.get("value");
            phoneType = (String) phoneData.get("use");
        }

        JSONArray address = data.getJSONArray("address");
        if (address.length() > 0) {
            JSONObject addressData = address.getJSONObject(0);
            JSONArray streets = addressData.getJSONArray("line");
            if (streets.length() > 0) {
                street = (String) streets.get(0);
            }
            city = (String) addressData.get("city");
            state = (String) addressData.get("state");
            postalCode = (String) addressData.get("postalCode");
            country = (String) addressData.get("country");
        }

        JSONObject maritalInfo = data.getJSONObject("maritalStatus");
        if (maritalInfo.length() > 0) {
            maritalStatus = (String) maritalInfo.get("text");
        }

        System.out.println(givenName);
        System.out.println(familyName);
        System.out.println(prefixName);
        System.out.println(patientID);
        System.out.println(phone);
        System.out.println(phoneType);
        System.out.println(gender);
        System.out.println(birthDate);
        System.out.println(street);
        System.out.println(city);
        System.out.println(state);
        System.out.println(postalCode);
        System.out.println(country);
        System.out.println(maritalStatus);
        System.out.println(versionID);
        System.out.println(lastUpdated);
        System.out.println(socialSecurityNumber);
        System.out.println(driverLicense);

        String versionID;
        String lastUpdated;
        String socialSecurityNumber;
        String driverLicense;

    }
}