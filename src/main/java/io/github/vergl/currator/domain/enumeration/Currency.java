package io.github.vergl.currator.domain.enumeration;

/**
 * Enumeration with all currencies available in the service.
 */
public enum Currency {

    EUR("EU euro"),
    USD("US dollar"),
    JPY("Japanese yen"),
    BGN("Bulgarian lev"),
    CZK("Czech koruna"),
    DKK("Danish krone"),
    GBP("Pound sterling"),
    HUF("Hungarian forint"),
    PLN("Polish zloty"),
    RON("Romanian leu"),
    SEK("Swedish krona"),
    CHF("Swiss franc"),
    ISK("Icelandic krona"),
    NOK("Norwegian krone"),
    HRK("Croatian kuna"),
    RUB("Russian rouble"),
    TRY("Turkish lira"),
    AUD("Australian dollar"),
    BRL("Brazilian real"),
    CAD("Canadian dollar"),
    CNY("Chinese yuan renminbi"),
    HKD("Hong Kong dollar"),
    IDR("Indonesian rupiah"),
    ILS("Israeli shekel"),
    INR("Indian rupee"),
    KRW("South Korean won"),
    MXN("Mexican peso"),
    MYR("Malaysian ringgit"),
    NZD("New Zealand dollar"),
    PHP("Philippine peso"),
    SGD("Singapore dollar"),
    THB("Thai baht"),
    LTL("Lithuanian litas"),
    LVL("Latvian lat"),
    EEK("Estonian kroon"),
    SKK("Slovak koruna"),
    CYP("Cypriot pound"),
    MTL("Maltese lira"),
    SIT("Slovenian tolar"),
    ROL("Romanian leu"),
    TRL("Turkish lira (first)"),
    ZAR("South African rand");

    private final String label;

    Currency(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
