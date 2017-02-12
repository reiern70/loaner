package com.antilia.loanserver.spring

import com.antilia.common.spring.SpringBootstrap
import com.antilia.loanserver.web.LoanWebApplication
import org.apache.wicket.protocol.http.WebApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Wicket spring configuration!
 */

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@Import(Array(classOf[SpringBootstrap]))
class WicketSpringBootstrap {
  /**
   * webApplication
   * @return WebApplication
   */
  @Bean
  def webApplication: WebApplication = {
    new LoanWebApplication
  }
}
