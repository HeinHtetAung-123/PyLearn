package com.example.pylearn.ui.flashcards

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FlashcardViewModelTest {

    private lateinit var viewModel: FlashcardViewModel

    @Before
    fun setUp() {
        viewModel = FlashcardViewModel()
    }

    @Test
    fun loadTopic_withValidTopic_loadsFlashcards() {
        viewModel.loadTopic("variables")

        val state = viewModel.uiState.value

        assertNotNull(state.topic)
        assertEquals("variables", state.topic?.id)
        assertTrue(state.flashcards.isNotEmpty())
        assertNotNull(state.currentCard)
        assertNull(state.errorMessage)
    }

    @Test
    fun loadTopic_withInvalidTopic_setsErrorMessage() {
        viewModel.loadTopic("invalid_topic")

        val state = viewModel.uiState.value

        assertNull(state.topic)
        assertTrue(state.flashcards.isEmpty())
        assertEquals(
            "The selected Python topic could not be found.",
            state.errorMessage
        )
    }

    @Test
    fun flipCard_revealsAnswer() {
        viewModel.loadTopic("variables")

        assertFalse(
            viewModel.uiState.value.isCardFlipped
        )

        viewModel.flipCard()

        assertTrue(
            viewModel.uiState.value.isCardFlipped
        )
    }

    @Test
    fun flipCard_twiceReturnsToPrompt() {
        viewModel.loadTopic("variables")

        viewModel.flipCard()
        viewModel.flipCard()

        assertFalse(
            viewModel.uiState.value.isCardFlipped
        )
    }

    @Test
    fun markRemembered_beforeFlipping_doesNotAdvance() {
        viewModel.loadTopic("variables")

        viewModel.markRemembered()

        val state = viewModel.uiState.value

        assertEquals(0, state.currentCardIndex)
        assertEquals(0, state.rememberedCount)
        assertFalse(state.isSessionComplete)
    }

    @Test
    fun markRemembered_afterFlipping_advancesCard() {
        viewModel.loadTopic("variables")

        viewModel.flipCard()
        viewModel.markRemembered()

        val state = viewModel.uiState.value

        assertEquals(1, state.currentCardIndex)
        assertEquals(1, state.rememberedCount)
        assertEquals(0, state.reviewAgainCount)
        assertFalse(state.isCardFlipped)
    }

    @Test
    fun markForReview_afterFlipping_updatesReviewCount() {
        viewModel.loadTopic("variables")

        viewModel.flipCard()
        viewModel.markForReview()

        val state = viewModel.uiState.value

        assertEquals(1, state.currentCardIndex)
        assertEquals(0, state.rememberedCount)
        assertEquals(1, state.reviewAgainCount)
        assertFalse(state.isCardFlipped)
    }

    @Test
    fun completingAllCards_marksSessionComplete() {
        viewModel.loadTopic("variables")

        while (!viewModel.uiState.value.isSessionComplete) {
            viewModel.flipCard()
            viewModel.markRemembered()
        }

        val state = viewModel.uiState.value

        assertTrue(state.isSessionComplete)
        assertEquals(
            state.totalCards,
            state.rememberedCount
        )
    }

    @Test
    fun restartSession_resetsFlashcardProgress() {
        viewModel.loadTopic("variables")

        viewModel.flipCard()
        viewModel.markRemembered()

        viewModel.restartSession()

        val state = viewModel.uiState.value

        assertEquals(0, state.currentCardIndex)
        assertEquals(0, state.rememberedCount)
        assertEquals(0, state.reviewAgainCount)
        assertFalse(state.isCardFlipped)
        assertFalse(state.isSessionComplete)
    }

    @Test
    fun flippingCard_emitsCardFlippedEvent() =
        runTest {
            viewModel.loadTopic("variables")

            val eventDeferred = async(
                start = CoroutineStart.UNDISPATCHED
            ) {
                viewModel.events.first()
            }

            viewModel.flipCard()

            assertEquals(
                FlashcardUiEvent.CardFlipped,
                eventDeferred.await()
            )
        }

    @Test
    fun completingSession_emitsSessionCompletedEvent() =
        runTest {
            viewModel.loadTopic("variables")

            while (
                viewModel.uiState.value.currentCardIndex <
                viewModel.uiState.value.flashcards.lastIndex
            ) {
                viewModel.flipCard()
                viewModel.markRemembered()
            }

            viewModel.flipCard()

            val eventDeferred = async(
                start = CoroutineStart.UNDISPATCHED
            ) {
                viewModel.events.first()
            }

            viewModel.markRemembered()

            assertEquals(
                FlashcardUiEvent.SessionCompleted,
                eventDeferred.await()
            )
        }
}