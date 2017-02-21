package com.antilia.loanserver.web.components

import org.apache.wicket.markup.html.form.{DropDownChoice, EnumChoiceRenderer}
import org.apache.wicket.model.IModel

import scala.collection.JavaConverters._

/**
 * EnumDropDownChoice
 */
class EnumDropDownChoice[T <: Enum[T]](id: String, model: IModel[T], tClass: Class[T]) extends DropDownChoice[T](id) {
  
  override def onInitialize(): Unit = {
    super.onInitialize()
    setDefaultModel(model)
    setChoiceRenderer(new EnumChoiceRenderer[T]())
    setNullValid(true)
    setChoices(getChoicesList)
  }

  protected def getChoicesList: java.util.List[T] = tClass.getEnumConstants.toSeq.asJava
}
