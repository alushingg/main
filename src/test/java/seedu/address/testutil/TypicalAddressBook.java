package seedu.address.testutil;

import static seedu.address.testutil.TypicalFacilitators.getTypicalFacilitators;
import static seedu.address.testutil.TypicalLessons.getTypicalLessons;
import static seedu.address.testutil.TypicalModules.getTypicalModules;

import seedu.address.model.ModManager;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;

/**
 * A utility class containing an {@code ModManager} object to be used in tests.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code ModManager} with all the typical facilitators.
     */
    public static ModManager getTypicalAddressBook() {
        ModManager ab = new ModManager();
        for (Module module : getTypicalModules()) {
            ab.addModule(module);
        }
        for (Facilitator facilitator : getTypicalFacilitators()) {
            ab.addFacilitator(facilitator);
        }

        for (Lesson lesson : getTypicalLessons()) {
            ab.addLesson(lesson);
        }

        return ab;
    }
}
