package framework

import domain.vo.*
import framework.adapters.input.generic.SwitchManagementGenericAdapter
import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.Test

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SwitchTest {
  private val switchManagementGenericAdapter: SwitchManagementGenericAdapter = SwitchManagementGenericAdapter()

  private val location: Location = Location("Amos Ln", "Tully", "NY", 13159, "United States", 42.797310F, -76.130750F)

  @Test
  @Order(1)
  fun `retrieve switch`() {
    val switchId = Id("922dbcd5-d071-41bd-920b-00f83eb4bb46")

    val switch = switchManagementGenericAdapter.retrieveSwitch(switchId)

    withClue("Switch should be present") {
      switch.shouldNotBeNull()
    }
  }

  @Test
  @Order(2)
  fun `create and add switch to edge router`() {
    val routerId = Id("b07f5187-2d82-4975-a14b-bdbad9a8ad46")

    val router = switchManagementGenericAdapter.createAndAddSwitchToEdgeRouter(
      Vendor.HP,
      Model.XYZ0004,
      IP("15.0.0.1"),
      location,
      SwitchType.LAYER3,
      routerId
    )

    val switch = router.switches
      .values
      .singleOrNull { it.ip.address == "15.0.0.1" }
    withClue("Switch should be present") {
      switch.shouldNotBeNull()
    }
  }

  @Test
  @Order(3)
  fun `remove switch from edge router`() {
    val switchId = Id("922dbcd5-d071-41bd-920b-00f83eb4bb47")
    val routerId = Id("b07f5187-2d82-4975-a14b-bdbad9a8ad46")

    val router = switchManagementGenericAdapter.removeSwitchFromEdgeRouter(switchId, routerId)

    router.switches[switchId].shouldBeNull()
  }
}