package com.antilia.loanserver.web

import javax.inject.Inject

import com.antilia.loan.common.dao.IScriptsDao
import com.antilia.loanserver.init.{IInitializingScript, InsertInitialData, ScriptsApplier}
import de.agilecoders.wicket.core.Bootstrap
import de.agilecoders.wicket.core.settings.BootstrapSettings
import org.apache.wicket.Page
import org.apache.wicket.injection.Injector
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.springframework.context.ApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils

class LoanWebApplication extends WebApplication {

  override def getHomePage: Class[_ <: Page] = classOf[HomePage]


  var scripts: List[IInitializingScript] = _

  @Inject
  var scriptsDao: IScriptsDao = _

  override def init(): Unit = {
    super.init()

    val bootstrapSettings: BootstrapSettings = new BootstrapSettings
    bootstrapSettings.setAutoAppendResources(false)
    Bootstrap.install(this, bootstrapSettings)

    //getJavaScriptLibrarySettings.setJQueryReference(GeekedInApplication.JQUERY)

    getMarkupSettings.setStripWicketTags(true)
    val springContext: ApplicationContext = getSpringContext
    getComponentInstantiationListeners.add(new SpringComponentInjector(this, springContext))
    Injector.get.inject(this)

    scripts =  new InsertInitialData() :: Nil

    ScriptsApplier.applyScripts(scripts, scriptsDao)
  }

  final def getSpringContext: ApplicationContext = {
    WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext)
  }
}
