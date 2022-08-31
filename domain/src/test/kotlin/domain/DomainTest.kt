package domain

import domain.entity.*
import domain.exception.GenericSpecificationException
import domain.service.NetworkService
import domain.service.RouterService
import domain.service.SwitchService
import domain.vo.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.kotest.matchers.types.shouldBeTypeOf
import kotlin.test.Test

class DomainTest {
  @Test
  fun `adding network to switch should succeed`() {
    val location = createLocation("US")
    val network = createNetwork("30.0.0.1", 8)
    val switch = createSwitch("30.0.0.0", 8, location)

    switch.addNetworkToSwitch(network) shouldBe true
  }

  @Test
  fun `adding network to switch should fail because of same network address`() {
    val location = createLocation("US")
    val network = createNetwork("30.0.0.0", 8)
    val switch = createSwitch("30.0.0.0", 8, location)

    shouldThrow<GenericSpecificationException> { switch.addNetworkToSwitch(network) }
      .shouldHaveMessage("This network already exists.")
  }

  @Test
  fun `adding switch to edge router should succeed`() {
    val location = createLocation("US")
    val switch = createSwitch("30.0.0.0", 8, location)
    val edgeRouter = createEdgeRouter(location, "30.0.0.1")

    edgeRouter.addSwitch(switch)

    edgeRouter.switches.size shouldBe 1
  }

  @Test
  fun `adding switch to edge router should fail because equipment of different countries`() {
    val locationUs = createLocation("US")
    val locationJp = createLocation("JP")
    val switch = createSwitch("30.0.0.0", 8, locationUs)
    val edgeRouter = createEdgeRouter(locationJp, "30.0.0.1")

    shouldThrow<GenericSpecificationException> { edgeRouter.addSwitch(switch) }
      .shouldHaveMessage("The equipment should be in the same country.")
  }

  @Test
  fun `adding edge to core router should succeed`() {
    val location = createLocation("US")
    val edgeRouter = createEdgeRouter(location, "30.0.0.1")
    val coreRouter = createCoreRouter(location, "40.0.0.1")

    coreRouter.addRouter(edgeRouter)

    coreRouter.routers.size shouldBe 1
  }

  @Test
  fun `adding edge to core router should fail because routers of different countries`() {
    val locationUs = createLocation("US")
    val locationJp = createLocation("JP")
    val edgeRouter = createEdgeRouter(locationUs, "30.0.0.1")
    val coreRouter = createCoreRouter(locationJp, "40.0.0.1")

    shouldThrow<GenericSpecificationException> { coreRouter.addRouter(edgeRouter) }
      .shouldHaveMessage("The equipment should be in the same country.")
  }

  @Test
  fun `adding core to core router should succeed`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val newCoreRouter = createCoreRouter(location, "40.0.0.1")

    coreRouter.addRouter(newCoreRouter)

