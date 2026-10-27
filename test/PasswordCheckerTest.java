public class PasswordCheckerTest {
    public static void main(String[] args) {
        PasswordCheckerTest tests = new PasswordCheckerTest();
        tests.acceptsPasswordWhenOptionalRulesAreDisabled();
        tests.rejectsBlankPasswords();
        tests.rejectsPasswordsBelowMinimumLength();
        tests.enforcesConfiguredMaximumLength();
        tests.enforcesRequiredDigits();
        tests.enforcesRequiredUppercaseLetters();
        tests.enforcesRequiredLowercaseLetters();
        tests.rejectsPasswordsThatDoNotMatch();
        tests.rejectsInvalidMinimumLength();
        System.out.println("All PasswordChecker tests passed.");
    }

    void acceptsPasswordWhenOptionalRulesAreDisabled() {
        PasswordChecker checker = new PasswordChecker();
        assertEquals(PasswordChecker.PASSWORD_OK, checker.check("password", "password"));
    }

    void rejectsBlankPasswords() {
        PasswordChecker checker = new PasswordChecker();
        assertEquals(PasswordChecker.ERR_BLANK, checker.check("", ""));
        assertEquals(PasswordChecker.ERR_BLANK, checker.check("   ", "   "));
        assertEquals(PasswordChecker.ERR_BLANK, checker.check(null, null));
    }

    void rejectsPasswordsBelowMinimumLength() {
        PasswordChecker checker = new PasswordChecker();
        assertEquals(PasswordChecker.ERR_LENGTH, checker.check("short", "short"));
    }

    void enforcesConfiguredMaximumLength() {
        PasswordChecker checker = new PasswordChecker();
        checker.setMaxLength(10);

        assertEquals(PasswordChecker.PASSWORD_OK, checker.check("1234567890", "1234567890"));
        assertEquals(PasswordChecker.ERR_LENGTH, checker.check("12345678901", "12345678901"));
    }

    void enforcesRequiredDigits() {
        PasswordChecker checker = new PasswordChecker();
        checker.setCheckDigits(true);

        assertEquals(PasswordChecker.ERR_DIGITS, checker.check("password", "password"));
        assertEquals(PasswordChecker.PASSWORD_OK, checker.check("password1", "password1"));
    }

    void enforcesRequiredUppercaseLetters() {
        PasswordChecker checker = new PasswordChecker();
        checker.setCheckUppers(true);
        assertEquals(PasswordChecker.ERR_UPPERS, checker.check("password", "password"));
    }

    void enforcesRequiredLowercaseLetters() {
        PasswordChecker checker = new PasswordChecker();
        checker.setCheckLowers(true);
        assertEquals(PasswordChecker.ERR_LOWERS, checker.check("PASSWORD", "PASSWORD"));
    }

    void rejectsPasswordsThatDoNotMatch() {
        PasswordChecker checker = new PasswordChecker();
        assertEquals(PasswordChecker.ERR_NO_MATCH, checker.check("password", "different"));
    }

    void rejectsInvalidMinimumLength() {
        PasswordChecker checker = new PasswordChecker();
        try {
            checker.setMinLength(0);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            assertEquals(PasswordChecker.ERR_INVALID_MIN_LENGTH, exception.getMessage());
        }
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(String expected, String actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("Expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }
}
