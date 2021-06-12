package pl.zzpj.spacer.exception;

public class AccountException extends AppBaseException {

    private final static String ACCOUNT_EXISTS = "Account already exists";
    private final static String NO_ACCOUNT = "No such account found";
    private final static String USERNAME_MISMATCH = "Usernames do not match";

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException accountExistsException() {
        return new AccountException(ACCOUNT_EXISTS);
    }

    public static AccountException noSuchAccountException() {
        return new AccountException(NO_ACCOUNT);
    }

    public static AccountException usernameMismatch() {
        return new AccountException(USERNAME_MISMATCH);
    }
}
