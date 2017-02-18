package com.antilia.loan.common.dao

import com.antilia.loan.common.domain.{Currency, LoanerData}

trait ILoanerDataDao extends IDao[LoanerData] {

  /**
    * Returns loaner data in ascending rate order.
    *
    * @param currency The currency to filter out loaner.
    * @return
    */
  def getAllFor(currency: Currency): java.util.List[LoanerData]


  /**
    * Returns the next available loaner i.e. the loaner with smallest lastLoanDate and whose rate is
    * >= previous.getRate
    *
    * @param currency
    * @return
    */
  def getNextAvailableLoaner(currency: Currency, previous: LoanerData): Option[LoanerData]


  /**
    * Update system/data after a loand has been granted.
    *
    * @param data
    * @return
    */
  def storeLoanDataAfterLoanGrated(data: java.util.List[LoanerData]): Unit

  /**
    * Revert changes.
    *
    * @param data
    * @return
    */
  def revertLoanDataAfterLoanGrated(data: java.util.List[LoanerData]): Unit

}
