package org.kotlinacademy.components

import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.presentation.info.InfoPresenter
import org.kotlinacademy.presentation.info.InfoView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.infoFormView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement
import kotlin.properties.Delegates.observable

class SubmitInfoComponent : BaseComponent<RProps, SubmitInfoComponentState>(), InfoView {

    private val presenter by presenter { InfoPresenter(this) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> infoFormView(onSubmit = presenter::onSubmitCommentClicked)
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backToRootAfterDelay(millis = 3_000)
    }
}

external interface SubmitInfoComponentState : BaseState {
    var loading: Boolean?
    var showThankYouPage: Boolean?
}