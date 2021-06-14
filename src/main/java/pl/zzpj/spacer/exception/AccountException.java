package pl.zzpj.spacer.exception;

import pl.zzpj.spacer.util.I18n;

public class AccountException extends AppBaseException {

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException accountExistsException() {
        return new AccountException(I18n.ACCOUNT_EXISTS);
    }

    public static AccountException noSuchAccountException() {
        return new AccountException(I18n.NO_ACCOUNT);
    }

    public static AccountException usernameMismatch() {
        return new AccountException(I18n.USERNAME_MISMATCH);
    }

    public static AccountException noSuchLikedPictureException() {
        return new AccountException(I18n.NO_SUCH_LIKED_PICTURE);
    }
  
    public static AccountException noSuchEmailException() {
        return new AccountException(I18n.EMAIL_NO_EXIST);
    }
}
