package framework

import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.vo.*
import framework.adapters.input.generic.RouterManagementGenericAdapter
import framework.adapters.input.generic.createRouter
import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlin.test.Test

class RouterTest {
  private val routerManagementGenericAdapter: RouterManagementGenericAdapter = RouterManagementGenericAdapter()

  private val location: Location = Location("Amos Ln", "Tully", "NY", 13159, "United States", 42.797310F, -76.130750F)

  @Test
  fun `retrieve router`() {
    val id = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0c")

    val router = routerManagementGenericAdapter.retrieveRouter(id)

    withClue("Router should be present") {
      router.shouldNotBeNull()
    }
  }

  @Test
  fun `create router`() {
    val routerId =
      routerManagementGenericAdapter.createRouter<EdgeRouter>(Vendor.DLINK, Model.XYZ0001, IP("40.0.0.1"), location).id

    val router = routerManagementGenericAdapter.retrieveRouter(routerId)
    withClue("Router should be present") {
      router.shouldNotBeNull()
    }
    router!!.vendor shouldBe Vendor.DLINK
    router.model shouldBe Model.XYZ0001
    router.ip.address shouldBe "40.0.0.1"
    router.location shouldBe location
    router.shouldBeTypeOf<EdgeRouter>()
  }

  @Test
  fun `add router to core router`() {
    val routerId = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0b")
    val coreRouterId = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0c")

    routerManagementGenericAdapter.addRouterToCoreRouter(routerId, coreRouterId)

    val coreRouter = routerManagementGenericAdapter.retrieveRouter(coreRouterId) as CoreRouter
    val router = coreRouter.routers[routerId]
    withClue("Router should be present") {
      router.shouldNotBeNull()
    }
  }

  @Test
  fun `remove router from core router`() {
    val routerId = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0a")
    val coreRouterId = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0c")

    routerManagementGenericAdapter.removeRouterFromCoreRouter(routerId, coreRouterId)

    val coreRouter = routerManagementGenericAdapter.retrieveRouter(coreRouterId) as CoreRouter
    coreRouter.routers[routerId].shouldBeNull()
  }

  @Test
  fun `remove router`() {
    val routerId = Id("b832ef4f-f894-4194-8feb-a99c2cd4be0b")

    routerManagementGenericAdapter.removeRouter(routerId)
  }
}