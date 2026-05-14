package com.example.exambytel9.geschaeftslogik;

public enum ZulassungsStatus {
    ALLES_GUT,
    EIN_TEST_NICHT_BESTANDEN,
    ZWEI_TESTS_NICHT_BESTANDEN,
    ZULASSUNG_NICHT_ERREICHBAR,
    ZULASSUNG_ERREICHT,
    ZULASSUNG_NICHT_ERREICHT;

    public static ZulassungsStatus berechne(int bewerteteTests, int nichtBestandenCount) {
        if (bewerteteTests >= 14) {
            return nichtBestandenCount <= 2 ? ZULASSUNG_ERREICHT : ZULASSUNG_NICHT_ERREICHT;
        }
        return switch (nichtBestandenCount) {
            case 0 -> ALLES_GUT;
            case 1 -> EIN_TEST_NICHT_BESTANDEN;
            case 2 -> ZWEI_TESTS_NICHT_BESTANDEN;
            default -> ZULASSUNG_NICHT_ERREICHBAR;
        };
    }

    public String getBeschreibung() {
        return switch (this) {
            case ALLES_GUT -> "Alle bisher bewerteten Tests bestanden – alles auf gutem Kurs!";
            case EIN_TEST_NICHT_BESTANDEN -> "Ein Test nicht bestanden – die nächsten Tests mit Sorgfalt angehen.";
            case ZWEI_TESTS_NICHT_BESTANDEN -> "Zwei Tests nicht bestanden – Achtung! Beim nächsten nicht bestandenen Test ist die Zulassung nicht mehr erreichbar.";
            case ZULASSUNG_NICHT_ERREICHBAR -> "Mehr als zwei Tests nicht bestanden – die Zulassung kann dieses Semester nicht mehr erworben werden.";
            case ZULASSUNG_ERREICHT -> "Zulassung erreicht! Maximal zwei Tests nicht bestanden.";
            case ZULASSUNG_NICHT_ERREICHT -> "Zulassung nicht erreicht – mehr als zwei Tests nicht bestanden.";
        };
    }

    public String getFarbe() {
        return switch (this) {
            case ALLES_GUT, ZULASSUNG_ERREICHT -> "success";
            case EIN_TEST_NICHT_BESTANDEN -> "warning";
            case ZWEI_TESTS_NICHT_BESTANDEN -> "orange";
            case ZULASSUNG_NICHT_ERREICHBAR, ZULASSUNG_NICHT_ERREICHT -> "danger";
        };
    }

    public String getIcon() {
        return switch (this) {
            case ALLES_GUT, ZULASSUNG_ERREICHT -> "✅";
            case EIN_TEST_NICHT_BESTANDEN -> "⚠️";
            case ZWEI_TESTS_NICHT_BESTANDEN -> "🔶";
            case ZULASSUNG_NICHT_ERREICHBAR, ZULASSUNG_NICHT_ERREICHT -> "❌";
        };
    }
}
