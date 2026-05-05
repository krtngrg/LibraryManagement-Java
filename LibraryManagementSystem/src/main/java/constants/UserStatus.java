package constants;

public enum UserStatus {

    CREATED("Created"),
    ENABLED("Enabled"),
    VERIFIED("Verified"),
    VERIFICATION_PENDING("Verification Pending");

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

