package com.ichi2.anki.dialogs;

import android.content.res.Resources;

import com.ichi2.anki.R;
import com.ichi2.libanki.Card;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import androidx.annotation.CheckResult;

public class RescheduleDialog extends IntegerDialog {
    private RescheduleDialog() {
        super();
    }

    @NotNull
    @CheckResult
    public static RescheduleDialog rescheduleSingleCard(Resources resources, Card currentCard, IntRunnable callbackRunnable) {
        RescheduleDialog rescheduleDialog = new RescheduleDialog();

        String content = getContentString(resources, currentCard);

        rescheduleDialog.setArgs(
                resources.getString(R.string.reschedule_card_dialog_title),
                resources.getString(R.string.reschedule_card_dialog_message),
                4,
                content);

        if (callbackRunnable != null) {
            rescheduleDialog.setCallbackRunnable(callbackRunnable);
        }

        return rescheduleDialog;
    }

    @NotNull
    @CheckResult
    public static RescheduleDialog rescheduleMultipleCards(Resources resources, IntRunnable callbackRunnable) {
        RescheduleDialog rescheduleDialog = new RescheduleDialog();

        rescheduleDialog.setArgs(
                resources.getString(R.string.reschedule_card_dialog_title),
                resources.getString(R.string.reschedule_card_dialog_message),
                4);

        if (callbackRunnable != null) {
            rescheduleDialog.setCallbackRunnable(callbackRunnable);
        }

        return rescheduleDialog;
    }


    @Nullable
    private static String getContentString(Resources resources, Card currentCard) {
        //#5595 - Help a user reschedule cards by showing them the current interval.

        if (!currentCard.isReview() || currentCard.isDynamic()) {
            //DEFECT: We should be able to calculate this for all card types.
            return null;
        }

        return resources.getQuantityString(R.plurals.reschedule_card_dialog_interval, currentCard.getIvl(), currentCard.getIvl());
    }

}