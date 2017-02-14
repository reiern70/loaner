package com.antilia.loanserver.init

import scala.beans.BeanProperty


/**
 * Abstract script.
 */
abstract class AbstractScript(@BeanProperty var id: String) extends IInitializingScript {

}