    coreRouter.routers.size shouldBe 1
  }

  @Test
  fun `adding core to core router should fail because routers of same ip`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val newCoreRouter = createCoreRouter(location, "30.0.0.1")

    shouldThrow<GenericSpecificationException> { coreRouter.addRouter(newCoreRouter) }
      .shouldHaveMessage("It's not possible to attach routers with same IP.")
  }

  @Test
  fun `removing router should succeed`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val edgeRouter = createEdgeRouter(location, "40.0.0.1")
    val expectedId = edgeRouter.id
    coreRouter.addRouter(edgeRouter)

    val actualId = coreRouter.removeRouter(edgeRouter)?.id

    withClue("Actual id should be present") {
      actualId shouldNotBe null
    }
    actualId shouldBe expectedId
  }

  @Test
  fun `removing switch should succeed`() {
    val location = createLocation("US")
    val network = createNetwork("30.0.0.0", 8)
    val switch = createSwitch("30.0.0.0", 8, location)
    val edgeRouter = createEdgeRouter(location, "40.0.0.1")
    edgeRouter.addSwitch(switch)
    switch.removeNetworkFromSwitch(network)
    val expectedId = Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")

    val actualId = edgeRouter.removeSwitch(switch)?.id

    actualId shouldBe expectedId
  }

  @Test
  fun `removing network should succeed`() {
    val location = createLocation("US")
    val network = createNetwork("30.0.0.0", 8)
    val switch = createSwitch("30.0.0.0", 8, location)
    val sizeBefore = switch.networks.size

    val removed = switch.removeNetworkFromSwitch(network)

    sizeBefore shouldBe 1
    removed.shouldBeTrue()
    switch.networks.shouldBeEmpty()
  }

  @Test
  fun `filter routers by type`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val edgeRouter = createEdgeRouter(location, "40.0.0.1")
    val routers = listOf(coreRouter, edgeRouter)

    val coreRouters = RouterService.filterAndRetrieveRouters(routers, Router.typePredicate<CoreRouter>())
    val edgeRouters = RouterService.filterAndRetrieveRouters(routers, Router.typePredicate<EdgeRouter>())

    coreRouters[0].shouldBeTypeOf<CoreRouter>()
    edgeRouters[0].shouldBeTypeOf<EdgeRouter>()
  }

  @Test
  fun `filter routers by vendor`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val edgeRouter = createEdgeRouter(location, "40.0.0.1")
    val routers = listOf(coreRouter, edgeRouter)

    val actualVendor1 = RouterService.filterAndRetrieveRouters(routers, Equipment.vendorPredicate(Vendor.HP))[0].vendor
    val actualVendor2 =
      RouterService.filterAndRetrieveRouters(routers, Equipment.vendorPredicate(Vendor.CISCO))[0].vendor

    actualVendor1 shouldBe Vendor.HP
    actualVendor2 shouldBe Vendor.CISCO
  }

  @Test
  fun `filter routers by location`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val routers = listOf(coreRouter)

    val actualCountry =
      RouterService.filterAndRetrieveRouters(routers, Router.countryPredicate(location))[0].location.country

    actualCountry shouldBe location.country
  }

  @Test
  fun `filter routers by model`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val newCoreRouter = createCoreRouter(location, "40.0.0.1")
    val routers = listOf(coreRouter, newCoreRouter)

    val actualModel = RouterService.filterAndRetrieveRouters(routers, Router.modelPredicate(Model.XYZ0001))[0].model

    actualModel shouldBe Model.XYZ0001
  }

  @Test
  fun `filter switch by type`() {
    val location = createLocation("US")
    val switch = createSwitch("30.0.0.0", 8, location)
    val switches = listOf(switch)

    val actualType = SwitchService.filterAndRetrieveSwitches(switches, Switch.typePredicate(SwitchType.LAYER3))[0].type

    actualType shouldBe SwitchType.LAYER3
  }

  @Test
  fun `filter network by protocol`() {
    val network = createNetwork("30.0.0.0", 8)
    val networks = listOf(network)
    val expectedProtocol = Protocol.IPV4

    val actualProtocol = NetworkService.filterAndRetrieveNetworks(
      networks,
      Switch.networkProtocolPredicate(Protocol.IPV4)
    )[0].address.protocol

    actualProtocol shouldBe expectedProtocol
  }

  @Test
  fun `find router by id`() {
    val location = createLocation("US")
    val coreRouter = createCoreRouter(location, "30.0.0.1")
    val newCoreRouter = createCoreRouter(location, "40.0.0.1")
    coreRouter.addRouter(newCoreRouter)
    val routersOfCoreRouter = mapOf(newCoreRouter.id to newCoreRouter)
    val expectedId = newCoreRouter.id

    val actualId = RouterService.findById(routersOfCoreRouter, expectedId)?.id

    withClue("Actual id should be present") {
      actualId shouldNotBe null
    }
    actualId shouldBe expectedId
  }

  @Test
  fun `find switch by id`() {
    val location = createLocation("US")
    val switch = createSwitch("30.0.0.0", 8, location)
    val switches = mapOf(switch.id to switch)
    val expectedId = Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")

    val actualId = SwitchService.findById(switches, expectedId)?.id

    withClue("Actual id should be present") {
      actualId shouldNotBe null
    }
    actualId shouldBe expectedId
  }
}

private fun createNetwork(address: String, cidr: Int): Network = Network(IP(address), "NewNetwork", cidr)

private fun createLocation(country: String): Location =
  Location("Test street", "Test city", "Test state", 0, country, 10f, -10f)

@Suppress("SameParameterValue")
private fun createSwitch(networkAddress: String, networkCidr: Int, location: Location): Switch =
  Switch(
    Id("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"),
    Vendor.CISCO,
    Model.XYZ0004,
    IP("20.0.0.100"),
    location,
    SwitchType.LAYER3,
    listOf(createNetwork(networkAddress, networkCidr))
  )

private fun createEdgeRouter(location: Location, address: String): EdgeRouter =
  EdgeRouter(Id(), Vendor.CISCO, Model.XYZ0002, IP(address), location)

private fun createCoreRouter(location: Location, address: String): CoreRouter =
  CoreRouter(Id(), Vendor.HP, Model.XYZ0001, IP(address), location)