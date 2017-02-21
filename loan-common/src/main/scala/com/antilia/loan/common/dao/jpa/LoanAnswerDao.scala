package com.antilia.loan.common.dao.jpa

import com.antilia.loan.common.dao.ILoanAnswerDao
import com.antilia.loan.common.domain.LoanAnswer

class LoanAnswerDao extends AbstractDao[LoanAnswer](classOf[LoanAnswer]) with ILoanAnswerDao{

}
