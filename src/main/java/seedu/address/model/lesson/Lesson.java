package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.model.lesson.exceptions.InvalidLessonTypeException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.DailySchedulable;

/**
 * Represents a Lesson in Mod Manager.
 */
public class Lesson implements Comparable<Lesson>, DailySchedulable {
    public static final String MESSAGE_CONSTRAINTS_DAY =
            "Day should only be either MONDAY, TUESDAY, WEDNESDAY, THURSDAY, or FRIDAY";

    public static final String MESSAGE_CONSTRAINTS_TIME =
            "Time should be in HH:MM 24 hours format";

    public static final String MESSAGE_CONSTRAINTS_DAY_AND_TIME =
            "Day, start time and end time should be provided";

    public static final String MESSAGE_CONSTRAINTS_VENUE =
            "Venue cannot be an empty string";

    private ModuleCode moduleCode;
    private LessonType type;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue; // optional

    public Lesson(ModuleCode moduleCode, LessonType type, DayOfWeek day, LocalTime startTime, LocalTime endTime,
                  String venue) throws InvalidTimeRangeException {
        requireAllNonNull(moduleCode, type, day, startTime, endTime);
        if (startTime.compareTo(endTime) >= 0) {
            throw new InvalidTimeRangeException();
        }
        this.moduleCode = moduleCode;
        this.type = type;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public LessonType getType() {
        return type;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getVenue() {
        return venue;
    }

    public boolean doesVenueExist() {
        return getVenue() != null;
    }

    /**
     * Checks if the venue of the lessons are the same.
     *
     * @param otherLesson The other lesson to compare with.
     * @return True if lessons are at the same venue and false otherwise.
     */
    private boolean isSameVenue(Lesson otherLesson) {
        boolean isSameVenue = false;
        if (otherLesson.doesVenueExist() && doesVenueExist()) {
            isSameVenue = otherLesson.getVenue().equals(getVenue());
        } else if (!otherLesson.doesVenueExist() && !doesVenueExist()) {
            isSameVenue = true;
        }
        return isSameVenue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        return otherLesson.getModuleCode().equals(getModuleCode())
                && otherLesson.getType().equals(getType())
                && otherLesson.getDay().equals(getDay())
                && otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getEndTime())
                && isSameVenue(otherLesson);
    }

    /**
     * Compares the instance of lesson to {@code lesson}.
     */
    public int compareTo(Lesson lesson) {
        DayOfWeek day = lesson.getDay();
        LocalTime time = lesson.getStartTime();
        int val = this.getDay().compareTo(day);
        if (val > 0) {
            return 1;
        } else if (val < 0) {
            return -1;
        } else {
            return this.getStartTime().compareTo(time);
        }
    }

    /**
     * Checks if type is a valid lesson type.
     * @return True if it is valid and false otherwise.
     */
    public static boolean isValidType(String type) {
        requireNonNull(type);
        for (LessonType t : LessonType.values()) {
            if (t.name().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if time is a valid time.
     */
    public static boolean isValidTimeFormat(String time) {
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts string to a lesson type.
     */
    public static LessonType convertStringToLessonType(String type) {
        requireNonNull(type);
        for (LessonType t : LessonType.values()) {
            if (t.name().equals(type)) {
                return t;
            }
        }
        throw new InvalidLessonTypeException();
    }

    @Override
    public String toString() {
        if (doesVenueExist()) {
            return getModuleCode() + " " + getType() + " " + getDay() + " " + getStartTime() + "-" + getEndTime()
                    + " at " + venue;
        }
        return getModuleCode() + " " + getType() + " " + getDay() + " " + getStartTime() + "-" + getEndTime();
    }

    @Override
    public Optional<LocalTime> getComparableTime() {
        return Optional.of(getStartTime());
    }
}

