package com.learn.mapping.type_conversion;

import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mapper interface for type conversions between different data types.
 * This interface provides methods to convert between String, LocalDate, LocalDateTime,
 *
 * Usage: Inject this mapper wherever type conversions are needed.
 * @Mapper(componentModel = "spring", uses = {TypeConversionMapper.class})
 * allows this mapper to be used in other mappers if needed.
 */
@Mapper(componentModel = "spring")
public interface TypeConversionMapper {

    // String to LocalDate
    default LocalDate stringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date) : null;
    }

    // LocalDate to String
    default String localDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    // LocalDateTime to String with format
    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null
                ? dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }

    // String to LocalDateTime
    default LocalDateTime stringToLocalDateTime(String dateTime) {
        return dateTime != null
                ? LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }

    // BigDecimal to String
    default String bigDecimalToString(BigDecimal value) {
        return value != null ? value.toPlainString() : null;
    }

    // String to BigDecimal
    default BigDecimal stringToBigDecimal(String value) {
        return value != null ? new BigDecimal(value) : null;
    }

    // Instant to Long (epoch millis)
    default Long instantToLong(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }

    // Long to Instant
    default Instant longToInstant(Long epochMilli) {
        return epochMilli != null ? Instant.ofEpochMilli(epochMilli) : null;
    }
}