package dev.nautchkafe.animation;

import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class to simplify logging operations around keyframe computation or logic where detail logging is crucial.
 * This class utilizes SLF4J for logging and Vavr for functional programming approaches, particularly for handling potential failures in a declarative way.
 */
public final class KeyframeLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger("LogicLogger");

    private KeyframeLogger() {
    }

    /**
     * Executes a function, logs a specific info message before execution and captures any thrown exception to log it as error.
     * @param <TYPE> Input type of the function
     * @param <RESULT> Result type of the function
     * @param functor Function to execute
     * @param input Input value to the function
     * @param message Info message to log before the function execution
     * @return Either containing the result of the function or the throwable if something goes wrong
     */
    public static <TYPE, RESULT> Either<Throwable, RESULT> logWithInfo(final Function1<TYPE, RESULT> functor, final TYPE input, final String message) {
        LOGGER.info(message);
        return Try.of(() -> functor.apply(input))
                .toEither()
                .peekLeft(e -> LOGGER.error("> Error occurred: {}", e.getMessage(), e));
    }

    /**
     * Executes a function and logs an error message if the function throws an exception.
     * @param <TYPE> Input type of the function
     * @param <RESULT> Result type of the function
     * @param functor Function to execute
     * @param input Input value to the function
     * @param errorMessage Error message to log in case of an exception
     * @return Either containing the result of the function or the throwable if something goes wrong
     */
    public static <TYPE, RESULT> Either<Throwable, RESULT> logWithError(final Function1<TYPE, RESULT> functor, final TYPE input, final String errorMessage) {
        return Try.of(() -> functor.apply(input))
                .toEither()
                .peekLeft(e -> LOGGER.error(errorMessage, e));
    }

    /**
     * Logs a provided info message using the configured logger for this class.
     * @param message Info message to log
     */
    public static void logInfo(final String message) {
        LOGGER.info(message);
    }
}
