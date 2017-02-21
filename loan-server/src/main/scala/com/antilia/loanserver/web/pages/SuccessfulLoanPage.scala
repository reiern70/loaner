package com.antilia.loanserver.web.pages

import com.antilia.loan.common.domain.LoanAnswer
import com.antilia.loanserver.web.components.LoanAnswerDetailPanel
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.model.IModel

class SuccessfulLoanPage(loanAnswer: IModel[LoanAnswer]) extends WebPage {

  override def onInitialize(): Unit = {
    super.onInitialize()

    add(new LoanAnswerDetailPanel("details", loanAnswer))
  }
}
