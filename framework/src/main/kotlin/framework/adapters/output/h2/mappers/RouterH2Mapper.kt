package framework.adapters.output.h2.mappers

import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.entity.Router
import domain.entity.Switch
import domain.factory.RouterFactory
import domain.factory.createRouter
import domain.vo.*
import framework.adapters.output.h2.data.*
import java.util.*

fun RouterData.toDomain(): Router {
  val vendor = Vendor.valueOf(vendor.name)
  val model = Model.valueOf(model.name)
  val ip = IP(ip.address)
  val location = location.toDomain()
  val id = Id(id.toString())
  return when (type) {
    RouterTypeData.CORE -> {
      val router = RouterFactory.createRouter<CoreRouter>(vendor, model, ip, location, id)
      val subRouters = routers.map { it.toDomain() }
        .associateBy { it.id }
      router.copy(routers = subRouters)
    }

    RouterTypeData.EDGE -> {
      val router = RouterFactory.createRouter<EdgeRouter>(vendor, model, ip, location, id)
      val subSwitches = switches.map { it.toDomain() }
        .associateBy { it.id }
      router.copy(switches = subSwitches)
    }
  }
}

fun SwitchData.toDomain(): Switch =
  Switch(
    Id(id.toString()),
    Vendor.valueOf(vendor.name),
    Model.valueOf(model.name),
    IP(ip.address),
    location.toDomain(),
    SwitchType.valueOf(type.name),
    networks.map { it.toDomain() }
  )

private fun LocationData.toDomain(): Location = Location(address, city, state, zipCode, country, latitude, longitude)

private fun NetworkData.toDomain(): Network = Network(IP(ip.address), name, cidr)

fun Router.toData(parentCoreId: UUID? = null): RouterData =
  when (this) {
    is CoreRouter -> toData(parentCoreId)
    is EdgeRouter -> toData(id.id)
  }

private fun CoreRouter.toData(parentCoreId: UUID? = null): RouterData =
  RouterData(
    id.id,
    parentCoreId,
    VendorData.valueOf(vendor.name),
    ModelData.valueOf(model.name),
    IPData(ip.address),
    location.toData(),
    RouterTypeData.CORE,
    listOf(),
    routers.values.map { it.toData(id.id) }
  )

private fun EdgeRouter.toData(parentCoreId: UUID): RouterData =
  RouterData(
    id.id,
    parentCoreId,
    VendorData.valueOf(vendor.name),
    ModelData.valueOf(model.name),
    IPData(ip.address),
    location.toData(),
    RouterTypeData.EDGE,
    switches.values.map { it.toData(id.id) },
    listOf()
  )

fun Switch.toData(routerId: UUID): SwitchData =
  SwitchData(
    id.id,
    routerId,
    VendorData.valueOf(vendor.name),
    ModelData.valueOf(model.name),
    SwitchTypeData.valueOf(type.name),
    networks.map { it.toData(id.id) },
    IPData(ip.address),
    location.toData()
  )

private fun Location.toData(): LocationData = LocationData(address, city, state, zipCode, country, latitude, longitude)

private fun Network.toData(switchId: UUID): NetworkData = NetworkData(switchId, IPData(ip.address), name, cidr)