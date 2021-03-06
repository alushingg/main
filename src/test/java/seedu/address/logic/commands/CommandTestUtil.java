package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MODULE_CODE_CS2103T = "CS2103T";
    public static final String VALID_MODULE_CODE_CS2101 = "CS2101";
    public static final String VALID_MODULE_CODE_CS4215 = "CS3230";
    public static final String VALID_MODULE_CODE_CS3230 = "CS4215";
    public static final String VALID_MODULE_CODES_CHAIN = "CS2101 CS2103T CS4215 CS3230";
    public static final String VALID_DESCRIPTION_CS2103T = "Software Engineering";
    public static final String VALID_DESCRIPTION_CS2101 = "Effective Communication for Computing Professionals";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_OFFICE_AMY = "COM3-04-04";
    public static final String VALID_OFFICE_BOB = "COM3-03-03";

    public static final String MODULE_CODE_DESC_CS2103T = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T;
    public static final String MODULE_CODE_DESC_CS2101 = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2101;
    public static final String MODULE_CODE_CHAIN = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODES_CHAIN;
    public static final String DESCRIPTION_DESC_CS2103T = " " + PREFIX_DESCRIPTION + " " + VALID_DESCRIPTION_CS2103T;
    public static final String DESCRIPTION_DESC_CS2101 = " " + PREFIX_DESCRIPTION + " " + VALID_DESCRIPTION_CS2101;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + " " + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + " " + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + " " + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + " " + VALID_EMAIL_BOB;
    public static final String OFFICE_DESC_AMY = " " + PREFIX_OFFICE + " " + VALID_OFFICE_AMY;
    public static final String OFFICE_DESC_BOB = " " + PREFIX_OFFICE + " " + VALID_OFFICE_BOB;

    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE
            + " cs2103*"; // '*' not allowed in module codes
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " very very very very"
            + " very very very very very very very very very long description"; // string more than 64 characters
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + " 911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + " bob!yahoo"; // missing '@' symbol
    public static final String INVALID_OFFICE_DESC = " " + PREFIX_OFFICE; // empty string not allowed for offices

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final FacilEditCommand.EditFacilitatorDescriptor DESC_AMY;
    public static final FacilEditCommand.EditFacilitatorDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withOffice(VALID_OFFICE_AMY)
                .withModuleCodes(VALID_MODULE_CODE_CS2101).build();
        DESC_BOB = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withOffice(VALID_OFFICE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_CS2101).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                CommandType type, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, type);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered facilitator list and selected facilitator in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ModManager expectedModManager = new ModManager(actualModel.getModManager());
        List<Facilitator> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFacilitatorList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModManager, actualModel.getModManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredFacilitatorList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the facilitator at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFacilitatorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFacilitatorList().size());

        Facilitator facilitator = model.getFilteredFacilitatorList().get(targetIndex.getZeroBased());
        final String[] splitName = facilitator.getName().fullName.split("\\s+");
        model.updateFilteredFacilitatorList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFacilitatorList().size());
    }

}
