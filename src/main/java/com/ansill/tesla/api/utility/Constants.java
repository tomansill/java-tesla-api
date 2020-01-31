package com.ansill.tesla.api.utility;

import static com.ansill.tesla.api.utility.Utility.f;

public final class Constants{

    /** Last known working URL */
    public static final String URL = "https://owner-api.teslamotors.com";

    /** Last known working Client ID */
    public static final String CLIENT_ID = "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384";

    /** Last known working Client secret */
    public static final String CLIENT_SECRET = "c7257eb71a564034f9419ee651c7d0e5f7aa6bfbd18bafb5c5c033b093bb2fa3";

    private Constants(){
        throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
    }
}
