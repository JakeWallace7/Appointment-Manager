package wallace.helper;

import javafx.collections.ObservableList;
import wallace.DAO.FromDB;
import wallace.model.Appointment;

import java.sql.SQLException;
import java.time.*;

/**
 * Helper class containing several time related methods
 */
public class TimeTravel {
    /**
     * Checks a new start and end LocalDateTime against all other start and end times for time conflicts.  Returns true
     * if there is a conflict
     * @param newStart the start time to check
     * @param newEnd the end time to check
     * @param newCustomerId the customer associated with the appointment being checked
     * @return boolean true if there is a conflict
     * @throws SQLException
     */
    public static boolean IsAddConflict(LocalDateTime newStart, LocalDateTime newEnd, int newCustomerId) throws SQLException {
        ObservableList<Appointment> allAppointments = FromDB.getAppointments();

        for(Appointment existingAppointment : allAppointments){
            LocalDateTime existingStart = existingAppointment.getStart();
            LocalDateTime existingEnd = existingAppointment.getEnd();

            if ((newStart.isAfter(existingStart) && newStart.isBefore(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newEnd.isAfter(existingStart) && newEnd.isBefore(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newStart.isEqual(existingStart) && newCustomerId == existingAppointment.getCustomerId() || newEnd.isEqual(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newStart.isBefore(existingStart) && newEnd.isAfter(existingEnd) && newCustomerId == existingAppointment.getCustomerId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the updated start and end LocalDateTimes for the appointment being modified. LAMBDA: Using a lambda, the
     * appointment being modified is removed from the list of appointments being checked, as it would cause a false conflict.
     * this avoids having to loop through the appointments resulting in less code.
     * @param newStart the new start time
     * @param newEnd the new end time
     * @param newCustomerId the new customer ID
     * @param currentId the current customer ID
     * @return boolean true if there is a conflict
     * @throws SQLException
     */
    public static boolean IsUpdateConflict(LocalDateTime newStart, LocalDateTime newEnd, int newCustomerId, int currentId) throws SQLException {
        ObservableList<Appointment> allAppointments = FromDB.getAppointments();

        allAppointments.removeIf(a -> a.getId() == currentId);

        for(Appointment existingAppointment : allAppointments){
            LocalDateTime existingStart = existingAppointment.getStart();
            LocalDateTime existingEnd = existingAppointment.getEnd();

            if ((newStart.isAfter(existingStart) && newStart.isBefore(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newEnd.isAfter(existingStart) && newEnd.isBefore(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newStart.isEqual(existingStart) && newCustomerId == existingAppointment.getCustomerId() || newEnd.isEqual(existingEnd) && newCustomerId == existingAppointment.getCustomerId()) ||
                    (newStart.isBefore(existingStart) && newEnd.isAfter(existingEnd) && newCustomerId == existingAppointment.getCustomerId())){
                return true;
            }
        }
        return false;
    }



    /**
     * Checks is there an appointment coming up in the next 15 minutes
     * @return A string including the ID and time of the appointment
     * @throws SQLException
     */
    public static String hasUpcomingAppointment() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = now.plusMinutes(15);
        ObservableList<Appointment> allAppointments = FromDB.getAppointments();

        for (Appointment a : allAppointments) {
            if (a.getStart().isAfter(now) && a.getStart().isBefore(fifteenMinutesLater)) {
                return "Upcoming Appointment: " + a.getId() + " At: " + a.getStart();
            }
        }
        return "No upcoming Appointments";
    }

    /**
     * Checks the start and end time to ensure they do not overlap with office hours of 8:00-22:00 EST
     * @param startDateTime the start time to check
     * @param endDateTime the end time to check
     * @return boolean true if times overlap with office hours
     */
    public static boolean isOutsideOfficeHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        ZonedDateTime localStart = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localEnd = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime estStart = localStart.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime estEnd = localEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalDateTime officeStart = LocalDateTime.of(estStart.toLocalDate(), LocalDateTime.of(0, 1, 1, 8, 0).toLocalTime());
        LocalDateTime officeEnd = LocalDateTime.of(estStart.toLocalDate(), LocalDateTime.of(0, 1, 1, 22, 0).toLocalTime());

        return estStart.toLocalDateTime().isBefore(officeStart) ||
                estEnd.toLocalDateTime().isAfter(officeEnd);
    }

    /**
     * Converts a LocalDateTime to the users local time
     * @param utcDateTime the time to convert
     * @return the time in users local time
     */
    public static LocalDateTime toUserLocalTime(LocalDateTime utcDateTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        ZonedDateTime localTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(userTimeZone);
        return localTime.toLocalDateTime();
    }

    /**
     * Converts a LocalDateTime to UTC
     * @param userLocalDateTime the time to convert
     * @return the time converted to UTC
     */
    public static LocalDateTime toUTCTime(LocalDateTime userLocalDateTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        ZonedDateTime localTime = userLocalDateTime.atZone(userTimeZone);
        ZonedDateTime utcTime = localTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcTime.toLocalDateTime();
    }

    /**
     * Combines date, hour, and minute inputs in one LocalDateTime
     * @param dateInput the LocalDate
     * @param hrInput the hour
     * @param minInput the minute
     * @return
     */
    public static LocalDateTime combineDate(LocalDate dateInput, int hrInput, int minInput){
        LocalTime time = LocalTime.of(hrInput, minInput);
        return dateInput.atTime(time);
    }
}
