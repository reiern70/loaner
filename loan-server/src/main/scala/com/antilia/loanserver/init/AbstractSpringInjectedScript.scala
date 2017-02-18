package com.antilia.loanserver.init

import org.apache.wicket.injection.Injector

/**
 * Script injecting spring dependencies.
 */
@SuppressWarnings(Array("serial")) 
abstract class AbstractSpringInjectedScript(id: String) extends AbstractScript(id) {
    Injector.get.inject(this)
}
