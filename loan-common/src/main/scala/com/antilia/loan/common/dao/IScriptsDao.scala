package com.antilia.loan.common.dao

import com.antilia.loan.common.domain.Script

/**
 * DAO for companies.
 */
trait IScriptsDao extends IDao[Script]{
  
  def findById(scriptId: String): Option[Script]

  def exists(scriptId: String): Boolean
}
