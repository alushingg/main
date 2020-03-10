package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilitatorAtIndex;
import static seedu.address.testutil.TypicalFacilitators.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FACILITATOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FACILITATOR;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.facil.FacilDelete;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code FacilDelete}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Facilitator facilitatorToDelete = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST_FACILITATOR.getZeroBased());
        FacilDelete deleteCommand = new FacilDelete(INDEX_FIRST_FACILITATOR);

        String expectedMessage = String.format(FacilDelete.MESSAGE_DELETE_FACILITATOR_SUCCESS, facilitatorToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteFacilitator(facilitatorToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFacilitatorList().size() + 1);
        FacilDelete deleteCommand = new FacilDelete(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFacilitatorAtIndex(model, INDEX_FIRST_FACILITATOR);

        Facilitator facilitatorToDelete = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST_FACILITATOR.getZeroBased());
        FacilDelete deleteCommand = new FacilDelete(INDEX_FIRST_FACILITATOR);

        String expectedMessage = String.format(FacilDelete.MESSAGE_DELETE_FACILITATOR_SUCCESS, facilitatorToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteFacilitator(facilitatorToDelete);
        showNoFacilitator(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFacilitatorAtIndex(model, INDEX_FIRST_FACILITATOR);

        Index outOfBoundIndex = INDEX_SECOND_FACILITATOR;
        // ensures that outOfBoundIndex is still in bounds of facilitator list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFacilitatorList().size());

        FacilDelete deleteCommand = new FacilDelete(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FacilDelete deleteFirstCommand = new FacilDelete(INDEX_FIRST_FACILITATOR);
        FacilDelete deleteSecondCommand = new FacilDelete(INDEX_SECOND_FACILITATOR);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        FacilDelete deleteFirstCommandCopy = new FacilDelete(INDEX_FIRST_FACILITATOR);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different facilitator -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFacilitator(Model model) {
        model.updateFilteredFacilitatorList(p -> false);

        assertTrue(model.getFilteredFacilitatorList().isEmpty());
    }
}
