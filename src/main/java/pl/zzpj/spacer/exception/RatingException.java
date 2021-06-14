package pl.zzpj.spacer.exception;

import pl.zzpj.spacer.util.I18n;

public class RatingException extends  AppBaseException{


    public RatingException(String message) {
        super(message);
    }

    public RatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RatingException noSuchRating() {
        return new RatingException(I18n.NO_SUCH_RATING);
    }
}
