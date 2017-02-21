package com.antilia.loanserver.web.pages

import java.util
import javax.inject.Inject

import com.antilia.loan.common.domain.{Currency, LoanRequest}
import com.antilia.loan.common.service.{ILoanService, LoanComputationException}
import com.antilia.loanserver.web.components.EnumDropDownChoice
import com.antilia.loanserver.web.model.LoanAnswerLDM
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form.{DropDownChoice, Form, TextField}
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.Model
import org.apache.wicket.validation.validator.RangeValidator

class LoanPage extends WebPage {

  @Inject
  var service: ILoanService = _

  val amount = new Model[java.lang.Long]()
  val currency = new Model[Currency](Currency.BritishPounds)
  val period = new Model[Integer](36)

  override def onInitialize(): Unit = {
    super.onInitialize()

    val form = new Form[Void]("form") {

      override def onSubmit(): Unit = {
        val loandReuest = new LoanRequest()
        loandReuest.setAmount(amount.getObject)
        loandReuest.setCurrency(currency.getObject)
        loandReuest.setPeriod(period.getObject)
        try {
          service.requestAnonymousLoan(loandReuest) match {
            case Some(answer) =>
              setResponsePage(new SuccessfulLoanPage(new LoanAnswerLDM(answer)))
            case None => error(getString("loan.failure"))
          }
        } catch {
          case e: LoanComputationException => error(e.getMessage)
            e.printStackTrace()
        }
      }
    }
    add(form)

    form.add(new TextField[java.lang.Long]("amount", amount, classOf[java.lang.Long]).add(new RangeValidator[java.lang.Long](10L, 10000000L)).setRequired(true))
    form.add(new EnumDropDownChoice[Currency]("currency", currency, classOf[Currency]).setNullValid(false).setRequired(true))

    val periods = new util.ArrayList[Integer]()
    periods.add(36)
    periods.add(24)
    periods.add(12)

    form.add(new DropDownChoice[Integer]("period", period, periods).setNullValid(false).setRequired(true))

    add(new FeedbackPanel("feedback"))
  }
}
