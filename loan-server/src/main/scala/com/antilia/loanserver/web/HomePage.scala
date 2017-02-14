package com.antilia.loanserver.web

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label

class HomePage extends WebPage {

  override def onInitialize(): Unit = {
    super.onInitialize()
    add(new Label("message", "If you see this message wicket is properly configured and running"))
  }
}
