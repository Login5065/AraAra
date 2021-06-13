package pl.zzpj.spacer.exception;

import pl.zzpj.spacer.util.I18n;

public class PictureException extends AppBaseException {
    public PictureException(String message) {
        super(message);
    }

    public PictureException(String message, Throwable cause) {
        super(message, cause);
    }

    public static PictureException pictureExistsException() {
        return new PictureException(I18n.PICTURE_EXISTS);
    }

    public static PictureException noSuchPictureException() {
        return new PictureException(I18n.NO_SUCH_PICTURE);
    }
}
