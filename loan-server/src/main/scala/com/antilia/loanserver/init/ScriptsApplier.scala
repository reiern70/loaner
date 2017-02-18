package com.antilia.loanserver.init

import java.util.Date

import com.antilia.loan.common.dao.IScriptsDao
import com.antilia.loan.common.domain.Script
import com.antilia.util.thread.ThreadUtils
import org.slf4j.{Logger, LoggerFactory}

object ScriptsApplier {
  def applyScripts(scripts: List[IInitializingScript], scriptsDao: IScriptsDao): Unit = {
    ThreadUtils.thread( new ScriptsApplier(scripts, scriptsDao), false)
  }
}

class ScriptsApplier(scripts: List[IInitializingScript], scriptsDao: IScriptsDao) extends Runnable {

  val LOGGER: Logger = LoggerFactory.getLogger(classOf[ScriptsApplier])

  def applyScripts(): Unit = {
    if(scripts != null) {
      scripts.foreach((script: IInitializingScript)=>{
        if(script.isInstanceOf[IRunAlwaysScript]) {
          try {
            script.execute()
          } catch  {
            case e: Exception =>
              LOGGER.error("applyScripts", e)
              if(!script.shouldContinueOnError) throw new RuntimeException(e)
          }
        } else {
          val s = scriptsDao.findById(script.getId)
          // if scrip has not been applied apply it
          s.getOrElse({
            try {
              script.execute()
              val sScript = new Script
              sScript.setScriptId(script.getId)
              sScript.setExecuted(new Date())
              scriptsDao.persist(sScript)
            } catch  {
              case e: Exception =>
                LOGGER.error("applyScripts", e)
                if(!script.shouldContinueOnError) throw new RuntimeException(e)
            }
          })
        }
      })
    }
  }

  override def run(): Unit = applyScripts()
}

