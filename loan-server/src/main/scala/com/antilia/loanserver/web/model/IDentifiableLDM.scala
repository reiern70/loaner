package com.antilia.loanserver.web.model

import com.antilia.loan.common.dao.IDao
import com.antilia.loan.common.domain.IIdentifiable
import org.apache.wicket.injection.Injector
import org.apache.wicket.model.LoadableDetachableModel

/**
 * Base class for entities LDMs.
 */
abstract class IDentifiableLDM[T <: IIdentifiable](obj: T) extends LoadableDetachableModel[T](obj) {
  
  var id: Option[Long] = if(obj != null) Some(obj.getId) else None
  Injector.get.inject(this)
  
  override final def load(): T = {
      id match {
        case Some(a) =>
          getDao.loadById(a).get
        case None => null.asInstanceOf[T]
      }
  }

  override def setObject(obj: T) {
    if (obj != null) {
      this.id = new Some(obj.getId)
    } else {
      id = None
    }
    super.setObject(obj)
  }
  
  protected def getDao: IDao[T]

}
