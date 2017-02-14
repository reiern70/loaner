package com.antilia.loan.common.dao

import com.antilia.loan.common.domain.{Currency, LoanerData}

trait ILoanerDataDao extends IDao[LoanerData] {

  def getAllFor(currency: Currency): java.util.List[LoanerData]
}
