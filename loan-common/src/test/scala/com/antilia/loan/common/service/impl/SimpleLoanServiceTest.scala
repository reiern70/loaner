package com.antilia.loan.common.service.impl

import java.util
import java.util.{Comparator, Date}

import com.antilia.loan.common.dao.ILoanerDataDao
import com.antilia.loan.common.domain.{Currency, LoanRequest, LoanerData}
import org.junit.runner.RunWith
import org.junit.{Assert, Before, Test}
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{InjectMocks, Mock}

import scala.collection.JavaConversions._

@Test
@RunWith(classOf[MockitoJUnitRunner])
class SimpleLoanServiceTest {


  @Mock
  var loanerDataDao: ILoanerDataDao = _
  @InjectMocks
  var loanService: SimpleLoanService = _

  @Before
  def setUp(): Unit = {
    val data =  LoanerData.fromResources()
    data.sort( new Comparator[LoanerData] {
      override def compare(o1: LoanerData, o2: LoanerData): Int = o1.getRate.compare(o2.getRate)
    })
    val stack = new util.Stack[LoanerData]()
    for(a <- data.reverse) {
      stack.push(a)
    }
    when(loanerDataDao.getAllFor(any(classOf[Currency]))).thenReturn(data)

    when(loanerDataDao.getNextAvailableLoaner(any(classOf[Currency]), any(classOf[LoanerData]))).thenReturn(Some(stack.pop()), Some(stack.pop()), Some(stack.pop()), Some(stack.pop()), Some(stack.pop()))
  }

  @Test
  def test_requestLoan: Unit = {
    val loandRequest = new LoanRequest
    loandRequest.setAmount(1000)
    loandRequest.setCurrency(Currency.BritishPounds)
    loandRequest.setPeriod(36)
    loandRequest.setRequestDate(new Date())
    val answer = loanService.requestLoan(loandRequest)
    Assert.assertEquals(0.07, answer.getRate, 0.0001)
    Assert.assertEquals(30.78, answer.getMonthlyPayment, 0.01)
    Assert.assertEquals(1108.04, answer.getTotalPayment, 0.01)
    println(answer)
  }

  /*
  @Test(expected = classOf[LoanComputationException])
  def test_requestLoanFails: Unit = {
    val loandRequest = new LoanRequest
    loandRequest.setAmount(10000)
    loandRequest.setCurrency(Currency.BritishPounds)
    loandRequest.setPeriod(36)
    loandRequest.setRequestDate(new Date())
    val answer = loanService.requestLoan(loandRequest)
    println(answer)
  }*/

}
