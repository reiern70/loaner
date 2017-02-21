package com.antilia.loanserver.web.components

import com.antilia.loan.common.domain.LoanAnswer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.{CompoundPropertyModel, IModel}


class LoanAnswerDetailPanel(id: String, model: IModel[LoanAnswer]) extends GenericPanel[LoanAnswer](id, new CompoundPropertyModel[LoanAnswer](model)) {

  override def onInitialize(): Unit = {
    super.onInitialize()
    add(new Label("totalPayment"))
    add(new Label("rate"))
    add(new Label("monthlyPayment"))
    add(new Label("currency"))
  }
}
