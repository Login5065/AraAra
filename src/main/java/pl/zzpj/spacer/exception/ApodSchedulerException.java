package pl.zzpj.spacer.exception;

import pl.zzpj.spacer.util.I18n;

public class ApodSchedulerException extends AppBaseException {
    public ApodSchedulerException(String message) {
        super(message);
    }

    public ApodSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ApodSchedulerException operationResultIsNull() {
        return new ApodSchedulerException(I18n.APOD_SCHEDULER_FETCH_NULL);
    }
}
