package com.antilia.loanserver

import java.lang.management.ManagementFactory
import javax.management.MBeanServer

import org.eclipse.jetty.jmx.MBeanContainer
import org.eclipse.jetty.server._
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.util.ssl.SslContextFactory
import org.eclipse.jetty.webapp.WebAppContext

/**
 * Starts jetty for development mode.
 */
object LaunchJetty {

  def main() {
    System.setProperty("jetty", "true")
    System.setProperty("wicket.configuration", "development")

    val server: Server = new Server
    val http_config: HttpConfiguration = new HttpConfiguration
    http_config.setSecureScheme("https")
    http_config.setSecurePort(9443)
    http_config.setOutputBufferSize(32768)

    val http: ServerConnector = new ServerConnector(server, new HttpConnectionFactory(http_config))
    http.setPort(9080)
    http.setIdleTimeout(1000 * 60 * 60)
    server.addConnector(http)

    val keystore: Resource = Resource.newClassPathResource("/keystore")


    if (keystore != null && keystore.exists) {
      val sslContextFactory: SslContextFactory = new SslContextFactory
      sslContextFactory.setKeyStoreResource(keystore)
      sslContextFactory.setKeyStorePassword("wicket")
      sslContextFactory.setKeyManagerPassword("wicket")
      val https_config: HttpConfiguration = new HttpConfiguration(http_config)
      https_config.addCustomizer(new SecureRequestCustomizer)
      val https: ServerConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config))
      https.setPort(8443)
      https.setIdleTimeout(500000)
      server.addConnector(https)
      System.out.println("SSL access to the examples has been enabled on port 8443")
      System.out.println("You can access the application using SSL on https://localhost:8443")
      System.out.println()
    }

    val bb: WebAppContext = new WebAppContext
    bb.setServer(server)
    bb.setContextPath("/")
    bb.setWar("src/main/webapp")
    server.setHandler(bb)


    val mBeanServer: MBeanServer = ManagementFactory.getPlatformMBeanServer
    val mBeanContainer: MBeanContainer = new MBeanContainer(mBeanServer)
    server.addEventListener(mBeanContainer)
    server.addBean(mBeanContainer)

    try {
      server.start()
      server.join()
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
        System.exit(100)
    }
  }
}
