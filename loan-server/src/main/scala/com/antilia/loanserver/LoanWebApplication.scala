package com.antilia.loanserver

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication

class LoanWebApplication extends WebApplication {

  override def getHomePage: Class[_ <: Page] = classOf[HomePage]

}
