package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;

import java.time.DayOfWeek;
import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Finds the next lesson in Mod Manager that is happening soon.
 * Keyword matching is case insensitive.
 */
public class LessonFindCommand extends LessonCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND
            + ": Finds the next class\n"
            + "Example: " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND + " " + PREFIX_NEXT
            + " or " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND + " " + PREFIX_AT + " MONDAY";

    private final DayOfWeek day;

    public LessonFindCommand(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result;
        if (day == null) {
            Lesson lesson;
            lesson = model.findNextLesson();
            if (lesson == null) {
                result = "No more lessons this week";
            } else {
                result = "Next lesson:\n" + lesson.toString();
            }
        } else {
            List<Lesson> lessonsOfTheDay = model.findLessonByDay(day);
            if (lessonsOfTheDay.size() == 0) {
                result = "No lessons on " + day.toString();
            } else {
                result = "Lessons on " + day.toString() + "\n";
                for (Lesson l : lessonsOfTheDay) {
                    result = result + "\u2022 " + l.toString() + "\n";
                }
            }

        }
        return new CommandResult(result, CommandType.LESSON);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonFindCommand // instanceof handles nulls
                && day == ((LessonFindCommand) other).day); // state check
    }

}
