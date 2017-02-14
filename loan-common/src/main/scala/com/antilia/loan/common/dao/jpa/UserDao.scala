package com.antilia.loan.common.dao.jpa

import javax.persistence.TypedQuery

import com.antilia.loan.common.domain.{User, UserRole}
import com.antilia.loan.common.dao.IUserDao

/**
  * Created by reiern70 on 2/13/17.
  */
class UserDao extends AbstractDao[User](classOf[User]) with IUserDao {

  override def loadBy(email: String, role: UserRole): Option[User] = {
    val query: TypedQuery[User] = getEntityManager.createQuery("select p from User p where p.email=:email and p.role=:role", classOf[User])
    query.setParameter("email", email)
    query.setParameter("role", role)
    findOne(query)
  }
}
