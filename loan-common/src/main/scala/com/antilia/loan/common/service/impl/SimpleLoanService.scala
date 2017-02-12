package com.antilia.loan.common.service.impl

import com.antilia.loan.common.domain.{LoanAnswer, LoanRequest}
import com.antilia.loan.common.service.ILoanService
import org.springframework.stereotype.Service

@Service
class SimpleLoanService extends ILoanService {

  override def requestLoan(request: LoanRequest): LoanAnswer = null

}
