import com.tookitaki.controllers._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ProductsController, "/products/*")
    context.initParameters("org.scalatra.environment") = "development"
  }
}
