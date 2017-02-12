package com.antilia.loan.common.dao

import com.antilia.loan.common.domain.{User, UserRole}


trait IUserDao extends IDao[User] {

  def loadBy(mail: String, userRole: UserRole): Option[User]

}
