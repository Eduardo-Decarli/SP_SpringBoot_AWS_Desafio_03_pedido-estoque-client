package com.compass.ms_order;

public class UsedVariable {

    public static final String RESPONSE_CLIENT = "[{\n" +
            "    \"name\": \"Rodrigo\",\n" +
            "    \"email\": \"RODRIGO.SILVA@GMAIL.COM\"\n" +
            "}]";

    public static final String NOTFOUND_EXCEPTION = "[{\n" +
            "    \"path\": \"/api/v1/client/email/TET@gmail.com\",\n" +
            "    \"method\": \"GET\",\n" +
            "    \"status\": 404,\n" +
            "    \"statusText\": \"Not Found\",\n" +
            "    \"message\": \"error: not found client by email TET@gmail.com\",\n" +
            "    \"errors\": null\n" +
            "}]";

    public static final String RESPONSE_STOCK_NAME = "[{\n" +
            "    \"name\": \"Computador\",\n" +
            "    \"quantity\": \"5\"\n" +
            "}]";
}
