package com.kubernetes.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogUtils {
    /**
     * logger属性.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(LogUtils.class);

    /**
     * info信息.
     * @param message 信息
     */
    public void logInfo(final String message) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.info(builder.toString());
    }
    /**
     * info 异常信息.
     * @param message 信息
     * @param exception 异常信息
     */
    public void logInfo(final String message, final Throwable exception) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.info(builder.toString(), exception);
    }

    /**
     * Warn 信息.
     * @param message 信息
     */
    public void logWarn(final String message) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);

        LOGGER.warn(builder.toString());
    }
    /**
     * Warn 异常信息.
     * @param message 信息
     * @param exception 异常信息
     */
    public void logWarn(final String message, final Throwable exception) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.warn(builder.toString(), exception);
    }
    /**
     * Debug 信息.
     * @param message 信息
     */
    public void logDebug(final String message) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.debug(builder.toString());
    }
    /**
     * Debug 异常信息.
     * @param message 信息
     * @param exception 异常信息
     */
    public void logDebug(final String message, final Throwable exception) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.debug(builder.toString(), exception);
    }
    /**
     * Error 信息.
     * @param message 信息
     */
    public void logError(final String message) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.error(builder.toString());
    }

    /**
     * Error 异常信息.
     * @param message 信息
     * @param exception 异常信息
     */
    public void logError(final String message, final Throwable exception) {
        final StringBuilder builder = new StringBuilder();
        builder.append(message);
        LOGGER.error(builder.toString(), exception);
    }

}
